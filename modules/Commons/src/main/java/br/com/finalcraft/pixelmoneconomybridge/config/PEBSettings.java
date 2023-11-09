package br.com.finalcraft.pixelmoneconomybridge.config;

import br.com.finalcraft.evernifecore.config.Config;
import br.com.finalcraft.evernifecore.util.numberwrapper.NumberWrapper;
import br.com.finalcraft.evernifecore.version.MCVersion;

public class PEBSettings {

    public static int vault_syncInterval = 10;
    public static boolean removeDecimals = true;

    public static void initialize(Config config){
        vault_syncInterval = NumberWrapper.of(
                config.getOrSetDefaultValue(
                        "Settings.vaultOnly_syncInterval",
                        10,
                        "How often (in ticks) will this plugin check on Vault to update the players!" +
                                "\nThese checks are async, but there is no reason to use a value lower than 10 ticks")
        ).boundLower(1).intValue();

        if (MCVersion.isEqual(MCVersion.v1_16)){
            removeDecimals = config.getOrSetDefaultValue(
                    "Settings.removeDecimals",
                    true,
                    "On 1.16.5 there is the possibility to have decimals on the ClientGui" +
                            "\nBut to be honest, no one wants to have '82,692.318' displayed on the gui." +
                            "\n" +
                            "\nThis option will remove the decimals of the displayed money, it will be '82,692'"
            );
        }

        config.saveIfNewDefaults();
    }

}
