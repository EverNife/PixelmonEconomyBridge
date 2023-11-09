package br.com.finalcraft.pixelmoneconomybridge.common.settings;

import br.com.finalcraft.evernifecore.config.Config;

public class BPPSettings {

    public static String DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON = "";

    public static void initialize(Config config){

        DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON = config.getOrSetDefaultValue(
                "Settings.DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON",
                "",
                "When you try to get data from a PokeSlot and that PokeSlot is empty," +
                        "\nwhat shold be returned as result?" +
                        "\n" +
                        "\nThis option is here because some players use DeluxeMenus and it" +
                        "\ndoes not behave well with empty placeholders!"
        );

        config.saveIfNewDefaults();
    }

    public static String getDefaultPlaceholderWhenNoPokemon() {
        return DEFAULT_PLACEHOLDER_WHEN_NO_POKEMON;
    }
}
