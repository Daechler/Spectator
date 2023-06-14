package net.daechler.spectator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spectator extends JavaPlugin implements CommandExecutor {
    private boolean isSpectatorModeEnabled;
    private Location previousLocation;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + getName() + " has been enabled!");
        getCommand("spectator").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + getName() + " has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by players!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("spectator")) {
            toggleSpectatorMode(player);
            return true;
        }

        return false;
    }

    private void toggleSpectatorMode(Player player) {
        if (isSpectatorModeEnabled) {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(previousLocation);
            isSpectatorModeEnabled = false;
            player.sendMessage(ChatColor.YELLOW + "You have left spectator mode.");
        } else {
            previousLocation = player.getLocation().clone();
            player.setGameMode(GameMode.SPECTATOR);
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
            player.sendMessage(ChatColor.YELLOW + "You have entered spectator mode.");
            isSpectatorModeEnabled = true;
        }
    }
}
