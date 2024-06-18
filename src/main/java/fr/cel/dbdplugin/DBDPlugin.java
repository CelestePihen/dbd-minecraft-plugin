package fr.cel.dbdplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import fr.cel.dbdplugin.commands.HelloCommand;
import fr.cel.dbdplugin.commands.ItemCommand;
import fr.cel.dbdplugin.commands.JoinCommand;
import fr.cel.dbdplugin.commands.StartCommand;
import fr.cel.dbdplugin.commands.TabComplete;
import fr.cel.dbdplugin.commands.UtilsCommand;
import fr.cel.dbdplugin.listeners.ListenersManager;
import fr.cel.dbdplugin.manager.GameManager;

@Getter
public class DBDPlugin extends JavaPlugin {

    private final List<UUID> survivors = new ArrayList<>();
    private final List<UUID> killer = new ArrayList<>();
    private final List<UUID> spectators = new ArrayList<>();

    private final String prefix = "§4[DBD] §6-§r";

    private GameManager gameManager;

    @Override
    public void onEnable() {
        getLogger().info("[DBD-Plugin] Plugin active");

        gameManager = new GameManager(this);
        new ListenersManager(this, gameManager).registerListeners();

        getCommand("hello").setExecutor(new HelloCommand());
        getCommand("getspeedplayer").setExecutor(new UtilsCommand(this));
        getCommand("testresourcepack").setExecutor(new UtilsCommand(this));
        getCommand("start").setExecutor(new StartCommand(gameManager));

        getCommand("joinsurvivor").setExecutor(new JoinCommand(this));
        getCommand("joinkiller").setExecutor(new JoinCommand(this));
        getCommand("joinspectator").setExecutor(new JoinCommand(this));

        getCommand("dbditem").setExecutor(new ItemCommand(gameManager));
        getCommand("dbditem").setTabCompleter(new TabComplete());
    }

    @Override
    public void onDisable() {
        getLogger().info("[DBD-Plugin] Plugin desactive");
    }

}