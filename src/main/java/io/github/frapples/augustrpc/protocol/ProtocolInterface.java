package io.github.frapples.augustrpc.protocol;

import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public interface ProtocolInterface {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes) throws SerializeParseException;

    byte[] packRequest(Request request);

    Request unpackRequest(byte[] bytes) throws SerializeParseException;

    byte[] packResponse(Response response);

    Response unpackResponse(byte[] bytes) throws SerializeParseException;
}
