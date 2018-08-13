package io.github.frapples.augustrpc.ref.exception;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class AugustRpcException extends Exception {

    public AugustRpcException() {
    }

    public AugustRpcException(String msg) {
        super(msg);
    }

    public AugustRpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AugustRpcException(Throwable cause) {
        super(cause);
    }

}
