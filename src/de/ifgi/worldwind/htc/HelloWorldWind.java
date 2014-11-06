package de.ifgi.worldwind.htc;


import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.Earth.CountryBoundariesLayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import kinect.SkeletonKinectHandler;

import org.apache.log4j.Logger;

import de.ifgi.data.DataRetriever;
import de.ifgi.worldwind.layer.AmazonGDPLayer;
import de.ifgi.worldwind.layer.AmazonLanduseLayer;
import de.ifgi.worldwind.layer.AmazonPopAcumLayer;

/**
 * This is the most basic World Wind program.
 *
 * @version $Id: HelloWorldWind.java 4869 2008-03-31 15:56:36Z tgaskins $
 */
public class HelloWorldWind
{
    // An inner class is used rather than directly subclassing JFrame in the main class so
    // that the main can configure system properties prior to invoking Swing. This is
    // necessary for instance on OS X (Macs) so that the application name can be specified.
    public static class AppFrame extends JFrame
    {
        AmazonPopAcumLayer amaPopAcumLayer2002;
        AmazonPopAcumLayer amaPopAcumLayer2003;
        AmazonPopAcumLayer amaPopAcumLayer2004;
        AmazonPopAcumLayer amaPopAcumLayer2005;
        AmazonPopAcumLayer amaPopAcumLayer2006;
        AmazonPopAcumLayer amaPopAcumLayer2007;
        AmazonPopAcumLayer amaPopAcumLayer2008;
        
        AmazonLanduseLayer amaLanduse2002;
        AmazonLanduseLayer amaLanduse2003;
        AmazonLanduseLayer amaLanduse2004;
        AmazonLanduseLayer amaLanduse2005;
        AmazonLanduseLayer amaLanduse2006;
        AmazonLanduseLayer amaLanduse2007;
        AmazonLanduseLayer amaLanduse2008;
        
        AmazonGDPLayer amagdp2004;
        AmazonGDPLayer amagdp2005;
        AmazonGDPLayer amagdp2006;
        AmazonGDPLayer amagdp2007;
        AmazonGDPLayer amagdp2008;
       
        AnnotationLayer anoLayer2002;
        AnnotationLayer anoLayer2003;
        AnnotationLayer anoLayer2004;
        AnnotationLayer anoLayer2005;
        AnnotationLayer anoLayer2006;
        AnnotationLayer anoLayer2007;
        AnnotationLayer anoLayer2008;

        AnnotationLayer generalAnoLayer2004;
        AnnotationLayer generalAnoLayer2005;
        AnnotationLayer generalAnoLayer2006;
        AnnotationLayer generalAnoLayer2007;
        AnnotationLayer generalAnoLayer2008;


        private Timer updater;
        private static Logger log = Logger.getLogger(HelloWorldWind.class);
        private AppFrameController controller;
//        private TouchHandler touchHandler;
//        private KinectHandler kinectHandler;

        //private final static boolean isMtwMachine=true;
        public WorldWindowGLCanvas wwd;

       
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

//        private Properties props;
        private int height = 768;
        private int width = 1024;

        public static final double GLOBE_ZOOM = 2.3e7;
        public static final Position MS_POS = Position.fromDegrees(-4.72826,-52.302247,7000000);

        int layerChanger =0;
        
        public JLayeredPane layeredPane;
       
