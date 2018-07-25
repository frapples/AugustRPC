package io.github.frapples.augustrpc.protocol;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/25
 */
public interface ProtocolInterface {

    byte[] serialize(Object object);

    Object deserialize(byte[] bytes);

}
