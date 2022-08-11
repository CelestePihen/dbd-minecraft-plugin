package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.utils.ItemBuilder;
import net.kyori.adventure.text.Component;

public class ItemListener implements Listener {

    private DBDPlugin main;

    public ItemListener(DBDPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Action action = e.getAction();
        ItemStack itemStack = e.getItem();

        if (itemStack == null) {
            return;
        }

        if (!itemStack.hasItemMeta()) {
            return;
        }

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            ItemStack spectreInvi = new ItemBuilder(Material.STICK).setDisplayName("§2Invisibilité")
                    .addEnchant(Enchantment.ARROW_INFINITE, 1)
                    .addItemFlags(ItemFlag.HIDE_ENCHANTS).toItemStack();

            ItemStack spectreWait = new ItemBuilder(Material.STICK).setDisplayName("§6Attente du pouvoir")
                    .toItemStack();

            ItemStack spectreNoInvi = new ItemBuilder(Material.STICK).setDisplayName("§4Invisibilité")
                    .toItemStack();

            switch (itemStack.getItemMeta().displayName().toString()) {

                case "§2Invisibilité":
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, true));
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false, false));

                    player.playSound(player, Sound.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.sendMessage("§2Vous êtes invisible !");

                    player.getInventory().setItemInOffHand(spectreWait);

                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        player.getInventory().setItemInOffHand(spectreNoInvi);
                    }, 5 * 20);

                    break;

                case "§4Invisibilité":
                    for (PotionEffect effects : player.getActivePotionEffects()) {
                        player.removePotionEffect(effects.getType());
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1, 0, false, false));

                    player.getInventory().setItemInOffHand(spectreWait);

                    Bukkit.getScheduler().runTaskLater(main, () -> {
                        player.getInventory().setItemInOffHand(spectreInvi);
                    }, 5 * 20);

                    player.playSound(player, Sound.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.sendMessage("§4Vous êtes visible !");
                    break;

                case "§6Attente du pouvoir":
                    player.sendMessage("Votre pouvoir reviendra dans quelques instants !");
                    break;
                
                case "§cMenu Principal":
                    openMainMenu(player);
                    break;

            }

        }

    }

    private void openMainMenu(Player player) {

        Inventory mainMenu = Bukkit.createInventory(null, (9*6), Component.text("§f\uF80A\uF809\uF902"));

        // killer
        ItemStack killer = new ItemBuilder(Material.GOLDEN_HOE).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setDisplayName("§cTueur").setCustomModelData(1).toItemStack();
        mainMenu.setItem(9, killer);
        mainMenu.setItem(10, killer);
        mainMenu.setItem(11, killer);
        mainMenu.setItem(18, killer);
        mainMenu.setItem(19, killer);
        mainMenu.setItem(20, killer);
        mainMenu.setItem(27, killer);
        mainMenu.setItem(28, killer);
        mainMenu.setItem(29, killer);

        // survivor
        ItemStack survivor = new ItemBuilder(Material.GOLDEN_HOE).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setDisplayName("§2Survivant").setCustomModelData(1).toItemStack();
        mainMenu.setItem(12, survivor);
        mainMenu.setItem(13, survivor);
        mainMenu.setItem(14, survivor);
        mainMenu.setItem(21, survivor);
        mainMenu.setItem(22, survivor);
        mainMenu.setItem(23, survivor);
        mainMenu.setItem(30, survivor);
        mainMenu.setItem(31, survivor);
        mainMenu.setItem(32, survivor);
        
        // survivor
        ItemStack spectator = new ItemBuilder(Material.GOLDEN_HOE).addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setDisplayName("§7Spectateur").setCustomModelData(1).toItemStack();
        mainMenu.setItem(15, spectator);
        mainMenu.setItem(16, spectator);
        mainMenu.setItem(17, spectator);
        mainMenu.setItem(24, spectator);
        mainMenu.setItem(25, spectator);
        mainMenu.setItem(26, spectator);
        mainMenu.setItem(33, spectator);
        mainMenu.setItem(34, spectator);
        mainMenu.setItem(35, spectator);

        player.openInventory(mainMenu);

    }

}