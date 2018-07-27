package io.github.frapples.augustrpc.transport.consumer.exception;


/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class NoSuitableProviderException extends RequestFailException {

    public NoSuitableProviderException() {
    }

    public NoSuitableProviderException(String msg) {
        super(msg);
    }
}
