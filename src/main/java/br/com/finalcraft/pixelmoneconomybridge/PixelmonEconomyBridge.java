package br.com.finalcraft.pixelmoneconomybridge;

import br.com.finalcraft.pixelmoneconomybridge.listener.PlayerListenerGlobal;
import br.com.finalcraft.pixelmoneconomybridge.pixelmon.BukkitPixelmonBankAccountManager;
import com.pixelmonmod.pixelmon.Pixelmon;
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

    @Override
    public void onEnable() {
        instance = this;
        new BukkitRunnable(){
            @Override
            public void run() {
                info("§aIntegrating Pixelmon to FinalEconomy...");
                Pixelmon.moneyManager = new BukkitPixelmonBankAccountManager();
                PlayerListenerGlobal.registerIfPossible();
            }
        }.runTaskLater(this, 1);//Only execute after the entire server is up and running
    }

}
