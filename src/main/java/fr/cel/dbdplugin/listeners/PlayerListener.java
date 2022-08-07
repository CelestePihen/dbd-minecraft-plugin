package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import fr.cel.dbdplugin.utils.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

    private DBDPlugin main;
    private GameManager gameManager;
    public PlayerListener(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        String joinMessage = main.getPrefix() + "§e" + player.getName() + " §7a rejoint la partie §a(" + Bukkit.getOnlinePlayers().size() + "§a/" + Bukkit.getMaxPlayers() + ")";
        ChatColor.translateAlternateColorCodes('&', joinMessage);
        e.setJoinMessage(joinMessage);

        player.getInventory().setItem(4, new ItemBuilder(Material.COMPASS).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cMenu Principal")).toItemStack());
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if(gameManager.isStatus(GameState.GAME)) {
            String quitMessage = main.getPrefix() + "§c" + player.getDisplayName() + "§6 a quitté la partie !";
            ChatColor.translateAlternateColorCodes('&', quitMessage);
            e.setQuitMessage(quitMessage);
        } else {
            String quitMessage = main.getPrefix() + "§c" + player.getDisplayName() + "§6 a quitté le serveur !";
            ChatColor.translateAlternateColorCodes('&', quitMessage);
            e.setQuitMessage(quitMessage);
        }
    }
}