package io.github.frapples.augustrpc.filter.impl;

import io.github.frapples.augustrpc.filter.BaseFilter;
import io.github.frapples.augustrpc.transport.model.CallId;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/30
 */
public class TimeLogFilterImpl extends BaseFilter {

    private final static Logger log = LoggerFactory.getLogger(TimeLogFilterImpl.class);

    public TimeLogFilterImpl(BaseFilter next) {
        super(next);
    }

    @Override
    public Response handle(Request request) {
        long start = System.currentTimeMillis();

        RuntimeException except = null;
        Response response = null;
        try {
            response = this.getNext().handle(request);
        } catch (RuntimeException e) {
            except = e;
        }

        long end = System.currentTimeMillis();
        CallId callId = request.getCallId();
        log.info("This invoking costs {} ms, {}::{}",
            end - start,
            callId.getServiceFullyQualifiedName(),
            callId.getMethodName());

        if (except != null) {
            throw except;
        } else {
            return response;
        }
    }
}
