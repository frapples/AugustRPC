package io.github.frapples.augustrpc.filter;

import io.github.frapples.augustrpc.transport.consumer.exception.RequestFailException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/30
 */
public abstract class BaseFilter {

    private final BaseFilter next;

    protected BaseFilter() {
        this.next = null;
        throw new UnsupportedOperationException();
    }

    public BaseFilter(BaseFilter next) {
        this.next = next;
    }

    protected BaseFilter getNext() {
        return next;
    }


    public abstract Response handle(Request request) throws RequestFailException;

}
