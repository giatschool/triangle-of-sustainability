package de.ifgi.worldwind.amazon;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.Earth.CountryBoundariesLayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import kinect.SkeletonKinectHandler;

import de.ifgi.data.DataRetriever;
import de.ifgi.worldwind.htc.WindowClosingAdapter;
import de.ifgi.worldwind.layer.AmazonGDPLayer;
import de.ifgi.worldwind.layer.AmazonLanduseLayer2;
import de.ifgi.worldwind.layer.AmazonPopAcumLayer;

/**
 * This is the most basic World Wind program.
 * 
 * @version $Id: HelloWorldWind.java 4869 2008-03-31 15:56:36Z tgaskins $
 */
public class AmazonDeforestation {
	// An inner class is used rather than directly subclassing JFrame in the
	// main class so
	// that the main can configure system properties prior to invoking Swing.
	// This is
	// necessary for instance on OS X (Macs) so that the application name can be
	// specified.
	public static class AppFrame extends JFrame implements KeyListener {
		RenderableLayer ama2002;
		RenderableLayer ama2003;
		RenderableLayer ama2004;
		RenderableLayer ama2005;
		RenderableLayer ama2006;
		RenderableLayer ama2007;
		RenderableLayer ama2008;

		AnnotationLayer anoLayer2002;
		AnnotationLayer anoLayer2003;
		AnnotationLayer anoLayer2004;
		AnnotationLayer anoLayer2005;
		AnnotationLayer anoLayer2006;
		AnnotationLayer anoLayer2007;
		AnnotationLayer anoLayer2008;

		AnnotationLayer generalAnoLayer2002;
		AnnotationLayer generalAnoLayer2003;
		AnnotationLayer generalAnoLayer2004;
		AnnotationLayer generalAnoLayer2005;
		AnnotationLayer generalAnoLayer2006;
		AnnotationLayer generalAnoLayer2007;
		AnnotationLayer generalAnoLayer2008;

		boolean year2002 = false;

		private Timer updater;

		private AppFrameController controller;

		private WorldWindowGLCanvas wwd;

		private SkeletonKinectHandler kinectHandler;

		private int height = 768;
		private int width = 1024;

		public static final double GLOBE_ZOOM = 2.3e7;
		public static final Position MS_POS = Position.fromDegrees(-4.72826,
				-52.302247, 7000000);

		int layerChanger = 0;

		private JLayeredPane layeredPane;

		public AppFrame() {

			wwd = new WorldWindowGLCanvas();
			// wwd.setPreferredSize(new java.awt.Dimension(d.width,d.height));
			wwd.addKeyListener(this);
			wwd.setPreferredSize(new java.awt.Dimension(width, height));
			wwd.setModel(new BasicModel());
			wwd.setBounds(0, 0, width + 1, height + 1); // +1 because without it
														// the camera image isnt
														// shown

			layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new java.awt.Dimension(width, height));

			layeredPane.add(wwd, java.awt.BorderLayout.CENTER);

			layeredPane.setBounds(0, 0, width, height);

			layeredPane.doLayout();

			addWindowListener(new WindowClosingAdapter(true));
			this.setUndecorated(true);
			this.getContentPane()
					.add(layeredPane, java.awt.BorderLayout.CENTER);
			// this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
			this.pack();
			this.setBounds(0, 0, width, height);

			// wwd = new WorldWindowGLCanvas();
			//
			// //wwd.setPreferredSize(new java.awt.Dimension(d.width,d.height));
			// wwd.setPreferredSize(new java.awt.Dimension(width,height));
			//
			// addWindowListener(new WindowClosingAdapter(true));
			// this.setUndecorated(true);
			//
			// this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
			// this.pack();
			// this.setBounds(0, 0, width, height);
			// wwd.setModel(new BasicModel());

			this.controller = new AppFrameController(this);

			DataRetriever dataR = new DataRetriever();
			dataR.queryForStates();

