package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

public class VaultBankAccountManager_v1_12_2 implements IPixelmonBankAccountManager {

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!VaultIntegration.econ.hasAccount(offlinePlayer)){
            VaultIntegration.econ.createPlayerAccount(offlinePlayer);
        }

        return Optional.of(new VaultBankAccount_v1_12_2(uuid, offlinePlayer));
    }

}
