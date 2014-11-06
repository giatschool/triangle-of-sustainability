package de.ifgi.worldwind.layer;

import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.layers.IconLayer;
import gov.nasa.worldwind.render.UserFacingIcon;
import gov.nasa.worldwind.render.WWIcon;
import java.util.List;

import de.ifgi.worldwind.layer.WWXMLIconLayer.ClickableIcon;

public class IfgiLayer extends IconLayer {
	List<WWIcon> icons;
	public IfgiLayer(List<WWIcon> icons, double altitude){
		super();
//		setMaxActiveAltitude(altitude);
//		this.icons=icons;
//		for(WWIcon icon:icons)addIcon(icon);
	}
	
//	private WWIcon lastToolTipIcon = null;
//	public void selected(SelectEvent event) {
//    	if(event.hasObjects()
//	    	&& event.getTopObject() instanceof ClickableIcon 
//    		&& ((ClickableIcon)event.getTopObject()).getParent() != this) 
//    		return;
//    	
//    	if (event.getEventAction().equals(SelectEvent.LEFT_CLICK)) {
//    		if (event.hasObjects()) {
//    			if (event.getTopObject() instanceof ClickableIcon){    				ClickableIcon icon = (ClickableIcon)event.getTopObject();
//    				if(icon.getUrl().startsWith("http")) {
//    					// Launch local web browser
//    					try {
//    						//Desktop.getDesktop().browse(new URI(lastToolTipIcon.getUrl()));
//    						Runtime.getRuntime().exec("cmd /c start " + icon.getUrl());
//    					} catch (Exception e) {
//    					}
//    				}
//    			}
//    		}
//    	}
//    	else if (event.getEventAction().equals(SelectEvent.HOVER)) {
//    		if (lastToolTipIcon != null) {
//    			lastToolTipIcon.setShowToolTip(false);
//    			this.lastToolTipIcon = null;
//    			//this.wwd.repaint();
//    		}
//
//    		if (event.hasObjects()) {
//    			if (event.getTopObject() instanceof ClickableIcon){
//    				this.lastToolTipIcon = (ClickableIcon) event.getTopObject();
//    				lastToolTipIcon.setShowToolTip(true);
//    				//this.wwd.repaint();
//    			}
//    		}
//    	}
//    	else if (event.getEventAction().equals(SelectEvent.ROLLOVER)) {
//    		this.highlight(event.getTopObject());
//    		//this.wwd.repaint();
//    	}
//    }
//	
//	private WWIcon lastPickedIcon;
//    private void highlight(Object o) {
//        if (this.lastPickedIcon == o)
//            return; // same thing selected
//
//        if (this.lastPickedIcon != null) {
//            this.lastPickedIcon.setHighlighted(false);
//            this.lastPickedIcon = null;
//        }
//
//        if (o != null && o instanceof ClickableIcon) {
//            this.lastPickedIcon = (ClickableIcon) o;
//            this.lastPickedIcon.setHighlighted(true);
//        }
//    }
	
	@Override
	public String toString(){
		return "ifgi Layer";
	}
}
