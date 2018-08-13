package io.github.frapples.augustrpc.simpleexample;

import io.github.frapples.augustrpc.service.Config;
import io.github.frapples.augustrpc.service.RpcContext;
import io.github.frapples.augustrpc.service.exception.AugustRpcInitFailException;
import io.github.frapples.augustrpc.simpleexample.cosumer.ConsumerDemoService;
import java.io.IOException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class Main {

    public static void main(String[] args) throws AugustRpcInitFailException, IOException {
        Config config = Config.ofResourcePath("simpleexample.config.json");
        RpcContext.init(config);

        ConsumerDemoService consumerDemoService = new ConsumerDemoService();
        consumerDemoService.demo();
        consumerDemoService.threadDemo();
    }
}
