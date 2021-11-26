package br.com.finalcraft.pixelmoneconomybridge.commands;

import br.com.finalcraft.evernifecore.commands.finalcmd.FinalCMDManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandRegisterer {

    public static void registerCommands(JavaPlugin pluginInstance) {

        FinalCMDManager.registerCommand(pluginInstance, CMDCoreCommand.class);

    }

}
