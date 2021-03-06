package com.willfp.ecoenchants.commands;

import com.willfp.ecoenchants.Main;
import com.willfp.ecoenchants.config.ConfigManager;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class CommandEcodebug implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("ecodebug")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ConfigManager.getLang().getMessage("not-player"));
                return true;
            }

            if (!sender.hasPermission("ecoenchants.ecodebug")) {
                sender.sendMessage(ConfigManager.getLang().getNoPermission());
                return true;
            }

            Bukkit.getLogger().info("--------------- BEGIN DEBUG ----------------");
            Player player = (Player) sender;
            Bukkit.getLogger().info("Running Version: " + Main.getInstance().getDescription().getVersion());
            Bukkit.getLogger().info("Held Item: " + player.getInventory().getItemInMainHand().toString());
            Bukkit.getLogger().info("EcoEnchants.getAll(): " + EcoEnchants.getAll().toString());
            Bukkit.getLogger().info("Enchantment.values(): " + Arrays.toString(Enchantment.values()));
            try {
                Field byNameField = Enchantment.class.getDeclaredField("byName");
                byNameField.setAccessible(true);
                Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);
                Bukkit.getLogger().info("Enchantment.byName: " + byName.toString());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Bukkit.getLogger().info("--------------- END DEBUG ----------------");
        }

        return false;
    }
}
