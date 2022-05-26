package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class VaultLogoutListener_v1_12_2 implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        VaultUpdaterThread_v1_12_2.PLAYERS_LAST_MONEY_AMOUNT.remove(event.getPlayer().getUniqueId());
    }

}
