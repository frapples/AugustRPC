package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public interface RequestSender {

    void send(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<Byte[], Throwable> onComplete);
}
