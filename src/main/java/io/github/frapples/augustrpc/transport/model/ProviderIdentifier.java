package io.github.frapples.augustrpc.transport.model;


import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/26
 */
@Immutable
public class ProviderIdentifier {

    private final String ip;
    private final int port;

    public ProviderIdentifier(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ProviderIdentifier{" +
            "ip='" + ip + '\'' +
            ", port=" + port +
            '}';
    }
}
