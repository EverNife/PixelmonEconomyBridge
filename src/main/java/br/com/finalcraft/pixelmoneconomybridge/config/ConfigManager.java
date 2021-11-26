package br.com.finalcraft.pixelmoneconomybridge.config;

import br.com.finalcraft.evernifecore.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    public static Config config;

    public static Config getMainConfig() {
        return config;
    }

    public static void initialize(JavaPlugin instance){
        config = new Config(instance, "config.yml", true);

        PEBSettings.initialize();
    }

}
