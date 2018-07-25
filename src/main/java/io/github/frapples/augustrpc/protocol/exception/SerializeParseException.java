package io.github.frapples.augustrpc.protocol.exception;

import io.github.frapples.augustrpc.exception.AugustRpcRuntimeException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class SerializeParseException extends AugustRpcRuntimeException {

    public SerializeParseException(String msg) {
        super(msg);
    }
}
