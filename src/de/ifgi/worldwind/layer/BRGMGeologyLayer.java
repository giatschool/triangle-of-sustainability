/*
Copyright (C) 2001, 2006 United States Government
as represented by the Administrator of the
National Aeronautics and Space Administration.
All Rights Reserved.
*/
package de.ifgi.worldwind.layer;

import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.BasicTiledImageLayer;
import gov.nasa.worldwind.util.*;

import java.net.*;
import java.util.GregorianCalendar;

/**
 * @author tag
 * @version $Id: CountryBoundariesLayer.java 2471 2007-07-31 21:50:57Z tgaskins $
 */
public class BRGMGeologyLayer extends BasicTiledImageLayer
{
    public BRGMGeologyLayer()
    {
        super(makeLevels(new URLBuilder()));
        this.setUseTransparentTextures(true);
        this.setMaxActiveAltitude(100e3);
    }

    private static LevelSet makeLevels(URLBuilder urlBuilder)
    {
        AVList params = new AVListImpl();

        params.setValue(AVKey.TILE_WIDTH, 512);
        params.setValue(AVKey.TILE_HEIGHT, 512);
        params.setValue(AVKey.DATA_CACHE_NAME, "Earth/BRGM");
        params.setValue(AVKey.SERVICE, "http://ogcpublic.brgm.fr/geologie");
        params.setValue(AVKey.DATASET_NAME, "scan_50");
        params.setValue(AVKey.FORMAT_SUFFIX, ".jpg");
        params.setValue(AVKey.NUM_LEVELS, 20);
        params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
        params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(Angle.fromDegrees(.2d), Angle.fromDegrees(.2d)));
        params.setValue(AVKey.SECTOR, Sector.fromDegrees(41.32526397666552, 51.20139642647427, -5.625179091996616, 11.1443951255116));
        params.setValue(AVKey.TILE_URL_BUILDER, urlBuilder);
        //params.setValue(AVKey.EXPIRY_TIME, new GregorianCalendar(2007, 7, 6).getTimeInMillis());

        return new LevelSet(params);
    }

    private static class URLBuilder implements TileUrlBuilder
    {
        public URL getURL(Tile tile, String string) throws MalformedURLException
        {
            StringBuffer sb = new StringBuffer(tile.getLevel().getService());
            if (sb.lastIndexOf("?") != sb.length() - 1)
                sb.append("?");
            sb.append("request=GetMap");
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

            sb.append("&format=image/jpeg");
            sb.append("&version=1.1.1");
            sb.append("&styles=");

            return new java.net.URL(sb.toString());
        }
    }

    @Override
    public String toString()
    {
        return "France BRGM geologie";
    }
}
