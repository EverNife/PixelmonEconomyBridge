package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.metrics.Metrics;
import br.com.finalcraft.pixelmoneconomybridge.finaleconomy.FEBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.finaleconomy.FEUpdateListener;
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

    @Override
    public void onEnable() {
        instance = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("EverNifeCore")){
            warning("You need EverNifeCore to run this plugin!");
            return;
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                info("§aIntegrating Pixelmon to Bukkit...");

                ECListener.register(PixelmonEconomyBridge.this, PlayerLoginListener.class);

                if (Bukkit.getPluginManager().isPluginEnabled("FinalEconomy")){
                    info("FinalEconomy was found!");
                    info("HighPerformance full-async synchronization enabled!");
                    Pixelmon.moneyManager = new FEBankAccountManager();
                    ECListener.register(PixelmonEconomyBridge.this, FEUpdateListener.class);
                }else {
                    info("Vault was found!");
                    info("Dedicated Thread sync synchronization enabled!");
                    Pixelmon.moneyManager = new VaultBankAccountManager();
                    VaultUpdaterThread.initialize();
                }

                new Metrics(instance, 13410); //PixelmonEconomyBridge bstats
            }
        }.runTaskLater(this, 1);//Only execute after the entire server is up and running
    }

}
