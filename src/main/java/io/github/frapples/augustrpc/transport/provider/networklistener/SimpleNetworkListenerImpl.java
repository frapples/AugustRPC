package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.consumer.model.Request;
import io.github.frapples.augustrpc.transport.consumer.model.Response;
import io.github.frapples.augustrpc.transport.provider.exception.ReceiverFailException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 *
 * A simple single thread implementation for testing
 */
public class SimpleNetworkListenerImpl implements NetworkListener {

    private static final int BUFFER_SIZE = 2048;

    private IocBridge iocBridge;
    private ProviderIdentifier providerIdentifier;
    private ProtocolInterface protocolInterface;

    @Override
    public void init(IocBridge iocBridge, ProviderIdentifier providerIdentifier, ProtocolInterface protocolInterface) {
        this.iocBridge = iocBridge;
        this.providerIdentifier = providerIdentifier;
        this.protocolInterface = protocolInterface;
    }

    @Override
    public void listen() throws ReceiverFailException {
        try (ServerSocket serverSocket = new ServerSocket(providerIdentifier.getPort())) {
            while (true) {
                Socket socket = serverSocket.accept();
                handle(socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws ReceiverFailException {
        byte[] buffer = new byte[BUFFER_SIZE];

        InputStream in;
        try {
            in = socket.getInputStream();
            int count;
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((count = in.read(buffer)) > 0) {
                byteArray.write(buffer, 0, count);
            }
            Request request = this.protocolInterface.unpackRequest(byteArray.toByteArray());
            Response response = invoke(request);
            byte[] responsePackedBytes = this.protocolInterface.packResponse(response);
            OutputStream out = socket.getOutputStream();
            out.write(responsePackedBytes);
        } catch (IOException | SerializeParseException originException) {
            ReceiverFailException e = new ReceiverFailException();
            e.addSuppressed(originException);
            throw e;
        }
    }

    private Response invoke(Request request) throws ReceiverFailException {
        // TODO
        Object result = null;
        return new Response(result);
    }
}
