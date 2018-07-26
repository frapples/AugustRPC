package io.github.frapples.augustrpc.transport.provider.networklistener;

import io.github.frapples.augustrpc.iocbridge.IocBridge;
import io.github.frapples.augustrpc.protocol.ProtocolInterface;
import io.github.frapples.augustrpc.transport.consumer.model.ProviderIdentifier;
import io.github.frapples.augustrpc.transport.provider.exception.ReceiverFailException;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
public interface NetworkListener {

    void init(IocBridge iocBridge, ProviderIdentifier providerIdentifier, ProtocolInterface protocolInterface);

    void listen() throws ReceiverFailException;

}
