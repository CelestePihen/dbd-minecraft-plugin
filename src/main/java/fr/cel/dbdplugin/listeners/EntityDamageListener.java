package fr.cel.dbdplugin.listeners;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.entity.Panda.Gene;
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

			if(main.getSurvivors().contains(damager.getUniqueId())) {
				event.setCancelled(true);
				return;
			} 

			generator(event, ec, damager, "generateur1");
			generator(event, ec, damager, "generateur2");
			generator(event, ec, damager, "generateur3");
			generator(event, ec, damager, "generateur4");
			generator(event, ec, damager, "generateur5");
			
		}

    }

	public void generator(EntityDamageByEntityEvent event, EnderCrystal ec, Player damager, String name) {

		if (GeneratorManager.generators.get(name) != null) {
			
			GeneratorManager gen = GeneratorManager.generators.get(name);
			EnderCrystal gen2 = gen.getEc();
			
			if (ec.equals(gen2)) {

				// si pas en game ou si un spec "réussit" par une magie inconnue de taper sinon
				// on annule (pour éviter de faire exploser la map s'il y a des problèmes avec
				// les EC)
				if (!gameManager.isGameState(GameState.GAME) || main.getSpectators().contains(damager.getUniqueId())) {
					event.setCancelled(true);
					return;
				}

				// tueur

				// pour pas faire en sorte que le tueur tape en boucle le gén
				if (gen.isPause()) {
					damager.sendMessage(
							Component.text("Vous ne pouvez pas attaquer ce générateur car vous l'avez déjà attaqué !"));
				}

				// si la vie est égale à 0 alors on le remet à 0 pour éviter les bugs et on ne
				// le met pas en pause car inutile
				else if (gen.getCharges() == 0) {
					gen.setCharges(0);
					gen.setPause(false);
					damager.sendMessage(
							Component.text("Vous ne pouvez pas attaquer ce générateur car il est à 0 charge !"));
				}

				// si la vie est inférieur à 100 et si le gén est pas en pause alors on le met à
				// 0 et en pause
				else if (gen.getCharges() <= 100 && !gen.isPause()) {
					gen.setCharges(0);
					gen.setPause(true);
				}

				// on prend la vie du gén et on le divise par 2 et ce sera la vie du gén et on
				// le met en pause
				else {
					gen.removeCharges(gen.getCharges() / 2);
					gen.setPause(true);
				}

				damager.sendMessage(Component.text("Générateur " + name + " : " + gen.getCharges() + " charges"));
				event.setCancelled(true);
				
			}
			
		} else {
			event.setCancelled(true);
		}

	}
    
}
