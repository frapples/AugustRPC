package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.utils.ReflectionUtils;
import io.github.frapples.augustrpc.utils.exception.CreatedFailException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class NetworkListenerFactory {

    public static NetworkListener createFromClass(String fullyQualifiedName) throws CreatedFailException {
        return ReflectionUtils.createFromClassName(fullyQualifiedName, NetworkListener.class);
    }

}
