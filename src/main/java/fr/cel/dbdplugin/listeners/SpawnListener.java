package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.cel.dbdplugin.utils.ItemBuilder;

public class SpawnListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        Player player = e.getPlayer();
        Action action = e.getAction();
        ItemStack itemStack = e.getItem();

        if (itemStack == null) {
            return;
        }

        switch (itemStack.getType()) {

            case COMPASS:
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                switch (itemStack.getItemMeta().getDisplayName()) {
                    case "§cMenu Principal":
                    createInventory(player);
                }
                
            }
            break;

            default:
            break;
        }
    }

    private void createInventory(Player player) {

        String spaceInventoryName = "\uF80A\uF809";
        Inventory mainMenu = Bukkit.createInventory(null, (9*6), "§f" + spaceInventoryName + "\uF902");

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
