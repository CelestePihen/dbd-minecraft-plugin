package fr.cel.dbdplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return null;

        if (label.equalsIgnoreCase("dbditem")) {
            if(args.length == 1) {
                return List.of("spectreInvi", "spectreNoInvi");
            }
        }

        return null;
    }
    
}
