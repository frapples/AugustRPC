package io.github.frapples.augustrpc.simpleexample.cosumer;

import io.github.frapples.augustrpc.ref.AuguestRpcFacade;
import io.github.frapples.augustrpc.simpleexample.rpcinterface.ProviderDemoService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/31
 */
public class ConsumerDemoService {

    private final ProviderDemoService providerDemoService;

    public ConsumerDemoService() {
        this.providerDemoService = AuguestRpcFacade.refer(ProviderDemoService.class);
    }

    public void demo() {
        System.out.println(providerDemoService); // Dynamic Proxy Object
        Integer result = providerDemoService.add(1, 2);
        System.out.println(result);
        result = providerDemoService.sub(1, 2);
        System.out.println(result);
    }

    public void threadDemo() {
        int n = 100;

        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                int result = this.providerDemoService.add(finalI, 0);
                System.out.println(result);
            });
        }

        for (int i = 0; i < n; i++) {
            threads[i].start();
        }
        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
