package io.github.frapples.augustrpc.iocbridge;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
public interface IocBridge {

    <T> T getBean(Class<T> clazz);

}
