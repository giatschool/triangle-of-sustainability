package de.ifgi.worldwind.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.Literal;

import de.ifgi.data.MesoRegion;
import de.ifgi.data.MicroRegion;
import de.ifgi.data.MunicipalityDataItem;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.AnnotationAttributes;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.ExtrudedPolygon;
import gov.nasa.worldwind.render.GlobeAnnotation;
import gov.nasa.worldwind.render.Material;

import gov.nasa.worldwind.render.ScreenAnnotation;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.airspaces.AirspaceAttributes;
import gov.nasa.worldwind.render.airspaces.Curtain;
import gov.nasa.worldwind.render.airspaces.Polygon;
import gov.nasa.worldwind.render.airspaces.Route;
import gov.nasa.worldwindx.examples.util.PowerOfTwoPaddedImage;

public class AmazonSoyCattleLayer extends RenderableLayer {
	
//	static ArrayList<Literal> border;
//	static ArrayList<Literal> label;
//	static ArrayList<Literal> pop0;
//	static ArrayList<Literal> pop1;
//	static ArrayList<Literal> pop2;
//	static ArrayList<Literal> pop3;
//	static ArrayList<Literal> pop4;
//	static ArrayList<Literal> pop5;
//	static ArrayList<Literal> pop6;
//	static ArrayList<Literal> pop7;
//	static ArrayList<Literal> pop8;
//	static ArrayList<Literal> pop9;
	
	ShapeAttributes sideAttributesPlain;
	ShapeAttributes sideHighlightAttributesPlain;
	ShapeAttributes capAttributesPlain;
	
	ShapeAttributes sideAttributes;
	ShapeAttributes sideHighlightAttributes;
	ShapeAttributes capAttributes;
	
	ShapeAttributes sideAttributes2;
	ShapeAttributes sideHighlightAttributes2;
	ShapeAttributes capAttributes2;
	
	ShapeAttributes sideAttributes3;
	ShapeAttributes sideHighlightAttributes3;
	ShapeAttributes capAttributes3;
	
	ShapeAttributes sideAttributes4;
	ShapeAttributes sideHighlightAttributes4;
	ShapeAttributes capAttributes4;
	
	ShapeAttributes normalAttributes;
	
	ArrayList<MunicipalityDataItem> muniData;
	ArrayList<MesoRegion> mesoRegions;

	String name;
	String nameMin;
	String nameMax;
	LatLon pos;
	LatLon posMin;
	LatLon posMax;
	String year = "";
	
	int minPos = 0;
	int maxPos = 0;
	
	double minpop =1000000000;
	double maxpop = 0;
	
