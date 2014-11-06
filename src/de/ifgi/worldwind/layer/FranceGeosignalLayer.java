package de.ifgi.worldwind.layer;

import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;
import gov.nasa.worldwind.util.*;

import java.net.*;
import java.util.GregorianCalendar;

/**
 * France Raster layers from Geosignal WMS server http://www.geosignal.org/cgi-bin/wmsmap.
 * http://www.geosignal.fr
 * 
 * @author Patrick Murris
 * @version $Id$
 */
public class FranceGeosignalLayer extends BasicTiledImageLayer {
    public FranceGeosignalLayer() {
        super(makeLevels(new URLBuilder()));
        this.setUseTransparentTextures(true);
        this.setMaxActiveAltitude(2000e3);
    }

    private static LevelSet makeLevels(URLBuilder urlBuilder) {
        AVList params = new AVListImpl();

        params.setValue(AVKey.TILE_WIDTH, 512);
        params.setValue(AVKey.TILE_HEIGHT, 512);
        params.setValue(AVKey.DATA_CACHE_NAME, "Earth/Geosignal/Raster");
        params.setValue(AVKey.SERVICE, "http://www.geosignal.org/cgi-bin/wmsmap");
        params.setValue(AVKey.DATASET_NAME, "RASTER");
        params.setValue(AVKey.FORMAT_SUFFIX, ".dds");
        params.setValue(AVKey.NUM_LEVELS, 14);
        params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
        params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(Angle.fromDegrees(36d), Angle.fromDegrees(36d)));
        params.setValue(AVKey.SECTOR, Sector.fromDegrees(41.1632, 51.2918, -6.06258, 10.8783));
        params.setValue(AVKey.TILE_URL_BUILDER, urlBuilder);
        //params.setValue(AVKey.EXPIRY_TIME, new GregorianCalendar(2007, 7, 6).getTimeInMillis());

        return new LevelSet(params);
    }

    private static final String[] layerNames =
            {"4000", "4000", "4000", "4000", "1000", "1000", "500", "250", "100", "50", "25", "5", "5", "5", "5"};

    private static class URLBuilder implements TileUrlBuilder {
        //public URL getURL(Tile tile, String altImageFormat) throws MalformedURLException
        public URL getURL(Tile  tile, String string) throws MalformedURLException {
            String layerName = tile.getLevel().getDataset() + layerNames[tile.getLevelNumber()] + "K";
            StringBuffer sb = new StringBuffer(tile.getLevel().getService());
            if (sb.lastIndexOf("?") != sb.length() - 1)
                sb.append("?");
            sb.append("request=GetMap");
            sb.append("&layers=");
            sb.append(layerName);
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
            sb.append("&version=1.1.1");
            sb.append("&styles=");
            //System.out.println(tile.getLevelNumber() + " " + sb);

            return new java.net.URL(sb.toString());
        }
    }

    @Override
    public String toString() {
        return "France WMS Geosignal";
    }
}
