package io.github.frapples.augustrpc.ref.exception;


public enum ErrorCode {
    NO_SUITABLE_PROVIDER(1, "NO_SUITABLE_PROVIDER"),
    IO_ERROR(2, "IO_ERROR"),
    DATA_BROKEN(3, "DATA_BROKEN"),
    INVOKE_FAIL(5, "INVOKE_FAIL"),
    UNKNOWN(6, "UNKNOWN");

    private int code;
    private String stringCode;

    ErrorCode(int code, String stringCode) {
    }

    public int getCode() {
        return code;
    }

    public String getStringCode() {
        return stringCode;
    }
}
