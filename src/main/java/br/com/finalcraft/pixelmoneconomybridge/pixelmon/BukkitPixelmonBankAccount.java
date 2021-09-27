package br.com.finalcraft.pixelmoneconomybridge.pixelmon;

import br.com.finalcraft.finaleconomy.config.data.FEPlayerData;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;

import java.util.UUID;

public class BukkitPixelmonBankAccount implements IPixelmonBankAccount {

    private final FEPlayerData playerData;

    public BukkitPixelmonBankAccount(FEPlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public int getMoney() {
        return this.playerData.getMoneyWrapper().intValue();
    }

    @Override
    public void setMoney(int amount) {
        this.playerData.setMoney(amount);
    }

    @Override
    public int changeMoney(int amount) {
        if (amount > 0) {
            this.playerData.addMoney(amount);
        } else {
            this.playerData.removeMoney(Math.abs(amount));
        }

        return this.getMoney();
    }

    @Override
    public UUID getOwnerUUID() {
        return this.playerData.getUniqueId();
    }

}
