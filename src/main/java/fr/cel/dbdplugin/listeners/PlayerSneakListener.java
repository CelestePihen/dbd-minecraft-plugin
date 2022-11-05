package fr.cel.dbdplugin.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import fr.cel.dbdplugin.manager.GeneratorManager;
import net.kyori.adventure.text.Component;

public class PlayerSneakListener implements Listener {
    
	private DBDPlugin main;
	private GameManager gameManager;

    public PlayerSneakListener(DBDPlugin main, GameManager gameManager) {
		this.main = main;
		this.gameManager = gameManager;
	}

    @EventHandler
	public void detectGenerator(PlayerInteractEntityEvent /* jsp quel event mettre juste pour detect un player à côté d'un EndC */ event) {

		Player player = event.getPlayer();

		for (Entity entity : player.getNearbyEntities(1, 0, 1)) {
			if (entity.getType() == EntityType.ENDER_CRYSTAL) {
				EnderCrystal ec = (EnderCrystal) entity;
				generator(ec, player, "generator1");
				generator(ec, player, "generator2");
				generator(ec, player, "generator3");
				generator(ec, player, "generator4");
				generator(ec, player, "generator5");
			}
		}

	}

    public void generator(EnderCrystal ec, Player survivor, String name) {

		if (GeneratorManager.getGenerators().get(name) != null) {
				
			GeneratorManager gen = GeneratorManager.getGenerators().get(name);
			EnderCrystal enderCrystal = gen.getEc();
			
			if (ec.equals(enderCrystal)) {

                // survivant
                if (main.getSurvivors().contains(survivor.getUniqueId())) {

                    // si le gén est en pause alors on l'enlève
                    if (gen.isPause()) { 
						gen.setPause(false);
						survivor.sendMessage(Component.text("Le générateur n'est plus en pause !"));
					}

                    gen.addCharges(1);
                    survivor.sendMessage(Component.text("Générateur " + name + " : " + gen.getCharges() + " charges"));

                    // if ((Math.random() * 100) < 10) { pour les test d'habilité ?

                    // }

                }

                // générateur fini
                if (gen.getCharges() >= 500) {
					
					gen.setCharges(500);
					
					if (gen != null) enderCrystal.remove();
					GeneratorManager.getGenerators().remove(name);
					
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
			
		}

	}

    private String getGeneratorString() {
        return (gameManager.getGenerator() == 1 || gameManager.getGenerator() == 0) ? " générateur" : " générateurs";
    }

}