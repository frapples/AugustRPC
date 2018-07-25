package io.github.frapples.augustrpc.context.common;

import io.github.frapples.augustrpc.context.consumer.ConsumerRpcContext;
import io.github.frapples.augustrpc.context.provider.ProviderRpcContext;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public class Environment {

    static ProviderRpcContext providerRpcContext;
    static ConsumerRpcContext consumerRpcContext;

    public static ProviderRpcContext getProviderRpcContext() {
        return providerRpcContext;
    }

    public static ConsumerRpcContext getConsumerRpcContext() {
        return consumerRpcContext;
    }

}
