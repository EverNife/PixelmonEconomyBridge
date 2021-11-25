package br.com.finalcraft.pixelmoneconomybridge.vaultonly;

import br.com.finalcraft.evernifecore.integration.VaultIntegration;
import br.com.finalcraft.evernifecore.util.FCScheduller;
import br.com.finalcraft.pixelmoneconomybridge.PixelmonEconomyBridge;
import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class VaultUpdaterThread{

    private static BukkitTask bukkitTask;
    private static HashMap<UUID, Double> PLAYERS_LAST_MONEY_AMOUNT = new HashMap<>();

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

                FCScheduller.runSync(() -> { //Do sync as we do not know how good this VaultPlugin has created his system
                    for (Player player : playersToUpdate) {
                        if (player.isOnline()){
                            Pixelmon.moneyManager.getBankAccount(player.getUniqueId()).ifPresent(iPixelmonBankAccount -> {
                                iPixelmonBankAccount.updatePlayer(iPixelmonBankAccount.getMoney());
                            });
                        }
                    }
                });

            }
        }.runTaskTimerAsynchronously(PixelmonEconomyBridge.instance, 1,20);
    }

}
