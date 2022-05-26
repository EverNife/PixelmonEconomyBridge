package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.listener;

import br.com.finalcraft.evernifecore.api.events.ECFullyLoggedInEvent;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.PixelonIntegration_v1_16_5;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLoginListener_v1_16_5 implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(ECFullyLoggedInEvent event) {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (event.getPlayerData().isPlayerOnline()){
                    PixelonIntegration_v1_16_5.accountManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(bankAccount -> {
                        bankAccount.updatePlayer();
                    });
                }
            }
        }.runTaskLater(PixelmonEconomyBridge.instance, 20);
    }

}
