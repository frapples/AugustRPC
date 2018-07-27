package io.github.frapples.augustrpc.service.iocbridge;

import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
@ThreadSafe
public interface IocBridge {

    <T> T getBean(Class<T> clazz);

    Class<?>[] getAllBeanTypes(Class<?> annotation);

    Class<?>[] getAllBeanTypesWithAugustRpcService();

}
