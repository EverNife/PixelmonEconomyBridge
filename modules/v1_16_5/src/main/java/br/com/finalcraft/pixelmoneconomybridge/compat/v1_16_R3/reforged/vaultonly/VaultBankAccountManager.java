package br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

public class VaultBankAccountManager implements BankAccountManager {

    @Override
    public Optional<? extends BankAccount> getBankAccount(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!VaultIntegration.econ.hasAccount(offlinePlayer)){
            VaultIntegration.econ.createPlayerAccount(offlinePlayer);
        }

        return Optional.of(new VaultBankAccount(uuid, offlinePlayer));
    }

}
