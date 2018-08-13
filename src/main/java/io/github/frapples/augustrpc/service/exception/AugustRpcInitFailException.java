package io.github.frapples.augustrpc.service.exception;

import io.github.frapples.augustrpc.ref.exception.AugustRpcException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class AugustRpcInitFailException extends AugustRpcException {

    public AugustRpcInitFailException() {
    }

    public AugustRpcInitFailException(String msg) {
        super(msg);
    }

    public AugustRpcInitFailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AugustRpcInitFailException(Throwable cause) {
        super(cause);
    }
}
