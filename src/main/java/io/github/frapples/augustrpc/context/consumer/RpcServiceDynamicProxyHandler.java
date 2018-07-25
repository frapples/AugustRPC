package io.github.frapples.augustrpc.context.consumer;

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

    private final Class<T> clazz;

    RpcServiceDynamicProxyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.warn("Invoked, class: method: args:", this.clazz.getName(), method.getName(), args);
        return null;
    }
}
