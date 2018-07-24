package io.github.frapples.augustrpc.context.exception;

import io.github.frapples.augustrpc.exception.AugustRpcException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class InitFailException extends AugustRpcException {

    public InitFailException(String msg) {
        super(msg);
    }

}
