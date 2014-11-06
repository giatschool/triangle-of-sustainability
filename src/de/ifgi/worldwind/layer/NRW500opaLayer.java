package de.ifgi.worldwind.layer;

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

import java.net.MalformedURLException;
import java.net.URL;

 
public class NRW500opaLayer extends BasicTiledImageLayer {
	public NRW500opaLayer(){
		super(makeLevels(new URLBuilder()));
        this.setUseTransparentTextures(true);
        this.setMaxActiveAltitude(150e3);
//        this.setMinActiveAltitude(30e3);
        this.setMinActiveAltitude(20e3);
//        this.setForceLevelZeroLoads(true);
//        this.setRetainLevelZeroTiles(true);
	}
	
	private static LevelSet makeLevels(URLBuilder urlBuilder)
    {
		/* 
		 * http://www.geoserver.nrw.de/GeoOgcWms1.3/servlet/NRW500?REQUEST=GetMap&VERSION=1.1.0&
			LAYERS=Raster:UEK500:UEK500&SRS=EPSG:31466&BBOX=2560000,5660000,2574200,5674200&
			FORMAT=image/png&WIDTH=200&HEIGHT=200&TRANSPARENT=TRUE&STYLES=
		 */
		AVList params = new AVListImpl();
		params.setValue(AVKey.TILE_WIDTH, 512);
		params.setValue(AVKey.TILE_HEIGHT, 512);
		params.setValue(AVKey.DATA_CACHE_NAME, "Earth/NRW500opa");
		params.setValue(AVKey.SERVICE, "http://www.geoserver.nrw.de/GeoOgcWms1.3/servlet/NRW500");
		params.setValue(AVKey.DATASET_NAME, "Raster:UEK500:UEK500");
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
	
	private static class URLBuilder implements TileUrlBuilder
    {
        public URL getURL(Tile  tile, String string) throws MalformedURLException
        {
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

//            System.out.println(sb.toString());
            
            return new java.net.URL(sb.toString());
        }
    }

@Override
public String toString(){
	return "NRW Übersicht";
}
}
