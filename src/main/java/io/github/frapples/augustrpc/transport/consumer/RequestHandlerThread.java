package io.github.frapples.augustrpc.transport.consumer;

import io.github.frapples.augustrpc.protocol.JsonProtocolImpl;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.exception.NoSuitableProviderException;
import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.model.RequestQueue;
import io.github.frapples.augustrpc.transport.model.RequestQueue.QueueItem;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import io.github.frapples.augustrpc.transport.consumer.sender.RequestSender;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class RequestHandlerThread extends Thread {

    private final static Logger log = LoggerFactory.getLogger(RequestHandlerThread.class);

    private final RequestSender requestSender;
    private final RegistryManager registryManager;
    private final ProtocolInterface protocolInterface;

    private final RequestQueue<Request, Response> requestQueue;
    private volatile boolean stop = false;


    RequestHandlerThread(RequestQueue<Request, Response> requestQueue, RequestSender requestSender, RegistryManager registryManager,
        ProtocolInterface protocolInterface) {
        this.requestQueue = requestQueue;
        this.requestSender = requestSender;
        this.registryManager = registryManager;
        this.protocolInterface = protocolInterface;
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

        log.info("Handle Request, provider: {}, service class: {}, method: {}",
            providerIdentifier,
            request.getCallId().getServiceFullyQualifiedName(),
            request.getCallId().getMethodName());

        if (providerIdentifier == null) {
            onComplete.accept(null, new NoSuitableProviderException(
                String.format("Service class: %s", request.getCallId().getServiceFullyQualifiedName())));
            return;
        }

        byte[] data = protocolInterface.packRequest(request);

        if (protocolInterface instanceof JsonProtocolImpl) {
            log.info("Request packed bytes (to String): {}", new String(data, StandardCharsets.UTF_8));
        }

        this.requestSender.send(providerIdentifier, data, (result, e) -> {
            if (e == null) {
                try {
                    Response response = this.protocolInterface.unpackResponse(result);
                    onComplete.accept(response, null);
                } catch (SerializeParseException e1) {
                    onComplete.accept(null, e1);
                }

            } else {
                onComplete.accept(null, e);
            }
        });
    }
}
