package io.github.frapples.augustrpc.service;

import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
@Immutable
public class Config {

    private final String iocBridgeImplClassName;
    private final String requestSenderImplClassName;
    private final String networkListenerImplClassName;

    public Config(String iocBridgeImplClassName, String requestSenderImplClassName, String networkListenerImplClassName) {
        this.iocBridgeImplClassName = iocBridgeImplClassName;
        this.requestSenderImplClassName = requestSenderImplClassName;
        this.networkListenerImplClassName = networkListenerImplClassName;
    }

    public String getIocBridgeImplClassName() {
        return iocBridgeImplClassName;
    }

    public String getRequestSenderImplClassName() {
        return requestSenderImplClassName;
    }

    public String getNetworkListenerImplClassName() {
        return networkListenerImplClassName;
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    @Override
    public String toString() {
        return "Config{" +
            "iocBridgeImplClassName='" + iocBridgeImplClassName + '\'' +
            ", requestSenderImplClassName='" + requestSenderImplClassName + '\'' +
            '}';
    }


    public static final class ConfigBuilder {

        private String iocBridgeImplClassName;
        private String requestSenderImplClassName;
        private String networkListenerImplClassName;

        private ConfigBuilder() {
        }

        public ConfigBuilder iocBridgeImplClassName(String iocBridgeImplClassName) {
            this.iocBridgeImplClassName = iocBridgeImplClassName;
            return this;
        }

        public ConfigBuilder requestSenderImplClassName(String requestSenderImplClassName) {
            this.requestSenderImplClassName = requestSenderImplClassName;
            return this;
        }

        public ConfigBuilder networkListenerImplClassName(String networkListenerImplClassName) {
            this.networkListenerImplClassName = networkListenerImplClassName;
            return this;
        }

        public Config build() {
            return new Config(iocBridgeImplClassName, requestSenderImplClassName, networkListenerImplClassName);
        }
    }
}
