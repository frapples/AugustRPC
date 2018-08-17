package io.github.frapples.augustrpc.plugin.exception;

import io.github.frapples.augustrpc.ref.exception.AugustRpcException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/17
 */
public class PluginLoadedFailException extends AugustRpcException {

    public PluginLoadedFailException() {
    }

    public PluginLoadedFailException(String msg) {
        super(msg);
    }

    public PluginLoadedFailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PluginLoadedFailException(Throwable cause) {
        super(cause);
    }
}
