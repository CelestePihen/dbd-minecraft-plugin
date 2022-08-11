package fr.cel.dbdplugin.utils.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.kyori.adventure.text.Component;

public class GameRunnable extends BukkitRunnable {

    // juste pour savoir le temps de la game à la fin de la partie (ou p-ê le mettre dans un scoreboard)
    public int timer = 0;

    @Override
    public void run() {
        timer++;
        setActionBar();
    }

    public void setActionBar() {
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.sendActionBar(Component.text(timer));
        }
    }
    
}