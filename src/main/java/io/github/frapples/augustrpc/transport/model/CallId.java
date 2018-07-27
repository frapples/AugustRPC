package io.github.frapples.augustrpc.transport.model;

import java.lang.reflect.Method;
import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/27
 */
@Immutable
public class CallId {
    private final String serviceFullyQualifiedName;
    private final String methodName;
    private final String[] methodArgumentTypeFullyQualifiedNames;

    public static CallId of(Class<?> clazz, Method method) {
        return new CallId(clazz, method);
    }

    public CallId(String serviceFullyQualifiedName, String methodName, String[] methodArgumentTypeFullyQualifiedNames) {
        this.serviceFullyQualifiedName = serviceFullyQualifiedName;
        this.methodName = methodName;
        this.methodArgumentTypeFullyQualifiedNames = methodArgumentTypeFullyQualifiedNames;
    }

    private CallId(Class<?> clazz, Method method) {
        Class<?>[] paramterTypes = method.getParameterTypes();
        String[] argumentTypeNames = new String[paramterTypes.length];
        for (int i = 0; i < paramterTypes.length; i++) {
            argumentTypeNames[i] = paramterTypes[i].getName();
        }

        this.serviceFullyQualifiedName = clazz.getName();
        this.methodName = method.getName();
        this.methodArgumentTypeFullyQualifiedNames = argumentTypeNames;
    }

    public String getServiceFullyQualifiedName() {
        return serviceFullyQualifiedName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodArgumentTypeFullyQualifiedNames() {
        return methodArgumentTypeFullyQualifiedNames;
    }
}
