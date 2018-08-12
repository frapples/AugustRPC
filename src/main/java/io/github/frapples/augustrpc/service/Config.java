package io.github.frapples.augustrpc.service;

import com.google.gson.Gson;
import io.github.frapples.augustrpc.utils.FileUtils;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    public static Config of(String confilgFileContext) {
        Gson gson = new Gson();
        return gson.fromJson(confilgFileContext, Config.class);
    }

    public static Config of(URL configResource) throws IOException {
        String context = FileUtils.readFromStream(configResource.openStream(), StandardCharsets.UTF_8);
        return Config.of(context);
    }

    public static Config ofResourcePath(String path) throws IOException {
        return Config.of(FileUtils.getResource(path));
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
