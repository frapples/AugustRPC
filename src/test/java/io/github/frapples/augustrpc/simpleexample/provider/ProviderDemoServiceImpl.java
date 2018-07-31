package io.github.frapples.augustrpc.simpleexample.provider;

import io.github.frapples.augustrpc.service.annotation.AugustRpcProvider;
import io.github.frapples.augustrpc.simpleexample.rpcinterface.ProviderDemoService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@AugustRpcProvider
public class ProviderDemoServiceImpl implements ProviderDemoService {

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}
