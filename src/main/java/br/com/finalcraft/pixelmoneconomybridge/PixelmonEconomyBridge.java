package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.logger.ECLogger;
import br.com.finalcraft.pixelmoneconomybridge.commands.CommandRegisterer;
import br.com.finalcraft.pixelmoneconomybridge.common.factory.PixelmonEconomyFactory;
import br.com.finalcraft.pixelmoneconomybridge.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@ECPlugin(
        spigotID = "97889",
        bstatsID = "13410"
)
public class PixelmonEconomyBridge extends JavaPlugin{

    public static PixelmonEconomyBridge instance; {
        instance = this;
    }

    private final ECLogger ecLogger = new ECLogger(this);

    public static ECLogger getLog(){
        return instance.ecLogger;
    }

    private PixelmonEconomyFactory FACTORY;

    @Override
    public void onEnable() {
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    if (!Bukkit.getPluginManager().isPluginEnabled("EverNifeCore")){
                        getLog().warning("You need EverNifeCore to run this plugin!");
                        throw new IllegalStateException("EverNifeCore Plugin not Found!");
                    }

                    getLog().info("§aLoading up Configurations...");
                    ConfigManager.initialize(PixelmonEconomyBridge.instance);

                    getLog().info("§aRegistering Commands...");
                    CommandRegisterer.registerCommands(PixelmonEconomyBridge.instance);

                    getLog().info("§aIntegrating Pixelmon to Bukkit...");


                    if (Bukkit.getPluginManager().isPluginEnabled("FinalEconomy")){
                        getLog().info("FinalEconomy was found!");
                        getLog().info("HighPerformance full-async Instant Synchronization enabled!");

                        FACTORY = PixelmonEconomyFactory.create(PixelmonEconomyFactory.IntegrationType.FINAL_ECONOMY);
                        FACTORY.registerOrReload(PixelmonEconomyBridge.this);
                    }else {
                        getLog().info("Vault was found!");
                        getLog().info("Dedicated Thread Delayed Synchronization enabled!");

                        FACTORY = PixelmonEconomyFactory.create(PixelmonEconomyFactory.IntegrationType.GENERIC_VAULT);
                        FACTORY.registerOrReload(PixelmonEconomyBridge.this);
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }.runTaskLater(this, 100);//Only execute after the entire server is up and running
    }

    @ECPlugin.Reload
    public void onReload(){
        ConfigManager.initialize(PixelmonEconomyBridge.instance);
        FACTORY.registerOrReload(PixelmonEconomyBridge.this);
    }
}
