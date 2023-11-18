package br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.listener;

import br.com.finalcraft.evernifecore.api.events.ECFullyLoggedInEvent;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.scheduler.FCScheduler;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class PlayerLoginListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(ECFullyLoggedInEvent event) {

        FCScheduler.scheduleSyncInTicks(() -> {
            if (event.getPlayerData().isPlayerOnline()){
                Pixelmon.moneyManager.getBankAccount(event.getPlayerData().getUniqueId()).ifPresent(iPixelmonBankAccount -> {
                    iPixelmonBankAccount.updatePlayer(iPixelmonBankAccount.getMoney());//Update player money on Login
                });
            }
        }, 20);

    }

}
