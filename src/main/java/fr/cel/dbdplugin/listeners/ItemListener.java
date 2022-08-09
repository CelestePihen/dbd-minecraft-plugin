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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import fr.cel.dbdplugin.DBDPlugin;

import fr.cel.dbdplugin.utils.ItemBuilder;

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

            switch (itemStack.getItemMeta().getDisplayName()) {

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

            }

        }

    }

}