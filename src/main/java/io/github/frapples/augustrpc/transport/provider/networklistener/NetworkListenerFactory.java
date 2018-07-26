package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.utils.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class NetworkListenerFactory {

    public static NetworkListener createFromClass(String fullyQualifiedName) throws CreatedFailException {
        if (StringUtils.isEmpty(fullyQualifiedName)) {
            throw new IllegalArgumentException("NetworkListener impl class name is empty");
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            throw new CreatedFailException("NetworkListener impl class not found: " + fullyQualifiedName);
        }

        if (!NetworkListener.class.isAssignableFrom(clazz)) {
            throw new CreatedFailException(String.format("NetworkListener impl class %s is not IocBridge", fullyQualifiedName));
        }

        NetworkListener networkListener;
        try {
            networkListener = (NetworkListener) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CreatedFailException(
                String.format("Create instance of NetworkListener impl class %s fail, reason: %s", fullyQualifiedName, e));
        }
        return networkListener;
    }

}
