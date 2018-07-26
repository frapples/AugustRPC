package io.github.frapples.augustrpc.context;

import io.github.frapples.augustrpc.context.annotation.AugustRpcService;
import io.github.frapples.augustrpc.context.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.context.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.iocbridge.IocBridgeFactory;
import io.github.frapples.augustrpc.protocol.JsonProtocol;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.ConsumerTransportContext;
import io.github.frapples.augustrpc.transport.provider.ProviderTransportContext;
import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@ThreadSafe
public class RpcContext {

    private final static Logger log = LoggerFactory.getLogger(RpcContext.class);

    private static volatile RpcContext instance = null;

    private volatile ProviderRpcContext providerRpcContext;
    private volatile ConsumerRpcContext consumerRpcContext;

    private volatile ConsumerTransportContext consumerTransportContext;
    private volatile ProtocolInterface protocolInterface;
    private volatile ProviderTransportContext providerTransportContext;

    private volatile IocBridge iocBridge;
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
        log.info("Initializing August RPC with config {}...", config);
        this.config = config;
        log.info("Initializing RegistryManager");
        this.registryManager = new RegistryManager();
        log.info("Initializing ProtocolInterface");
        this.protocolInterface = new JsonProtocol();

        log.info("Initializing provider side...", config);
        initProvider();
        log.info("Initializing consumer side...", config);
        initConsumer();
    }


    private void initProvider() throws InitFailException {
        if (this.providerRpcContext != null) {
            return;
        }

        log.info("Initializing IocBridge");
        try {
            this.iocBridge = IocBridgeFactory.createFromClass(this.config.getIocBridgeImplClassName());
        } catch (CreatedFailException e) {
            throw new InitFailException(e.getMessage());
        }

        log.info("Initializing ProviderRpcContext");
        this.providerRpcContext = new ProviderRpcContext(this.iocBridge);
        log.info("Initializing ProviderTransportContext");
        this.providerTransportContext = new ProviderTransportContext(
            this.registryManager, this.iocBridge, this.protocolInterface, this.config.getNetworkListenerImplClassName());
        this.providerTransportContext.init();
    }

    private void initConsumer() throws InitFailException {
        if (this.consumerRpcContext != null) {
            return;
        }

        log.info("Initializing ConsumerRpcContext");
        this.consumerRpcContext = new ConsumerRpcContext();
        log.info("Initializing ConsumerTransportContext");
        this.consumerTransportContext = new ConsumerTransportContext(
            config.getRequestSenderImplClassName(),
            this.registryManager,
            this.protocolInterface);
        this.consumerTransportContext.init();
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

}
