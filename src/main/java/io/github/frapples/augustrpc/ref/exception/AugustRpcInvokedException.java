package io.github.frapples.augustrpc.ref.exception;

public class AugustRpcInvokedException extends AugustRpcRuntimeException {

    private ErrorCode errorCode;

    public AugustRpcInvokedException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public AugustRpcInvokedException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public AugustRpcInvokedException(ErrorCode errorCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.errorCode = errorCode;
    }

    public AugustRpcInvokedException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
