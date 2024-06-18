package fr.cel.dbdplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;

public class UtilsCommand implements CommandExecutor {

    private DBDPlugin main;
    public UtilsCommand(DBDPlugin main) { this.main = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            return true;
        }

        if (label.equalsIgnoreCase("mainmenu")) {
            player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setDisplayName(Component.text("§cMenu Principal")).toItemStack());
        }

        if (label.equalsIgnoreCase("getspeedplayer")) {
            player.sendMessage(main.getPrefix() + "La vitesse du joueur est §e" + player.getWalkSpeed() + "F§f. Normal = 0.2F");
            return true;
        }

        if (label.equalsIgnoreCase("testresourcepack")) {
            player.sendTitlePart(TitlePart.TITLE, Component.text("Étoile : \uF901"));
            player.sendMessage("Test Resource Pack : \uF901");

            Inventory inventoryTest = Bukkit.createInventory(null, (9*6), Component.text("§f\uF80A\uF809\uF902"));
            inventoryTest.setItem(20, new ItemBuilder(Material.GOLDEN_HOE).toItemStack());
            inventoryTest.setItem(21, new ItemBuilder(Material.GOLDEN_HOE).toItemStack());
            inventoryTest.setItem(29, new ItemBuilder(Material.GOLDEN_HOE).toItemStack());
            inventoryTest.setItem(30, new ItemBuilder(Material.GOLDEN_HOE).toItemStack());
            player.openInventory(inventoryTest);

            return true;
        }

        return false;
    }
}
