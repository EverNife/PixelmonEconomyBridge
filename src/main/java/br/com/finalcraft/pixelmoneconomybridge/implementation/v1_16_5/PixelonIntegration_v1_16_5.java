package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.finaleconomy.FEBankAccountManager_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.finaleconomy.FEUpdateListener_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.listener.PlayerLoginListener_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly.VaultBankAccountManager_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly.VaultLogoutListener_v1_16_5;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly.VaultUpdaterThread_v1_16_5;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;

public class PixelonIntegration_v1_16_5 {

    public static BankAccountManager accountManager;

    public static void initializeFinalEconomy(){
        BankAccountProxy.setAccountManager(accountManager = new FEBankAccountManager_v1_16_5());
        ECListener.register(PixelmonEconomyBridge.instance, FEUpdateListener_v1_16_5.class);
    }

    public static void initializeVault(){
        BankAccountProxy.setAccountManager(accountManager = new VaultBankAccountManager_v1_16_5());
        ECListener.register(PixelmonEconomyBridge.instance, VaultLogoutListener_v1_16_5.class);
        ECListener.register(PixelmonEconomyBridge.instance, PlayerLoginListener_v1_16_5.class);
        VaultUpdaterThread_v1_16_5.initialize();
    }
}
