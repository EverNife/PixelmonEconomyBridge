package br.com.finalcraft.pixelmoneconomybridge.listener;

import br.com.finalcraft.evernifecore.api.events.ECFullyLoggedInEvent;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLoginListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(ECFullyLoggedInEvent event) {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (event.getPlayerData().isPlayerOnline()){
                    Pixelmon.moneyManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(iPixelmonBankAccount -> {
                        iPixelmonBankAccount.updatePlayer(iPixelmonBankAccount.getMoney());//Update player money on Login
                    });
                }
            }
        }.runTaskLater(PixelmonEconomyBridge.instance, 20);
    }

}
