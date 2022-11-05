package fr.cel.dbdplugin.utils.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cel.dbdplugin.DBDPlugin;

public class DoorRunnable extends BukkitRunnable {

    private int timer = 120;

    private DBDPlugin main;
    public DoorRunnable(DBDPlugin main) { this.main = main; }

    @Override
    public void run() {

        timer--;
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }

        if(timer <= 0) {
            this.cancel();
        }

    }
    
}
