package fr.cel.dbdplugin.utils.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import net.kyori.adventure.text.Component;

public class DoorRunnable extends BukkitRunnable {

    private int timer = 120;

    private final DBDPlugin main;
    private final GameManager gameManager;

    public DoorRunnable(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {

        if (!(gameManager.isGameState(GameState.GAME))) {
            timer = 120;
            cancel();
            return;
        }

        timer--;
        setLevel();

        if (timer <= 0) {
            this.cancel();
            gameManager.setGameState(GameState.END);
            // ??
            Bukkit.broadcast(Component.text(main.getPrefix() + "Bug DoorRunnable"));
            return;
        }

        for (Player pls : Bukkit.getOnlinePlayers()) {
            switch (timer) {
                case 110:
                    gameManager.showBossBar(pls, 0.95F);
                    break;
                case 100:
                    gameManager.showBossBar(pls, 0.9F);
                    break;
                case 90:
                    gameManager.showBossBar(pls, 0.85F);
                    break;
                case 80:
                    gameManager.showBossBar(pls, 0.8F);
                    break;
                case 70:
                    gameManager.showBossBar(pls, 0.7F);
                    break;
                case 60:
                    gameManager.showBossBar(pls, 0.6F);
                    break;
                case 50:
                    gameManager.showBossBar(pls, 0.5F);
                    break;
                case 40:
                    gameManager.showBossBar(pls, 0.4F);
                    break;
                case 30:
                    gameManager.showBossBar(pls, 0.3F);
                    break;
                case 20:
                    gameManager.showBossBar(pls, 0.2F);
                    break;
                case 10:
                    gameManager.showBossBar(pls, 0.1F);
                    break;
                case 0:
                    gameManager.showBossBar(pls, 0);
                    break;
            }
        }

    }

    private void setLevel() {
        for (Player players : Bukkit.getOnlinePlayers()) players.setLevel(timer);
    }
    
}
