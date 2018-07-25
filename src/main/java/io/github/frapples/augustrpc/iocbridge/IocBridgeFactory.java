package io.github.frapples.augustrpc.iocbridge;

import io.github.frapples.augustrpc.utils.StringUtils;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public class IocBridgeFactory {

    public static IocBridge createFromClass(String fullyQualifiedName) throws CreatedFailException {
        if (StringUtils.isEmpty(fullyQualifiedName)) {
            throw new IllegalArgumentException("IocBridge impl class name is empty");
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            throw new CreatedFailException("IocBridge impl class not found: " + fullyQualifiedName);
        }

        if (!IocBridge.class.isAssignableFrom(clazz)) {
            throw new CreatedFailException(String.format("IocBridge impl class %s is not IocBridge", fullyQualifiedName));
        }

        IocBridge iocBridge;
        try {
            iocBridge = (IocBridge) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CreatedFailException(
                String.format("Create instance of IocBridge impl class %s fail, reason: %s", fullyQualifiedName, e));
        }
        return iocBridge;
    }

}