    private final static PowerOfTwoPaddedImage redPic =
            PowerOfTwoPaddedImage.fromPath("images/red.png");
    private final static PowerOfTwoPaddedImage greenPic =
            PowerOfTwoPaddedImage.fromPath("images/green.png");
    private final static PowerOfTwoPaddedImage orangePic =
            PowerOfTwoPaddedImage.fromPath("images/orange.png");
    private final static PowerOfTwoPaddedImage yellowPic =
            PowerOfTwoPaddedImage.fromPath("images/yellow.png");
	
	
	public AmazonSoyCattleLayer(ArrayList<MunicipalityDataItem> muniData, ArrayList<MesoRegion> mesoRegions, String year){
		super();
		this.year = year;
		this.muniData = muniData;
		this.mesoRegions = mesoRegions;
//		this.border = border;
//		this.label = label;
//		this.pop0 = pop0;
//		this.pop1 = pop1;
//		this.pop2 = pop2;
//		this.pop3 = pop3;
//		this.pop4 = pop4;
//		this.pop5 = pop5;
//		this.pop6 = pop6;
//		this.pop7 = pop7;
//		this.pop8 = pop8;
//		this.pop9 = pop9;
		setAttributes();
		drawPolygons();
		
	}
	
	
	public void setAttributes(){
		
		 sideAttributesPlain = new BasicShapeAttributes();
         sideAttributesPlain.setInteriorMaterial(Material.WHITE);
         sideAttributesPlain.setOutlineOpacity(0.5);
         sideAttributesPlain.setInteriorOpacity(0.5);
         sideAttributesPlain.setOutlineMaterial(Material.WHITE);
         sideAttributesPlain.setOutlineWidth(1);
         sideAttributesPlain.setDrawOutline(true);
         sideAttributesPlain.setDrawInterior(true);
         sideAttributesPlain.setEnableLighting(true);
     
         sideHighlightAttributesPlain = new BasicShapeAttributes(sideAttributesPlain);
         sideHighlightAttributesPlain.setOutlineMaterial(Material.WHITE);
         sideHighlightAttributesPlain.setOutlineOpacity(0.05);

         capAttributesPlain = new BasicShapeAttributes(sideAttributesPlain);
         capAttributesPlain.setInteriorMaterial(Material.WHITE);
         capAttributesPlain.setInteriorOpacity(0.5);
         capAttributesPlain.setDrawInterior(true);
         capAttributesPlain.setEnableLighting(true);
		
		 sideAttributes = new BasicShapeAttributes();
         sideAttributes.setInteriorMaterial(Material.GREEN);
         sideAttributes.setOutlineOpacity(0.5);
         sideAttributes.setInteriorOpacity(0.5);
         sideAttributes.setOutlineMaterial(Material.GREEN);
         sideAttributes.setOutlineWidth(2);
         sideAttributes.setDrawOutline(true);
         sideAttributes.setDrawInterior(true);
         sideAttributes.setEnableLighting(true);
     
         sideHighlightAttributes = new BasicShapeAttributes(sideAttributes);
         sideHighlightAttributes.setOutlineMaterial(Material.WHITE);
         sideHighlightAttributes.setOutlineOpacity(1);

         capAttributes = new BasicShapeAttributes(sideAttributes);
         capAttributes.setInteriorMaterial(Material.GREEN);
         capAttributes.setInteriorOpacity(0.8);
         capAttributes.setDrawInterior(true);
         capAttributes.setEnableLighting(true);
         
         
         sideAttributes2 = new BasicShapeAttributes();
         sideAttributes2.setInteriorMaterial(Material.RED);
         sideAttributes2.setOutlineOpacity(0.5);
         sideAttributes2.setInteriorOpacity(0.5);
         sideAttributes2.setOutlineMaterial(Material.RED);
         sideAttributes2.setOutlineWidth(2);
         sideAttributes2.setDrawOutline(true);
         sideAttributes2.setDrawInterior(true);
         sideAttributes2.setEnableLighting(true);
     
         sideHighlightAttributes2 = new BasicShapeAttributes(sideAttributes2);
         sideHighlightAttributes2.setOutlineMaterial(Material.WHITE);
         sideHighlightAttributes2.setOutlineOpacity(1);

         capAttributes2 = new BasicShapeAttributes(sideAttributes2);
         capAttributes2.setInteriorMaterial(Material.RED);
         capAttributes2.setInteriorOpacity(0.8);
         capAttributes2.setDrawInterior(true);
         capAttributes2.setEnableLighting(true);
         
         
         sideAttributes3 = new BasicShapeAttributes();
         sideAttributes3.setInteriorMaterial(Material.YELLOW);
         sideAttributes3.setOutlineOpacity(0.5);
         sideAttributes3.setInteriorOpacity(0.5);
         sideAttributes3.setOutlineMaterial(Material.YELLOW);
         sideAttributes3.setOutlineWidth(2);
         sideAttributes3.setDrawOutline(true);
         sideAttributes3.setDrawInterior(true);
         sideAttributes3.setEnableLighting(true);
     
         sideHighlightAttributes3 = new BasicShapeAttributes(sideAttributes3);
         sideHighlightAttributes3.setOutlineMaterial(Material.WHITE);
         sideHighlightAttributes3.setOutlineOpacity(1);

         capAttributes3 = new BasicShapeAttributes(sideAttributes3);
         capAttributes3.setInteriorMaterial(Material.YELLOW);
         capAttributes3.setInteriorOpacity(0.8);
         capAttributes3.setDrawInterior(true);
         capAttributes3.setEnableLighting(true);
         
         
         sideAttributes4 = new BasicShapeAttributes();
         sideAttributes4.setInteriorMaterial(Material.ORANGE);
         sideAttributes4.setOutlineOpacity(1);
         sideAttributes4.setInteriorOpacity(1);
         sideAttributes4.setOutlineMaterial(Material.ORANGE);
         sideAttributes4.setOutlineWidth(2);
         sideAttributes4.setDrawOutline(true);
         sideAttributes4.setDrawInterior(true);
         sideAttributes4.setEnableLighting(true);
     
         sideHighlightAttributes4 = new BasicShapeAttributes(sideAttributes4);
         sideHighlightAttributes4.setOutlineMaterial(Material.WHITE);
         sideHighlightAttributes4.setOutlineOpacity(1);

         capAttributes4 = new BasicShapeAttributes(sideAttributes4);
         capAttributes4.setInteriorMaterial(Material.ORANGE);
         capAttributes4.setInteriorOpacity(1);
         capAttributes4.setDrawInterior(true);
         capAttributes4.setEnableLighting(true);
         
         
         normalAttributes = new BasicShapeAttributes();
         normalAttributes.setInteriorMaterial(Material.YELLOW);
         normalAttributes.setOutlineOpacity(0.5);
         normalAttributes.setInteriorOpacity(0.8);
         normalAttributes.setOutlineMaterial(Material.GREEN);
         normalAttributes.setOutlineWidth(2);
         normalAttributes.setDrawOutline(true);
         normalAttributes.setDrawInterior(true);
         normalAttributes.setEnableLighting(true);
	}
	
