package io.github.frapples.augustrpc.transport.provider;

import io.github.frapples.augustrpc.exception.CreatedFailException;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.service.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.provider.invoker.ServiceInvoker;
import io.github.frapples.augustrpc.transport.provider.networklistener.NetworkListener;
import io.github.frapples.augustrpc.transport.provider.networklistener.NetworkListenerFactory;
import java.util.Random;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class ProviderTransportContext {

    private final RegistryManager registryManager;
    private final ProtocolInterface protocolInterface;
    private final ProviderRpcContext providerRpcContext;

    private final NetworkListener networkListener;
    private final ListeningThread requestHandlerThread;
    private final ServiceInvoker serviceInvoker;
    private final ProviderIdentifier providerIdentifier;

    public ProviderTransportContext(RegistryManager registryManager, ProviderRpcContext providerRpcContext, ProtocolInterface protocolInterface,
        String networkListenerClassName) throws CreatedFailException {
        this.registryManager = registryManager;
        this.protocolInterface = protocolInterface;
        this.providerRpcContext = providerRpcContext;
        this.serviceInvoker = new ServiceInvoker(providerRpcContext);
        int port = (new Random()).nextInt(10000) + 10000;
        this.networkListener = NetworkListenerFactory.createFromClass(networkListenerClassName);
        this.providerIdentifier = new ProviderIdentifier("127.0.0.1", port);
        this.requestHandlerThread = new ListeningThread(this.providerIdentifier, this.networkListener);
    }

    public void init() {

        this.networkListener.init(this.serviceInvoker, this.providerIdentifier, this.protocolInterface);
        Class<?>[] serviceTypes = this.providerRpcContext.getAllBeanTypesWithAugustRpcService();
        for (Class<?> clazz : serviceTypes) {
            this.registryManager.addProvider(clazz, providerIdentifier);
        }

        this.requestHandlerThread.start();
    }
}
