package fr.cel.dbdplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.utils.ItemKiller;
import fr.cel.dbdplugin.utils.timer.DoorRunnable;
import fr.cel.dbdplugin.utils.timer.GameRunnable;
import fr.cel.dbdplugin.utils.timer.LobbyRunnable;
import net.kyori.adventure.text.Component;

public class GameManager {

    private final DBDPlugin main;
    private GameState gameState = GameState.WAITING;

    private final PlayerManager playerManager;
    private final ItemKiller itemKiller;

    private LobbyRunnable lobbyRunnable;
    private GameRunnable gameRunnable;
    private DoorRunnable doorRunnable;

    private int nGenerator = 5;

    public GameManager(DBDPlugin main) {
        this.main = main;
        this.playerManager = new PlayerManager(this);
        this.itemKiller = new ItemKiller();
    }
    
    public void setGameState(GameState gameState) {
        // if (this.gameState == GameState.WAITING && gameState == GameState.STARTING) return;
        // if (this.gameState == gameState) return;

        this.gameState = gameState;

        switch (gameState) {
            case WAITING:
                Bukkit.broadcast(Component.text("Waiting !"));

                getPlayerManager().gmAdventure();

                for(Entity e : Bukkit.getWorld("world").getEntities()) {
                    if(e.getType() == EntityType.ENDER_CRYSTAL) {
                        e.remove();
                    }
                }

                this.lobbyRunnable = new LobbyRunnable(main, this);
                this.lobbyRunnable.runTaskTimer(main, 0, 20);
                break;

            case STARTING:
                if (this.lobbyRunnable != null) this.lobbyRunnable.cancel();
            
                Bukkit.broadcast(Component.text("Starting !"));

                getPlayerManager().clearPlayers();
                getPlayerManager().healPlayers();
                getPlayerManager().teleportPlayers();
                this.spawnGenerator();
                this.setGameState(GameState.GAME);

                break;

            case GAME:
                Bukkit.broadcast(Component.text("Game !"));
            
                this.gameRunnable = new GameRunnable();
                this.gameRunnable.runTaskTimer(main, 0, 20);
                break;

            case PREDOOR:
                break;

            case DOOR:
                this.doorRunnable = new DoorRunnable();
                this.doorRunnable.runTaskTimer(main, 0, 20);
                break;

            case END:
                Bukkit.broadcast(Component.text("End !"));
                break;
            
        }
    }

    public boolean isGameState(GameState status) {
        return this.gameState == status;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ItemKiller getItemKiller() {
        return itemKiller;
    }

    public int getGenerator() {
        return this.nGenerator;
    }

    public int removeGenerator() {
        return this.nGenerator--;
    }

    private void spawnGenerator() {

		this.create(new Location(Bukkit.getWorld("world"), -122, 73, -9), "generator1");
		this.create(new Location(Bukkit.getWorld("world"), -122, 73, -6), "generator2");
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, -3), "generator3");
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, 0), "generator4");
        this.create(new Location(Bukkit.getWorld("world"), -126, 73, 0), "generator5");
    }

    private void create(Location loc, String name) {
		EnderCrystal enderCrystal = (EnderCrystal) Bukkit.getWorld("world").spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        enderCrystal.customName(Component.text(name));
		enderCrystal.setCustomNameVisible(false);
		
		new GeneratorManager(enderCrystal, name);
	}

}