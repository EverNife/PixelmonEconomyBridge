package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.finaleconomy;

import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Optional;
import java.util.UUID;

public class FEBankAccountManager_v1_12_2 implements IPixelmonBankAccountManager {

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(UUID uuid) {
        FEPlayerData playerData = PlayerController.getPDSection(uuid, FEPlayerData.class);

        if (playerData != null){
            return Optional.of(new FEBankAccount_v1_12_2(playerData));
        }

        return Optional.empty();
    }

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(EntityPlayerMP entityPlayerMP) {
        return this.getBankAccount(entityPlayerMP.getUniqueID());
    }

}
