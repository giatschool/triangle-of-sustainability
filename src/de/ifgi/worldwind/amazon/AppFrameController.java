package de.ifgi.worldwind.amazon;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.view.orbit.OrbitView;

import java.util.ConcurrentModificationException;

import de.ifgi.worldwind.amazon.AmazonDeforestation.AppFrame;

public class AppFrameController {

	private OrbitView view;
	private AppFrame appFrame;
	private WorldWindowGLCanvas canvas;

	public AppFrameController(AppFrame appFrame) {
		this.appFrame = appFrame;
		this.canvas = appFrame.getWwd();
		this.view = (OrbitView) canvas.getView();
	}

	public void setCenterLatLon(Angle lat, Angle lon) {
		if (this.view == null)
			return;
		setCenterPosition(new Position(lat, lon, view.getCenterPosition()
				.getElevation()));
	}

	public void setCenterPosition(Position newCenter) {
		if (this.view == null)
			return;
		Position clampedCenter = clampedCenter(newCenter);
		view.setCenterPosition(clampedCenter);
	}

	public void pan(double moveY, double moveX) {
		double sinHeading = view.getHeading().sin();
		double cosHeading = view.getHeading().cos();
		double latFactor = (cosHeading * moveY + sinHeading * moveX) / 10.0;
		double lonFactor = (sinHeading * moveY - cosHeading * moveX) / 10.0;
		Angle latChange = computeLatOrLonChange(-latFactor, false);
		Angle lonChange = computeLatOrLonChange(-lonFactor, false);
		try {
			setCenterLatLon(
					this.view.getCenterPosition().getLatitude().add(latChange),
					this.view.getCenterPosition().getLongitude().add(lonChange));
		} catch (ConcurrentModificationException e) {
			System.err.println(e);
		}

	}

	public void zoom(double ratio) {
		if (this.view == null)
			return;

		final double zoomFactor = this.view.getZoom();

		int newzoom = (int) (zoomFactor * ratio);
		if (newzoom >= 1071941 && newzoom <= 18437542) {
			this.view.setZoom(newzoom);
			this.view.firePropertyChange(AVKey.VIEW, null, this.view);
		}
	}

	public void rotate(Angle angle) {
		if (this.view == null) {
			return;
		}

		Angle heading = view.getHeading();
		Angle newHeading = heading.add(angle);
		this.view.setHeading(newHeading);
		this.view.firePropertyChange(AVKey.VIEW, null, this.view);
	}

	public void tilt(Angle tilt) {
		if (this.view == null) {
			return;
		}

		Angle pitch = view.getPitch();
		Angle newPitch = pitch.add(tilt);

		if (newPitch.degrees < 0 || newPitch.degrees > 90) {
			return;
		}

		this.view.setPitch(newPitch);
		this.view.firePropertyChange(AVKey.VIEW, null, this.view);
	}

	public void flyToLatLon(LatLon latlon, double zoom) {
		Globe globe = canvas.getModel().getGlobe();
		Position position = new Position(latlon, globe.getElevation(
				latlon.getLatitude(), latlon.getLongitude()));

		flyToPosition(position, zoom);
	}

	public void flyToPosition(Position position, double zoom) {
		this.view.setEyePosition(position);
		view.setPitch(Angle.fromDegrees(35));
	}

	public void rotateToNorth(WorldWindowGLCanvas canvas) {
		this.view.setHeading(Angle.fromDegrees(0));
	}

	public void unTilt(WorldWindowGLCanvas canvas) {
		Angle pitch = Angle.fromDegrees(0);
		this.view.setPitch(pitch);
	}

	private Angle computeLatOrLonChange(double amount, boolean slow) {
		if (this.canvas == null || this.canvas.getModel() == null
				|| this.canvas.getModel().getGlobe() == null
				|| this.view == null || this.view.getEyePosition() == null) {
			return Angle.ZERO;
		}

		Position eyePos = this.view.getEyePosition();
		double normAlt = (eyePos.getElevation() / this.canvas.getModel()
				.getGlobe().getRadiusAt(eyePos));
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
		return Position.fromDegrees(lat > 90 ? 90 : (lat < -90 ? -90 : lat),
				lon > 180 ? lon - 360 : (lon < -180 ? 360 + lon : lon), elev);
	}


	/**
	 * @author Umut Tas
	 */
	public void yearForward() {
		if (appFrame.isYear2002()) {

			if (appFrame.getLayerChanger() == 0) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2002());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2002());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2002());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2003());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2003());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2003());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 1) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2003());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2003());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2003());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2004());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 2) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2004());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2005());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 3) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2005());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2006());

				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 4) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2006());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2007());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 5) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2007());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2008());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 6) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2008());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2002());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2002());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2002());
				appFrame.setLayerChanger(0);
			}
		} else {

			if (appFrame.getLayerChanger() == 0) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2004());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2005());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 1) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2005());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2006());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 2) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2006());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2007());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 3) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2007());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2008());
				appFrame.setLayerChanger(appFrame.getLayerChanger() + 1);

			} else if (appFrame.getLayerChanger() == 4) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2008());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2004());
				appFrame.setLayerChanger(0);
			}
		}

	}

	/**
	 * @author Umut Tas
	 */
	public void yearBackward() {

		if (appFrame.isYear2002()) {

			if (appFrame.getLayerChanger() == 0) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2002());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2002());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2002());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2008());

				appFrame.setLayerChanger(6);
			} else if (appFrame.getLayerChanger() == 1) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2003());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2003());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2003());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2002());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2002());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2002());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 2) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2004());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2003());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2003());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2003());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 3) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2005());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2004());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 4) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2006());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2005());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 5) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2007());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2006());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 6) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2008());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2007());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);
			}

		} else {
			if (appFrame.getLayerChanger() == 0) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2004());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2008());

				appFrame.setLayerChanger(4);

			} else if (appFrame.getLayerChanger() == 1) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2005());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2004());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2004());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 2) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2006());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2005());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2005());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 3) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2007());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2006());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2006());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);

			} else if (appFrame.getLayerChanger() == 4) {
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAma2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getAnoLayer2008());
				appFrame.getWwd().getModel().getLayers()
						.remove(appFrame.getGeneralAnoLayer2008());

				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAma2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getAnoLayer2007());
				appFrame.getWwd().getModel().getLayers()
						.add(appFrame.getGeneralAnoLayer2007());

				appFrame.setLayerChanger(appFrame.getLayerChanger() - 1);
			}
		}

	}

}
