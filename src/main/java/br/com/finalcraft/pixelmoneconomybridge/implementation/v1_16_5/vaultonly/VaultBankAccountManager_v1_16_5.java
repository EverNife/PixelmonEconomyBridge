package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

public class VaultBankAccountManager_v1_16_5 implements BankAccountManager {

    @Override
    public Optional<? extends BankAccount> getBankAccount(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!VaultIntegration.econ.hasAccount(offlinePlayer)){
            VaultIntegration.econ.createPlayerAccount(offlinePlayer);
        }

        return Optional.of(new VaultBankAccount_v1_16_5(uuid, offlinePlayer));
    }

}
