package fr.cel.dbdplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.cel.dbdplugin.DBDPlugin;
import net.kyori.adventure.text.Component;

public class SpawnListener implements Listener {

    private DBDPlugin main;

    public SpawnListener(DBDPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if(e.getCurrentItem() == null && e.getAction() != null) return;

        if(e.getView().title() == Component.text("§f\uF80A\uF809\uF902")) {

            e.setCancelled(true);

            switch(e.getCurrentItem().getItemMeta().displayName().toString()) {
                
                case "§cTueur":
                // rejoindre la team tueur

                if(main.getSurvivors().contains(player.getUniqueId())) {
                    main.getSurvivors().remove(player.getUniqueId());
                } else {
                    main.getSpectators().remove(player.getUniqueId());
                }

                if(!main.getKiller().contains(player.getUniqueId())) main.getKiller().add(player.getUniqueId());

                player.sendMessage(main.getPrefix() + "Vous êtes §cTueur §f!");
                player.closeInventory();
                break;

                case "§2Survivant":
                // rejoindre la team survivant

                if(main.getKiller().contains(player.getUniqueId())) {
                    main.getKiller().remove(player.getUniqueId());
                } else {
                    main.getSpectators().remove(player.getUniqueId());
                }
                
                if(!main.getSurvivors().contains(player.getUniqueId())) main.getSurvivors().add(player.getUniqueId());

                player.sendMessage(main.getPrefix() + "Vous êtes §2Survivant §f!");
                player.closeInventory();
                break;

                case "§7Spectateur":
                // rejoindre la team spectateur

                if(main.getKiller().contains(player.getUniqueId())) {
                    main.getKiller().remove(player.getUniqueId());
                } else {
                    main.getSurvivors().remove(player.getUniqueId());
                }
                
                if(!main.getSpectators().contains(player.getUniqueId())) main.getSpectators().add(player.getUniqueId());

                player.sendMessage(main.getPrefix() + "Vous êtes §7Spectateur §f!");
                player.closeInventory();
                break;
            }

        }

    }
    
}
