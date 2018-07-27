package io.github.frapples.augustrpc.transport.provider;

import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.provider.networklistener.NetworkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class ListeningThread extends Thread {

    private final static Logger log = LoggerFactory.getLogger(ListeningThread.class);

    private final NetworkListener networkListener;

    private final ProviderIdentifier providerIdentifier;

    private volatile boolean stop = false;

    public ListeningThread(ProviderIdentifier providerIdentifier,
        NetworkListener networkListener) {
        this.networkListener = networkListener;
        this.providerIdentifier = providerIdentifier;
    }

    @Override
    public void run() {
        log.info("Provider listening thread start: {}", this.providerIdentifier);
        this.networkListener.listen();
    }

    public void stopMe() {
        stop = true;
    }

}
