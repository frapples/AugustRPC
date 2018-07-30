package io.github.frapples.augustrpc.service.consumer;

import io.github.frapples.augustrpc.filter.FilterChainContext;
import io.github.frapples.augustrpc.transport.model.CallId;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class RpcServiceDynamicProxyHandler<T> implements InvocationHandler {

    private final static Logger log = LoggerFactory.getLogger(RpcServiceDynamicProxyHandler.class);

    private final FilterChainContext filterChainContext;

    private final Class<T> clazz;

    RpcServiceDynamicProxyHandler(FilterChainContext filterChainContext, Class<T> clazz) {
        this.filterChainContext = filterChainContext;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.warn("Invoked, class: {}, method: {}, args: {}", this.clazz.getName(), method.getName(), args);

        args = args == null ? new Object[0] : args;
        CallId callId = CallId.of(clazz, method);
        Request request = new Request(callId, args);
        Response response = this.filterChainContext.sendRequest(request);
        return response.getReturnResult();
    }
}
