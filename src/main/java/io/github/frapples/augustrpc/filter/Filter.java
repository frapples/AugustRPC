package io.github.frapples.augustrpc.filter;

import io.github.frapples.augustrpc.transport.consumer.exception.RequestFailException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/30
 */
public abstract class Filter {

    private final Filter next;

    protected Filter() {
        this.next = null;
        throw new UnsupportedOperationException();
    }

    public Filter(Filter next) {
        this.next = next;
    }

    protected Filter getNext() {
        return next;
    }


    public abstract Response handle(Request request) throws RequestFailException;

}
