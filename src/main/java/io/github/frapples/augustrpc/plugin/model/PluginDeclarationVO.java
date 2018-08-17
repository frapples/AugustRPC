package io.github.frapples.augustrpc.plugin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/8/17
 */
public class PluginDeclarationVO {
    public static final String PLUGIN_CONFIG_FILE = "august-rpc.plugin.json";

    public String pluginName;
    public Map<String, ItemGroup> items = new HashMap<>();

    public static class ItemGroup {

        public ItemConfig iocBridge;
        public ItemConfig requestSender;
        public ItemConfig networkListener;
    }

    public static class ItemConfig {

        public String className;
    }

}
