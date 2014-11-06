package de.ifgi.worldwind.htc;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.util.Logging;
import gov.nasa.worldwind.view.orbit.OrbitView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import de.ifgi.worldwind.htc.HelloWorldWind.AppFrame;

public class AppFrameController {

	private OrbitView view;
	private AppFrame appFrame;
	private WorldWindowGLCanvas canvas;
	
	private boolean viewOutOfFocus;

	public AppFrameController(AppFrame appFrame) {
		this.appFrame = appFrame;
		this.canvas = appFrame.getWwd();
		this.view = (OrbitView)canvas.getView();
	}
	

	public void center(LatLon center){
		Angle lat=center.getLatitude();
		Angle lon=center.getLongitude();
		setCenterLatLon(lat, lon);
	}

	public void setCenterLatLon(Angle lat, Angle lon) {
		if (this.view == null)
			return;
		setCenterPosition(new Position(lat, lon, view.getCenterPosition().getElevation()));
	}

	public void setCenterPosition(Position newCenter) {
		if (this.view == null)
			return;
		Position clampedCenter = clampedCenter(newCenter);
		view.setCenterPosition(clampedCenter);
	}

	//TODO: check out package gov.nasa.worldwind.awt.OrbitViewInputBroker;
	public void pan(Position prevPosition, Position curPosition){
		 setCenterLatLon(
                 this.view.getCenterPosition().getLatitude().add(prevPosition.getLatitude()).subtract(curPosition.getLatitude()),
                 this.view.getCenterPosition().getLongitude().add(prevPosition.getLongitude()).subtract(curPosition.getLongitude()));
//		System.out.println("pan position");
	}
	
	
	public void pan(double moveY, double moveX){
		double sinHeading = view.getHeading().sin();
		double cosHeading = view.getHeading().cos();
		double latFactor = (cosHeading * moveY + sinHeading * moveX) / 10.0;
		double lonFactor = (sinHeading * moveY - cosHeading * moveX) / 10.0;
		Angle latChange = computeLatOrLonChange(-latFactor, false);
		Angle lonChange = computeLatOrLonChange(-lonFactor, false);
		try{
			setCenterLatLon(
					this.view.getCenterPosition().getLatitude().add(latChange),
					this.view.getCenterPosition().getLongitude().add(lonChange));
		}catch (ConcurrentModificationException e){
			System.err.println(e);
		}

//		System.out.println("pan DIR");
//		System.out.println(moveY);
		
	}

	public void zoom(double ratio){
		if (this.view == null)
            return;
		
		final double zoomFactor = this.view.getZoom();
		System.out.println(zoomFactor);
		
     //   this.view.stopStateIterators();
		if(zoomFactor > 10 && zoomFactor < 18437542){
		    this.view.setZoom(zoomFactor*ratio);
		    this.view.firePropertyChange(AVKey.VIEW, null, this.view);
		}
//        System.out.println("zoom");
	}
	
	public void rotate(Angle angle) {
		if (this.view == null){
			return;
		}
		
		Angle heading = view.getHeading();
		Angle newHeading = heading.add(angle);
		
//        this.view.stopStateIterators();
        this.view.setHeading(newHeading);
        this.view.firePropertyChange(AVKey.VIEW, null, this.view);
	}

	public void  tilt(Angle tilt){
		if (this.view == null){
			return;
		}
		
//		if (isViewOutOfFocus()){
//			focusView();
//		}
		 
		Angle pitch = view.getPitch();
		Angle newPitch = pitch.add(tilt);
		//Angle clampedPith = clampedPitch(newPitch);
		
		if(newPitch.degrees<0 || newPitch.degrees>90 ){
			return;
		}

//		this.view.stopStateIterators();
        this.view.setPitch(newPitch);
        this.view.firePropertyChange(AVKey.VIEW, null, this.view);
//        setViewOutOfFocus(true);
	}
	
	public void flyToLatLon(LatLon latlon, double zoom){
		Globe globe = canvas.getModel().getGlobe();
		Position position = new Position(latlon, globe.getElevation(latlon.getLatitude(), latlon.getLongitude()));
    	
    	flyToPosition(position, zoom);
	}
	
	public void flyToPosition(Position position, double zoom){
		Globe globe = canvas.getModel().getGlobe();
		Angle heading = view.getHeading();
    	Angle pitch = view.getPitch();
    	
    	
    	
    	this.view.setEyePosition(position);
    	view.setPitch(Angle.fromDegrees(35));
//    	this.view.applyStateIterator(FlyToOrbitViewStateIterator.createPanToIterator(this.view, globe, position, heading, pitch, zoom));
	}

	public double multiPositionDistance(ArrayList<Position> positions){
		double distance=0.0;
		for(int i=0;i<positions.size()-1;i++){
			Position p1 = positions.get(i);
			Position p2 = positions.get(i+1);
			double ellipsoidaldistance = LatLon.ellipsoidalDistance(p1, p2, 
					canvas.getModel().getGlobe().getEquatorialRadius(),
					canvas.getModel().getGlobe().getPolarRadius());
			distance=distance+ellipsoidaldistance;
		}
		return distance;
	}

	
	public void rotateToNorth(WorldWindowGLCanvas canvas) {
		Angle heading = Angle.fromDegrees(0);
		
		this.view.setHeading(heading);
	//	this.view.stopStateIterators();
	}

