package de.ifgi.worldwind.layer;

import gov.nasa.worldwind.layers.IconLayer;
import gov.nasa.worldwind.render.WWIcon;

import java.util.List;

public class InfoLayer extends IconLayer {
	List<WWIcon> icons;

	public InfoLayer(List<WWIcon> icons, double altitude) {
		super();
		setMaxActiveAltitude(altitude);
		this.icons = icons;
		for (WWIcon icon : icons)
			addIcon(icon);
	}

	@Override
	public String toString() {
		return "Info Layer";
	}
}
