package fr.cel.dbdplugin.manager;

import java.util.HashMap;

import org.bukkit.entity.EnderCrystal;

public class GeneratorManager {

	private int heart;
	public String name;
	public EnderCrystal ec;
	
	public static HashMap<String, GeneratorManager> generators = new HashMap<>();
	
	public GeneratorManager(EnderCrystal ec, String name) {
		this.name = name;
		this.heart = 1000;
		this.ec = ec;
		generators.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getLife() {
		return heart;
	}
	
	public void setHeart(int heart) {
		this.heart = heart;
	}
	
	public void removeHeart(int i) {
		this.heart-=i;
	}

	public void addHeart(int i) {
		this.heart+=i;
	}

	public EnderCrystal getEc() {
		return ec;
	}
}