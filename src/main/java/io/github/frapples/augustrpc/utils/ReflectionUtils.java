package io.github.frapples.augustrpc.utils;

import io.github.frapples.augustrpc.utils.exception.CreatedFailException;
import java.util.HashMap;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/27
 *
 * TODO: Confirm that it is thread safe
 */
@ThreadSafe
public class ReflectionUtils {

    private static final HashMap<String, Class<?>> PRIMITIVE_CLASS = new HashMap<String, Class<?>>() {
        {
            put("byte", Byte.TYPE);
            put("char", Character.TYPE);
            put("short", Short.TYPE);
            put("int", Integer.TYPE);
            put("long", Long.TYPE);
            put("float", Float.TYPE);
            put("double", Double.TYPE);
            put("boolean", Boolean.TYPE);
        }
    };

    public static Class<?> fullyQualifiedNameToClass(String fullyQualifiedName) throws ClassNotFoundException {
        if (PRIMITIVE_CLASS.containsKey(fullyQualifiedName)) {
            return PRIMITIVE_CLASS.get(fullyQualifiedName);
        } else {
            return Class.forName(fullyQualifiedName);
        }
    }

    public static <T> T createFromClassName(String fullyQualifiedName,Class<T> superClass) throws CreatedFailException {
        if (StringUtils.isEmpty(fullyQualifiedName)) {
            throw new IllegalArgumentException("Class name is empty: " + fullyQualifiedName);
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(fullyQualifiedName);
        } catch (ClassNotFoundException e) {
            throw new CreatedFailException("Class not found: " + fullyQualifiedName);
        }

        if (!superClass.isAssignableFrom(clazz)) {
            throw new CreatedFailException(
                String.format("Class %s is not %s", superClass.getName(), fullyQualifiedName));
        }

        T instance;
        try {
            instance = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CreatedFailException(
                String.format("Create instance of class %s fail", fullyQualifiedName), e);
        }
        return instance;
    }
}
