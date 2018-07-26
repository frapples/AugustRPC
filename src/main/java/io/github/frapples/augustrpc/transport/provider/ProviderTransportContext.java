package io.github.frapples.augustrpc.transport.provider;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import java.util.Random;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class ProviderTransportContext {

    private final RegistryManager registryManager;
    private final IocBridge iocBridge;

    private final ListeningThread requestHandlerThread;
    private final ProviderIdentifier providerIdentifier;

    public ProviderTransportContext(RegistryManager registryManager, IocBridge iocBridge) {
        this.registryManager = registryManager;
        this.iocBridge = iocBridge;
        int port = (new Random()).nextInt(10000) + 10000;
        this.providerIdentifier = new ProviderIdentifier("127.0.0.1", port);
        this.requestHandlerThread = new ListeningThread(iocBridge, this.providerIdentifier);
    }

    public void init() {

        Class<?>[] serviceTypes = this.iocBridge.getAllBeanTypesWithAugustRpcService();
        for (Class<?> clazz : serviceTypes) {
            this.registryManager.addProvider(clazz, providerIdentifier);
        }

        this.requestHandlerThread.start();
    }
}
