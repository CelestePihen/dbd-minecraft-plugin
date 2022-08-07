package fr.cel.dbdplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("dbditem")) {

            if(args.length == 1) {

                ArrayList<String> items = new ArrayList<String>();
                items.add("spectre1");
                items.add("spectre2");

                return items;

            }
        }

        return null;
    }
    
}
