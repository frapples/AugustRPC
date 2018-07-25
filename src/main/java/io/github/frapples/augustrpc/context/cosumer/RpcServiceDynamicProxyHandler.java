package io.github.frapples.augustrpc.context.cosumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class RpcServiceDynamicProxyHandler<T> implements InvocationHandler {

    private final Class<T> clazz;

    RpcServiceDynamicProxyHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
