package io.github.frapples.augustrpc.service.iocbridge;

import io.github.frapples.augustrpc.utils.ReflectionUtils;
import io.github.frapples.augustrpc.utils.exception.CreatedFailException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class IocBridgeFactory {

    public static IocBridge createFromClass(String fullyQualifiedName) throws CreatedFailException {
        return ReflectionUtils.createFromClassName(fullyQualifiedName, IocBridge.class);
    }

}
