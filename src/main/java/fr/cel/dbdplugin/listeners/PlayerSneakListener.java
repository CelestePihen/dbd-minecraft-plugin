package fr.cel.dbdplugin.listeners;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.cel.dbdplugin.DBDPlugin;
import fr.cel.dbdplugin.manager.GameManager;
import fr.cel.dbdplugin.manager.GameState;
import fr.cel.dbdplugin.manager.GeneratorManager;
import net.kyori.adventure.text.Component;

public class PlayerSneakListener implements Listener {
    
	private final DBDPlugin main;
	private final GameManager gameManager;

    public PlayerSneakListener(DBDPlugin main, GameManager gameManager) {
		this.main = main;
		this.gameManager = gameManager;
	}

	// jsp quel event mettre juste pour detect un player à côté d'un EnderCrystal mdr
    @EventHandler
	public void detectGenerator(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		for (Entity entity : player.getNearbyEntities(1, 0, 1)) {
			if (entity.getType() == EntityType.END_CRYSTAL) {
				EnderCrystal ec = (EnderCrystal) entity;
				generator(ec, player, "generator1");
				generator(ec, player, "generator2");
				generator(ec, player, "generator3");
				generator(ec, player, "generator4");
				generator(ec, player, "generator5");
			}
		}

	}

    private void generator(EnderCrystal ec, Player survivor, String name) {
		GeneratorManager gen = GeneratorManager.getGenerators().get(name);
		if (gen != null) {
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
                    survivor.sendActionBar(Component.text("Générateur " + name + " : " + gen.getCharges() + " charges"));

                    // if ((Math.random() * 100) < 10) { pour les test d'habilité ?

                    // }

                }

                // générateur fini
                if (gen.getCharges() >= 500) {
				    // gen.setCharges(500);

                    enderCrystal.remove();
					GeneratorManager.getGenerators().remove(name);
					
					gameManager.removeGenerator();

					Bukkit.getOnlinePlayers().forEach(players -> {
						players.sendMessage(Component.text("Un générateur a été effectué ! Il reste " + gameManager.getGenerator() + getGeneratorString() + " à faire !", NamedTextColor.AQUA));
						players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 0.5f, 0.5f);
					});
					
					if (gameManager.getGenerator() == 0) {
						gameManager.setGameState(GameState.DOOR);
						Bukkit.broadcast(Component.text("Les portes sont maintenant activées !"));
					}

				}
				
			}
			
		}

	}

    private String getGeneratorString() {
        return (gameManager.getGenerator() == 1 || gameManager.getGenerator() == 0) ? " générateur" : " générateurs";
    }

}