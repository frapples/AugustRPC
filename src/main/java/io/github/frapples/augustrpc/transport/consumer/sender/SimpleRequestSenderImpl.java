package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.utils.ByteOrderUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 *
 * A simple implementation for testing, single-threaded processing each request in turn
 */
public class SimpleRequestSenderImpl implements RequestSender {

    private static final int BUFFER_SIZE = 2048;

    @Override
    public void send(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<byte[], Throwable> onComplete) {
        this.sendData(providerIdentifier, data, onComplete);
    }

    private void sendData(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<byte[], Throwable> onComplete) {
        final byte[] buffer = new byte[BUFFER_SIZE];

        try (Socket socket = new Socket(providerIdentifier.getIp(), providerIdentifier.getPort())) {
            OutputStream out = socket.getOutputStream();
            int size = data.length;
            out.write(ByteOrderUtils.intToBytesWithBigEndian(size));
            out.write(data);

            InputStream in = socket.getInputStream();
            int count;
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            while ((count = in.read(buffer)) > 0) {
                byteArray.write(buffer, 0, count);
            }

            onComplete.accept(byteArray.toByteArray(), null);
        } catch (IOException e) {
            onComplete.accept(null, e);
        }
    }
}
