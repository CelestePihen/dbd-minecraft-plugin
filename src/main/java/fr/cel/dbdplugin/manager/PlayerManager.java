package fr.cel.dbdplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerManager {

    private GameManager gameManager;

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void clearPlayers() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(this::clear);
    }

    private void clear(Player player) {
        player.getInventory().clear();
    }

    public void teleportPlayers() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(this::teleport);
    }

    private void teleport(Player player) {
        Location spawnGame = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        player.teleport(spawnGame);
    }

}