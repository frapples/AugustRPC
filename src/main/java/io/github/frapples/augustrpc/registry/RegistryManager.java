package io.github.frapples.augustrpc.registry;

import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.consumer.model.Request;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26 This is a simple implementation for dev and test now
 */
public class RegistryManager {

    public ProviderIdentifier getProvider(Request request) {
        // TODO
        return new ProviderIdentifier("127.0.0.1", 4567);
    }
}
