package io.github.frapples.augustrpc.context;

import io.github.frapples.augustrpc.context.exception.RpcServiceTypeErrorException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
@ThreadSafe
public class ProviderRpcContext {

    private final ConcurrentHashMap<Class<?>, Object> services = new ConcurrentHashMap<>();
    private final IocBridge iocBridge;


    public ProviderRpcContext(IocBridge iocBridge) {
        this.iocBridge = iocBridge;
    }

    public void putServices(Map<Class<?>, Object> services) {
        for (Entry<Class<?>, Object> item : services.entrySet()) {
            Class<?> clazz = item.getKey();
            Object service = item.getValue();
            putService(clazz, service);
        }
    }

    public void putService(Class<?> clazz, Object service) {
        if (clazz == null || service == null) {
            return;
        }

        if (clazz.isAssignableFrom(service.getClass())) {
            throw new RpcServiceTypeErrorException(
                String.format("Required: %s, Actual: %s", clazz.getName(), service.getClass().getName()));
        }

        this.services.put(clazz, service);
    }

    public <T> T getService(Class<T> clazz) {
        Object service = services.get(clazz);

        if (service == null) {
            service = iocBridge.getBean(clazz);
            this.putService(clazz, service);
        }

        if (clazz.isAssignableFrom(service.getClass())) {
            return (T) service;
        } else {
            throw new RpcServiceTypeErrorException(
                String.format("Required: %s, Actual: %s", clazz.getName(), service.getClass().getName()));
        }
    }

}
