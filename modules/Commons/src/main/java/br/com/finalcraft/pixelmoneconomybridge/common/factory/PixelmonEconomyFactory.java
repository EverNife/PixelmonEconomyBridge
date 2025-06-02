package br.com.finalcraft.pixelmoneconomybridge.common.factory;

import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import br.com.finalcraft.evernifecore.version.MCDetailedVersion;
import br.com.finalcraft.evernifecore.version.MCVersion;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PixelmonEconomyFactory {

    private final IntegrationType integrationType;

    public PixelmonEconomyFactory(IntegrationType integrationType) {
        this.integrationType = integrationType;
    }

    public static PixelmonEconomyFactory create(IntegrationType integrationType){

        String versionName = MCVersion.getCurrent().name();

        if (MCVersion.isEqual(MCVersion.v1_20)){
            versionName = MCDetailedVersion.v1_20_R2.name(); // Enforce 1.20.2 path when in 1.20.x
        }

        if (MCVersion.isEqual(MCVersion.v1_21)){
            versionName = MCDetailedVersion.v1_21_R1.name(); // Enforce 1.21.1 path when in 1.21.x
        }

        return (PixelmonEconomyFactory) FCReflectionUtil.getConstructor(
                "br.com.finalcraft.pixelmoneconomybridge.compat." + versionName +".reforged.factory.PixelmonEconomyFactoryImpl",
                IntegrationType.class
        ).invoke(integrationType);
    }

    public void registerOrReload(JavaPlugin javaPlugin){
        switch (this.integrationType){
            case FINAL_ECONOMY:
                registerOnFinalEconomy(javaPlugin);
                break;
            case GENERIC_VAULT:
                registerOnVault(javaPlugin);
                break;
        }
    }

    protected abstract void registerOnVault(JavaPlugin javaPlugin);

    protected abstract void registerOnFinalEconomy(JavaPlugin javaPlugin);

    public enum IntegrationType {
        FINAL_ECONOMY,
        GENERIC_VAULT
    }

}