        public AppFrame()
        {
        	
            wwd = new WorldWindowGLCanvas();
            //wwd.setPreferredSize(new java.awt.Dimension(d.width,d.height));
            wwd.setPreferredSize(new java.awt.Dimension(width,height));
        	wwd.setModel(new BasicModel());
        	wwd.setBounds(0, 0, width+1, height+1); //+1 because without it the camera image isnt shown
        	layeredPane = new JLayeredPane();
        	layeredPane.setPreferredSize(new java.awt.Dimension(width,height));
        	
        	layeredPane.add(wwd,java.awt.BorderLayout.CENTER);
        	layeredPane.setBounds(0, 0, width, height);
        	
        	layeredPane.doLayout();
        	




                addWindowListener(new WindowClosingAdapter(true));
                this.setUndecorated(true);
                this.getContentPane().add(layeredPane, java.awt.BorderLayout.CENTER);
//                this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
                this.pack();
                this.setBounds(0, 0, width, height);
                

              this.controller = new AppFrameController(this);


//            insertBeforeBeforeCompass(this.getWwd(), new OpenStreetMapLayer());



//            insertBeforeBeforeCompass(this.getWwd(), new NRWOverviewLayer());
//            insertBeforeBeforeCompass(this.getWwd(), new Tk100Layer());
//            insertBeforeBeforeCompass(this.getWwd(), new Tk50Layer());
//            insertBeforeBeforeCompass(this.getWwd(), new Tk25Layer());
              
//            insertBeforeBeforeCompass(this.getWwd(), new GeocontentWMSLayer());
              
//            insertBeforeBeforeCompass(this.getWwd(), new NrwOrthoLayer());
        //    insertBeforeBeforeCompass(this.getWwd(), new NASAWFSPlaceNameLayer());

           
           
            DataRetriever dataR = new DataRetriever();
            dataR.queryForStates();
           
            // TOTAL POPULATION AND DEFORESTATION
//            amaPopAcumLayer2002 = new AmazonPopAcumLayer(dataR.getMuniData(), "2002");
//            anoLayer2002 =amaPopAcumLayer2002.addAnnotations();
//
//            amaPopAcumLayer2003 = new AmazonPopAcumLayer(dataR.getMuniData(), "2003");
//            anoLayer2003 =amaPopAcumLayer2003.addAnnotations();
//
//            amaPopAcumLayer2004 = new AmazonPopAcumLayer(dataR.getMuniData(), "2004");
//            anoLayer2004 =amaPopAcumLayer2004.addAnnotations();
//
//            amaPopAcumLayer2005 = new AmazonPopAcumLayer(dataR.getMuniData(), "2005");
//            anoLayer2005 =amaPopAcumLayer2005.addAnnotations();
//
//            amaPopAcumLayer2006 = new AmazonPopAcumLayer(dataR.getMuniData(), "2006");
//            anoLayer2006 =amaPopAcumLayer2006.addAnnotations();
//
//            amaPopAcumLayer2007 = new AmazonPopAcumLayer(dataR.getMuniData(), "2007");
//            anoLayer2007 =amaPopAcumLayer2007.addAnnotations();
//
//            amaPopAcumLayer2008 = new AmazonPopAcumLayer(dataR.getMuniData(), "2008");
//            anoLayer2008 =amaPopAcumLayer2008.addAnnotations();
//            
//            insertBeforeBeforeCompass(this.getWwd(), amaPopAcumLayer2002);
//            insertBeforeBeforeCompass(this.getWwd(),anoLayer2002);
//           
//          updater = new Timer(5000, new ActionListener(){
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // TODO Auto-generated method stub
//                if( layerChanger==0){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2002);
//                    wwd.getModel().getLayers().remove(anoLayer2002);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2003);
//                    wwd.getModel().getLayers().add(anoLayer2003);
//                    layerChanger++;
//                }
//                else if( layerChanger==1){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2003);
//                    wwd.getModel().getLayers().remove(anoLayer2003);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2004);
//                    wwd.getModel().getLayers().add(anoLayer2004);
//                    layerChanger++;
//
//                }
//                else if( layerChanger==2){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2004);
//                    wwd.getModel().getLayers().remove(anoLayer2004);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2005);
//                    wwd.getModel().getLayers().add(anoLayer2005);
//                    layerChanger++;
//
//                }
//                else if( layerChanger==3){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2005);
//                    wwd.getModel().getLayers().remove(anoLayer2005);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2006);
//                    wwd.getModel().getLayers().add(anoLayer2006);
//                    layerChanger++;
//
//                }
//                else if( layerChanger==4){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2006);
//                    wwd.getModel().getLayers().remove(anoLayer2006);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2007);
//                    wwd.getModel().getLayers().add(anoLayer2007);
//                    layerChanger++;
//
//                }
//                else if( layerChanger==5){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2007);
//                    wwd.getModel().getLayers().remove(anoLayer2007);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2008);
//                    wwd.getModel().getLayers().add(anoLayer2008);
//                    layerChanger++;
//
//                }
//                else if(layerChanger ==6){
//                    wwd.getModel().getLayers().remove(amaPopAcumLayer2008);
//                    wwd.getModel().getLayers().remove(anoLayer2008);
//                   
//                    wwd.getModel().getLayers().add(amaPopAcumLayer2002);
//                    wwd.getModel().getLayers().add(anoLayer2002);
//                    layerChanger=0;
//                }
//
//            }
//             
//          });
            
            
//            //TOTAL DEFORESTATION, LANDUSE AND EXPORT
//               amaLanduse2002 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2002");
//               anoLayer2002 =amaLanduse2002.addAnnotations();
//
//               amaLanduse2003 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2003");
//               anoLayer2003 =amaLanduse2003.addAnnotations();
//
//               amaLanduse2004 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2004");
//               anoLayer2004 =amaLanduse2004.addAnnotations();
//               
//               amaLanduse2005 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2005");
//               anoLayer2005 =amaLanduse2005.addAnnotations();
//               
//               amaLanduse2006 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2006");
//               anoLayer2006 =amaLanduse2006.addAnnotations();
//               
//               amaLanduse2007 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2007");
//               anoLayer2007 =amaLanduse2007.addAnnotations();
//               
//               amaLanduse2008 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2008");
//               anoLayer2008 =amaLanduse2008.addAnnotations();
//       
//            insertBeforeBeforeCompass(this.getWwd(), amaLanduse2002);
//            insertBeforeBeforeCompass(this.getWwd(), anoLayer2002);
//
//
//            insertBeforeBeforeCompass(this.getWwd(), new CountryBoundariesLayer());
//            
////            MS_POS
//           
//              controller.flyToPosition(MS_POS, GLOBE_ZOOM);
//             
//
//              
//              
//              updater = new Timer(5000, new ActionListener(){
//
//                  @Override
//                  public void actionPerformed(ActionEvent e) {
//                      // TODO Auto-generated method stub
//                      if( layerChanger==0){
//                          wwd.getModel().getLayers().remove(amaLanduse2002);
//                          wwd.getModel().getLayers().remove(anoLayer2002);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2003);
//                          wwd.getModel().getLayers().add(anoLayer2003);
//                          layerChanger++;
//                      }
//                      else if( layerChanger==1){
//                          wwd.getModel().getLayers().remove(amaLanduse2003);
//                          wwd.getModel().getLayers().remove(anoLayer2003);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2004);
//                          wwd.getModel().getLayers().add(anoLayer2004);
//                          layerChanger++;
//
//                      }
//                      else if( layerChanger==2){
//                          wwd.getModel().getLayers().remove(amaLanduse2004);
//                          wwd.getModel().getLayers().remove(anoLayer2004);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2005);
//                          wwd.getModel().getLayers().add(anoLayer2005);
//                          layerChanger++;
//
//                      }
//                      else if( layerChanger==3){
//                          wwd.getModel().getLayers().remove(amaLanduse2005);
//                          wwd.getModel().getLayers().remove(anoLayer2005);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2006);
//                          wwd.getModel().getLayers().add(anoLayer2006);
//                          layerChanger++;
//
//                      }
//                      else if( layerChanger==4){
//                          wwd.getModel().getLayers().remove(amaLanduse2006);
//                          wwd.getModel().getLayers().remove(anoLayer2006);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2007);
//                          wwd.getModel().getLayers().add(anoLayer2007);
//                          layerChanger++;
//
//                      }
//                      else if( layerChanger==5){
//                          wwd.getModel().getLayers().remove(amaLanduse2007);
//                          wwd.getModel().getLayers().remove(anoLayer2007);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2008);
//                          wwd.getModel().getLayers().add(anoLayer2008);
//                          layerChanger++;
//
//                      }
//                      else if(layerChanger ==6){
//                          wwd.getModel().getLayers().remove(amaLanduse2008);
//                          wwd.getModel().getLayers().remove(anoLayer2008);
//                         
//                          wwd.getModel().getLayers().add(amaLanduse2002);
//                          wwd.getModel().getLayers().add(anoLayer2002);
//                          layerChanger=0;
//                      }
//
//                  }
//                   
//                });
              
              
              //GDP
              


              
//                for(Layer layer:wwd.getModel().getLayers()){
//                    if (layer instanceof GeoNamesLayer ||
//                            layer instanceof EarthNASAPlaceNameLayer){
//                        layer.setEnabled(false);
//                    }
//                   
//                }
            //addGDPlayer(dataR);
           // addLanduseLayer(dataR);
            addAcumPoplayer(dataR);
             
        //    updater.start();
              removeCompass(this.getWwd());
              
              
              
        }
        
