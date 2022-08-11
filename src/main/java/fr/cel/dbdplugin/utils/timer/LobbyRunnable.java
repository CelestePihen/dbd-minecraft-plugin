package fr.cel.dbdplugin.utils.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import net.kyori.adventure.text.Component;

public class LobbyRunnable extends BukkitRunnable {

    public int timer = 11;
    public boolean start = false;

    private DBDPlugin main;
    private GameManager gameManager;

    public LobbyRunnable(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {

        if (!(gameManager.isGameState(GameState.WAITING))) {
            timer = 11;
            start = false;
            cancel();
            return;
        }

        if (Bukkit.getOnlinePlayers().size() <= 1) {
            Bukkit.broadcast(
                    Component.text(main.getPrefix() + "§cIl n'y a pas assez de joueurs pour lancer la partie."));
            timer = 11;
            start = false;
            cancel();
            return;
        }

        if (main.getSurvivors().isEmpty() || main.getKiller().isEmpty()) {
            Bukkit.broadcast(Component.text(main.getPrefix()
                    + "§cIl n'y a pas de survivants ou de tueurs.\nMerci d'avoir au moins 1 survivant et 1 tueur."));
            timer = 11;
            start = false;
            cancel();
            return;
        }

        timer--;
        setLevel();

        if (timer <= 0) {
            cancel();
            gameManager.setGameState(GameState.STARTING);
            return;
        }

        if ((timer == 60) || (timer == 30) || (timer == 15) || (timer == 10) || (timer <= 5 && timer != 0)) {
            Bukkit.broadcast(Component
                    .text(main.getPrefix() + "§6Démarrage de la partie dans §e" + timer + getSecond() + "§6."));
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);
            }
        }

    }

    private String getSecond() {
        return (timer == 1 || timer == 0) ? " seconde" : " secondes";
    }

    private void setLevel() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }

    }

}
