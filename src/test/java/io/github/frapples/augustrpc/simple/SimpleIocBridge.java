package io.github.frapples.augustrpc.simple;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.simple.service.SimpleService;
import io.github.frapples.augustrpc.simple.service.SimpleServiceImpl;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class SimpleIocBridge implements IocBridge {

    @Override
    public <T> T getBean(Class<T> clazz) {
        if (clazz == SimpleService.class) {
            return (T) new SimpleServiceImpl();
        }
        return null;
    }

    @Override
    public Class<?>[] getAllBeanTypes(Class<?> annotation) {
        return getAllBeanTypesWithAugustRpcService();
    }

    @Override
    public Class<?>[] getAllBeanTypesWithAugustRpcService() {
        return new Class<?>[]{ SimpleIocBridge.class};
    }

}
