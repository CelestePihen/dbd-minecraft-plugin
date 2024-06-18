package fr.cel.dbdplugin.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;

public class ItemListener implements Listener {

    private final DBDPlugin main;
    private final GameManager gameManager;

    public ItemListener(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        ItemStack itemStack = e.getItem();

        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            switch (itemStack.getItemMeta().getDisplayName()) {

                case "§2Invisibilité":
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PotionEffect.INFINITE_DURATION, 0, false, true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 2, false, false));

                    player.playSound(player, Sound.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.sendMessage(Component.text("§2Vous êtes invisible !"));

                    player.getInventory().setItemInOffHand(gameManager.getItemKiller().spectreWait);

                    Bukkit.getScheduler().runTaskLater(main, () -> player.getInventory().setItemInOffHand(gameManager.getItemKiller().spectreNoInvi), 5 * 20);
                    break;

                case "§4Invisibilité":
                    for (PotionEffect effects : player.getActivePotionEffects()) {
                        player.removePotionEffect(effects.getType());
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 1, 0, false, false));

                    player.getInventory().setItemInOffHand(gameManager.getItemKiller().spectreWait);

                    Bukkit.getScheduler().runTaskLater(main, () -> player.getInventory().setItemInOffHand(gameManager.getItemKiller().spectreInvi), 5 * 20);

                    player.playSound(player, Sound.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.sendMessage("§4Vous êtes visible !");
                    break;

                case "§6Attente du pouvoir":
                    player.sendMessage("Votre pouvoir reviendra dans quelques instants !");
                    break;

            }

        }

    }

}