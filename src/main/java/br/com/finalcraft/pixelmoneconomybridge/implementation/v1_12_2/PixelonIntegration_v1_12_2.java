package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.finaleconomy.FEBankAccountManager_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.finaleconomy.FEUpdateListener_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.listener.PlayerLoginListener_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly.VaultBankAccountManager_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly.VaultLogoutListener_v1_12_2;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_12_2.vaultonly.VaultUpdaterThread_v1_12_2;
import com.pixelmonmod.pixelmon.Pixelmon;

public class PixelonIntegration_v1_12_2 {

    public static void initializeFinalEconomy(){
        Pixelmon.moneyManager = new FEBankAccountManager_v1_12_2();
        ECListener.register(PixelmonEconomyBridge.instance, FEUpdateListener_v1_12_2.class);
        ECListener.register(PixelmonEconomyBridge.instance, PlayerLoginListener_v1_12_2.class);
    }

    public static void initializeVault(){
        Pixelmon.moneyManager = new VaultBankAccountManager_v1_12_2();
        ECListener.register(PixelmonEconomyBridge.instance, VaultLogoutListener_v1_12_2.class);
        ECListener.register(PixelmonEconomyBridge.instance, PlayerLoginListener_v1_12_2.class);
        VaultUpdaterThread_v1_12_2.initialize();
    }
}
