package br.com.finalcraft.pixelmoneconomybridge.pixelmon;

import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Optional;
import java.util.UUID;

public class BukkitPixelmonBankAccountManager implements IPixelmonBankAccountManager {

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(UUID uuid) {
        FEPlayerData playerData = PlayerController.getPDSection(uuid, FEPlayerData.class);

        if (playerData != null){
            return Optional.of(new BukkitPixelmonBankAccount(playerData));
        }

        return Optional.empty();
    }

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(EntityPlayerMP entityPlayerMP) {
        return this.getBankAccount(entityPlayerMP.getUniqueID());
    }

}
