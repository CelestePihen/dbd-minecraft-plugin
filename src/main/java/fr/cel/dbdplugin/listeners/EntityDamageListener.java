package fr.cel.dbdplugin.listeners;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GeneratorManager;

public class EntityDamageListener implements Listener {

    private DBDPlugin main;
    public EntityDamageListener(DBDPlugin main) { this.main = main; }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) return;

        if ((event.getEntity() instanceof EnderCrystal) && (event.getDamager() instanceof Player)) {
			
			EnderCrystal ec = (EnderCrystal) event.getEntity();
			Player damager = (Player) event.getDamager();
			
			generator(event, ec, damager, "gen1");
			generator(event, ec, damager, "gen2");
			generator(event, ec, damager, "gen3");
			generator(event, ec, damager, "gen4");
			generator(event, ec, damager, "gen5");
			
		}

    }

	public void generator(EntityDamageByEntityEvent event, EnderCrystal ec, Entity damager, String name) {

		if (GeneratorManager.generators.get(name) != null) {
				
			EnderCrystal gen = GeneratorManager.generators.get(name).getEc();
			
			if (ec.equals(gen)) {
				
				// tueur (à faire)
				if (main.getKiller().contains(damager.getUniqueId())) {
					GeneratorManager.generators.get(name).addHeart((int) (300));
					// faire en sorte que le tueur puisse pas retaper le gen en boucle
					event.setCancelled(true);
				}
				
				// normal damage
				if (main.getSurvivors().contains(damager.getUniqueId())) {
					GeneratorManager.generators.get(name).removeHeart((int) (event.getDamage() / 2));
					event.setCancelled(true);
					// if ((Math.random() * 100) < 10) { pour les test d'habilité ?
						
					// }
					
				}
				
				// générateur effectué
				if (event.getDamage() > GeneratorManager.generators.get(name).getLife()) {
					
					GeneratorManager.generators.get(name).setHeart(0);
					event.setCancelled(true);
					
					if (gen != null) gen.remove();
					GeneratorManager.generators.remove(name);
					
					Bukkit.getOnlinePlayers().forEach(players -> {
						players.sendMessage("§bUn générateur a été effectué !"); // mettre ?/5
						players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
					});
					
					return;
				}
			}
			
		} else {
			event.setCancelled(true);
		}

	}
    
}
