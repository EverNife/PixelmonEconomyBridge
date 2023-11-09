package br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.finaleconomy;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.scheduler.FCScheduler;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.factory.PixelmonEconomyFactoryImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class FEUpdateListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {
        FCScheduler.runSync(() -> {//Needs to update sync, i think?
            if (event.getPlayerData().isPlayerOnline()){
                PixelmonEconomyFactoryImpl.accountManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(bankAccount -> {
                    bankAccount.updatePlayer();
                });
            }
        });
    }

}
