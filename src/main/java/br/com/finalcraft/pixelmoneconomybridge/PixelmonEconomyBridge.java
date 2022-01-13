package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.evernifecore.autoupdater.SpigotUpdateChecker;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.metrics.Metrics;
import br.com.finalcraft.pixelmoneconomybridge.commands.CommandRegisterer;
import br.com.finalcraft.pixelmoneconomybridge.config.ConfigManager;
import br.com.finalcraft.pixelmoneconomybridge.finaleconomy.FEBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.finaleconomy.FEUpdateListener;
import br.com.finalcraft.pixelmoneconomybridge.integration.IntegrationType;
import br.com.finalcraft.pixelmoneconomybridge.listener.PlayerLoginListener;
import br.com.finalcraft.pixelmoneconomybridge.vaultonly.VaultBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.vaultonly.VaultUpdaterThread;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

    public static IntegrationType INTEGRATION_TYPE = null;

    @Override
    public void onEnable() {
        instance = this;

        new BukkitRunnable(){
            @Override
            public void run() {
                if (!Bukkit.getPluginManager().isPluginEnabled("EverNifeCore")){
                    warning("You need EverNifeCore to run this plugin!");
                    return;
                }

                info("§aRegistering Commands...");
                CommandRegisterer.registerCommands(PixelmonEconomyBridge.this);

                info("§aLoading up Configurations...");
                ConfigManager.initialize(PixelmonEconomyBridge.this);

                SpigotUpdateChecker.checkForUpdates(
                        PixelmonEconomyBridge.this,
                        "97889", //PixelmonEconomyBridge SpigotID: 97889
                        ConfigManager.getMainConfig()
                );


                info("§aIntegrating Pixelmon to Bukkit...");
                ECListener.register(PixelmonEconomyBridge.this, PlayerLoginListener.class);

                if (Bukkit.getPluginManager().isPluginEnabled("FinalEconomy")){
                    info("FinalEconomy was found!");
                    info("HighPerformance full-async Instant Synchronization enabled!");
                    Pixelmon.moneyManager = new FEBankAccountManager();
                    ECListener.register(PixelmonEconomyBridge.this, FEUpdateListener.class);
                    INTEGRATION_TYPE = IntegrationType.FINAL_ECONOMY;
                }else {
                    info("Vault was found!");
                    info("Dedicated Thread Delayed Synchronization enabled!");
                    Pixelmon.moneyManager = new VaultBankAccountManager();
                    VaultUpdaterThread.initialize();
                    INTEGRATION_TYPE = IntegrationType.GENERIC_VAULT;
                }

                new Metrics(instance, 13410); //PixelmonEconomyBridge bstats
            }
        }.runTaskLater(this, 1);//Only execute after the entire server is up and running
    }

}
