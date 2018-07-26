package io.github.frapples.augustrpc.transport.consumer.model;


import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@Immutable
public class Response {
    private final Object returnResult;

    public Response(Object returnResult) {
        this.returnResult = returnResult;
    }

    public Object getReturnResult() {
        return returnResult;
    }
}
