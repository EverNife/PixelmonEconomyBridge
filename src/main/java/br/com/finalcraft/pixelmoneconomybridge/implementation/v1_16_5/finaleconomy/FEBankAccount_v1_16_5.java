package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.finaleconomy;

import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import br.com.finalcraft.pixelmoneconomybridge.config.PEBSettings;
import com.pixelmonmod.pixelmon.api.economy.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class FEBankAccount_v1_16_5 implements BankAccount {

    private final FEPlayerData playerData;

    public FEBankAccount_v1_16_5(FEPlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public UUID getIdentifier() {
        return playerData.getUniqueId();
    }

    @Override
    public BigDecimal getBalance() {
        double money = playerData.getMoney();
        if (PEBSettings.removeDecimals){
            money = Math.floor(money);
        }
        return BigDecimal.valueOf(money);
    }

    @Override
    public void setBalance(BigDecimal bigDecimal) {
        playerData.setMoney(bigDecimal.doubleValue());
    }

    @Override
    public boolean hasBalance(BigDecimal bigDecimal) {
        return playerData.hasMoney(bigDecimal.doubleValue());
    }

    @Override
    public boolean take(BigDecimal bigDecimal) {
        double value = bigDecimal.doubleValue();
        if (this.playerData.hasMoney(value)){
            this.playerData.removeMoney(value);
            return true;
        }
        return false;
    }

    @Override
    public boolean add(BigDecimal bigDecimal) {
        playerData.addMoney(bigDecimal.doubleValue());
        return true;
    }
}