        public void addLanduseLayer(DataRetriever dataR){
          amaLanduse2002 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2002");
          anoLayer2002 =amaLanduse2002.addAnnotations();

          amaLanduse2003 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2003");
          anoLayer2003 =amaLanduse2003.addAnnotations();

          amaLanduse2004 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2004");
          anoLayer2004 =amaLanduse2004.addAnnotations();
          
          amaLanduse2005 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2005");
          anoLayer2005 =amaLanduse2005.addAnnotations();
          
          amaLanduse2006 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2006");
          anoLayer2006 =amaLanduse2006.addAnnotations();
          
          amaLanduse2007 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2007");
          anoLayer2007 =amaLanduse2007.addAnnotations();
          
          amaLanduse2008 = new AmazonLanduseLayer(dataR.getMuniData(),  dataR.getMesoRegions(),"2008");
          anoLayer2008 =amaLanduse2008.addAnnotations();
  
       insertBeforeBeforeCompass(this.getWwd(), amaLanduse2002);
       insertBeforeBeforeCompass(this.getWwd(), anoLayer2002);


       insertBeforeBeforeCompass(this.getWwd(), new CountryBoundariesLayer());
       
//       MS_POS
      
         controller.flyToPosition(MS_POS, GLOBE_ZOOM);
        

         
         
         updater = new Timer(5000, new ActionListener(){

             @Override
             public void actionPerformed(ActionEvent e) {
                 // TODO Auto-generated method stub
                 if( layerChanger==0){
                     wwd.getModel().getLayers().remove(amaLanduse2002);
                     wwd.getModel().getLayers().remove(anoLayer2002);
                    
                     wwd.getModel().getLayers().add(amaLanduse2003);
                     wwd.getModel().getLayers().add(anoLayer2003);
                     layerChanger++;
                 }
                 else if( layerChanger==1){
                     wwd.getModel().getLayers().remove(amaLanduse2003);
                     wwd.getModel().getLayers().remove(anoLayer2003);
                    
                     wwd.getModel().getLayers().add(amaLanduse2004);
                     wwd.getModel().getLayers().add(anoLayer2004);
                     layerChanger++;

                 }
                 else if( layerChanger==2){
                     wwd.getModel().getLayers().remove(amaLanduse2004);
                     wwd.getModel().getLayers().remove(anoLayer2004);
                    
                     wwd.getModel().getLayers().add(amaLanduse2005);
                     wwd.getModel().getLayers().add(anoLayer2005);
                     layerChanger++;

                 }
                 else if( layerChanger==3){
                     wwd.getModel().getLayers().remove(amaLanduse2005);
                     wwd.getModel().getLayers().remove(anoLayer2005);
                    
                     wwd.getModel().getLayers().add(amaLanduse2006);
                     wwd.getModel().getLayers().add(anoLayer2006);
                     layerChanger++;

                 }
                 else if( layerChanger==4){
                     wwd.getModel().getLayers().remove(amaLanduse2006);
                     wwd.getModel().getLayers().remove(anoLayer2006);
                    
                     wwd.getModel().getLayers().add(amaLanduse2007);
                     wwd.getModel().getLayers().add(anoLayer2007);
                     layerChanger++;

                 }
                 else if( layerChanger==5){
                     wwd.getModel().getLayers().remove(amaLanduse2007);
                     wwd.getModel().getLayers().remove(anoLayer2007);
                    
                     wwd.getModel().getLayers().add(amaLanduse2008);
                     wwd.getModel().getLayers().add(anoLayer2008);
                     layerChanger++;

                 }
                 else if(layerChanger ==6){
                     wwd.getModel().getLayers().remove(amaLanduse2008);
                     wwd.getModel().getLayers().remove(anoLayer2008);
                    
                     wwd.getModel().getLayers().add(amaLanduse2002);
                     wwd.getModel().getLayers().add(anoLayer2002);
                     layerChanger=0;
                 }

             }
              
           });
        }
        
