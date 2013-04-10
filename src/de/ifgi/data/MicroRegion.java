package de.ifgi.data;

import java.util.ArrayList;
/**
 * 
 * @author Umut Tas
 *
 */
public class MicroRegion {

	private String border;
	private String name;
	private ArrayList<MunicipalityDataItem> muniItems = new ArrayList<MunicipalityDataItem>();
	
	public MicroRegion(String border, String name,
			MunicipalityDataItem muniItems) {
		super();
		this.border = border;
		this.name = name;
		this.muniItems.add(muniItems);
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

	public ArrayList<MunicipalityDataItem> getMuniItems() {
		return muniItems;
	}

	public void setMuniItems(ArrayList<MunicipalityDataItem> muniItems) {
		this.muniItems = muniItems;
	}
	
	
	
}
