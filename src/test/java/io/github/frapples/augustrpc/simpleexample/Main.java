package io.github.frapples.augustrpc.simpleexample;

import io.github.frapples.augustrpc.service.Config;
import io.github.frapples.augustrpc.service.RpcContext;
import io.github.frapples.augustrpc.service.exception.InitFailException;
import io.github.frapples.augustrpc.simpleexample.cosumer.ConsumerDemoService;
import io.github.frapples.augustrpc.utils.FileUtils;
import java.io.IOException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class Main {

    public static void main(String[] args) throws InitFailException, IOException {
        Config config = Config.of(FileUtils.getResource("simpleexample.config.json"));
        RpcContext.init(config);

        ConsumerDemoService consumerDemoService = new ConsumerDemoService();
        consumerDemoService.demo();
        consumerDemoService.threadDemo();
    }
}
