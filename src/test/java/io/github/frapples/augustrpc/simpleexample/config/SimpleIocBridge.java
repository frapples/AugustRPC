package io.github.frapples.augustrpc.simpleexample.config;

import io.github.frapples.augustrpc.service.iocbridge.IocBridge;
import io.github.frapples.augustrpc.simpleexample.rpcinterface.ProviderDemoService;
import io.github.frapples.augustrpc.simpleexample.provider.ProviderDemoServiceImpl;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class SimpleIocBridge implements IocBridge {

    @Override
    public <T> T getBean(Class<T> clazz) {
        if (clazz == ProviderDemoService.class) {
            return (T) new ProviderDemoServiceImpl();
        }
        return null;
    }

    @Override
    public Class<?>[] getAllBeanTypes(Class<?> annotation) {
        return getAllBeanTypesWithAugustRpcService();
    }

    @Override
    public Class<?>[] getAllBeanTypesWithAugustRpcService() {
        return new Class<?>[]{ ProviderDemoService.class};
    }

}
