package fr.cel.dbdplugin.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.utils.ItemKiller;
import fr.cel.dbdplugin.utils.timer.DoorRunnable;
import fr.cel.dbdplugin.utils.timer.GameRunnable;
import fr.cel.dbdplugin.utils.timer.LobbyRunnable;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;

public class GameManager {

    private DBDPlugin main;
    @Getter
    private GameState gameState = GameState.WAITING;

    @Getter
    private final PlayerManager playerManager;
    @Getter
    private final ItemKiller itemKiller;

    private LobbyRunnable lobbyRunnable;
    private GameRunnable gameRunnable;
    private DoorRunnable doorRunnable;

    private int nGenerator = 5;

    public GameManager(DBDPlugin main) {
        this.main = main;
        this.playerManager = new PlayerManager();
        this.itemKiller = new ItemKiller();
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case WAITING:
                Bukkit.broadcast(Component.text("Waiting !"));

                getPlayerManager().gmAdventure();

                for (Entity e : Bukkit.getWorld("world").getEntities()) {
                    if (e.getType() == EntityType.END_CRYSTAL) {
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

            case PRE_DOOR:
                Bukkit.broadcast(Component.text("Pre-Door !"));
                break;

            case DOOR:
                Bukkit.broadcast(Component.text("Door !"));
                this.doorRunnable = new DoorRunnable(main, this);
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

    public int getGenerator() {
        return this.nGenerator;
    }

    public void removeGenerator() {
        this.nGenerator--;
    }

    private void spawnGenerator() {
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, -9), "generator1");
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, -6), "generator2");
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, -3), "generator3");
        this.create(new Location(Bukkit.getWorld("world"), -122, 73, 0), "generator4");
        this.create(new Location(Bukkit.getWorld("world"), -126, 73, 0), "generator5");
    }

    private void create(Location loc, String name) {
		EnderCrystal enderCrystal = (EnderCrystal) Bukkit.getWorld("world").spawnEntity(loc, EntityType.END_CRYSTAL);
        enderCrystal.customName(Component.text(name));
		enderCrystal.setCustomNameVisible(false);
		
		new GeneratorManager(enderCrystal, name);
	}

    public void showBossBar(Player player, float progress) {
        BossBar bossBar = BossBar.bossBar(Component.text("TIMER"), progress, BossBar.Color.RED, Overlay.NOTCHED_20);
        player.showBossBar(bossBar);
    }

}