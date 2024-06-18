package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import fr.cel.dbdplugin.utils.ItemBuilder;
import net.kyori.adventure.text.Component;

public class PlayerListener implements Listener {

    private DBDPlugin main;
    private GameManager gameManager;

    public PlayerListener(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        e.joinMessage(Component.text(main.getPrefix() + "§e" + player.getName() + " §7a rejoint le serveur §a(" + Bukkit.getOnlinePlayers().size() + "§a/" + Bukkit.getMaxPlayers() + ")"));
        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setDisplayName(Component.text("§cMenu Principal")).toItemStack());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if(gameManager.isGameState(GameState.GAME)) {
            e.quitMessage(Component.text(main.getPrefix() + "§c" + player.getName() + "§6 a quitté la partie !"));
        } else {
            e.quitMessage(Component.text(main.getPrefix() + "§c" + player.getName() + "§6 a quitté le serveur !"));
        }
    }

    @EventHandler
    public void onItemSwap(PlayerSwapHandItemsEvent e) {
        if(gameManager.isGameState(GameState.GAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if(gameManager.isGameState(GameState.GAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        Player player = e.getPlayer();
        Location loc = player.getLocation().getBlock().getLocation();

        if(gameManager.isGameState(GameState.GAME) || gameManager.isGameState(GameState.PRE_DOOR) || gameManager.isGameState(GameState.DOOR)) {
            if (player.getWorld().getBlockAt((int) loc.getX(), (int) loc.getY() - 1, (int) loc.getZ()).getType() != Material.INFESTED_COBBLESTONE) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void foodChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }

}