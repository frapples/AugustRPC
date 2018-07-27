package io.github.frapples.augustrpc.transport.model;


import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@Immutable
public class Request {

    /**
     * Identifies which method is invoked
     */
    private final CallId callId;
    /**
     * When the function is called, the arguments passed in
     */
    private final Object[] arguments;

    public Request(CallId callId, Object[] arguments) {
        if (callId == null || arguments == null) {
            throw new IllegalArgumentException();
        }

        this.callId = callId;
        this.arguments = arguments;
    }


    public CallId getCallId() {
        return callId;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
