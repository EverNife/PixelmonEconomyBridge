package br.com.finalcraft.pixelmoneconomybridge.vaultonly;

import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccountManager;
import net.minecraft.entity.player.EntityPlayerMP;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Optional;
import java.util.UUID;

public class VaultBankAccountManager implements IPixelmonBankAccountManager {

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (offlinePlayer != null){
            return Optional.of(new VaultBankAccount(uuid, offlinePlayer));
        }

        return Optional.empty();
    }

    @Override
    public Optional<? extends IPixelmonBankAccount> getBankAccount(EntityPlayerMP entityPlayerMP) {
        return this.getBankAccount(entityPlayerMP.getUniqueID());
    }

}
