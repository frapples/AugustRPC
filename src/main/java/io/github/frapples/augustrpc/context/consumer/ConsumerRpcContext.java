package io.github.frapples.augustrpc.context.consumer;

import io.github.frapples.augustrpc.context.annotation.AugustRpcService;
import java.lang.reflect.Proxy;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@ThreadSafe
public class ConsumerRpcContext {

    private final Logger log = LoggerFactory.getLogger(ConsumerRpcContext.class);

    public ConsumerRpcContext() {
    }

    public <T> T getService(Class<T> clazz) {
        if (clazz.getAnnotation(AugustRpcService.class) == null) {
            log.warn("AugustRpc service should be annotated by AugustRpcService, interface: {}", clazz.getName());
        }

        return getProxyedObject(clazz);
    }

    private <T> T getProxyedObject(Class<T> clazz) {
        Object object = Proxy.newProxyInstance(
            clazz.getClassLoader(),
            new Class[]{clazz},
            new RpcServiceDynamicProxyHandler<>(clazz));
        return (T) object;
    }
}
