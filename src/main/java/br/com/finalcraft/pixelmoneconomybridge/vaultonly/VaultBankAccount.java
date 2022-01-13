package br.com.finalcraft.pixelmoneconomybridge.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class VaultBankAccount implements IPixelmonBankAccount {

    private final UUID uuid;
    private final OfflinePlayer offlinePlayer;

    public VaultBankAccount(UUID uuid, OfflinePlayer offlinePlayer) {
        this.uuid = uuid;
        this.offlinePlayer = offlinePlayer;
    }

    @Override
    public int getMoney() {
        return (int) VaultIntegration.ecoGet(offlinePlayer);
    }

    @Override
    public void setMoney(int amount) {
        VaultIntegration.ecoSet(offlinePlayer,amount);
    }

    @Override
    public int changeMoney(int amount) {
        if (amount > 0) {
            VaultIntegration.ecoGive(offlinePlayer, amount);
        } else {
            VaultIntegration.ecoTake(offlinePlayer, Math.abs(amount));
        }

        return this.getMoney();
    }

    @Override
    public UUID getOwnerUUID() {
        return this.uuid;
    }

}
