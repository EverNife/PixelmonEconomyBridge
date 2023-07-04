package br.com.finalcraft.pixelmoneconomybridge.commands;

import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.evernifecore.ecplugin.ECPluginManager;
import br.com.finalcraft.pixelmoneconomybridge.PermissionNodes;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import org.bukkit.command.CommandSender;

@FinalCMD(
        aliases = {"pixelmoneconomybridge","economybridge"}
)
public class CMDCoreCommand {

    @FinalCMD.SubCMD(
            subcmd = {"reload"},
            permission = PermissionNodes.COMMAND_RELOAD
    )
    public void reload(CommandSender sender) {

        ECPluginManager.reloadPlugin(sender, PixelmonEconomyBridge.instance);

    }

}
