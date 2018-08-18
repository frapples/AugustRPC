package io.github.frapples.augustrpc.transport.model;


import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@Immutable
public class Response {
    private final Object returnResult;
    private final Throwable exception;

    public Response(Object returnResult) {
        this(returnResult, null);
    }

    public Response(Object returnResult, Throwable exception) {
        this.returnResult = returnResult;
        this.exception = exception;
    }

    public Object getReturnResult() {
        return returnResult;
    }

    public boolean hasException() {
        return this.exception != null;
    }
}
