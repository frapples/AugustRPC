package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class SimpleRequestSenderImpl implements RequestSender {

    @Override
    public void send(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<Byte[], Throwable> onComplete) {
        /* TODO */
        System.out.println("request");
        onComplete.accept(null, null);
    }
}
