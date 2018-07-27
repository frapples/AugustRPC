package io.github.frapples.augustrpc.transport.provider;

import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.registry.RegistryManager;
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
    private final IocBridge iocBridge;
    private final ProtocolInterface protocolInterface;

    private final NetworkListener networkListener;
    private final ListeningThread requestHandlerThread;
    private final ServiceInvoker serviceInvoker;
    private final ProviderIdentifier providerIdentifier;

    public ProviderTransportContext(RegistryManager registryManager, IocBridge iocBridge,
        ProtocolInterface protocolInterface, String networkListenerClassName) throws InitFailException {
        this.registryManager = registryManager;
        this.iocBridge = iocBridge;
        this.protocolInterface = protocolInterface;
        this.serviceInvoker = new ServiceInvoker(this.iocBridge);
        int port = (new Random()).nextInt(10000) + 10000;
        try {
            this.networkListener = NetworkListenerFactory.createFromClass(networkListenerClassName);
        } catch (CreatedFailException e) {
            throw new InitFailException(e.getMessage());
        }
        this.providerIdentifier = new ProviderIdentifier("127.0.0.1", port);
        this.requestHandlerThread = new ListeningThread(this.iocBridge, this.providerIdentifier, this.networkListener);
    }

    public void init() {

        this.networkListener.init(this.serviceInvoker, this.providerIdentifier, this.protocolInterface);
        Class<?>[] serviceTypes = this.iocBridge.getAllBeanTypesWithAugustRpcService();
        for (Class<?> clazz : serviceTypes) {
            this.registryManager.addProvider(clazz, providerIdentifier);
        }

        this.requestHandlerThread.start();
    }
}
