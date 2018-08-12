package io.github.frapples.augustrpc.ref;

import io.github.frapples.augustrpc.service.RpcContext;

public class AuguestRpcFacade {

    private AuguestRpcFacade() {
        throw new IllegalStateException("Not allowed to create an instance of class AuguestRpcFacade");
    }

    public static <T> T refer(Class<T> serviceClass) {
        return RpcContext.getInstance().getConsumerRpcContext().getService(serviceClass);
    }


}
