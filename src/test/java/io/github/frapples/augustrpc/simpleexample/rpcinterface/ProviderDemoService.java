package io.github.frapples.augustrpc.simpleexample.rpcinterface;

import io.github.frapples.augustrpc.ref.annotation.AugustRpcService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@AugustRpcService
public interface ProviderDemoService {

    Integer add(Integer a, Integer b);

    int sub(int a, int b);

}