			/*
			 * 
			 * Für die Seiten
			 */

//			 addGDPlayer(dataR); //economical

			 addLanduseLayer(dataR); // ecological

			 // year2002 = true;		   //social
			 // addAcumPoplayer(dataR); //social

//			updater.start();
			removeCompass(this.getWwd());

			initKinectHandler();

		}

		private void initKinectHandler() {
			kinectHandler = new SkeletonKinectHandler(this);
			kinectHandler.setBounds(15, 585, 224, 168);
			layeredPane.add(kinectHandler, new Integer(
					JLayeredPane.DEFAULT_LAYER.intValue() + 1));
		}

		public void addLanduseLayer(DataRetriever dataR) {
			// amaLanduse2002 = new AmazonLanduseLayer2(dataR.getMuniData(),
			// dataR.getMesoRegions(),"2002");
			// anoLayer2002 =amaLanduse2002.addAnnotations();
			//
			// amaLanduse2003 = new AmazonLanduseLayer2(dataR.getMuniData(),
			// dataR.getMesoRegions(),"2003");
			// anoLayer2003 =amaLanduse2003.addAnnotations();

			ama2004 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2004");
			anoLayer2004 = ((AmazonLanduseLayer2) ama2004).addAnnotations();
			generalAnoLayer2004 = ((AmazonLanduseLayer2) ama2004)
					.generalInformationLayer();

			ama2005 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2005");
			anoLayer2005 = ((AmazonLanduseLayer2) ama2005).addAnnotations();
			generalAnoLayer2005 = ((AmazonLanduseLayer2) ama2005)
					.generalInformationLayer();

			ama2006 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2006");
			anoLayer2006 = ((AmazonLanduseLayer2) ama2006).addAnnotations();
			generalAnoLayer2006 = ((AmazonLanduseLayer2) ama2006)
					.generalInformationLayer();

			ama2007 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2007");
			anoLayer2007 = ((AmazonLanduseLayer2) ama2007).addAnnotations();
			generalAnoLayer2007 = ((AmazonLanduseLayer2) ama2007)
					.generalInformationLayer();

			ama2008 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2008");
			anoLayer2008 = ((AmazonLanduseLayer2) ama2008).addAnnotations();
			generalAnoLayer2008 = ((AmazonLanduseLayer2) ama2008)
					.generalInformationLayer();

			insertBeforeBeforeCompass(this.getWwd(), ama2004);
			insertBeforeBeforeCompass(this.getWwd(), anoLayer2004);
			insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2004);

			insertBeforeBeforeCompass(this.getWwd(),
					new CountryBoundariesLayer());

			// MS_POS

			controller.flyToPosition(MS_POS, GLOBE_ZOOM);

