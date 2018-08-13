package io.github.frapples.augustrpc.transport.consumer.sender;

import io.github.frapples.augustrpc.utils.ReflectionUtils;
import io.github.frapples.augustrpc.utils.exception.CreatedFailException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public class RequestSenderFactory {

    public static RequestSender createFromClass(String fullyQualifiedName) throws CreatedFailException {
        return ReflectionUtils.createFromClassName(fullyQualifiedName, RequestSender.class);
    }
}
