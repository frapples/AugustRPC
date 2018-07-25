package io.github.frapples.augustrpc.transport.consumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class ConsumerTransportContext {

    private final Thread requestHandlerThread;

    private final RequestQueue<Request, Response> requestQueue;

    public ConsumerTransportContext() {
        this.requestQueue = new RequestQueue<>(1000);
        this.requestHandlerThread = new RequestHandlerThread(this.requestQueue);
    }

    public void init() {
        this.requestHandlerThread.start();
    }

    public Response sendCallMessage(Request request) throws InterruptedException {
        return this.requestQueue.add(request);
    }

}
