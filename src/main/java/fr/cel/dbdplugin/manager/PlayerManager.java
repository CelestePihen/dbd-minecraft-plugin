package fr.cel.dbdplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerManager {

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
        Location spawnGame = new Location(Bukkit.getWorld("world"), -140, 72, 0);
        player.teleport(spawnGame);
    }

    public void gmAdventure() {
        Bukkit.getOnlinePlayers().stream().filter(player -> (player.getGameMode() == GameMode.SURVIVAL) || (player.getGameMode() == GameMode.CREATIVE)).forEach(this::gmAdventurePlayers);
    }

    private void gmAdventurePlayers(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
    }

    // public void giveItemKiller(Player player) {
    //     player.getInventory().setItemInOffHand(gameManager.getItemKiller().spectreInvi);
    // }

    public void healPlayers() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(this::heal);
    }

    private void heal(Player player) {
        player.setHealth(20);
    }

}