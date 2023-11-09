package br.com.finalcraft.pixelmoneconomybridge.common.factory;

import br.com.finalcraft.evernifecore.util.FCReflectionUtil;
import br.com.finalcraft.evernifecore.version.MCVersion;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PixelmonEconomyFactory {

    private final IntegrationType integrationType;

    public PixelmonEconomyFactory(IntegrationType integrationType) {
        this.integrationType = integrationType;
    }

    public static PixelmonEconomyFactory create(IntegrationType integrationType){
        return (PixelmonEconomyFactory) FCReflectionUtil.getConstructor(
                "br.com.finalcraft.pixelmoneconomybridge.compat." + MCVersion.getCurrent().name() +".reforged.factory.PixelmonEconomyFactoryImpl",
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
