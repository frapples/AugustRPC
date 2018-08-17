package io.github.frapples.augustrpc.plugin;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import io.github.frapples.augustrpc.plugin.exception.PluginLoadedFailException;
import io.github.frapples.augustrpc.plugin.model.PluginDeclarationVO;
import io.github.frapples.augustrpc.plugin.model.PluginDeclarationVO.ItemGroup;
import io.github.frapples.augustrpc.service.iocbridge.IocBridge;
import io.github.frapples.augustrpc.transport.consumer.sender.RequestSender;
import io.github.frapples.augustrpc.transport.provider.networklistener.NetworkListener;
import io.github.frapples.augustrpc.utils.FileUtils;
import io.github.frapples.augustrpc.utils.ReflectionUtils;
import io.github.frapples.augustrpc.utils.exception.CreatedFailException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/15
 */
public class PluginManager {

    private Map<String, PluginDeclarationVO> pluginsConfig = new ConcurrentHashMap<>();
    private Map<String, Object> pluginClasses = new ConcurrentHashMap<>();

    public PluginManager() throws IOException {
        Gson gson = new Gson();
        List<URL> resources = FileUtils.getResouces(PluginDeclarationVO.PLUGIN_CONFIG_FILE);
        for (URL resource : resources) {
            String json = FileUtils.readFromStream(resource.openStream(), StandardCharsets.UTF_8);
            PluginDeclarationVO config = gson.fromJson(json, PluginDeclarationVO.class);
            this.pluginsConfig.put(config.pluginName, config);
        }
    }

    public IocBridge getIocBridge(String pluginName) throws PluginLoadedFailException {
        String className = this.getItemGroup(pluginName).iocBridge.className;
        return this.getPlugin(className, IocBridge.class);
    }

    public RequestSender getRequestSender(String pluginName) throws PluginLoadedFailException {
        String className = this.getItemGroup(pluginName).requestSender.className;
        return this.getPlugin(className, RequestSender.class);
    }

    public NetworkListener getNetworkListener(String pluginName) throws PluginLoadedFailException {
        String className = this.getItemGroup(pluginName).networkListener.className;
        return this.getPlugin(className, NetworkListener.class);
    }

    private ItemGroup getItemGroup(String pluginName) throws PluginLoadedFailException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(pluginName));

        String[] seg = pluginName.split("\\.", 2);
        String plugin;
        String group;
        if (seg.length == 0) {
            throw new PluginLoadedFailException("Plugin not exists: " + pluginName);
        } else if (seg.length == 1) {
            plugin = seg[0];
            group = "default";
        } else {
            plugin = seg[0];
            group = seg[1];
        }

        try {
            return pluginsConfig.get(plugin).items.get(group);
        } catch (NullPointerException e) {
            throw new PluginLoadedFailException("Plugin not exists: " + pluginName);
        }
    }

    private <T> T getPlugin(String className, Class<T> clazz) throws PluginLoadedFailException {
        if (!pluginClasses.containsKey(className)) {
            synchronized (this) {
                if (!pluginClasses.containsKey(className)) {
                    try {
                        pluginClasses.put(className, ReflectionUtils.createFromClassName(className, clazz));
                    } catch (CreatedFailException e) {
                        throw new PluginLoadedFailException("Plugin calss load fail", e);
                    }
                }
            }
        }
        return (T) pluginClasses.get(className);
    }

}
