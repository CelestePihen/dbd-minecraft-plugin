package fr.cel.dbdplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cel.dbdplugin.DBDPlugin;

public class JoinCommand implements CommandExecutor {

    private DBDPlugin main;
    public JoinCommand(DBDPlugin main) { this.main = main; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if(!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("joinsurvivor")) {
            main.getSurvivors().add(player.getUniqueId());
        }

        if(label.equalsIgnoreCase("joinkiller")) {
            main.getKiller().add(player.getUniqueId());
        }

        if(label.equalsIgnoreCase("joinspectator")) {
            main.getSpectators().add(player.getUniqueId());
        }

        return false;
    }
    
}
