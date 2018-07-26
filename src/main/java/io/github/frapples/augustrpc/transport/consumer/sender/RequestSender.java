package io.github.frapples.augustrpc.transport.consumer.sender;

import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public interface RequestSender {

    void send(byte[] data, BiConsumer<Byte[], Throwable> onComplete);
}
