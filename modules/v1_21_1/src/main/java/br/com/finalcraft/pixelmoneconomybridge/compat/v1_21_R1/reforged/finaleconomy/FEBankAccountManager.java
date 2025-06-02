package br.com.finalcraft.pixelmoneconomybridge.compat.v1_21_R1.reforged.finaleconomy;

import br.com.finalcraft.evernifecore.config.playerdata.PlayerController;
import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FEBankAccountManager implements BankAccountManager {

    @Override
    public CompletableFuture<? extends BankAccount> getBankAccount(UUID uuid) {

        FEPlayerData playerData = PlayerController.getPDSection(uuid, FEPlayerData.class);

        if (playerData != null){
            return CompletableFuture.completedFuture(new FEBankAccount(playerData));
        }

        return CompletableFuture.completedFuture(null);
    }

}
