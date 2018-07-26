package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.iocbridge.CreatedFailException;
import io.github.frapples.augustrpc.utils.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class RequestSenderFactory {

    public static RequestSender createFromClass(String fullyQualifiedName) throws CreatedFailException {
        if (StringUtils.isEmpty(fullyQualifiedName)) {
            throw new IllegalArgumentException("RequestSender impl class name is empty");
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            throw new CreatedFailException("RequestSender impl class not found: " + fullyQualifiedName);
        }

        if (!RequestSender.class.isAssignableFrom(clazz)) {
            throw new CreatedFailException(String.format("RequestSender impl class %s is not IocBridge", fullyQualifiedName));
        }

        RequestSender requestSender;
        try {
            requestSender = (RequestSender) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CreatedFailException(
                String.format("Create instance of RequestSender impl class %s fail, reason: %s", fullyQualifiedName, e));
        }
        return requestSender;
    }
}
