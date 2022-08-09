package fr.cel.dbdplugin.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemKiller {

    // Le Spectre
    public ItemStack spectreInvi = new ItemBuilder(Material.STICK).addEnchant(Enchantment.ARROW_INFINITE, 1)
            .addItemFlags(ItemFlag.HIDE_ENCHANTS).setDisplayName("§2Invisibilité").toItemStack();

    public ItemStack spectreWait = new ItemBuilder(Material.STICK).setDisplayName("§6Attente du pouvoir").toItemStack();

    public ItemStack spectreNoInvi = new ItemBuilder(Material.STICK).setDisplayName("§4Invisibilité")
            .toItemStack();

}