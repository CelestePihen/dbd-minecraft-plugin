package fr.cel.dbdplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cel.dbdplugin.manager.GameManager;

public class ItemCommand implements CommandExecutor {

    private GameManager gameManager;

    public ItemCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("dbditem")) {

            String item = args[0];
            
            switch (item) {

                case "spectreInvi":
                player.getInventory().addItem(gameManager.getItemKiller().spectreInvi);
                break;

                case "spectreNoInvi":
                player.getInventory().addItem(gameManager.getItemKiller().spectreNoInvi);
                break;
            }

        }

        return false;
    }

}