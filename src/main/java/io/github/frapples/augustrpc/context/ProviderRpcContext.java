package io.github.frapples.augustrpc.context;

import io.github.frapples.augustrpc.context.annotation.AugustRpcProvider;
import io.github.frapples.augustrpc.context.annotation.AugustRpcService;
import io.github.frapples.augustrpc.context.exception.RpcServiceTypeErrorException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
@ThreadSafe
public class ProviderRpcContext {

    private final Logger log = LoggerFactory.getLogger(ProviderRpcContext.class);

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

    /**
     * @param clazz An interface that is annotated by AugustRpcService
     * @return Return a implementing object of the given interface, and the object is annotated by AugustRpcProvider
     */
    public <T> T getService(Class<T> clazz) {
        if (clazz.getAnnotation(AugustRpcService.class) == null) {
            log.warn("AugustRpc service should be annotated by AugustRpcService, interface: {}", clazz.getName());
            return null;
        }

        Object service;
        synchronized (this) {
            service = services.get(clazz);
            if (service == null) {
                service = iocBridge.getBean(clazz);
                this.putService(clazz, service);
            }
        }

        if (service == null) {
            return null;
        }

        AugustRpcProvider annotation = service.getClass().getAnnotation(AugustRpcProvider.class);
        if (annotation == null) {
            log.warn("Implementation of AugustRpc service should be annotated by AugustRpcProvider, class: {}",
                service.getClass().getName());
            return null;
        }

        if (!clazz.isAssignableFrom(service.getClass())) {
            throw new RpcServiceTypeErrorException(
                String.format("Required: %s, Actual: %s", clazz.getName(), service.getClass().getName()));
        }

        return (T) service;
    }

}