        public void addGDPlayer(DataRetriever dataR){
            amagdp2004 = new AmazonGDPLayer(dataR.getMuniData(),"2004");
            anoLayer2004 =amagdp2004.addAnnotations();
            generalAnoLayer2004 = amagdp2004.generalInformationLayer();
            
            amagdp2005 = new AmazonGDPLayer(dataR.getMuniData(),"2005");  
            anoLayer2005 =amagdp2005.addAnnotations();
            generalAnoLayer2005 = amagdp2005.generalInformationLayer();

            amagdp2006 = new AmazonGDPLayer(dataR.getMuniData(),"2006");
            anoLayer2006 =amagdp2006.addAnnotations();
            generalAnoLayer2006 = amagdp2006.generalInformationLayer();

            amagdp2007 = new AmazonGDPLayer(dataR.getMuniData(),"2007");
            anoLayer2007 =amagdp2007.addAnnotations();
            generalAnoLayer2007 = amagdp2007.generalInformationLayer();

            amagdp2008 = new AmazonGDPLayer(dataR.getMuniData(),"2008");
            anoLayer2008 =amagdp2008.addAnnotations();
            generalAnoLayer2008 = amagdp2008.generalInformationLayer();

          //  insertBeforeBeforeCompass(this.getWwd(), amagdp2004.generalInformationLayer());

    
         insertBeforeBeforeCompass(this.getWwd(), amagdp2004);
         insertBeforeBeforeCompass(this.getWwd(), anoLayer2004);
         insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2004);

