package br.com.finalcraft.pixelmoneconomybridge.listener;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.clientStorage.UpdateClientPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListenerGlobal implements ECListener {

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {

        FCScheduller.runAssync(() -> {
            if (event.getPlayerData().isPlayerOnline()){
                EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(event.getPlayerData().getUniqueId());
                UpdateClientPlayerData updateClientPlayerData = new UpdateClientPlayerData(event.getPlayerData().getMoneyWrapper().intValue());
                Pixelmon.network.sendTo(updateClientPlayerData, player);
            }
        });

    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (event.getPlayer().isOnline()){
                    Pixelmon.moneyManager.getBankAccount(event.getPlayer().getUniqueId()).ifPresent(iPixelmonBankAccount -> {
                        iPixelmonBankAccount.updatePlayer(iPixelmonBankAccount.getMoney());//Update player money on Login
                    });
                }
            }
        }.runTaskLater(PixelmonEconomyBridge.instance, 10);
    }

}