			updater = new Timer(5000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					// if( layerChanger==0){
					// wwd.getModel().getLayers().remove(ama2002);
					// wwd.getModel().getLayers().remove(anoLayer2002);
					//
					// wwd.getModel().getLayers().add(ama2003);
					// wwd.getModel().getLayers().add(anoLayer2003);
					// layerChanger++;
					// }
					// else if( layerChanger==1){
					// wwd.getModel().getLayers().remove(ama2003);
					// wwd.getModel().getLayers().remove(anoLayer2003);
					//
					// wwd.getModel().getLayers().add(ama2004);
					// wwd.getModel().getLayers().add(anoLayer2004);
					// layerChanger++;
					//
					// }
					if (layerChanger == 0) {
						wwd.getModel().getLayers().remove(ama2004);
						wwd.getModel().getLayers().remove(anoLayer2004);
						wwd.getModel().getLayers().remove(generalAnoLayer2004);

						wwd.getModel().getLayers().add(ama2005);
						wwd.getModel().getLayers().add(anoLayer2005);
						wwd.getModel().getLayers().add(generalAnoLayer2005);

						layerChanger++;

					} else if (layerChanger == 1) {
						wwd.getModel().getLayers().remove(ama2005);
						wwd.getModel().getLayers().remove(anoLayer2005);
						wwd.getModel().getLayers().remove(generalAnoLayer2005);

						wwd.getModel().getLayers().add(ama2006);
						wwd.getModel().getLayers().add(anoLayer2006);
						wwd.getModel().getLayers().add(generalAnoLayer2006);

						layerChanger++;

					} else if (layerChanger == 2) {
						wwd.getModel().getLayers().remove(ama2006);
						wwd.getModel().getLayers().remove(anoLayer2006);
						wwd.getModel().getLayers().remove(generalAnoLayer2006);

						wwd.getModel().getLayers().add(ama2007);
						wwd.getModel().getLayers().add(anoLayer2007);
						wwd.getModel().getLayers().add(generalAnoLayer2007);

						layerChanger++;

					} else if (layerChanger == 3) {
						wwd.getModel().getLayers().remove(ama2007);
						wwd.getModel().getLayers().remove(anoLayer2007);
						wwd.getModel().getLayers().remove(generalAnoLayer2007);

						wwd.getModel().getLayers().add(ama2008);
						wwd.getModel().getLayers().add(anoLayer2008);
						wwd.getModel().getLayers().add(generalAnoLayer2008);

						layerChanger++;

					} else if (layerChanger == 4) {
						wwd.getModel().getLayers().remove(ama2008);
						wwd.getModel().getLayers().remove(anoLayer2008);
						wwd.getModel().getLayers().remove(generalAnoLayer2008);

						wwd.getModel().getLayers().add(ama2004);
						wwd.getModel().getLayers().add(anoLayer2004);
						wwd.getModel().getLayers().add(generalAnoLayer2004);

						layerChanger = 0;
					}

				}

			});
		}

		public void addGDPlayer(DataRetriever dataR) {
			ama2004 = new AmazonGDPLayer(dataR.getMuniData(), "2004");
			anoLayer2004 = ((AmazonGDPLayer) ama2004).addAnnotations();
			generalAnoLayer2004 = ((AmazonGDPLayer) ama2004)
					.generalInformationLayer();

			ama2005 = new AmazonGDPLayer(dataR.getMuniData(), "2005");
			anoLayer2005 = ((AmazonGDPLayer) ama2005).addAnnotations();
			generalAnoLayer2005 = ((AmazonGDPLayer) ama2005)
					.generalInformationLayer();

			ama2006 = new AmazonGDPLayer(dataR.getMuniData(), "2006");
			anoLayer2006 = ((AmazonGDPLayer) ama2006).addAnnotations();
			generalAnoLayer2006 = ((AmazonGDPLayer) ama2006)
					.generalInformationLayer();

			ama2007 = new AmazonGDPLayer(dataR.getMuniData(), "2007");
			anoLayer2007 = ((AmazonGDPLayer) ama2007).addAnnotations();
			generalAnoLayer2007 = ((AmazonGDPLayer) ama2007)
					.generalInformationLayer();

			ama2008 = new AmazonGDPLayer(dataR.getMuniData(), "2008");
			anoLayer2008 = ((AmazonGDPLayer) ama2008).addAnnotations();
			generalAnoLayer2008 = ((AmazonGDPLayer) ama2008)
					.generalInformationLayer();

//			 insertBeforeBeforeCompass(this.getWwd(),
			// ama2004.generalInformationLayer());

			insertBeforeBeforeCompass(this.getWwd(), ama2004);
			insertBeforeBeforeCompass(this.getWwd(), anoLayer2004);
			insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2004);

			insertBeforeBeforeCompass(this.getWwd(),
					new CountryBoundariesLayer());
			
			controller.flyToPosition(MS_POS, GLOBE_ZOOM);

			updater = new Timer(5000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (layerChanger == 0) {
						wwd.getModel().getLayers().remove(ama2004);
						wwd.getModel().getLayers().remove(anoLayer2004);
						wwd.getModel().getLayers().remove(generalAnoLayer2004);

						wwd.getModel().getLayers().add(ama2005);
						wwd.getModel().getLayers().add(anoLayer2005);
						wwd.getModel().getLayers().add(generalAnoLayer2005);

						layerChanger++;
					} else if (layerChanger == 1) {
						wwd.getModel().getLayers().remove(ama2005);
						wwd.getModel().getLayers().remove(anoLayer2005);
						wwd.getModel().getLayers().remove(generalAnoLayer2005);

						wwd.getModel().getLayers().add(ama2006);
						wwd.getModel().getLayers().add(anoLayer2006);
						wwd.getModel().getLayers().add(generalAnoLayer2006);

						layerChanger++;

					} else if (layerChanger == 2) {
						wwd.getModel().getLayers().remove(ama2006);
						wwd.getModel().getLayers().remove(anoLayer2006);
						wwd.getModel().getLayers().remove(generalAnoLayer2006);

						wwd.getModel().getLayers().add(ama2007);
						wwd.getModel().getLayers().add(anoLayer2007);
						wwd.getModel().getLayers().add(generalAnoLayer2007);

						layerChanger++;

					} else if (layerChanger == 3) {
						wwd.getModel().getLayers().remove(ama2007);
						wwd.getModel().getLayers().remove(anoLayer2007);
						wwd.getModel().getLayers().remove(generalAnoLayer2007);

						wwd.getModel().getLayers().add(ama2008);
						wwd.getModel().getLayers().add(anoLayer2008);
						wwd.getModel().getLayers().add(generalAnoLayer2008);

						layerChanger++;

					} else if (layerChanger == 4) {
						wwd.getModel().getLayers().remove(ama2008);
						wwd.getModel().getLayers().remove(anoLayer2008);
						wwd.getModel().getLayers().remove(generalAnoLayer2008);

						wwd.getModel().getLayers().add(ama2004);
						wwd.getModel().getLayers().add(anoLayer2004);
						wwd.getModel().getLayers().add(generalAnoLayer2004);

						layerChanger = 0;

					}

				}

			});
		}

		public void addAcumPoplayer(DataRetriever dataR) {

			ama2002 = new AmazonPopAcumLayer(dataR.getMuniData(), "2002");
			anoLayer2002 = ((AmazonPopAcumLayer) ama2002).addAnnotations();
			generalAnoLayer2002 = ((AmazonPopAcumLayer) ama2002)
					.generalInformationLayer();

			ama2003 = new AmazonPopAcumLayer(dataR.getMuniData(), "2003");
			anoLayer2003 = ((AmazonPopAcumLayer) ama2003).addAnnotations();
			generalAnoLayer2003 = ((AmazonPopAcumLayer) ama2003)
					.generalInformationLayer();

			ama2004 = new AmazonPopAcumLayer(dataR.getMuniData(), "2004");
			anoLayer2004 = ((AmazonPopAcumLayer) ama2004).addAnnotations();
			generalAnoLayer2004 = ((AmazonPopAcumLayer) ama2004)
					.generalInformationLayer();

			ama2005 = new AmazonPopAcumLayer(dataR.getMuniData(), "2005");
			anoLayer2005 = ((AmazonPopAcumLayer) ama2005).addAnnotations();
			generalAnoLayer2005 = ((AmazonPopAcumLayer) ama2005)
					.generalInformationLayer();

			ama2006 = new AmazonPopAcumLayer(dataR.getMuniData(), "2006");
			anoLayer2006 = ((AmazonPopAcumLayer) ama2006).addAnnotations();
			generalAnoLayer2006 = ((AmazonPopAcumLayer) ama2006)
					.generalInformationLayer();

			ama2007 = new AmazonPopAcumLayer(dataR.getMuniData(), "2007");
			anoLayer2007 = ((AmazonPopAcumLayer) ama2007).addAnnotations();
			generalAnoLayer2007 = ((AmazonPopAcumLayer) ama2007)
					.generalInformationLayer();

			ama2008 = new AmazonPopAcumLayer(dataR.getMuniData(), "2008");
			anoLayer2008 = ((AmazonPopAcumLayer) ama2008).addAnnotations();
			generalAnoLayer2008 = ((AmazonPopAcumLayer) ama2008)
					.generalInformationLayer();

			insertBeforeBeforeCompass(this.getWwd(),
					new CountryBoundariesLayer());
			insertBeforeBeforeCompass(this.getWwd(), ama2002);
			insertBeforeBeforeCompass(this.getWwd(), anoLayer2002);
			insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2002);
			controller.flyToPosition(MS_POS, GLOBE_ZOOM);

			updater = new Timer(8000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (layerChanger == 0) {
						wwd.getModel().getLayers().remove(ama2002);
						wwd.getModel().getLayers().remove(anoLayer2002);
						wwd.getModel().getLayers().remove(generalAnoLayer2002);

						wwd.getModel().getLayers().add(ama2003);
						wwd.getModel().getLayers().add(anoLayer2003);
						wwd.getModel().getLayers().add(generalAnoLayer2003);

						layerChanger++;
					} else if (layerChanger == 1) {
						wwd.getModel().getLayers().remove(ama2003);
						wwd.getModel().getLayers().remove(anoLayer2003);
						wwd.getModel().getLayers().remove(generalAnoLayer2003);

						wwd.getModel().getLayers().add(ama2004);
						wwd.getModel().getLayers().add(anoLayer2004);
						wwd.getModel().getLayers().add(generalAnoLayer2004);

						layerChanger++;

					} else if (layerChanger == 2) {
						wwd.getModel().getLayers().remove(ama2004);
						wwd.getModel().getLayers().remove(anoLayer2004);
						wwd.getModel().getLayers().remove(generalAnoLayer2004);

						wwd.getModel().getLayers().add(ama2005);
						wwd.getModel().getLayers().add(anoLayer2005);
						wwd.getModel().getLayers().add(generalAnoLayer2005);

						layerChanger++;

					} else if (layerChanger == 3) {
						wwd.getModel().getLayers().remove(ama2005);
						wwd.getModel().getLayers().remove(anoLayer2005);
						wwd.getModel().getLayers().remove(generalAnoLayer2005);

						wwd.getModel().getLayers().add(ama2006);
						wwd.getModel().getLayers().add(anoLayer2006);
						wwd.getModel().getLayers().add(generalAnoLayer2006);

						layerChanger++;

					} else if (layerChanger == 4) {
						wwd.getModel().getLayers().remove(ama2006);
						wwd.getModel().getLayers().remove(anoLayer2006);
						wwd.getModel().getLayers().remove(generalAnoLayer2006);

						wwd.getModel().getLayers().add(ama2007);
						wwd.getModel().getLayers().add(anoLayer2007);
						wwd.getModel().getLayers().add(generalAnoLayer2007);

						layerChanger++;

					} else if (layerChanger == 5) {
						wwd.getModel().getLayers().remove(ama2007);
						wwd.getModel().getLayers().remove(anoLayer2007);
						wwd.getModel().getLayers().remove(generalAnoLayer2007);

						wwd.getModel().getLayers().add(ama2008);
						wwd.getModel().getLayers().add(anoLayer2008);
						wwd.getModel().getLayers().add(generalAnoLayer2008);

						layerChanger++;

					} else if (layerChanger == 6) {
						wwd.getModel().getLayers().remove(ama2008);
						wwd.getModel().getLayers().remove(anoLayer2008);
						wwd.getModel().getLayers().remove(generalAnoLayer2008);

						wwd.getModel().getLayers().add(ama2002);
						wwd.getModel().getLayers().add(anoLayer2002);
						wwd.getModel().getLayers().add(generalAnoLayer2002);

						layerChanger = 0;
					}

				}

			});

		}

		public RenderableLayer getAma2002() {
			return ama2002;
		}

		public void setAma2002(RenderableLayer ama2002) {
			this.ama2002 = ama2002;
		}

		public RenderableLayer getAma2003() {
			return ama2003;
		}

		public void setAma2003(RenderableLayer ama2003) {
			this.ama2003 = ama2003;
		}

		public RenderableLayer getAma2004() {
			return ama2004;
		}

		public void setAma2004(RenderableLayer ama2004) {
			this.ama2004 = ama2004;
		}

		public RenderableLayer getAma2005() {
			return ama2005;
		}

		public void setAma2005(RenderableLayer ama2005) {
			this.ama2005 = ama2005;
		}

		public RenderableLayer getAma2006() {
			return ama2006;
		}

		public void setAma2006(RenderableLayer ama2006) {
			this.ama2006 = ama2006;
		}

		public RenderableLayer getAma2007() {
			return ama2007;
		}

		public void setAma2007(RenderableLayer ama2007) {
			this.ama2007 = ama2007;
		}

		public RenderableLayer getAma2008() {
			return ama2008;
		}

		public void setAma2008(RenderableLayer ama2008) {
			this.ama2008 = ama2008;
		}

		public AnnotationLayer getGeneralAnoLayer2002() {
			return generalAnoLayer2002;
		}

		public void setGeneralAnoLayer2002(AnnotationLayer generalAnoLayer2002) {
			this.generalAnoLayer2002 = generalAnoLayer2002;
		}

		public AnnotationLayer getGeneralAnoLayer2003() {
			return generalAnoLayer2003;
		}

		public void setGeneralAnoLayer2003(AnnotationLayer generalAnoLayer2003) {
			this.generalAnoLayer2003 = generalAnoLayer2003;
		}

		public AnnotationLayer getGeneralAnoLayer2004() {
			return generalAnoLayer2004;
		}

		public void setGeneralAnoLayer2004(AnnotationLayer generalAnoLayer2004) {
			this.generalAnoLayer2004 = generalAnoLayer2004;
		}

		public AnnotationLayer getGeneralAnoLayer2005() {
			return generalAnoLayer2005;
		}

		public void setGeneralAnoLayer2005(AnnotationLayer generalAnoLayer2005) {
			this.generalAnoLayer2005 = generalAnoLayer2005;
		}

		public AnnotationLayer getGeneralAnoLayer2006() {
			return generalAnoLayer2006;
		}

		public void setGeneralAnoLayer2006(AnnotationLayer generalAnoLayer2006) {
			this.generalAnoLayer2006 = generalAnoLayer2006;
		}

		public AnnotationLayer getGeneralAnoLayer2007() {
			return generalAnoLayer2007;
		}

		public void setGeneralAnoLayer2007(AnnotationLayer generalAnoLayer2007) {
			this.generalAnoLayer2007 = generalAnoLayer2007;
		}

		public AnnotationLayer getGeneralAnoLayer2008() {
			return generalAnoLayer2008;
		}

		public void setGeneralAnoLayer2008(AnnotationLayer generalAnoLayer2008) {
			this.generalAnoLayer2008 = generalAnoLayer2008;
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

		public WorldWindowGLCanvas getWwd() {
			return wwd;
		}

		public boolean isYear2002() {
			return year2002;
		}

		public void setYear2002(boolean year2002) {
			this.year2002 = year2002;
		}

		public AppFrameController getController() {
			// return null;
			return controller;
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode() == 32){
		           controller.flyToPosition(MS_POS, GLOBE_ZOOM);

			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
	}

	public static void removeCompass(WorldWindow wwd) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.remove(compassPosition);
	}

	public static void insertBeforeCompass(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition, layer);
	}

	public static void insertBeforeBeforeCompass(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition - 1, layer);
	}

	public static void main(String[] args) {
		AppFrame app = new AppFrame();
		app.setVisible(true);
	}
}
