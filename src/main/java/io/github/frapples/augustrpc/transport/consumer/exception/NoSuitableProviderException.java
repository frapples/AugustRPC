package io.github.frapples.augustrpc.transport.consumer.exception;

import io.github.frapples.augustrpc.exception.AugustRpcException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class NoSuitableProviderException extends AugustRpcException {

    public NoSuitableProviderException(String msg) {
        super(msg);
    }
}
