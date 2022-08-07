package fr.cel.dbdplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.utils.timer.LobbyRunnable;

public class GameManager {

    private final DBDPlugin main;
    private GameState gameState = GameState.WAITING;

    private final PlayerManager playerManager;

    private LobbyRunnable lobbyRunnable;

    public GameManager(DBDPlugin main) {
        this.main = main;
        this.playerManager = new PlayerManager(this);
    }
    
    public void setGameState(GameState gameState) {
        if(this.gameState == GameState.GAME && gameState == GameState.STARTING) return;
        if(this.gameState == gameState) return;

        this.gameState = gameState;

        switch (gameState) {
            case WAITING:
            Bukkit.broadcastMessage("Waiting !");
            if ((Bukkit.getOnlinePlayers().size() >= 1) && (!lobbyRunnable.start)) {
                this.lobbyRunnable = new LobbyRunnable(main, this);
                this.lobbyRunnable.runTaskTimer(main, 0L, 20L);
                lobbyRunnable.start = true;
            }
            break;

            case STARTING:
            Bukkit.broadcastMessage("Starting !");

            getPlayerManager().clearPlayers();
            getPlayerManager().teleportPlayers();



            World world = Bukkit.getWorld("world");
            world.setPVP(true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(16000);

            break;

            case GAME:
            Bukkit.broadcastMessage("Game !");
            break;

            case END:
            Bukkit.broadcastMessage("End !");
            break;
        }
    }

    public boolean isStatus(GameState status) {
        return this.gameState == status;
    }

    public GameState getStatus() {
        return this.gameState;
    }

    public PlayerManager getPlayerManager() { return playerManager; }

}
