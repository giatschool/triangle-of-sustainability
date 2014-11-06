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

public class GeocontentWMSLayer extends BasicTiledImageLayer {

    public GeocontentWMSLayer() {
    	super(makeLevels(new URLBuilder()));
    	this.setMaxActiveAltitude(45e3);
		this.setUseTransparentTextures(true);
	}

    private static LevelSet makeLevels(URLBuilder urlBuilder) {
        AVList params = new AVListImpl();

        params.setValue(AVKey.TILE_WIDTH, 256);
        params.setValue(AVKey.TILE_HEIGHT, 256);
        params.setValue(AVKey.DATA_CACHE_NAME, "Earth/GCWMSunims2");
        params.setValue(AVKey.SERVICE, "http://217.6.21.197/wms/unimuenster.php?");
    		params.setValue(AVKey.DATASET_NAME, "UniMuenster_Germany_WMS");
    		params.setValue(AVKey.FORMAT_SUFFIX, ".dds");
    		params.setValue(AVKey.NUM_LEVELS, 20);
    		params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
    		Angle levelZeroDelta = Angle.fromDegrees(.36d); 
    		params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(levelZeroDelta, levelZeroDelta));
//    		params.setValue(AVKey.SECTOR, Sector.FULL_SPHERE);
    		//params.setValue(AVKey.SECTOR, Sector.fromDegrees(49.998341, 52.7729742, 5.672412, 10.1417782));
    		params.setValue(AVKey.SECTOR, Sector.fromDegrees(46.8526,55.2102,5.1713,15.8287));
    		params.setValue(AVKey.TILE_URL_BUILDER, urlBuilder);
    		params.setValue(AVKey.EXPIRY_TIME, new GregorianCalendar(2007, 7, 6).getTimeInMillis());

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
    			sb.append("&TRANSPARENT=TRUE");
    			//sb.append("&BGCOLOR=0xFFFFFF");

//    			System.out.println(sb.toString());

    			return new java.net.URL(sb.toString());
    		}
    	}

    	@Override
    	public String toString(){
    		return "GeoContent WMS";
    	}
    }