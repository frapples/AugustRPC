package io.github.frapples.augustrpc.simple;

import io.github.frapples.augustrpc.context.Config;
import io.github.frapples.augustrpc.context.RpcContext;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.simple.service.SimpleService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class Main {

    public static void main(String[] args) throws InitFailException {
        RpcContext.init(getConfig());
        cunsumerDemo();
    }

    private static Config getConfig() {
        return Config.builder()
            .iocBridgeImplClassName("io.github.frapples.augustrpc.simple.SimpleIocBridge")
            .requestSenderImplClassName("io.github.frapples.augustrpc.transport.consumer.sender.SimpleRequestSenderImpl")
            .build();
    }

    private static void cunsumerDemo() {
        SimpleService simpleService = RpcContext.getInstance().getConsumerRpcContext().getService(SimpleService.class);
        System.out.println(simpleService); // Dynamic Proxy Object
        Integer result = simpleService.add(1, 2);
        System.out.println(result);
    }
}
