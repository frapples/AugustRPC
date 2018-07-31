package io.github.frapples.augustrpc.simpleexample;

import io.github.frapples.augustrpc.service.Config;
import io.github.frapples.augustrpc.service.RpcContext;
import io.github.frapples.augustrpc.service.exception.InitFailException;
import io.github.frapples.augustrpc.simpleexample.cosumer.ConsumerDemoService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class Main {

    public static void main(String[] args) throws InitFailException {
        Config config = Config.builder()
            .iocBridgeImplClassName("io.github.frapples.augustrpc.simpleexample.config.SimpleIocBridge")
            .requestSenderImplClassName("io.github.frapples.augustrpc.transport.consumer.sender.SimpleRequestSenderImpl")
            .networkListenerImplClassName("io.github.frapples.augustrpc.transport.provider.networklistener.SimpleNetworkListenerImpl")
            .build();
        RpcContext.init(config);

        ConsumerDemoService consumerDemoService = new ConsumerDemoService();
        consumerDemoService.demo();
    }
}
