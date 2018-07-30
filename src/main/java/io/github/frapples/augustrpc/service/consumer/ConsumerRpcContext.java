package io.github.frapples.augustrpc.service.consumer;

import io.github.frapples.augustrpc.filter.FilterChainContext;
import io.github.frapples.augustrpc.service.annotation.AugustRpcService;
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

    private final FilterChainContext filterChainContext;

    public ConsumerRpcContext(FilterChainContext filterChainContext) {
        this.filterChainContext = filterChainContext;
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
            new RpcServiceDynamicProxyHandler<>(filterChainContext, clazz));
        return (T) object;
    }
}
