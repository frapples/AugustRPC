package io.github.frapples.augustrpc.transport.provider.invoker;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.transport.consumer.model.Request;
import io.github.frapples.augustrpc.transport.consumer.model.Response;
import io.github.frapples.augustrpc.transport.provider.exception.ReceiverFailException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/27
 */
public class ServiceInvoker {

    private final IocBridge iocBridge;

    public ServiceInvoker(IocBridge iocBridge) {
        this.iocBridge = iocBridge;
    }

    public Response invoke(Request request) throws ReceiverFailException {
        Object result;
        try {
            String className = request.getServiceFullyQualifiedName();
            Class<?> clazz = Class.forName(className);

            String[] typeNames = request.getMethodArgumentTypeFullyQualifiedNames();
            Class<?>[] types = new Class<?>[typeNames.length];
            for (int i = 0; i < typeNames.length; i++) {
                types[i] = Class.forName(typeNames[i]);
            }

            Object service = iocBridge.getBean(clazz);
            Method method = service.getClass().getMethod(request.getMethodName(), types);
            result = method.invoke(service, request.getArguments());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ReceiverFailException();
        }

        return new Response(result);
    }
}
