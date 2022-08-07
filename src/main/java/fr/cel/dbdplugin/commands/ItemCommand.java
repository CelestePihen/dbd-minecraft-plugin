package fr.cel.dbdplugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.cel.dbdplugin.utils.ItemBuilder;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("dbditem")) {

            String item = args[0];
            
            switch (item) {

                case "spectre1":
                ItemStack itemSpectre = new ItemBuilder(Material.STICK).addEnchant(Enchantment.ARROW_INFINITE, 1)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS).setDisplayName("§2Invisibilité").toItemStack();
                player.getInventory().addItem(itemSpectre);
                break;

                case "spectre2":
                ItemStack itemSpectre2 = new ItemBuilder(Material.STICK).setDisplayName("§4Invisibilité").toItemStack();
                player.getInventory().addItem(itemSpectre2);
                break;
            }

        }

        return false;
    }

}