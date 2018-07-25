package io.github.frapples.augustrpc.transport.consumer;

import io.github.frapples.augustrpc.transport.consumer.RequestQueue.QueueItem;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class RequestHandlerThread extends Thread {

    private final RequestQueue<Request, Response> requestQueue;
    private volatile boolean stop = false;

    RequestHandlerThread(RequestQueue<Request, Response> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!stop) {
            QueueItem<Request, Response> item = null;
            try {
                item = requestQueue.poll();
                this.handleRequest(item.getData(), item::complete);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopMe() {
        stop = true;
    }

    private void handleRequest(Object request, Consumer<Response> onComplete) {
        System.out.println("request");
        onComplete.accept(null);
    }
}
