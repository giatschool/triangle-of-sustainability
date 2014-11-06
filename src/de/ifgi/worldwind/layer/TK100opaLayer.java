package de.ifgi.worldwind.layer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.GregorianCalendar;

import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.avlist.AVList;
import gov.nasa.worldwind.avlist.AVListImpl;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;
import gov.nasa.worldwind.util.LevelSet;
import gov.nasa.worldwind.util.Tile;
import gov.nasa.worldwind.util.TileUrlBuilder;

//https://www.geoportal.nrw.de/ASWeb30/ASC_Frame/portal.jsp
public class TK100opaLayer  extends BasicTiledImageLayer {
	
	public TK100opaLayer(){
		super(makeLevels(new URLBuilder()));
		this.setUseTransparentTextures(true);
		this.setMaxActiveAltitude(150e3);
//		this.setMinActiveAltitude(15e3);
	}
	
	private static LevelSet makeLevels(URLBuilder urlBuilder) {
		/*
		 * http://www.geoserver.nrw.de/GeoOgcWms1.3/servlet/TK100?REQUEST=getMap&VERSION=1.1.0&
			SERVICE=WMS&LAYERS=Raster%3ATK100_KMF%3AFarbkombination&
			STYLES=&SRS=EPSG:31466&FORMAT=image/png&BGCOLOR=0xFFFFFF&TRANSPARENT=TRUE&
			WIDTH=200&HEIGHT=200&BBOX=2587000,5718000,2589000,5720000
		 */
		AVList params = new AVListImpl();
		params.setValue(AVKey.TILE_WIDTH, 512);
		params.setValue(AVKey.TILE_HEIGHT, 512);
		params.setValue(AVKey.DATA_CACHE_NAME, "Earth/NRWTK100opa");
		params.setValue(AVKey.SERVICE, "http://www.geoserver.nrw.de/GeoOgcWms1.3/servlet/TK100");
		params.setValue(AVKey.DATASET_NAME, "Raster:TK100_KMF:Farbkombination");
		params.setValue(AVKey.FORMAT_SUFFIX, ".dds");
		params.setValue(AVKey.NUM_LEVELS, 20);
		params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
		Angle levelZeroDelta = Angle.fromDegrees(.36d); 
		params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(levelZeroDelta, levelZeroDelta));
//		params.setValue(AVKey.SECTOR, Sector.FULL_SPHERE);
		params.setValue(AVKey.SECTOR, Sector.fromDegrees(49.998341, 52.7729742, 5.672412, 10.1417782));
		params.setValue(AVKey.TILE_URL_BUILDER, urlBuilder);
//		params.setValue(AVKey.EXPIRY_TIME, new GregorianCalendar(2007, 7, 6).getTimeInMillis());

		return new LevelSet(params);
	}

	private static class URLBuilder implements TileUrlBuilder {
		public URL getURL(Tile  tile, String string) throws MalformedURLException {
			StringBuffer sb = new StringBuffer(tile.getLevel().getService());
			if (sb.lastIndexOf("?") != sb.length() - 1)
				sb.append("?");
			sb.append("request=GetMap");
			sb.append("&service=WMS");
			sb.append("&layers=");
			sb.append(tile.getLevel().getDataset());
			sb.append("&srs=EPSG:4326");
			sb.append("&width=");
			sb.append(tile.getLevel().getTileWidth());
			sb.append("&height=");
			sb.append(tile.getLevel().getTileHeight());

			Sector s = tile.getSector();
			sb.append("&bbox=");
			sb.append(s.getMinLongitude().getDegrees());
			sb.append(",");
			sb.append(s.getMinLatitude().getDegrees());
			sb.append(",");
			sb.append(s.getMaxLongitude().getDegrees());
			sb.append(",");
			sb.append(s.getMaxLatitude().getDegrees());

			sb.append("&format=image/png");
			sb.append("&version=1.1.0");
			sb.append("&styles=");
			sb.append("&TRANSPARENT=FALSE");
			//sb.append("&BGCOLOR=0xFFFFFF");

//			System.out.println(sb.toString());

			return new java.net.URL(sb.toString());
		}
	}

	@Override
	public String toString(){
		return "NRW TK 100";
	}
}