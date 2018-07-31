package io.github.frapples.augustrpc.simpleexample.cosumer;

import io.github.frapples.augustrpc.service.RpcContext;
import io.github.frapples.augustrpc.simpleexample.rpcinterface.ProviderDemoService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/31
 */
public class ConsumerDemoService {

    private final ProviderDemoService providerDemoService;

    public ConsumerDemoService() {
        this.providerDemoService = RpcContext.getInstance().getConsumerRpcContext().getService(ProviderDemoService.class);
    }

    public void demo() {
        System.out.println(providerDemoService); // Dynamic Proxy Object
        Integer result = providerDemoService.add(1, 2);
        System.out.println(result);
        result = providerDemoService.sub(1, 2);
        System.out.println(result);
    }
}
