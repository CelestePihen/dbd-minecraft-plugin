package fr.cel.dbdplugin.manager;

import java.util.HashMap;

import org.bukkit.entity.EnderCrystal;

public class GeneratorManager {

	private int charges;
	private String name;
	private EnderCrystal ec;
	private boolean isPause = false;
	
	private static HashMap<String, GeneratorManager> generators = new HashMap<>();
	
	public GeneratorManager(EnderCrystal ec, String name) {
		this.name = name;
		this.charges = 0;
		this.ec = ec;
		this.isPause = false;
		generators.put(name, this);
	}
	
	public static HashMap<String, GeneratorManager> getGenerators() {
		return generators;
	}

	public String getName() {
		return name;
	}
	
	public int getCharges() {
		return charges;
	}
	
	public void setCharges(int charges) {
		this.charges = charges;
	}
	
	public void removeCharges(int charges) {
		this.charges-=charges;
	}

	public void addCharges(int charges) {
		this.charges+=charges;
	}

	public void setPause(boolean setPause) {
		this.isPause = setPause;
	}

	public boolean isPause() {
		return this.isPause;
	}

	public EnderCrystal getEc() {
		return ec;
	}
}