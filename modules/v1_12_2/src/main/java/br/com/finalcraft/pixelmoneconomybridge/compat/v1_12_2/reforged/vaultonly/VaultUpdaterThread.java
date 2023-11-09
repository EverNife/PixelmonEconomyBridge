package br.com.finalcraft.pixelmoneconomybridge.compat.v1_12_2.reforged.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import br.com.finalcraft.evernifecore.scheduler.FCScheduler;
import br.com.finalcraft.pixelmoneconomybridge.config.PEBSettings;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class VaultUpdaterThread {

    private static BukkitTask bukkitTask;
    public static HashMap<UUID, Double> PLAYERS_LAST_MONEY_AMOUNT = new HashMap<>();

    public static void initialize(JavaPlugin javaPlugin){
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

                    if (oldBalance == null || !oldBalance.equals(currentBalance)){
                        playersToUpdate.add(onlinePlayer);
                    }

                }

                if (playersToUpdate.size() > 0){
                    FCScheduler.runSync(() -> { //Do sync as we do not know how good this VaultPlugin has created his system
                        for (Player player : playersToUpdate) {
                            if (player.isOnline()){
                                Pixelmon.moneyManager.getBankAccount(player.getUniqueId()).ifPresent(iPixelmonBankAccount -> {
                                    iPixelmonBankAccount.updatePlayer(iPixelmonBankAccount.getMoney());
                                });
                            }
                        }
                    });
                }
            }
        }.runTaskTimerAsynchronously(javaPlugin, 1, PEBSettings.vault_syncInterval);
    }

}
