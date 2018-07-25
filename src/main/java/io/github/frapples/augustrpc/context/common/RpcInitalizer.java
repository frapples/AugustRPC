package io.github.frapples.augustrpc.context.common;

import io.github.frapples.augustrpc.context.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.context.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.iocbridge.IocBridgeFactory;

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

        IocBridge iocBridge;
        try {
            iocBridge = IocBridgeFactory.createFromClass(this.config.getIocBridgeImplClassName());
        } catch (CreatedFailException e) {
            throw new InitFailException(e.getMessage());
        }

        Environment.providerRpcContext = new ProviderRpcContext(iocBridge);
    }

    private void initConsumer() {
        if (Environment.consumerRpcContext != null) {
            return;
        }

        Environment.consumerRpcContext = new ConsumerRpcContext();
    }
}
