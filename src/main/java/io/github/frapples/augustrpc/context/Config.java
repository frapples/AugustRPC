package io.github.frapples.augustrpc.context;

import net.jcip.annotations.Immutable;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/7/24
 */
@Immutable
public class Config {

    private final String iocBridgeImplClassName;
    private final String requestSenderImplClassName;

    public Config(String iocBridgeImplClassName, String requestSenderImplClassName) {
        this.iocBridgeImplClassName = iocBridgeImplClassName;
        this.requestSenderImplClassName = requestSenderImplClassName;
    }

    public String getIocBridgeImplClassName() {
        return iocBridgeImplClassName;
    }

    public String getRequestSenderImplClassName() {
        return requestSenderImplClassName;
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }


    public static final class ConfigBuilder {

        private String iocBridgeImplClassName;
        private String requestSenderImplClassName;

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

        public Config build() {
            return new Config(iocBridgeImplClassName, requestSenderImplClassName);
        }
    }
}
