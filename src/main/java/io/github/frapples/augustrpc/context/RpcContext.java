package io.github.frapples.augustrpc.context;

import io.github.frapples.augustrpc.context.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.context.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.iocbridge.IocBridgeFactory;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.ConsumerTransportContext;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@ThreadSafe
public class RpcContext {

    private static volatile RpcContext instance = null;

    private volatile ProviderRpcContext providerRpcContext;
    private volatile ConsumerRpcContext consumerRpcContext;

    private volatile ConsumerTransportContext consumerTransportContext;

    private volatile RegistryManager registryManager;

    private final Config config;

    public static synchronized void init(Config config) throws InitFailException {
        if (instance == null) {
            RpcContext.instance = new RpcContext(config);
        }
    }

    public static synchronized RpcContext getInstance() {
        return instance;
    }

    private RpcContext(Config config) throws InitFailException {
        this.config = config;
        this.registryManager = new RegistryManager();
        initProvider();
        initConsumer();
    }


    private void initProvider() throws InitFailException {
        if (this.providerRpcContext != null) {
            return;
        }

        IocBridge iocBridge;
        try {
            iocBridge = IocBridgeFactory.createFromClass(this.config.getIocBridgeImplClassName());
        } catch (CreatedFailException e) {
            throw new InitFailException(e.getMessage());
        }

        this.providerRpcContext = new ProviderRpcContext(iocBridge);
        this.consumerTransportContext = new ConsumerTransportContext(config.getRequestSenderImplClassName(), this.registryManager);
        this.consumerTransportContext.init();
    }

    private void initConsumer() {
        if (this.consumerRpcContext != null) {
            return;
        }

        this.consumerRpcContext = new ConsumerRpcContext();
    }

    public ConsumerTransportContext getConsumerTransportContext() {
        return consumerTransportContext;
    }


    public ProviderRpcContext getProviderRpcContext() {
        return providerRpcContext;
    }

    public ConsumerRpcContext getConsumerRpcContext() {
        return consumerRpcContext;
    }

    public RegistryManager getRegistryManager() {
        return registryManager;
    }

}
