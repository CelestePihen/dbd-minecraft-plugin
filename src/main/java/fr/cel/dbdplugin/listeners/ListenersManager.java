package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;

public class ListenersManager {

    private DBDPlugin main;
    private PluginManager pm;
    private GameManager gameManager;

    public ListenersManager(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.pm = Bukkit.getPluginManager();
        this.gameManager = gameManager;
    }

    public void registerListeners() {
        pm.registerEvents(new PlayerListener(main, gameManager), this.main);
        pm.registerEvents(new ItemListener(main), this.main);
        pm.registerEvents(new EntityDamageListener(main), this.main);
        pm.registerEvents(new SpawnListener(main), this.main);
    }
    
}
