package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.utils.ByteOrderUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/31
 */
public class ThreadPoolRequestSenderImpl implements RequestSender {

    private static final int BUFFER_SIZE = 2048;

    private final ExecutorService executorService;

    public ThreadPoolRequestSenderImpl() {
        int minSize = 1;
        int maxSize = 100;
        int keepAliveSecond = 60;

        ThreadFactory threadFactory = Thread::new;

        this.executorService = new ThreadPoolExecutor(minSize, maxSize,
            keepAliveSecond, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1), threadFactory);
    }

    @Override
    public void send(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<byte[], Throwable> onComplete) {
        System.out.println(this.executorService.toString());
        executorService.submit(() -> {
            this.sendData(providerIdentifier, data, onComplete);
        });
    }

    private void sendData(ProviderIdentifier providerIdentifier, byte[] data, BiConsumer<byte[], Throwable> onComplete) {
        final byte[] buffer = new byte[BUFFER_SIZE];

        try (Socket socket = new Socket(providerIdentifier.getIp(), providerIdentifier.getPort())) {
            OutputStream out = socket.getOutputStream();
            out.write(ByteOrderUtils.intToBytesWithBigEndian(data.length));
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
