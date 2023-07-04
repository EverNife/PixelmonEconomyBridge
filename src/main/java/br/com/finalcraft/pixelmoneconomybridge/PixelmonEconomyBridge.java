package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.evernifecore.ecplugin.annotations.ECPlugin;
import br.com.finalcraft.evernifecore.logger.ECLogger;
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

    public static PixelmonEconomyBridge instance; {
        instance = this;
    }

    private final ECLogger ecLogger = new ECLogger(this);
    private transient IntegrationType INTEGRATION_TYPE = null;

    public static ECLogger getLog(){
        return instance.ecLogger;
    }

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

                        if (MCVersion.isEqual(MCVersion.v1_12)){
                            PixelonIntegration_v1_12_2.initializeFinalEconomy();
                        }else {
                            PixelonIntegration_v1_16_5.initializeFinalEconomy();
                        }
                        INTEGRATION_TYPE = IntegrationType.FINAL_ECONOMY;

                    }else {
                        getLog().info("Vault was found!");
                        getLog().info("Dedicated Thread Delayed Synchronization enabled!");

                        if (MCVersion.isEqual(MCVersion.v1_12)){
                            PixelonIntegration_v1_12_2.initializeVault();
                        }else {
                            PixelonIntegration_v1_16_5.initializeVault();
                        }
                        INTEGRATION_TYPE = IntegrationType.GENERIC_VAULT;
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
        switch (INTEGRATION_TYPE){
            case GENERIC_VAULT:{
                if (MCVersion.isEqual(MCVersion.v1_12)){
                    VaultUpdaterThread_v1_12_2.initialize();
                }else {
                    VaultUpdaterThread_v1_16_5.initialize();
                }
            }
        }
    }
}
