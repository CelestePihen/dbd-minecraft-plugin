package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;

public class ListenersManager {

    private final DBDPlugin main;
    private final PluginManager pm;
    private final GameManager gameManager;

    public ListenersManager(DBDPlugin main, GameManager gameManager) {
        this.main = main;
        this.pm = Bukkit.getPluginManager();
        this.gameManager = gameManager;
    }

    public void registerListeners() {
        pm.registerEvents(new PlayerListener(main, gameManager), main);
        pm.registerEvents(new ItemListener(main, gameManager), main);
        pm.registerEvents(new EntityDamageListener(main, gameManager), main);
        pm.registerEvents(new MainMenuListener(main), main);
        pm.registerEvents(new PlayerSneakListener(main, gameManager), main);
    }
    
}