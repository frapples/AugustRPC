package io.github.frapples.augustrpc.service;

import io.github.frapples.augustrpc.filter.FilterChainContext;
import io.github.frapples.augustrpc.plugin.PluginManager;
import io.github.frapples.augustrpc.plugin.exception.PluginLoadedFailException;
import io.github.frapples.augustrpc.service.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.service.exception.AugustRpcInitFailException;
import io.github.frapples.augustrpc.service.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.service.iocbridge.IocBridge;
import io.github.frapples.augustrpc.protocol.JsonProtocolImpl;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.ConsumerTransportContext;
import io.github.frapples.augustrpc.transport.provider.ProviderTransportContext;
import java.io.IOException;
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
    private volatile PluginManager pluginManager;

    private final Config config;

    public static synchronized void init(Config config) throws AugustRpcInitFailException {
        if (instance == null) {
            RpcContext.instance = new RpcContext(config);
        }
    }

    public static synchronized RpcContext getInstance() {
        return instance;
    }

    private RpcContext(Config config) throws AugustRpcInitFailException {
        log.info("Initializing August RPC with config {}...", config);
        this.config = config;
        log.info("Initializing RegistryManager");
        this.registryManager = new RegistryManager();
        log.info("Initializing ProtocolInterface");
        this.protocolInterface = new JsonProtocolImpl();

        try {
            initPluginManager();
            log.info("Initializing provider side...", config);
            initProvider();
            log.info("Initializing consumer side...", config);
            initConsumer();
        } catch (PluginLoadedFailException | IOException e) {
            throw new AugustRpcInitFailException(e);
        }
    }

    private void initPluginManager() throws IOException {
        this.pluginManager = new PluginManager();
    }


    private void initProvider() throws AugustRpcInitFailException {
        if (this.providerRpcContext != null) {
            return;
        }

        try {
            log.info("Initializing IocBridge");
            IocBridge iocBridge = this.pluginManager.getIocBridge(this.config.getIocBridge());

            log.info("Initializing ProviderRpcContext");
            this.providerRpcContext = new ProviderRpcContext(iocBridge);
            log.info("Initializing ProviderTransportContext");
            ProviderTransportContext providerTransportContext = new ProviderTransportContext(
                this.registryManager, this.pluginManager,
                this.providerRpcContext, this.protocolInterface, this.config.getNetworkListener());
            providerTransportContext.init();
        } catch (PluginLoadedFailException e) {
            throw new AugustRpcInitFailException(e);
        }
    }

    private void initConsumer() throws PluginLoadedFailException {
        if (this.consumerRpcContext != null) {
            return;
        }

        log.info("Initializing FilterChainContext");
        FilterChainContext filterChainContext = new FilterChainContext(new String[]{});
        log.info("Initializing ConsumerRpcContext");
        this.consumerRpcContext = new ConsumerRpcContext(filterChainContext);
        log.info("Initializing ConsumerTransportContext");
        this.consumerTransportContext = new ConsumerTransportContext(
            this.registryManager,
            this.pluginManager,
            this.protocolInterface,
            config.getRequestSender());
        this.consumerTransportContext.init();
    }

    public ConsumerTransportContext getConsumerTransportContext() {
        return consumerTransportContext;
    }


    public ConsumerRpcContext getConsumerRpcContext() {
        return consumerRpcContext;
    }

    public PluginManager pluginManager() {
        return pluginManager;
    }

    public Config getConfig() {
        return config;
    }

}
