package br.com.finalcraft.pixelmoneconomybridge.finaleconomy;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.finaleconomy.api.events.EconomyUpdateEvent;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.comm.packetHandlers.clientStorage.UpdateClientPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.UUID;

public class FEUpdateListener implements ECListener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEconomyUpdateEvent(EconomyUpdateEvent event) {
        final UUID uuid = event.getPlayerData().getUniqueId();
        final int newMoney = (int) event.getAmount();
        FCScheduller.runAssync(() -> {
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
            if (player != null){
                UpdateClientPlayerData updateClientPlayerData = new UpdateClientPlayerData(newMoney);
                Pixelmon.network.sendTo(updateClientPlayerData, player); //Do MoneyUpdate Assync
            }
        });
    }

}
