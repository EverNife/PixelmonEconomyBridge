package br.com.finalcraft.pixelmoneconomybridge.listener;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.clientStorage.UpdateClientPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class PlayerListenerGlobal implements ECListener {

    private HashSet<FEPlayerData> PLAYERS_THAT_REQUIRED_UPDATE = new HashSet<>();

    @Override
    public void onRegister() {

        new BukkitRunnable(){
            @Override
            public void run() {

                final HashSet<FEPlayerData> oldSet;
                synchronized (PLAYERS_THAT_REQUIRED_UPDATE){
                    if (PLAYERS_THAT_REQUIRED_UPDATE.size() == 0){
                        return;
                    }
                    oldSet = PLAYERS_THAT_REQUIRED_UPDATE;
                    PLAYERS_THAT_REQUIRED_UPDATE = new HashSet<>();
                }

                FCScheduller.runSync(() -> {
                    for (FEPlayerData playerData : oldSet) {
                        EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerData.getUniqueId());
                        if (player != null){
                            UpdateClientPlayerData updateClientPlayerData = new UpdateClientPlayerData(playerData.getMoneyWrapper().intValue());
                            Pixelmon.network.sendTo(updateClientPlayerData, player);
                        }
                    }
                });
            }
        }.runTaskTimerAsynchronously(PixelmonEconomyBridge.instance, 20,20);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {
        synchronized (PLAYERS_THAT_REQUIRED_UPDATE){
            PLAYERS_THAT_REQUIRED_UPDATE.add(event.getPlayerData());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
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
