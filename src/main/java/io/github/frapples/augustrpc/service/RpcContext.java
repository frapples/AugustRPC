package io.github.frapples.augustrpc.service;

import io.github.frapples.augustrpc.filter.FilterChainContext;
import io.github.frapples.augustrpc.service.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.service.exception.InitFailException;
import io.github.frapples.augustrpc.service.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.exception.CreatedFailException;
import io.github.frapples.augustrpc.service.iocbridge.IocBridge;
import io.github.frapples.augustrpc.service.iocbridge.IocBridgeFactory;
import io.github.frapples.augustrpc.protocol.JsonProtocolImpl;
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

    private volatile FilterChainContext filterChainContext;

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
        this.protocolInterface = new JsonProtocolImpl();

        try {
            log.info("Initializing provider side...", config);
            initProvider();
            log.info("Initializing consumer side...", config);
            initConsumer();
        } catch (CreatedFailException originException) {
            InitFailException e = new InitFailException();
            e.addSuppressed(originException);
            throw e;
        }
    }


    private void initProvider() throws InitFailException, CreatedFailException {
        if (this.providerRpcContext != null) {
            return;
        }

        log.info("Initializing IocBridge");
        this.iocBridge = IocBridgeFactory.createFromClass(this.config.getIocBridgeImplClassName());

        log.info("Initializing ProviderRpcContext");
        this.providerRpcContext = new ProviderRpcContext(this.iocBridge);
        log.info("Initializing ProviderTransportContext");
        this.providerTransportContext = new ProviderTransportContext(
            this.registryManager, this.providerRpcContext, this.protocolInterface, this.config.getNetworkListenerImplClassName());
        this.providerTransportContext.init();
    }

    private void initConsumer() throws CreatedFailException {
        if (this.consumerRpcContext != null) {
            return;
        }

        log.info("Initializing FilterChainContext");
        this.filterChainContext = new FilterChainContext(new String[]{});
        log.info("Initializing ConsumerRpcContext");
        this.consumerRpcContext = new ConsumerRpcContext(this.filterChainContext);
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


    public ConsumerRpcContext getConsumerRpcContext() {
        return consumerRpcContext;
    }

    public Config getConfig() {
        return config;
    }

}
