package br.com.finalcraft.pixelmoneconomybridge.config;

import br.com.finalcraft.evernifecore.util.numberwrapper.NumberWrapper;

public class PEBSettings {

    public static int vault_syncInterval = 10;

    public static void initialize(){
        vault_syncInterval = NumberWrapper.of(
                ConfigManager.getMainConfig().getOrSetDefaultValue("Settings.vaultOnly_syncInterval", 10)
        ).boundLower(1).intValue();
    }

}
