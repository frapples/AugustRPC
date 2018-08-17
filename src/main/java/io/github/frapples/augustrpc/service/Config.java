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

    private final String iocBridge;
    private final String requestSender;
    private final String networkListener;

    public Config(String iocBridge, String requestSender, String networkListener) {
        this.iocBridge = iocBridge;
        this.requestSender = requestSender;
        this.networkListener = networkListener;
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

    public String getIocBridge() {
        return iocBridge;
    }

    public String getRequestSender() {
        return requestSender;
    }

    public String getNetworkListener() {
        return networkListener;
    }

    @Override
    public String toString() {
        return "Config{" +
            "iocBridge='" + iocBridge + '\'' +
            ", requestSender='" + requestSender + '\'' +
            '}';
    }
}
