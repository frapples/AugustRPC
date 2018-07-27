package io.github.frapples.augustrpc.transport.consumer;

import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.registry.RegistryManager;
import io.github.frapples.augustrpc.transport.consumer.exception.RequestFailException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.RequestQueue;
import io.github.frapples.augustrpc.transport.model.Response;
import io.github.frapples.augustrpc.transport.consumer.sender.RequestSender;
import io.github.frapples.augustrpc.transport.consumer.sender.RequestSenderFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class ConsumerTransportContext {

    private final Thread requestHandlerThread;

    private final RequestQueue<Request, Response> requestQueue;

    public ConsumerTransportContext(String requestSenderClassName, RegistryManager registryManager,
        ProtocolInterface protocolInterface) throws InitFailException {

        RequestSender requestSender;
        try {
            requestSender = RequestSenderFactory.createFromClass(requestSenderClassName);
        } catch (CreatedFailException e) {
            throw new InitFailException(e.getMessage());
        }
        this.requestQueue = new RequestQueue<>(1000);
        this.requestHandlerThread = new RequestHandlerThread(
            this.requestQueue, requestSender, registryManager, protocolInterface);
    }

    public void init() {
        this.requestHandlerThread.start();
    }

    public Response sendCallMessage(Request request) throws RequestFailException {
        return this.requestQueue.add(request);
    }

}
