package br.com.finalcraft.pixelmoneconomybridge.compat.v1_21_R1.reforged.finaleconomy;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_21_R1.reforged.factory.PixelmonEconomyFactoryImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class FEUpdateListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {

        if (event.getPlayerData().isPlayerOnline()){
            PixelmonEconomyFactoryImpl.accountManager.getBankAccount(event.getPlayerData().getUniqueId())
                    .thenAccept(bankAccount -> {
                        if (bankAccount != null){
                            bankAccount.updatePlayer();
                        }
                    });
        }

    }

}
