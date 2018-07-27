package io.github.frapples.augustrpc.simple.service;

import io.github.frapples.augustrpc.service.annotation.AugustRpcService;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@AugustRpcService
public interface SimpleService {

    Integer add(Integer a, Integer b);

    int sub(int a, int b);

}
