package io.github.frapples.augustrpc.transport.provider;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class ListeningThread extends Thread {

    private final IocBridge iocBridge;

    private final ProviderIdentifier providerIdentifier;

    private volatile boolean stop = false;

    public ListeningThread(IocBridge iocBridge, ProviderIdentifier providerIdentifier) {
        this.iocBridge = iocBridge;
        this.providerIdentifier = providerIdentifier;
    }

    @Override
    public void run() {
    }

    public void stopMe() {
        stop = true;
    }

}
