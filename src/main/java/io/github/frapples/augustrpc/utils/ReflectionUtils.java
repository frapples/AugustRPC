package io.github.frapples.augustrpc.utils;

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
}
