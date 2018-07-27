package io.github.frapples.augustrpc.service.exception;

import io.github.frapples.augustrpc.exception.AugustRpcRuntimeException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class RpcServiceTypeErrorException extends AugustRpcRuntimeException {

    public RpcServiceTypeErrorException(String msg) {
        super(msg);
    }
}