	public void drawPolygons(){
		  ExtrudedPolygon pgon;
		  Polygon pgon2;
		  Polygon pgon3;

		  double pop = 0;
        	
		  
//		  double maxDefor=0;
//		  double minDefor=2000000;
//		  for(int j=0;j< mesoRegions.size();j++){
//			  if(year =="2002"){
//	          		pop =  muniData.get(j).getTotalAcum2002();
//	          		System.out.println(muniData.get(j).getTotalPast06());
//	          		if(pop > maxpop){
//	          			maxpop = pop;
//	          			maxPos = j;
//	          		}
//	          		if(pop < minpop){
//	          			minpop = pop;
//	          			minPos = j;
//	          		}
//		  }
//		  }
//          
          // Create a path, set some of its properties and set its attributes.
          for(int i=0;i< mesoRegions.size();i++){
        	  for(MicroRegion micro: mesoRegions.get(i).getMicoRegions() ){
        	  
   //     	  System.out.println("ACUM: "+muniData.get(i).getTotalAcum2002());
        	  
        	  
//        	  double defor = 0;
//        	  if(year =="2002"){
//        		  
//        	   defor = muniData.get(i).getTotalDefor2002();
//        	  }
//        	  if(year =="2003"){
//            	  defor = muniData.get(i).getTotalDefor2003();
//            	  }
//        	  if(year =="2004"){
//            	   defor = muniData.get(i).getTotalDefor2004();
//            	  }
//        	  if(year =="2005"){
//            	   defor = muniData.get(i).getTotalDefor2005();
//            	  }
//        	  if(year =="2006"){
//            	   defor = muniData.get(i).getTotalDefor2006();
//            	  }
//        	  if(year =="2007"){
//            	   defor = muniData.get(i).getTotalDefor2007();
//            	  }
//        	  if(year =="2008"){
//            	   defor = muniData.get(i).getTotalDefor2008();
//            	  }
//        	  if(defor > maxDefor){
//        		  maxDefor = defor;
//        	  }
//        	  if(defor < minDefor){
//        		  minDefor = defor;
//        	  }
        	  
        	  
        
        	  
        //  	String defor2008String = muniData.get(i).get(i).toString().replace("^^http://www.w3.org/2001/XMLSchema#integer", "");
          	
//          	if(year =="2002"){
//          		pop =  muniData.get(i).getTotalAcum2002();
//          //		pop = muniData.get(i).getSoja04().get(0);
//          	//	System.out.println(pop);
////          		if(pop > maxpop){
////          			maxpop = pop;
////          		}
////          		if(pop < minpop){
////          			minpop = pop;
////          		}
//        //  	pop = muniData.get(i).getPop2002();
//          	 }
//          	if(year =="2003"){
//              	pop = muniData.get(i).getPop2003();
//              	 }
//          	if(year =="2004"){
//              	pop = muniData.get(i).getPop2004();
//              	 }
//          	if(year =="2005"){
//              	pop = muniData.get(i).getPop2005();
//              	 }
//          	if(year =="2006"){
//              	pop = muniData.get(i).getPop2006();
//              	 }
//          	if(year =="2007"){
//              	pop = muniData.get(i).getPop2007();
//              	 }
//          	if(year.equals("2008")){
//              	pop = muniData.get(i).getPop2008();
//              	 }
        	  
           String[] borderStringArray =micro.getBorder().split(";");
           
           double totalSoja = 0.0;
           double totalCattle = 0.0;
           double totalArea = 0.0;
           
        	   for(MunicipalityDataItem muniItem :micro.getMuniItems()){
        		   totalSoja +=muniItem.getSoja08();
        		   for(int k=0; k< muniItem.getOverlapArea().size();k++){
        			   totalArea +=muniItem.getOverlapArea().get(k);
        		   }
        	   
           }
        	   for(MunicipalityDataItem muniItem :micro.getMuniItems()){
        		   totalCattle +=muniItem.getCattle08();
        	   
           }
           
          	ArrayList<Position> borderPositions = new ArrayList<Position>();
          	for(String str: borderStringArray){
          		String[] splitStr = str.split(",");	
          		borderPositions.add(Position.fromDegrees(Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[0]), totalCattle *7000.0/totalArea ));
          	}
          
          	pgon = new ExtrudedPolygon(borderPositions);
          	System.out.println(totalSoja);
          	
          	if(totalSoja > 0){
        	ArrayList<Position> borderPositions2 = new ArrayList<Position>();
        	for(String str: borderStringArray){
          		String[] splitStr = str.split(",");	
          	//	borderPositions2.add(Position.fromDegrees(Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[0]), new Double("1000000")));
          		borderPositions2.add(Position.fromDegrees(Double.parseDouble(splitStr[1]), Double.parseDouble(splitStr[0])));

          	}
//          	ArrayList<Position> tempBorders = new ArrayList<Position>();
//          	for(int k = borderPositions2.size()-1;k >=0;k--){
//          		tempBorders.add(new Position(borderPositions2.get(k).latitude, borderPositions2.get(k).longitude, pop *1000000 +1000000));
//          	}
//          //	borderPositions2.addAll(tempBorders);
          	
          		
//          		ArrayList<String> textures = new ArrayList<String>();
//              	textures.add("images/cow.png");
//              	
         	pgon2 = new Polygon(borderPositions2);
//          	
            pgon2.setAltitudes(1.0+ totalCattle *7000.0/totalArea, 1.0+totalCattle *7000.0/totalArea + totalSoja *20000.0/totalArea );
           Material brown = new Material(new Color(138,90,43));
            pgon2.getAttributes().setMaterial(brown);
           addRenderable(pgon2);
          	}
//           pgon3 = new Polygon(borderPositions2);
//           
//           pgon3.setAltitudes(pop * 1000000 * (1-muniData.get(i).getTotalPermAgr06()+muniData.get(i).getTotalTempAgr06()), pop *1000000);
//           pgon3.getAttributes().setMaterial(Material.YELLOW);
          //	addRenderable(pgon3);
          //	pgon2.getAttributes()
          	
          	if(i == minPos){
          		posMin = pgon.getReferenceLocation();
          		
          	}
          	if(i == maxPos){
          		posMax = pgon.getReferenceLocation();
          	}
          	pos = pgon.getReferenceLocation();
          	name = muniData.get(i).getUri();
         	 pgon.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
       //  	 pgon2.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
         	//SETTING COLOR ACCORDING TO DEFORESTATION
//         	 if(defor < 0.01){
//          	 pgon.setSideAttributes(sideAttributes);
//          	 pgon.setSideHighlightAttributes(sideHighlightAttributes);
//          	 pgon.setCapAttributes(capAttributes);
//         	 }
//         	 if(defor< 0.02 && defor > 0.01){
//              	 pgon.setSideAttributes(sideAttributes3);
//              	 pgon.setSideHighlightAttributes(sideHighlightAttributes3);
//              	 pgon.setCapAttributes(capAttributes3);
//
//         	 }
//         	 if(defor >0.02 && defor <0.04){
//         		 pgon.setSideAttributes(sideAttributes4);
//              	 pgon.setSideHighlightAttributes(sideHighlightAttributes4);
//              	 pgon.setCapAttributes(capAttributes4); 
//         	 }
//         	 if(defor >0.04){
//         		pgon.setSideAttributes(sideAttributes2);
//             	 pgon.setSideHighlightAttributes(sideHighlightAttributes2);
//             	 pgon.setCapAttributes(capAttributes2); 
//         	 }

         //	pgon2.setAttributes(normalAttributes);
//        	 pgon2.setSideHighlightAttributes(sideHighlightAttributesPlain);
//        	 pgon2.setCapAttributes(capAttributesPlain); 
        		
         	 pgon.setSideAttributes(sideAttributes3);
         	 pgon.setSideHighlightAttributes(sideHighlightAttributes3);
         	 pgon.setCapAttributes(capAttributes3);
         	addRenderable(pgon);
         	drawArc();
          	
          }
          }
//         System.out.println("min: "+minDefor +" max: "+maxDefor);
	}
	
	public void drawArc(){
		
	//	AirspaceAttributes attributesPlain = new Air();
//   //     attributesPlain.setInteriorMaterial(Material.YELLOW);
//       .setOutlineOpacity(1);
//        attributesPlain.setOutlineWidth(10);
//        
//      //  attributesPlain.setInteriorOpacity(1);
//        attributesPlain.setOutlineMaterial(Material.BLACK);
//        attributesPlain.setOutlineWidth(1);
//        attributesPlain.setDrawOutline(true);
//        attributesPlain.setDrawInterior(true);
//     //   attributesPlain.setEnableLighting(true);
		
        Curtain curtain = new Curtain();
        curtain.setLocations(Arrays.asList(LatLon.fromDegrees(24.686952,116.68945), LatLon.fromDegrees(-5.878332,-68.360596)));
        curtain.setAltitudes(100000.0, 200000.0);
        curtain.setTerrainConforming(false, false);
        curtain.setValue(AVKey.DISPLAY_NAME, "Great arc Curtain from America to Japan.");
        curtain.getAttributes().setMaterial(Material.YELLOW);
        curtain.getAttributes().setOutlineOpacity(1);
        curtain.getAttributes().setOutlineWidth(10);
        curtain.setPathType(AVKey.RHUMB_LINE);

       //  attributesPlain.setInteriorOpacity(1);
        curtain.getAttributes().setOutlineMaterial(Material.YELLOW);
        curtain.getAttributes().setDrawOutline(true);
        curtain.getAttributes().setDrawInterior(true);
      //   attributesPlain.setEnableLighting(true);
      //  addRenderable(curtain);

        curtain = new Curtain();
        curtain.setLocations(Arrays.asList(LatLon.fromDegrees(27.0, -112.0), LatLon.fromDegrees(35.0, 138.0)));
        curtain.setPathType(AVKey.RHUMB_LINE);
        curtain.setAltitudes(1000.0, 100000.0);
        curtain.setTerrainConforming(false, false);
        curtain.setValue(AVKey.DISPLAY_NAME, "Rhumb Curtain from America to Japan.");
       
        
        Route route = new Route();
        route.setAltitudes(20000.0, 50000.0);
        route.setWidth(170000.0);
        route.setLocations(Arrays.asList(
            LatLon.fromDegrees(-11.178402,-49.533692),
            LatLon.fromDegrees(48.69096,10.583495)
//            LatLon.fromDegrees(44.0, -120.0),
//            LatLon.fromDegrees(43.0, -120.0)
            ));
     //   route.setTerrainConforming(false, false);
        route.getAttributes().setMaterial(Material.GREEN);
        route.getAttributes().setOutlineOpacity(1);
     //   curtain.getAttributes().setOutlineWidth(10);
       // curtain.setPathType(AVKey.RHUMB_LINE);
        route.setTerrainConforming(false, false);
        route.getAttributes().setOpacity(0.8);
        route.getAttributes().setOutlineMaterial(Material.BLACK);
        route.getAttributes().setDrawOutline(true);
        route.getAttributes().setDrawInterior(true);
        Iterator<? extends LatLon> posis =route.getLocations().iterator();
        posis.next();
        addRenderable(exportAnnotation(new Position( posis.next(), 50000.0), "Soja Export nach Europa"));
        addRenderable(route);
        
        Route route2 = new Route();
        route2.setAltitudes(20000.0, 50000.0);
        route2.setWidth(30000.0);
        
        route2.setLocations(Arrays.asList(
        		LatLon.fromDegrees(24.686952,116.68945),
        		LatLon.fromDegrees(21.371244,-156.196291),
            LatLon.fromDegrees(-5.878332,-68.360596)
            
//            LatLon.fromDegrees(44.0, -120.0),
//            LatLon.fromDegrees(43.0, -120.0)
            ));
     //   route2.setTerrainConforming(false, false);
        route2.getAttributes().setMaterial(Material.YELLOW);
        route2.getAttributes().setOutlineOpacity(1);
     //   curtain.getAttributes().setOutlineWidth(10);
    //    curtain.setPathType(AVKey.RHUMB_LINE);
        route2.setTerrainConforming(false, false);
        route2.getAttributes().setOpacity(0.8);
        addRenderable(exportAnnotation(route2.getReferencePosition(), "Soja Export nach China"));
       //  attributesPlain.setInteriorOpacity(1);
        route2.getAttributes().setOutlineMaterial(Material.BLACK);
        route2.getAttributes().setDrawOutline(true);
        route2.getAttributes().setDrawInterior(true);
        
        addRenderable(route2);
	}
	
	public GlobeAnnotation exportAnnotation(Position position, String str){
        AnnotationLayer annotationLayer = new AnnotationLayer();

		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
        defaultAttributes.setCornerRadius(10);
        defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
        defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
        defaultAttributes.setTextColor(Color.WHITE);
        defaultAttributes.setDrawOffset(new Point(25, 25));
        defaultAttributes.setDistanceMinScale(.5);
        defaultAttributes.setDistanceMaxScale(2);
        defaultAttributes.setDistanceMinOpacity(.5);
        defaultAttributes.setLeaderGapWidth(14);
        defaultAttributes.setDrawOffset(new Point(20, 40));
        
		 GlobeAnnotation ga2 = new GlobeAnnotation(str, position, Font.decode("Arial-BOLD-12"));
	        ga2.getAttributes().setDefaults(defaultAttributes);

	    
	    return ga2;
	}
	
	
	public AnnotationLayer addAnnotations(){
		
        AnnotationLayer annotationLayer = new AnnotationLayer();
        annotationLayer.setName(year +" population");
		
	    // Using a ScreenAnnotation
		AnnotationAttributes defaultAttributes = new AnnotationAttributes();
        defaultAttributes.setCornerRadius(10);
        defaultAttributes.setInsets(new Insets(8, 8, 8, 8));
        defaultAttributes.setBackgroundColor(new Color(00, 00, 99));
        defaultAttributes.setTextColor(Color.WHITE);
        defaultAttributes.setDrawOffset(new Point(25, 25));
        defaultAttributes.setDistanceMinScale(.5);
        defaultAttributes.setDistanceMaxScale(2);
        defaultAttributes.setDistanceMinOpacity(.5);
        defaultAttributes.setLeaderGapWidth(14);
        defaultAttributes.setDrawOffset(new Point(20, 40));
		
        ScreenAnnotation sa = new ScreenAnnotation("Gesamte- und Jährliche Abholzung des Regenwaldes in Pará, Brazilien", new Point(900, 1000));
        sa.getAttributes().setDefaults(defaultAttributes);
        sa.getAttributes().setCornerRadius(0);
        sa.getAttributes().setFont(Font.decode("Arial-BOLD-36"));
       sa.getAttributes().setSize(new Dimension(1300, 0));
     //   sa.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
       
        sa.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        sa.getAttributes().setHighlightScale(1);             // No highlighting either
        annotationLayer.addAnnotation(sa);
        
        ScreenAnnotation sa2 = new ScreenAnnotation(year, new Point(1750, 60));
        sa2.getAttributes().setDefaults(defaultAttributes);
        sa2.getAttributes().setCornerRadius(0);
        sa2.getAttributes().setSize(new Dimension(50, 0));
        sa2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
        sa2.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        sa2.getAttributes().setHighlightScale(1);             // No highlighting either
        annotationLayer.addAnnotation(sa2);
        
        AnnotationAttributes defaultAttributes2 = new AnnotationAttributes();
        defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
        defaultAttributes2.setTextColor(Color.WHITE);
        defaultAttributes2.setLeaderGapWidth(14);
        defaultAttributes2.setInsets(new Insets(8, 8, 8, 8));
 //       defaultAttributes2.setBackgroundColor(new Color(0f, 0f, 0f, .5f));

        defaultAttributes2.setCornerRadius(0);
        defaultAttributes2.setSize(new Dimension(300, 0));
        defaultAttributes2.setAdjustWidthToText(AVKey.SIZE_FIT_TEXT); // use strict dimension width - 200
        defaultAttributes2.setFont(Font.decode("Arial-BOLD-12"));
        defaultAttributes2.setBorderWidth(0);
        defaultAttributes2.setHighlightScale(1);             // No highlighting either
        defaultAttributes2.setCornerRadius(0);
        
        ScreenAnnotation legendHeader = new ScreenAnnotation("Jährliche Abholzung des Regenwaldes in % (" + year +")", new Point(1750, 125));
        legendHeader.getAttributes().setDefaults(defaultAttributes2);
        legendHeader.getAttributes().setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, .5f));
        legendHeader.getAttributes().setCornerRadius(0);
        legendHeader.getAttributes().setFont(Font.decode("Arial-BOLD-15"));
        legendHeader.getAttributes().setSize(new Dimension(250, 170));
     //   legendHeader.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
     //   legendHeader.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        legendHeader.getAttributes().setHighlightScale(1);             // No highlighting either
    //    legend.getAttributes().setImageOffset(new Point(1, 1));
        annotationLayer.addAnnotation(legendHeader);
        
        ScreenAnnotation legend = new ScreenAnnotation(">4%", new Point(1700, 220));
        legend.getAttributes().setDefaults(defaultAttributes2);
        legend.getAttributes().setImageSource(redPic.getPowerOfTwoImage());
        legend.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
        legend.getAttributes().setCornerRadius(0);
