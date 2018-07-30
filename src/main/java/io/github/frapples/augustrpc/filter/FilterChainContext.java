package io.github.frapples.augustrpc.filter;


import io.github.frapples.augustrpc.filter.impl.SendedFilterImpl;
import io.github.frapples.augustrpc.transport.consumer.exception.RequestFailException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/30
 */
public class FilterChainContext {

    private final Filter filterChain;

    public FilterChainContext(String[] customFilters) {
        filterChain = new SendedFilterImpl(null);
    }

    public Response sendRequest(Request request) throws RequestFailException {
        return this.filterChain.handle(request);
    }

}
