package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.version.MCVersion;
import br.com.finalcraft.pixelmoneconomybridge.commands.CommandRegisterer;
import br.com.finalcraft.pixelmoneconomybridge.config.ConfigManager;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.PixelonIntegration_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly.VaultUpdaterThread_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.PixelonIntegration_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly.VaultUpdaterThread_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.integration.IntegrationType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@ECPlugin(
        spigotID = "97889",
        bstatsID = "13410"
)
public class PixelmonEconomyBridge extends JavaPlugin{

    public static PixelmonEconomyBridge instance;

    public static void info(String msg){
        instance.getLogger().info("[Info] " + msg);
    }

    public static void debug(String msg){
        instance.getLogger().info("[Debug] " + msg);
    }

    public static void warning(String msg){
        instance.getLogger().warning("[Warning] " + msg);
    }

    private IntegrationType INTEGRATION_TYPE = null;

    @Override
    public void onEnable() {
        instance = this;

        new BukkitRunnable(){
            @Override
            public void run() {
                if (!Bukkit.getPluginManager().isPluginEnabled("EverNifeCore")){
                    warning("You need EverNifeCore to run this plugin!");
                    throw new IllegalArgumentException("EverNifeCore Plugin not Found!");
                }

                info("§aRegistering Commands...");
                CommandRegisterer.registerCommands(PixelmonEconomyBridge.this);

                info("§aLoading up Configurations...");
                ConfigManager.initialize(PixelmonEconomyBridge.this);

                info("§aIntegrating Pixelmon to Bukkit...");
                if (Bukkit.getPluginManager().isPluginEnabled("FinalEconomy")){
                    info("FinalEconomy was found!");
                    info("HighPerformance full-async Instant Synchronization enabled!");

                    if (MCVersion.isBellow1_13()){
                        PixelonIntegration_v1_12_2.initializeFinalEconomy();
                    }else {
                        PixelonIntegration_v1_16_5.initializeFinalEconomy();
                    }
                    INTEGRATION_TYPE = IntegrationType.FINAL_ECONOMY;

                }else {
                    info("Vault was found!");
                    info("Dedicated Thread Delayed Synchronization enabled!");

                    if (MCVersion.isBellow1_13()){
                        PixelonIntegration_v1_12_2.initializeVault();
                    }else {
                        PixelonIntegration_v1_16_5.initializeVault();
                    }
                    INTEGRATION_TYPE = IntegrationType.GENERIC_VAULT;
                }
            }
        }.runTaskLater(this, 1);//Only execute after the entire server is up and running
    }

    @ECPlugin.Reload
    public void onReload(){
        ConfigManager.initialize(PixelmonEconomyBridge.instance);
        switch (INTEGRATION_TYPE){
            case GENERIC_VAULT:{
                if (MCVersion.isBellow1_13()){
                    VaultUpdaterThread_v1_12_2.initialize();
                }else {
                    VaultUpdaterThread_v1_16_5.initialize();
                }
            }
        }
    }
}
