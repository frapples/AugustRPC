package io.github.frapples.augustrpc.protocol;

import io.github.frapples.augustrpc.protocol.exception.SerializeParseException;
import io.github.frapples.augustrpc.transport.model.Request;
import io.github.frapples.augustrpc.transport.model.Response;
import java.io.InputStream;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public interface ProtocolInterface {

    byte[] packRequest(Request request);

    Request unpackRequest(byte[] bytes) throws SerializeParseException;

    byte[] packResponse(Response response);

    Response unpackResponse(byte[] bytes) throws SerializeParseException;
}
