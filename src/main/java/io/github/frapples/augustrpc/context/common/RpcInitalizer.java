package io.github.frapples.augustrpc.context.common;

import io.github.frapples.augustrpc.context.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.context.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.utils.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class RpcInitalizer {

    private final Config config;

    public RpcInitalizer(Config config) {
        this.config = config;
    }

    public void init() throws InitFailException {
        initProvider();
        initConsumer();
    }

    private void initProvider() throws InitFailException {
        if (Environment.providerRpcContext != null) {
            return;
        }
        IocBridge iocBridge = loadIocBridge();
        Environment.providerRpcContext = new ProviderRpcContext(iocBridge);
    }

    private void initConsumer() {
        Environment.consumerRpcContext = new ConsumerRpcContext();
    }

    private IocBridge loadIocBridge() throws InitFailException {
        String className = this.config.getIocBridgeImplClassName();
        if (StringUtils.isEmpty(className)) {
            throw new InitFailException("IocBridge impl class name is empty");
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(this.config.getIocBridgeImplClassName());
        } catch (ClassNotFoundException e) {
            throw new InitFailException("IocBridge impl class not found: " + className);
        }

        if (!IocBridge.class.isAssignableFrom(clazz)) {
            throw new InitFailException(String.format("IocBridge impl class %s is not IocBridge", className));
        }

        IocBridge iocBridge;
        try {
            iocBridge = (IocBridge) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InitFailException(
                String.format("Create instance of IocBridge impl class %s fail, reason: %s", className, e));
        }
        return iocBridge;
    }

}
