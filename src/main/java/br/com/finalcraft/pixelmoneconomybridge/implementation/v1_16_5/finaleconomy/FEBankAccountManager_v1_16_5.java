package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.finaleconomy;

import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;

import java.util.Optional;
import java.util.UUID;

public class FEBankAccountManager_v1_16_5 implements BankAccountManager {

    @Override
    public Optional<? extends BankAccount> getBankAccount(UUID uuid) {
        FEPlayerData playerData = PlayerController.getPDSection(uuid, FEPlayerData.class);

        if (playerData != null){
            return Optional.of(new FEBankAccount_v1_16_5(playerData));
        }

        return Optional.empty();
    }

}
