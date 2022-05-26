package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.UUID;

public class VaultBankAccount_v1_16_5 implements BankAccount {

    private final UUID uuid;
    private final OfflinePlayer offlinePlayer;

    public VaultBankAccount_v1_16_5(UUID uuid, OfflinePlayer offlinePlayer) {
        this.uuid = uuid;
        this.offlinePlayer = offlinePlayer;
    }

    @Override
    public UUID getIdentifier() {
        return this.uuid;
    }

    @Override
    public BigDecimal getBalance() {
        return BigDecimal.valueOf(VaultIntegration.ecoGet(offlinePlayer));
    }

    @Override
    public void setBalance(BigDecimal bigDecimal) {
        VaultIntegration.ecoSet(offlinePlayer, bigDecimal.doubleValue());
    }

    @Override
    public boolean hasBalance(BigDecimal bigDecimal) {
        return VaultIntegration.ecoHasEnough(offlinePlayer, bigDecimal.doubleValue());
    }

    @Override
    public boolean take(BigDecimal bigDecimal) {
        return VaultIntegration.ecoTake(offlinePlayer, bigDecimal.doubleValue());
    }

    @Override
    public boolean add(BigDecimal bigDecimal) {
        VaultIntegration.ecoGive(offlinePlayer, bigDecimal.doubleValue());
        return true;
    }
}
