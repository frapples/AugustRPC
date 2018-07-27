package io.github.frapples.augustrpc.transport.provider.invoker;

import io.github.frapples.augustrpc.service.provider.ProviderRpcContext;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import io.github.frapples.augustrpc.transport.provider.exception.ReceiverFailException;
import io.github.frapples.augustrpc.utils.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.jcip.annotations.ThreadSafe;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/27
 */
@ThreadSafe
public class ServiceInvoker {

    private final ProviderRpcContext providerRpcContext;

    public ServiceInvoker(ProviderRpcContext providerRpcContext) {
        this.providerRpcContext = providerRpcContext;
    }

    public Response invoke(Request request) throws ReceiverFailException {
        Object result;
        try {
            String className = request.getCallId().getServiceFullyQualifiedName();
            Class<?> clazz = ReflectionUtils.fullyQualifiedNameToClass(className);

            String[] typeNames = request.getCallId().getMethodArgumentTypeFullyQualifiedNames();
            Class<?>[] types = new Class<?>[typeNames.length];
            for (int i = 0; i < typeNames.length; i++) {
                types[i] = ReflectionUtils.fullyQualifiedNameToClass(typeNames[i]);
            }

            Object service = this.providerRpcContext.getService(clazz);
            Method method = service.getClass().getMethod(request.getCallId().getMethodName(), types);
            result = method.invoke(service, request.getArguments());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ReceiverFailException();
        }

        return new Response(result);
    }

}
