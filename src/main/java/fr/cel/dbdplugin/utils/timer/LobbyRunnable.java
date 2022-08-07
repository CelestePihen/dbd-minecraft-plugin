package fr.cel.dbdplugin.utils.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import net.md_5.bungee.api.ChatColor;

public class LobbyRunnable extends BukkitRunnable {

    public int timer = 61;
    public boolean start = false;

    private DBDPlugin main;
    private GameManager gameManager;
    public LobbyRunnable(DBDPlugin main, GameManager gameManager) { this.main = main; }

    @Override
    public void run() {
        
        if(!(gameManager.isStatus(GameState.WAITING))) {
            timer = 61;
            start = false;
            cancel();
            return;
        }

        if(Bukkit.getOnlinePlayers().size() < 1) {
            Bukkit.broadcastMessage(main.getPrefix() + "§cIl n'y a pas assez de joueurs pour lancer la partie.");
            timer = 61;
            start = false;
            cancel();
            return;
        }
        
        timer--;
        setLevel();

        if(timer <= 0) {
            cancel();
            gameManager.setGameState(GameState.GAME);
            return;
        }

        if((timer == 60) || (timer == 30) || (timer == 15) || (timer == 10) || (timer <= 5 && timer != 0)) {
            String startMessage = main.getPrefix() + "&6Démarrage de la partie dans &e" + timer  + getSecond() + "&6.";
            ChatColor.translateAlternateColorCodes('&', startMessage);
            Bukkit.broadcastMessage(startMessage);
            for(Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);
            }
        }

    }

    private String getSecond() { return (timer == 1 || timer == 0) ? " seconde" : " secondes"; }

    private void setLevel() {
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }
        
    }
    
}
