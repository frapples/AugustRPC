package io.github.frapples.augustrpc.transport.model;


import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
@Immutable
public class Request {

    private final String serviceFullyQualifiedName;
    private final String methodName;
    private final String[] methodArgumentTypeFullyQualifiedNames;
    private final Object[] arguments;

    public Request(String serviceFullyQualifiedName, String methodName, String[] methodArgumentTypeFullyQualifiedNames, Object[] arguments) {
        if (serviceFullyQualifiedName == null || methodName == null ||
            methodArgumentTypeFullyQualifiedNames == null || arguments == null) {
            throw new IllegalArgumentException();
        }

        this.serviceFullyQualifiedName = serviceFullyQualifiedName;
        this.methodName = methodName;
        this.methodArgumentTypeFullyQualifiedNames = methodArgumentTypeFullyQualifiedNames;
        this.arguments = arguments;
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

    public Object[] getArguments() {
        return arguments;
    }

}