         insertBeforeBeforeCompass(this.getWwd(), new CountryBoundariesLayer());
         
//         MS_POS
        
           controller.flyToPosition(MS_POS, GLOBE_ZOOM);
          

           
           
           updater = new Timer(5000, new ActionListener(){

               @Override
               public void actionPerformed(ActionEvent e) {
                   // TODO Auto-generated method stub
                   if( layerChanger==0){
                       wwd.getModel().getLayers().remove(amagdp2004);
                       wwd.getModel().getLayers().remove(anoLayer2004);
                       wwd.getModel().getLayers().remove(generalAnoLayer2004);

                       wwd.getModel().getLayers().add(amagdp2005);
                       wwd.getModel().getLayers().add(anoLayer2005);
                       wwd.getModel().getLayers().add(generalAnoLayer2005);

                       layerChanger++;
                   }
                   else if( layerChanger==1){
                       wwd.getModel().getLayers().remove(amagdp2005);
                       wwd.getModel().getLayers().remove(anoLayer2005);
                       wwd.getModel().getLayers().remove(generalAnoLayer2005);

                      
                       wwd.getModel().getLayers().add(amagdp2006);
                       wwd.getModel().getLayers().add(anoLayer2006);
                       wwd.getModel().getLayers().add(generalAnoLayer2006);

                       layerChanger++;

                   }
                   else if( layerChanger==2){
                       wwd.getModel().getLayers().remove(amagdp2006);
                       wwd.getModel().getLayers().remove(anoLayer2006);
                       wwd.getModel().getLayers().remove(generalAnoLayer2006);
                      
                       wwd.getModel().getLayers().add(amagdp2007);
                       wwd.getModel().getLayers().add(anoLayer2007);
                       wwd.getModel().getLayers().add(generalAnoLayer2007);

                       layerChanger++;

                   }
                   else if( layerChanger==3){
                       wwd.getModel().getLayers().remove(amagdp2007);
                       wwd.getModel().getLayers().remove(anoLayer2007);
                       wwd.getModel().getLayers().remove(generalAnoLayer2007);
                      
                       wwd.getModel().getLayers().add(amagdp2008);
                       wwd.getModel().getLayers().add(anoLayer2008);
                       wwd.getModel().getLayers().add(generalAnoLayer2008);

                       layerChanger++;

                   }
                   else if( layerChanger==4){
                       wwd.getModel().getLayers().remove(amagdp2008);
                       wwd.getModel().getLayers().remove(anoLayer2008);
                       wwd.getModel().getLayers().remove(generalAnoLayer2008);
                      
                       wwd.getModel().getLayers().add(amagdp2004);
                       wwd.getModel().getLayers().add(anoLayer2004);
                       wwd.getModel().getLayers().add(generalAnoLayer2004);

                       layerChanger=0;

                   }
                  

               }
                
             });
        }
        
