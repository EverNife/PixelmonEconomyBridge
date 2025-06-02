package br.com.finalcraft.pixelmoneconomybridge.compat.v1_21_R1.reforged.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import br.com.finalcraft.pixelmoneconomybridge.config.PEBSettings;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.UUID;

public class VaultBankAccount implements BankAccount {

    private final UUID uuid;
    private final OfflinePlayer offlinePlayer;

    public VaultBankAccount(UUID uuid, OfflinePlayer offlinePlayer) {
        this.uuid = uuid;
        this.offlinePlayer = offlinePlayer;
    }

    @Override
    public UUID getIdentifier() {
        return this.uuid;
    }

    @Override
    public BigDecimal getBalance() {
        double money = VaultIntegration.ecoGet(offlinePlayer);
        if (PEBSettings.removeDecimals){
            money = Math.floor(money);
        }
        return BigDecimal.valueOf(money);
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
