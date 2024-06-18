package fr.cel.dbdplugin.manager;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.EnderCrystal;

public class GeneratorManager {

	@Setter @Getter private int charges;
	@Getter private final String name;
	@Getter private final EnderCrystal ec;
	private boolean isPause = false;
	
	@Getter
    private final static HashMap<String, GeneratorManager> generators = new HashMap<>();
	
	public GeneratorManager(EnderCrystal ec, String name) {
		this.name = name;
		this.charges = 0;
		this.ec = ec;
		this.isPause = false;
		generators.put(name, this);
	}

    public void addCharges(int charges) {
		this.charges += charges;
	}

	public void removeCharges(int charges) {
		this.charges -= charges;
	}

	public void setPause(boolean setPause) {
		this.isPause = setPause;
	}

	public boolean isPause() {
		return this.isPause;
	}

}