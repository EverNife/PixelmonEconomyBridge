package br.com.finalcraft.pixelmoneconomybridge.config;

import br.com.finalcraft.evernifecore.util.numberwrapper.NumberWrapper;

public class PEBSettings {

    public static int vault_syncInterval = 10;

    public static void initialize(){
        vault_syncInterval = NumberWrapper.of(
                ConfigManager.getMainConfig().getOrSetDefaultValue(
                        "Settings.vaultOnly_syncInterval",
                        10,
                        "How often (in ticks) will this plugin check on Vault to update the players!" +
                                "\nThese checks are async, but there is no reason to use a value lower than 10 ticks")
        ).boundLower(1).intValue();
        ConfigManager.getMainConfig().saveIfNewDefaults();
    }

}
