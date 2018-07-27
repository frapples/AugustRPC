package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.transport.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.provider.invoker.ServiceInvoker;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public interface NetworkListener {

    void init(ServiceInvoker serviceInvoker, ProviderIdentifier providerIdentifier, ProtocolInterface protocolInterface);

    void listen();

}
