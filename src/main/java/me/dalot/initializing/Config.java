package me.dalot.initializing;

import me.dalot.ProxyAnnouncer;
import org.simpleyaml.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    public static YamlConfiguration DEFAULT;
    public static String DEFAULT_PATH = ProxyAnnouncer.getInstance().getDataFolder() + "/config.yml";
    public static YamlConfiguration DATA;
    public static String DATA_PATH = ProxyAnnouncer.getInstance().getDataFolder() + "/data.yml";

    public static void init() {
        //DEFAULT = get(DEFAULT_PATH, "config.yml");
        DATA = get(DATA_PATH, "data.yml");
    }

    public static void save(YamlConfiguration config, String dest) {
        try {
            config.save(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfiguration get(String path) {
        return get(path, null);
    }

    public static YamlConfiguration get(String path, String def) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (def != null) {
                    ProxyAnnouncer.getInstance().saveResource(def, false);
                } else {
                    file.createNewFile();
                }
               ProxyAnnouncer.getInstance().logger().info("Created " + path);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml;
    }
}