package io.github.frapples.augustrpc.transport.consumer;

import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.exception.NoSuitableProviderException;
import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.consumer.model.RequestQueue;
import io.github.frapples.augustrpc.transport.consumer.model.RequestQueue.QueueItem;
import io.github.frapples.augustrpc.transport.consumer.model.Request;
import io.github.frapples.augustrpc.transport.consumer.model.Response;
import io.github.frapples.augustrpc.transport.consumer.sender.RequestSender;
import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class RequestHandlerThread extends Thread {

    private final RequestQueue<Request, Response> requestQueue;
    private final RequestSender requestSender;
    private final RegistryManager registryManager;
    private volatile boolean stop = false;

    RequestHandlerThread(RequestQueue<Request, Response> requestQueue, RequestSender requestSender, RegistryManager registryManager) {
        this.requestQueue = requestQueue;
        this.requestSender = requestSender;
        this.registryManager = registryManager;
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

    private void handleRequest(Request request, BiConsumer<Response, Throwable> onComplete) {
        ProviderIdentifier providerIdentifier = this.registryManager.getProvider(request);
        if (providerIdentifier == null) {
            onComplete.accept(null, new NoSuitableProviderException(
                String.format("Service class: %s", request.getServiceFullyQualifiedName())));
        }

        // TODO
        byte[] data = new byte[0];
        this.requestSender.send(providerIdentifier, data, (result, e) -> {
            onComplete.accept(null, e);
        });
    }
}
