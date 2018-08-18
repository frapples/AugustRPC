package io.github.frapples.augustrpc.ref.exception;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class AugustRpcRuntimeException extends RuntimeException {

    public AugustRpcRuntimeException() {
        super();
    }

    public AugustRpcRuntimeException(String msg) {
        super(msg);
    }

    public AugustRpcRuntimeException(Throwable cause) {
        super(cause);
    }

    public AugustRpcRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
