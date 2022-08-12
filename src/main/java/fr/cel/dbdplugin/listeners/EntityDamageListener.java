package fr.cel.dbdplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import fr.cel.dbdplugin.manager.GeneratorManager;
import net.kyori.adventure.text.Component;

public class EntityDamageListener implements Listener {

	private DBDPlugin main;
	private GameManager gameManager;

    public EntityDamageListener(DBDPlugin main, GameManager gameManager) {
		this.main = main;
		this.gameManager = gameManager;
	}

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
			GeneratorManager gen2 = GeneratorManager.generators.get(name);
			
			if (ec.equals(gen)) {
				
				// si pas en game ou si un spec "réussit" par une magie inconnue de taper sinon on annule (pour éviter de faire exploser la map s'il y a des problèmes avec les EC)
				if (!gameManager.isGameState(GameState.GAME) || main.getSpectators().contains(damager.getUniqueId())) {
					event.setCancelled(true);
					return;
				}

				// tueur
				if (main.getKiller().contains(damager.getUniqueId())) {
					// pour pas faire en sorte que le tueur tape en boucle le gén
					if(gen2.isPause()) {
						damager.sendMessage(Component.text("Vous ne pouvez pas attaquer ce générateur car vous l'avez déjà attaqué !"));
					}
					else if (gen2.getCharges() == 0) {
						gen2.setCharges(0);
						gen2.setPause(false);
					}
					// si la vie est inférieur à 100 et si le gén est pas en pause alors on le met à 0 et en pause
					else if (gen2.getCharges() <= 100 && !gen2.isPause()) {
						gen2.setCharges(0);
						gen2.setPause(true);
					}
					// on prend la vie du gén et on le divise par 2 et ce sera la vie du gén et on le met en pause
					else {
						gen2.removeCharges(gen2.getCharges() / 2);
						gen2.setPause(true);
					}
					damager.sendMessage(Component.text("Générateur " + name + " : " + gen2.getCharges() + " charges"));
					event.setCancelled(true);
				}
				
				// survivant
				if (main.getSurvivors().contains(damager.getUniqueId())) {
					// si le gén est en pause alors on l'enlève 
					if(gen2.isPause()) gen2.setPause(false);
					gen2.addCharges(1);
					damager.sendMessage(Component.text("Générateur " + name + " : " + gen2.getCharges() + " charges"));
					event.setCancelled(true);

					// if ((Math.random() * 100) < 10) { pour les test d'habilité ?
						
					// }
					
				}
				
				// générateur effectué
				if (gen2.getCharges() >= 500) {
					
					gen2.setCharges(500);
					event.setCancelled(true);
					
					if (gen != null) gen.remove();
					GeneratorManager.generators.remove(name);
					
					gameManager.removeGenerator();

					Bukkit.getOnlinePlayers().forEach(players -> {
						players.sendMessage("§bUn générateur a été effectué ! Il reste " + gameManager.getGenerator() + getGeneratorString() + " à faire !");
						players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
					});
					
					if(gameManager.getGenerator() == 0) {
						gameManager.setGameState(GameState.DOOR);
						Bukkit.broadcast(Component.text("Les portes sont maintenant activées !"));
					}

					return;
				}
			}
			
		} else {
			event.setCancelled(true);
		}

	}

    private String getGeneratorString() {
        return (gameManager.getGenerator() == 1 || gameManager.getGenerator() == 0) ? " générateur" : " générateurs";
    }
    
}
