package io.github.frapples.augustrpc.simple;

import io.github.frapples.augustrpc.context.common.Config;
import io.github.frapples.augustrpc.context.common.Environment;
import io.github.frapples.augustrpc.context.common.RpcInitalizer;
import io.github.frapples.augustrpc.context.exception.InitFailException;
import io.github.frapples.augustrpc.simple.service.SimpleService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class Main {

    public static void main(String[] args) throws InitFailException {
        init();
        demo();
    }

    private static void init() throws InitFailException {
        Config config = new Config();
        config.setIocBridgeImplClassName("io.github.frapples.augustrpc.simple.SimpleIocBridge");
        RpcInitalizer initalizer = new RpcInitalizer(config);
        initalizer.init();
    }

    private static void demo() {
        SimpleService simpleService = Environment.getConsumerRpcContext().getService(SimpleService.class);
        System.out.println(simpleService); // Dynamic Proxy Object
        Integer result = simpleService.add(1, 2);
        System.out.println(result);
    }

}
