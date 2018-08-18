package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.ref.exception.AugustRpcInvokedException;
import io.github.frapples.augustrpc.ref.exception.ErrorCode;
import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import io.github.frapples.augustrpc.transport.provider.invoker.ServiceInvoker;
import io.github.frapples.augustrpc.utils.ByteOrderUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 *
 * A simple single thread implementation for testing
 */
public class SimpleNetworkListenerImpl implements NetworkListener {

    private final static Logger log = LoggerFactory.getLogger(SimpleNetworkListenerImpl.class);

    private static final int BUFFER_SIZE = 2048;

    private ServiceInvoker serviceInvoker;
    private ProviderIdentifier providerIdentifier;
    private ProtocolInterface protocolInterface;

    @Override
    public void init(ServiceInvoker serviceInvoker, ProviderIdentifier providerIdentifier, ProtocolInterface protocolInterface) {
        this.serviceInvoker = serviceInvoker;
        this.providerIdentifier = providerIdentifier;
        this.protocolInterface = protocolInterface;
    }

    @Override
    public void listen() {
        try (ServerSocket serverSocket = new ServerSocket(providerIdentifier.getPort())) {
            while (true) {
                Socket socket = serverSocket.accept();
                handle(socket);
            }

        } catch (IOException e) {
            log.error("{}", e);
        }
    }

    private void handle(Socket socket) {
        byte[] buffer = new byte[BUFFER_SIZE];

        InputStream in;
        try {
            in = socket.getInputStream();
            int count;
            count = in.read(buffer, 0, Integer.BYTES);
            if (count != Integer.BYTES) {
                throw new AugustRpcInvokedException(ErrorCode.DATA_BROKEN);
            }

            int dataSize = ByteOrderUtils.bytesToIntWithBigEndian(Arrays.copyOfRange(buffer, 0, Integer.BYTES));

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            int readedSize = 0;
            int blockSize = (dataSize - readedSize) > BUFFER_SIZE ? BUFFER_SIZE : dataSize - readedSize;
            while ((count = in.read(buffer, 0, blockSize)) > 0) {
                readedSize += count;
                blockSize = (dataSize - readedSize) > BUFFER_SIZE ? BUFFER_SIZE : dataSize - readedSize;
                byteArray.write(buffer, 0, count);
            }
            Request request = this.protocolInterface.unpackRequest(byteArray.toByteArray());
            Response response = serviceInvoker.invoke(request);
            byte[] responsePackedBytes = this.protocolInterface.packResponse(response);
            OutputStream out = socket.getOutputStream();
            out.write(responsePackedBytes);
            out.close();
        } catch (SerializeParseException e) {
            throw new AugustRpcInvokedException(ErrorCode.DATA_BROKEN, e);
        } catch (IOException e) {
            throw new AugustRpcInvokedException(ErrorCode.IO_ERROR, e);
        }
    }
}