        public void addAcumPoplayer(DataRetriever dataR){
        	
          amaPopAcumLayer2002 = new AmazonPopAcumLayer(dataR.getMuniData(), "2002");
          anoLayer2002 =amaPopAcumLayer2002.addAnnotations();

          amaPopAcumLayer2003 = new AmazonPopAcumLayer(dataR.getMuniData(), "2003");
          anoLayer2003 =amaPopAcumLayer2003.addAnnotations();

          amaPopAcumLayer2004 = new AmazonPopAcumLayer(dataR.getMuniData(), "2004");
          anoLayer2004 =amaPopAcumLayer2004.addAnnotations();

          amaPopAcumLayer2005 = new AmazonPopAcumLayer(dataR.getMuniData(), "2005");
          anoLayer2005 =amaPopAcumLayer2005.addAnnotations();

          amaPopAcumLayer2006 = new AmazonPopAcumLayer(dataR.getMuniData(), "2006");
          anoLayer2006 =amaPopAcumLayer2006.addAnnotations();

          amaPopAcumLayer2007 = new AmazonPopAcumLayer(dataR.getMuniData(), "2007");
          anoLayer2007 =amaPopAcumLayer2007.addAnnotations();

          amaPopAcumLayer2008 = new AmazonPopAcumLayer(dataR.getMuniData(), "2008");
          anoLayer2008 =amaPopAcumLayer2008.addAnnotations();
          
          insertBeforeBeforeCompass(this.getWwd(), amaPopAcumLayer2002);
          insertBeforeBeforeCompass(this.getWwd(),anoLayer2002);
          insertBeforeBeforeCompass(this.getWwd(), new CountryBoundariesLayer());

//        MS_POS
          
          controller.flyToPosition(MS_POS, GLOBE_ZOOM);
         
//        updater = new Timer(5000, new ActionListener(){
//
//          @Override
//          public void actionPerformed(ActionEvent e) {
//              // TODO Auto-generated method stub
//              if( layerChanger==0){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2002);
//                  wwd.getModel().getLayers().remove(anoLayer2002);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2003);
//                  wwd.getModel().getLayers().add(anoLayer2003);
//                  layerChanger++;
//              }
//              else if( layerChanger==1){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2003);
//                  wwd.getModel().getLayers().remove(anoLayer2003);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2004);
//                  wwd.getModel().getLayers().add(anoLayer2004);
//                  layerChanger++;
//
//              }
//              else if( layerChanger==2){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2004);
//                  wwd.getModel().getLayers().remove(anoLayer2004);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2005);
//                  wwd.getModel().getLayers().add(anoLayer2005);
//                  layerChanger++;
//
//              }
//              else if( layerChanger==3){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2005);
//                  wwd.getModel().getLayers().remove(anoLayer2005);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2006);
//                  wwd.getModel().getLayers().add(anoLayer2006);
//                  layerChanger++;
//
//              }
//              else if( layerChanger==4){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2006);
//                  wwd.getModel().getLayers().remove(anoLayer2006);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2007);
//                  wwd.getModel().getLayers().add(anoLayer2007);
//                  layerChanger++;
//
//              }
//              else if( layerChanger==5){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2007);
//                  wwd.getModel().getLayers().remove(anoLayer2007);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2008);
//                  wwd.getModel().getLayers().add(anoLayer2008);
//                  layerChanger++;
//
//              }
//              else if(layerChanger ==6){
//                  wwd.getModel().getLayers().remove(amaPopAcumLayer2008);
//                  wwd.getModel().getLayers().remove(anoLayer2008);
//                 
//                  wwd.getModel().getLayers().add(amaPopAcumLayer2002);
//                  wwd.getModel().getLayers().add(anoLayer2002);
//                  layerChanger=0;
//              }
//
//          }
//           
//        });
        	
        }

        public AmazonPopAcumLayer getAmaPopAcumLayer2002() {
			return amaPopAcumLayer2002;
		}

