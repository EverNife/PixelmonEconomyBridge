package br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.listener;

import br.com.finalcraft.evernifecore.api.events.ECFullyLoggedInEvent;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.scheduler.FCScheduler;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.factory.PixelmonEconomyFactoryImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class PlayerLoginListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(ECFullyLoggedInEvent event) {

        FCScheduler.scheduleSyncInTicks(() -> {
            if (event.getPlayerData().isPlayerOnline()){
                PixelmonEconomyFactoryImpl.accountManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(bankAccount -> {
                    bankAccount.updatePlayer();
                });
            }
        }, 20);

    }

}
