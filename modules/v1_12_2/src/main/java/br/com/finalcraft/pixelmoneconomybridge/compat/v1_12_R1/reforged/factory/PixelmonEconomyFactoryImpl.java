package br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.factory;

import br.com.finalcraft.evernifecore.listeners.base.ECListener;
import br.com.finalcraft.pixelmoneconomybridge.common.factory.PixelmonEconomyFactory;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.finaleconomy.FEBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.finaleconomy.FEUpdateListener;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.listener.PlayerLoginListener;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.vaultonly.VaultBankAccountManager;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.vaultonly.VaultLogoutListener;
import br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_R1.reforged.vaultonly.VaultUpdaterThread;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.plugin.java.JavaPlugin;

public class PixelmonEconomyFactoryImpl extends PixelmonEconomyFactory {

    private boolean firstRun = false;

    public PixelmonEconomyFactoryImpl(IntegrationType integrationType) {
        super(integrationType);
    }

    @Override
    public void registerOnVault(JavaPlugin javaPlugin) {
        if (firstRun == false){
            firstRun = true;
            Pixelmon.moneyManager = new VaultBankAccountManager();
            ECListener.register(javaPlugin, VaultLogoutListener.class);
            ECListener.register(javaPlugin, PlayerLoginListener.class);
        }
        VaultUpdaterThread.initialize(javaPlugin);
    }

    @Override
    public void registerOnFinalEconomy(JavaPlugin javaPlugin) {
        Pixelmon.moneyManager = new FEBankAccountManager();
        ECListener.register(javaPlugin, FEUpdateListener.class);
        ECListener.register(javaPlugin, PlayerLoginListener.class);
    }

}