//        legend.getAttributes().setSize(new Dimension(50, 0));
        legend.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
        legend.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        legend.getAttributes().setHighlightScale(1);             // No highlighting either
    //    legend.getAttributes().setImageOffset(new Point(1, 1));
        legend.getAttributes().setInsets(new Insets(0, 40, 0, 0));
        annotationLayer.addAnnotation(legend);
		
        ScreenAnnotation legend2 = new ScreenAnnotation("2-4%", new Point(1700, 190));
        legend2.getAttributes().setDefaults(defaultAttributes2);
        legend2.getAttributes().setImageSource(orangePic.getPowerOfTwoImage());
        legend2.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
        legend2.getAttributes().setCornerRadius(0);
     //   legend2.getAttributes().setSize(new Dimension(50, 0));
        legend2.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
        legend2.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        legend2.getAttributes().setHighlightScale(1);             // No highlighting either
        legend2.getAttributes().setInsets(new Insets(0, 40, 0, 0));
        annotationLayer.addAnnotation(legend2);
        
        ScreenAnnotation legend3 = new ScreenAnnotation("1-2%", new Point(1700, 160));
        legend3.getAttributes().setDefaults(defaultAttributes2);
        legend3.getAttributes().setImageSource(yellowPic.getPowerOfTwoImage());
        legend3.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
        legend3.getAttributes().setCornerRadius(0);
   //     legend3.getAttributes().setSize(new Dimension(50, 0));
        legend3.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
        legend3.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        legend3.getAttributes().setHighlightScale(1);             // No highlighting either
        legend3.getAttributes().setInsets(new Insets(0, 40, 0, 0));

        annotationLayer.addAnnotation(legend3);
        
        ScreenAnnotation legend4 = new ScreenAnnotation("<1%", new Point(1700, 130));
        legend4.getAttributes().setDefaults(defaultAttributes2);
        legend4.getAttributes().setImageSource(greenPic.getPowerOfTwoImage());
        legend4.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
        legend4.getAttributes().setCornerRadius(0);
 //       legend4.getAttributes().setSize(new Dimension(50, 0));
        legend4.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED); // use strict dimension width - 200
        legend4.getAttributes().setDrawOffset(new Point(100, 0)); // screen point is annotation bottom left corner
        legend4.getAttributes().setHighlightScale(1);             // No highlighting either
        legend4.getAttributes().setInsets(new Insets(0, 40, 0, 0));

        annotationLayer.addAnnotation(legend4);
        
        
//        GlobeAnnotation ga = new GlobeAnnotation(""+(int)(minpop*100)+"%", (Position) posMin, Font.decode("Arial-BOLD-12"));
//            ga.getAttributes().setDefaults(defaultAttributes);
//
//        annotationLayer.addAnnotation(ga);
        
        GlobeAnnotation ga2 = new GlobeAnnotation(""+(int)(maxpop*100)+"%", (Position) posMax, Font.decode("Arial-BOLD-12"));
        ga2.getAttributes().setDefaults(defaultAttributes);

    annotationLayer.addAnnotation(ga2);
      
        return annotationLayer;
	}


}

