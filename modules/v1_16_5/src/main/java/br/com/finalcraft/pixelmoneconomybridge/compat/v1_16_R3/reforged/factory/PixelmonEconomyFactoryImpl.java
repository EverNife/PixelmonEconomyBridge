package br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.factory;

import br.com.finalcraft.evernifecore.listeners.PlayerLoginListener;
import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.common.factory.PixelmonEconomyFactory;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.finaleconomy.FEBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.finaleconomy.FEUpdateListener;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.vaultonly.VaultBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.vaultonly.VaultLogoutListener;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_16_R3.reforged.vaultonly.VaultUpdaterThread;
import com.pixelmonmod.pixelmon.api.economy.BankAccountManager;
import com.pixelmonmod.pixelmon.api.economy.BankAccountProxy;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelmonEconomyFactoryImpl extends PixelmonEconomyFactory {

    private boolean firstRun = false;

    public PixelmonEconomyFactoryImpl(IntegrationType integrationType) {
        super(integrationType);
    }

    public static BankAccountManager accountManager;

    @Override
    public void registerOnVault(JavaPlugin javaPlugin) {
        if (firstRun == false){
            firstRun = true;
            BankAccountProxy.setAccountManager(accountManager = new VaultBankAccountManager());
            ECListener.register(javaPlugin, VaultLogoutListener.class);
            ECListener.register(javaPlugin, PlayerLoginListener.class);
        }

        VaultUpdaterThread.initialize(javaPlugin);
    }

    @Override
    public void registerOnFinalEconomy(JavaPlugin javaPlugin) {
        BankAccountProxy.setAccountManager(accountManager = new FEBankAccountManager());
        ECListener.register(javaPlugin, FEUpdateListener.class);
    }
}
