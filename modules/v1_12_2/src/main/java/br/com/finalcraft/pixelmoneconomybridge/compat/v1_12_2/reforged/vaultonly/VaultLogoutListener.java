package br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_2.reforged.vaultonly;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class VaultLogoutListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        VaultUpdaterThread.PLAYERS_LAST_MONEY_AMOUNT.remove(event.getPlayer().getUniqueId());
    }

}
