package br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import br.com.finalcraft.pixelmoneconomybridge.config.PEBSettings;
import br.com.finalcraft.pixelmoneconomybridge.implementation.v1_16_5.PixelonIntegration_v1_16_5;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class VaultUpdaterThread_v1_16_5 {

    private static BukkitTask bukkitTask;
    public static HashMap<UUID, Double> PLAYERS_LAST_MONEY_AMOUNT = new HashMap<>();

    public static void initialize(){
        if (bukkitTask != null){
            bukkitTask.cancel();
        }
        bukkitTask = new BukkitRunnable(){
            @Override
            public void run() {

                final HashSet<Player> playersToUpdate = new HashSet<>();

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    Double currentBalance = VaultIntegration.ecoGet(onlinePlayer);
                    Double oldBalance = PLAYERS_LAST_MONEY_AMOUNT.put(onlinePlayer.getUniqueId(), currentBalance);

                    if (oldBalance == null || oldBalance != currentBalance){
                        playersToUpdate.add(onlinePlayer);
                    }

                }

                if (playersToUpdate.size() > 0){
                    FCScheduller.runSync(() -> { //Do sync as we do not know how good this VaultPlugin has created his system
                        for (Player player : playersToUpdate) {
                            if (player.isOnline()){
                                PixelonIntegration_v1_16_5.accountManager.getBankAccount(player.getUniqueId()).ifPresent(bankAccount -> {
                                    bankAccount.updatePlayer();
                                });
                            }
                        }
                    });
                }
            }
        }.runTaskTimerAsynchronously(PixelmonEconomyBridge.instance, 1, PEBSettings.vault_syncInterval);
    }

}
