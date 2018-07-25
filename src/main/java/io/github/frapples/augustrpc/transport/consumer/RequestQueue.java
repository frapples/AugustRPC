package io.github.frapples.augustrpc.transport.consumer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@ThreadSafe
public class RequestQueue<T, R> {


    public static class QueueItem<T, R> {

        T data;
        R result;

        QueueItem(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void complete(R result) {
            synchronized (this) {
                this.result = result;
                this.notifyAll();
            }
        }
    }


    private BlockingDeque<QueueItem<T, R>> queue;

    public RequestQueue(int maxSize) {
        queue = new LinkedBlockingDeque<>(maxSize);
    }

    QueueItem<T, R> poll() throws InterruptedException {
        return queue.take();
    }

    R add(T data) throws InterruptedException {
        QueueItem<T, R> item = new QueueItem<>(data);
        synchronized (item) {
            queue.put(item);
            item.wait();
            return item.result;
        }
    }
}
