package io.github.frapples.augustrpc.filter.impl;

import io.github.frapples.augustrpc.filter.BaseFilter;
import io.github.frapples.augustrpc.service.RpcContext;
import io.github.frapples.augustrpc.transport.consumer.ConsumerTransportContext;
import io.github.frapples.augustrpc.transport.consumer.exception.RequestFailException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/30
 */
public class SendedFilterImpl extends BaseFilter {

    public SendedFilterImpl(BaseFilter next) {
        super(next);
    }

    @Override
    public Response handle(Request request) throws RequestFailException {
        RpcContext rpcContext = RpcContext.getInstance();
        ConsumerTransportContext consumerTransportContext = rpcContext.getConsumerTransportContext();
        return consumerTransportContext.sendCallMessage(request);
    }
}
