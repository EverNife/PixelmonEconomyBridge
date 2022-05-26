package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.finaleconomy;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.PixelonIntegration_v1_16_5;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class FEUpdateListener_v1_16_5 implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {
        FCScheduller.runSync(() -> {//Needs to update sync, i think?
            if (event.getPlayerData().isPlayerOnline()){
                PixelonIntegration_v1_16_5.accountManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(bankAccount -> {
                    bankAccount.updatePlayer();
                });
            }
        });
    }

}
