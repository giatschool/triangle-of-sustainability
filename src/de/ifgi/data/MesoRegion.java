package de.ifgi.data;

import java.util.ArrayList;

public class MesoRegion {

	private String border;
	private String name;
	private ArrayList<MicroRegion> micoRegions = new ArrayList<MicroRegion>();
	
	
	
	public MesoRegion(String border, String name,
			MicroRegion micoRegions) {
		super();
		this.border = border;
		this.name = name;
		this.micoRegions.add(micoRegions);
	}
	
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<MicroRegion> getMicoRegions() {
		return micoRegions;
	}
	public void setMicoRegions(ArrayList<MicroRegion> micoRegions) {
		this.micoRegions = micoRegions;
	}
	
	
}
