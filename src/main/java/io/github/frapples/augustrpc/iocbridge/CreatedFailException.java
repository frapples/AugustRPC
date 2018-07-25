package io.github.frapples.augustrpc.iocbridge;

import io.github.frapples.augustrpc.exception.AugustRpcException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class CreatedFailException extends AugustRpcException {

    public CreatedFailException(String msg) {
        super(msg);
    }
}
