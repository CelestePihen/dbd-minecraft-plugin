package fr.cel.dbdplugin.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemKiller {

    // Le Spectre
    public ItemStack spectreInvi = new ItemBuilder(Material.STICK).addEnchant(Enchantment.INFINITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS)
            .setDisplayName(Component.text("§2Invisibilité")).toItemStack();

    public ItemStack spectreWait = new ItemBuilder(Material.STICK).setDisplayName(Component.text("§6Attente du pouvoir")).toItemStack();

    public ItemStack spectreNoInvi = new ItemBuilder(Material.STICK).setDisplayName(Component.text("§4Invisibilité")).toItemStack();

}