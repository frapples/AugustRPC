package io.github.frapples.augustrpc.utils.exception;


/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class CreatedFailException extends Exception {

    public CreatedFailException(String msg) {
        super(msg);
    }

    public CreatedFailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
