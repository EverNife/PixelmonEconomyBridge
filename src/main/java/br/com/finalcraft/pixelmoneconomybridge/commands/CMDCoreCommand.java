package br.com.finalcraft.pixelmoneconomybridge.commands;

import br.com.finalcraft.evernifecore.commands.finalcmd.annotations.FinalCMD;
import br.com.finalcraft.pixelmoneconomybridge.PermissionNodes;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import br.com.finalcraft.pixelmoneconomybridge.config.ConfigManager;
import br.com.finalcraft.pixelmoneconomybridge.vaultonly.VaultUpdaterThread;
import org.bukkit.command.CommandSender;

@FinalCMD(
        aliases = {"economybridge"}
)
public class CMDCoreCommand {

    @FinalCMD.SubCMD(
            subcmd = {"reload"},
            permission = PermissionNodes.COMMAND_RELOAD
    )
    public void reload(CommandSender sender) {

        ConfigManager.initialize(PixelmonEconomyBridge.instance);
        switch (PixelmonEconomyBridge.INTEGRATION_TYPE){
            case GENERIC_VAULT:{
                VaultUpdaterThread.initialize();
            }
        }

        sender.sendMessage("§2§l ▶ §aPixelmonEconomyBridge was Realoded!");
    }

}