	public void unTilt(WorldWindowGLCanvas canvas) {
		Angle pitch = Angle.fromDegrees(0);
		
		this.view.setPitch(pitch);
//		this.view.stopStateIterators();
	}

	private Angle computeLatOrLonChange(double amount, boolean slow) {
		if (this.canvas == null
				|| this.canvas.getModel() == null
				|| this.canvas.getModel().getGlobe() == null
				|| this.view == null
				|| this.view.getEyePosition() == null) {
			return Angle.ZERO;
		}

		Position eyePos = this.view.getEyePosition();
		double normAlt = (eyePos.getElevation() / this.canvas.getModel().getGlobe().getRadiusAt(eyePos));
		if (normAlt < 0)
			normAlt = 0;
		else if (normAlt > 1)
			normAlt = 1;

		double coeff = (0.0001 * (1 - normAlt)) + (2 * normAlt);
		if (slow)
			coeff /= 4.0;

		return Angle.fromDegrees(coeff * amount);
	}
	
	private static Position clampedCenter(Position unclampedCenter) {
        if (unclampedCenter == null)
            return null;

        // Clamp latitude to the range [-90, 90],
        // Normalize longitude to the range [-180, 180],
        // Don't change elevation.
        double lat = unclampedCenter.getLatitude().degrees;
        double lon = unclampedCenter.getLongitude().degrees;
        double elev = unclampedCenter.getElevation();
        lon = lon % 360;
        return Position.fromDegrees(
                lat > 90 ? 90 : (lat < -90 ? -90 : lat),
                lon > 180 ? lon - 360 : (lon < -180 ? 360 + lon : lon),
                elev);
    }
	
	 private static Angle clampedPitch(Angle unclampedPitch) {
	        if (unclampedPitch == null)
	            return null;

	        // Clamp pitch to the range [0, 90].
	        double pitch = unclampedPitch.degrees;
	        return Angle.fromDegrees(pitch > 90 ? 90 : (pitch < 0 ? 0 : pitch));
	    }

	public boolean isViewOutOfFocus() {
		return viewOutOfFocus;
	}

	public void setViewOutOfFocus(boolean viewOutOfFocus) {
		this.viewOutOfFocus = viewOutOfFocus;
	}
	
	private void focusView() {
		if (this.view == null)
			return;

		try {
			// Update the View's focus.
			if (this.view.canFocusOnViewportCenter())
			{
				this.view.focusOnViewportCenter();
				setViewOutOfFocus(false);
			}
		} catch (Exception e) {
			String message = Logging.getMessage("generic.ExceptionWhileChangingView");
			Logging.logger().log(java.util.logging.Level.SEVERE, message, e);
			// If updating the View's focus failed, raise the flag again.
			setViewOutOfFocus(true);
		}
	}
	
	
	
	public void yearForward() {

		if (appFrame.getLayerChanger() == 0) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2002());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2002());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2003());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2003());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);
		} else if (appFrame.getLayerChanger() == 1) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2003());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2003());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2004());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2004());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

		} else if (appFrame.getLayerChanger() == 2) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2004());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2004());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2005());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2005());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

		} else if (appFrame.getLayerChanger() == 3) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2005());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2005());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2006());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2006());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

		} else if (appFrame.getLayerChanger() == 4) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2006());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2006());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2007());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2007());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

		} else if (appFrame.getLayerChanger() == 5) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2007());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2007());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2008());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2008());
			appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

		} else if (appFrame.getLayerChanger() == 6) {
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAmaPopAcumLayer2008());
			appFrame.getWwd().getModel().getLayers()
					.remove(appFrame.getAnoLayer2008());

			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAmaPopAcumLayer2002());
			appFrame.getWwd().getModel().getLayers()
					.add(appFrame.getAnoLayer2002());
			appFrame.setLayerChanger(0);
		}

	}
	
	public void yearBackward(){
		
	      if( appFrame.getLayerChanger()==0){
	      appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2002());
	      appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2002());
	     
	      appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2008());
	      appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2008());
	      appFrame.setLayerChanger(6);
	  }
	  else if( appFrame.getLayerChanger()==1){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2003());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2003());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2002());
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2002());
	      appFrame.setLayerChanger( appFrame.getLayerChanger()-1);

	  }
	  else if( appFrame.getLayerChanger()==2){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2004());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2004());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2003());
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2003());
	      appFrame.setLayerChanger( appFrame.getLayerChanger()-1);

	  }
	  else if( appFrame.getLayerChanger()==3){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2005());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2005());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2004());
	      appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2004());
	      appFrame.setLayerChanger( appFrame.getLayerChanger()-1);

	  }
	  else if( appFrame.getLayerChanger()==4){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2006());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2006());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2005());
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2005());
	      appFrame.setLayerChanger( appFrame.getLayerChanger()-1);

	  }
	  else if( appFrame.getLayerChanger()==5){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2007());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2007());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2006());
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2006());
	      appFrame.setLayerChanger( appFrame.getLayerChanger()-1);

	  }
	  else if(appFrame.getLayerChanger() ==6){
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAmaPopAcumLayer2008());
		  appFrame.getWwd().getModel().getLayers().remove(appFrame.getAnoLayer2008());
	     
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAmaPopAcumLayer2007());
		  appFrame.getWwd().getModel().getLayers().add(appFrame.getAnoLayer2007());
	      appFrame.setLayerChanger(appFrame.getLayerChanger()-1);
	  }

			
		}
	
}
