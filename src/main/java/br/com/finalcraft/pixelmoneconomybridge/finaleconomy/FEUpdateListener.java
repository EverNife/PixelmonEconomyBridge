package br.com.finalcraft.pixelmoneconomybridge.finaleconomy;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.clientStorage.UpdateClientPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class FEUpdateListener implements ECListener {

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

                for (FEPlayerData playerData : oldSet) {
                    EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(playerData.getUniqueId());
                    if (player != null){
                        UpdateClientPlayerData updateClientPlayerData = new UpdateClientPlayerData(playerData.getMoneyWrapper().intValue());
                        Pixelmon.network.sendTo(updateClientPlayerData, player); //Do MoneyUpdate Assync
                    }
                }

            }
        }.runTaskTimerAsynchronously(PixelmonEconomyBridge.instance, 20, 5);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {
        synchronized (PLAYERS_THAT_REQUIRED_UPDATE){
            PLAYERS_THAT_REQUIRED_UPDATE.add(event.getPlayerData());
        }
    }

}
