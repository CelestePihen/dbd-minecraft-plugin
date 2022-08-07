package fr.cel.dbdplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

import fr.cel.dbdplugin.commands.HelloCommand;
import fr.cel.dbdplugin.commands.ItemCommand;
import fr.cel.dbdplugin.commands.TabComplete;
import fr.cel.dbdplugin.commands.UtilsCommand;
import fr.cel.dbdplugin.listeners.ListenersManager;
import fr.cel.dbdplugin.manager.GameManager;
import net.md_5.bungee.api.ChatColor;

public class DBDPlugin extends JavaPlugin {

    private List<UUID> waiting = new ArrayList<>();
    private List<UUID> survivors = new ArrayList<>();
    private List<UUID> killer = new ArrayList<>();

    public boolean game, generators, exitdoors = false;

    private GameManager gameManager;

    @Override
    public void onEnable() {
        getLogger().info("[DBD-Plugin] Plugin active");

        this.gameManager = new GameManager(this);

        getCommand("hello").setExecutor(new HelloCommand());
        getCommand("getspeedplayer").setExecutor(new UtilsCommand(this));
        getCommand("testresourcepack").setExecutor(new UtilsCommand(this));

        getCommand("dbditem").setExecutor(new ItemCommand());
        getCommand("dbditem").setTabCompleter(new TabComplete());

        new ListenersManager(this, gameManager).registerListeners();
        
    }

    @Override
    public void onDisable() {
        getLogger().info("[DBD-Plugin] Plugin desactive");
    }

    public String getPrefix() {
        String prefix = "&4[DBD] &6- ";
        ChatColor.translateAlternateColorCodes('&', prefix);
        return prefix;
    }

    public List<UUID> getWaitingPlayer() {
        return waiting;
    }

    public List<UUID> getKiller() {
        return killer;
    }

    public List<UUID> getSurvivors() {
        return survivors;
    }
    
}