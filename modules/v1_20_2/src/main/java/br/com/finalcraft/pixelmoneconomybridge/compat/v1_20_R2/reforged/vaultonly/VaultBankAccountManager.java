package br.com.finalcraft.pixelmoneconomybridge.compat.v1_20_R2.reforged.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VaultBankAccountManager implements BankAccountManager {

    @Override
    public CompletableFuture<? extends BankAccount> getBankAccount(UUID uuid) {

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!VaultIntegration.econ.hasAccount(offlinePlayer)){
            VaultIntegration.econ.createPlayerAccount(offlinePlayer);
        }

        return CompletableFuture.completedFuture(new VaultBankAccount(uuid, offlinePlayer));
    }

}