		public void setAmaPopAcumLayer2002(AmazonPopAcumLayer amaPopAcumLayer2002) {
			this.amaPopAcumLayer2002 = amaPopAcumLayer2002;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2003() {
			return amaPopAcumLayer2003;
		}

		public void setAmaPopAcumLayer2003(AmazonPopAcumLayer amaPopAcumLayer2003) {
			this.amaPopAcumLayer2003 = amaPopAcumLayer2003;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2004() {
			return amaPopAcumLayer2004;
		}

		public void setAmaPopAcumLayer2004(AmazonPopAcumLayer amaPopAcumLayer2004) {
			this.amaPopAcumLayer2004 = amaPopAcumLayer2004;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2005() {
			return amaPopAcumLayer2005;
		}

		public void setAmaPopAcumLayer2005(AmazonPopAcumLayer amaPopAcumLayer2005) {
			this.amaPopAcumLayer2005 = amaPopAcumLayer2005;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2006() {
			return amaPopAcumLayer2006;
		}

		public void setAmaPopAcumLayer2006(AmazonPopAcumLayer amaPopAcumLayer2006) {
			this.amaPopAcumLayer2006 = amaPopAcumLayer2006;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2007() {
			return amaPopAcumLayer2007;
		}

		public void setAmaPopAcumLayer2007(AmazonPopAcumLayer amaPopAcumLayer2007) {
			this.amaPopAcumLayer2007 = amaPopAcumLayer2007;
		}

		public AmazonPopAcumLayer getAmaPopAcumLayer2008() {
			return amaPopAcumLayer2008;
		}

		public void setAmaPopAcumLayer2008(AmazonPopAcumLayer amaPopAcumLayer2008) {
			this.amaPopAcumLayer2008 = amaPopAcumLayer2008;
		}

		public AnnotationLayer getAnoLayer2002() {
			return anoLayer2002;
		}

		public void setAnoLayer2002(AnnotationLayer anoLayer2002) {
			this.anoLayer2002 = anoLayer2002;
		}

		public AnnotationLayer getAnoLayer2003() {
			return anoLayer2003;
		}

		public void setAnoLayer2003(AnnotationLayer anoLayer2003) {
			this.anoLayer2003 = anoLayer2003;
		}

		public AnnotationLayer getAnoLayer2004() {
			return anoLayer2004;
		}

		public void setAnoLayer2004(AnnotationLayer anoLayer2004) {
			this.anoLayer2004 = anoLayer2004;
		}

		public AnnotationLayer getAnoLayer2005() {
			return anoLayer2005;
		}

		public void setAnoLayer2005(AnnotationLayer anoLayer2005) {
			this.anoLayer2005 = anoLayer2005;
		}

		public AnnotationLayer getAnoLayer2006() {
			return anoLayer2006;
		}

		public void setAnoLayer2006(AnnotationLayer anoLayer2006) {
			this.anoLayer2006 = anoLayer2006;
		}

		public AnnotationLayer getAnoLayer2007() {
			return anoLayer2007;
		}

		public void setAnoLayer2007(AnnotationLayer anoLayer2007) {
			this.anoLayer2007 = anoLayer2007;
		}

		public AnnotationLayer getAnoLayer2008() {
			return anoLayer2008;
		}

		public void setAnoLayer2008(AnnotationLayer anoLayer2008) {
			this.anoLayer2008 = anoLayer2008;
		}

		public int getLayerChanger() {
			return layerChanger;
		}

		public void setLayerChanger(int layerChanger) {
			this.layerChanger = layerChanger;
		}

		public WorldWindowGLCanvas getWwd()
        {
            return wwd;
        }



        public AppFrameController getController() {
            //return null;
            return controller;
        }
    }


    public static void removeCompass(WorldWindow wwd)
    {
        // Insert the layer into the layer list just before the compass.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof CompassLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.remove(compassPosition);
    }
    public static void insertBeforeCompass(WorldWindow wwd, Layer layer)
    {
        // Insert the layer into the layer list just before the compass.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof CompassLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition, layer);
    }

    public static void insertBeforeBeforeCompass(WorldWindow wwd, Layer layer)
    {
        // Insert the layer into the layer list just before the compass.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof CompassLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition-1, layer);
    }
   
    public static class HandsTracker extends JFrame{
       
       
        public HandsTracker(SkeletonKinectHandler k)
          {
            super("Hands Tracker");

            Container c = getContentPane();
            c.setLayout( new BorderLayout() );  

            c.add( k, BorderLayout.CENTER);

            pack(); 
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
          } // end of HandsTracker()
        
        
    }


    public static void main(String[] args)
    {               
        AppFrame app=new AppFrame();       

//        SkeletonKinectHandler skeletonKinectHandler = new SkeletonKinectHandler((de.ifgi.worldwind.htc.HelloWorldWind.AppFrame)app);
////        new HandsTracker(skeletonKinectHandler);
//        skeletonKinectHandler.setBounds(15, 585, 224, 168);
//        app.layeredPane.add(skeletonKinectHandler,
//                new Integer (
//                        JLayeredPane.DEFAULT_LAYER.intValue()
//                        +1));

        app.setVisible(true);
       
       



    }
}
