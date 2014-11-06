//package de.ifgi.worldwind.htc;
//
//import gov.nasa.worldwind.View;
//import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
//import gov.nasa.worldwind.geom.Angle;
//import gov.nasa.worldwind.geom.Intersection;
//import gov.nasa.worldwind.geom.LatLon;
//import gov.nasa.worldwind.geom.Line;
//import gov.nasa.worldwind.geom.Position;
//import gov.nasa.worldwind.geom.Vec4;
//import gov.nasa.worldwind.globes.Globe;
//
//import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.geom.Point2D;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.Enumeration;
//import java.util.Hashtable;
//import java.util.Properties;
//
//import org.apache.log4j.Logger;
//
//import de.ifgi.multitouch.tuio.KMeansClustering;
//import de.ifgi.multitouch.tuio.TouchCluster;
//import de.ifgi.multitouch.tuio.TouchCursor;
//import de.ifgi.multitouch.tuio.TuioClient;
//import de.ifgi.multitouch.tuio.TuioListener;
//import de.ifgi.worldwind.htc.HelloWorldWind.AppFrame;
//
//public class TouchHandler implements TuioListener {
//
//	private static final int CLICK_CNT = 8;
//	private Hashtable<Long, TouchCursor> touchCurList;
//	private WorldWindowGLCanvas canvas;
//	private static double pointerAccurracy;
//	private static double featureSelectAccurracy=0.01;
//	private Position selectedPosition;
//	private TouchCursor lastPointerTouch;
//	private AppFrame myAppFrame;
//	private ArrayList<Rectangle> rectangelButtons;
//	private AppFrameController controller;
//	private Rectangle wwjRec;
//	private Rectangle compassRec;
//	private Properties props;
//	private LatLon touchLatLon;
//	double latTouchWert=0.1;
//	double longTouchWert=0.1;
//	int height;
//	int width;
//
//
//
//
//	int counterClick=0;
//
//	private static Logger log = Logger.getLogger(TouchHandler.class);
//
//
//
//	public TouchHandler(AppFrame myAppFrame) {
//		this.myAppFrame = myAppFrame;
//		this.canvas = myAppFrame.getWwd();
//		this.controller= myAppFrame.getController();
//		touchCurList = new Hashtable<Long, TouchCursor>();
//		wwjRec = canvas.getBounds();
//		compassRec = new Rectangle((int)wwjRec.getMaxX()-200, (int)wwjRec.getY(), 200 ,200);
//
//		Reader reader;
//		try {
//			reader = new FileReader( "conf/wwj.conf" );
//			props = new Properties(); 
//			props.load( reader );
//
//			this.pointerAccurracy = Double.parseDouble(props.getProperty("POINTERACCURACY"));
//			//this.featureSelectAccurracy = Double.parseDouble(props.getProperty("FEATURESELECTACCURACY"));
//
//						height = Integer.parseInt(props.getProperty("HEIGHT"));
//				        width = Integer.parseInt(props.getProperty("WIDTH"));
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//
//
//	}
//
//	@Override
//	public void refresh() {
//		TouchCursor tcur=touchCurList.elements().nextElement();
//
//		Point2D direction= new Point2D.Double(tcur.getXdir(),tcur.getYdir());
//		Point2D screenPosition = new Point2D.Double(tcur.getXpos(),tcur.getYpos());
//		Point2D lastScreenPosition=TuioClient.getLastPointerPosition();
//
//		//Point2D lastPosition;
//		boolean isClicked = tcur.isClicked();
//		if(tcur.isClicked()==true)	 counterClick=0;
//
//		//aktuelle Höhe
//		int elevation  = (int) canvas.getView().getEyePosition().getElevation() / 1000;
//		
//		// single touch
//	
//		if(touchCurList.size()==1){
//			double x = tcur.getXpos();
//			double y = tcur.getYpos();
//
//			//	x=x-wwjRec.getMinX()-myAppFrame.getInsets().left;
//			//	y= heigth - (y-wwjRec.getMinY()-myAppFrame.getInsets().top);
//
//
//
//			final double dirY = tcur.getYdir();
//			final double dirX = tcur.getXdir();
//
//			System.out.println(x);
//			System.out.println(dirX);
//			System.out.println(y);
//			System.out.println(dirY);
//
//			if (Math.abs(direction.getX()) < pointerAccurracy
//					&& Math.abs(direction.getY()) < pointerAccurracy
//					&& (screenPosition.distance(lastScreenPosition)) > 10.0  //2.0 
//					&& !isClicked) 
//			{
//				if(compassRec.contains(x,y)){
//					controller.rotateToNorth(canvas);
//					controller.unTilt(canvas);
//				} else {
//
//
//					x=x-wwjRec.getMinX()-myAppFrame.getInsets().left;
//					y=height-(y-wwjRec.getMinY()-myAppFrame.getInsets().top);
//
//					
//				lastPointerTouch=tcur;
//				}	
//
//			} 
//			else if (Math.abs(direction.getX())>pointerAccurracy || Math.abs(direction.getY())>pointerAccurracy)
//			{
//				
//				x=getCanvasX(x);
//				y=getCanvasY(y);
//				final double prevX = x+dirX;
//				final double prevY = y+dirY;
//
//				this.selectedPosition= canvas.getView().computePositionFromScreenPoint(x,y);
//
//				Position prevPosition = computePositionAtPoint(prevX, prevY);
//				Position curPosition = computePositionAtPoint(x, y);
//
//				//log.debug("x: "+x+", prevX: "+prevX+", y:"+y+", prevY: "+prevY+"\n prevPos: "+prevPosition+", currPos: "+curPosition );
//				//				log.debug("( "+x+ " , "+y+" ) =>" + "( "+curPosition.getLatitude()+ " , "+curPosition.getLongitude()+" ) =>"+	
//				//				"( "+canvas.getModel().getGlobe().computePointFromPosition(curPosition).x+ " , "+canvas.getModel().getGlobe().computePointFromPosition(curPosition).y+" )");
//
//				if ((prevPosition != null && curPosition != null)
//						&&(Math.abs(prevPosition.subtract(curPosition).getLatitude().degrees)<2.0&&Math.abs(prevPosition.subtract(curPosition).getLongitude().degrees)<2.0)){
//					this.controller.pan(prevPosition, curPosition);
//				}    else {
//					this.controller.pan(dirY, dirX);
//				}
//				
//			}
//
//		}
//		// multi-touch
//		else { 
//			Point2D[] directions;
//			Point2D[] positions;
//			int[] clustersize={1,1};
//			Enumeration<TouchCursor> tcEnum = touchCurList.elements();
//
//
//			// two touches
//			if(touchCurList.size()==2){
//				directions = new Point2D[2];
//				positions = new Point2D[2];
//
//				TouchCursor tc1 = tcEnum.nextElement();
//				TouchCursor tc2 = tcEnum.nextElement();
//
//				directions[0]=new Point2D.Double(tc1.getXdir(),tc1.getYdir());
//				positions[0]= new Point2D.Double(tc1.getXpos(),tc1.getYpos());
//				directions[1]=new Point2D.Double(tc2.getXdir(),tc2.getYdir());
//				positions[1]= new Point2D.Double(tc2.getXpos(),tc2.getYpos());
//
//			} 
//			// multiple touches
//			else {
//				KMeansClustering clustering = new KMeansClustering(2);
//				clustering.cluster(touchCurList);
//				TouchCluster[]cluster= clustering.getCluster(); //cluster(2);
//
//
//
//				directions = new Point2D[cluster.length];
//				positions = new Point2D[cluster.length];
//
//				for(int i=0;i<cluster.length;i++) {
//					directions[i]=new Point2D.Double(cluster[i].avgXdir(),cluster[i].avgYdir());
//					positions[i]= new Point2D.Double(cluster[i].avgXpos(),cluster[i].avgYpos());
//					clustersize[i]=cluster[i].getSize();
//				}
//			}
//
//			// TODO Globe navigation
//			// check if clusters moving in the same direction
//			final double xdir1 = directions[0].getX();
//			final double ydir1 = directions[0].getY();
//			final double xdir2 = directions[1].getX();
//			final double ydir2 = directions[1].getY();
//			if(((Math.abs(xdir1-xdir2)<10)&&(Math.abs(ydir1-ydir2)<10))
//			){
//				// pan
//				final double avgX=(positions[0].getX()+positions[1].getX())/2;
//				final double avgY=(positions[0].getY()+positions[1].getY())/2;
//				final double prevAvgX = 
//					(((positions[0].getX()+directions[0].getX())
//							+(positions[1].getX()+directions[1].getX())))/2;
//				final double prevAvgY = 
//					(((positions[0].getY()+directions[0].getY())
//							+(positions[1].getY()+directions[1].getY())))/2;
//				final double avgDirX = (directions[0].getX()+directions[1].getX())/2;
//				final double avgDirY = (directions[0].getY()+directions[1].getY())/2;
//
//
//				this.selectedPosition= canvas.getView().computePositionFromScreenPoint(avgX,avgY);
//				Position prevPosition = computePositionAtPoint(prevAvgX, prevAvgY);
//				Position curPosition = computePositionAtPoint(avgX, avgY);
//
//
//				//				if (prevPosition != null && curPosition != null){
//				//				this.controller.pan(prevPosition, curPosition);
//				//				}    else {
//				if ((prevPosition != null && curPosition != null)
//						&&(Math.abs(prevPosition.subtract(curPosition).getLatitude().degrees)<2.0&&Math.abs(prevPosition.subtract(curPosition).getLongitude().degrees)<2.0)){
//					this.controller.pan(avgDirY, avgDirX);
//				}
//
//
//			} else {
//				// zoom || rotate 
//				double ratio=1.0;
//				Angle tilt=Angle.fromDegrees(0.0);
//
//				boolean isZoom=true;//(dirX1>4 && dirY1>4)  || (dirX2>4 && dirY2>4);
//
//				final Point2D p0 = positions[0];
//				final Point2D p1 = positions[1];
//
//				final Point2D prevP0 = new Point2D.Double(p0.getX()-xdir1,p0.getY()-ydir1) ;
//				final Point2D prevP1 = new Point2D.Double(p1.getX()-xdir2,p1.getY()-ydir2) ;
//
//				double prevDist = prevP0.distance(prevP1);
//				double actDist = p0.distance(p1);
//
//				//log.debug("prev. dist: "+prevDist+ ", act. dist:"+actDist);
//
//				final Point2D prevP0P1 = new Point2D.Double(prevP1.getX()-prevP0.getX(),prevP1.getY()-prevP0.getY()) ;
//				final Point2D p0p1 = new Point2D.Double(p1.getX()-p0.getX(),p1.getY()-p0.getY()) ;
//
//				double angleFrom = polar(prevP0P1);
//				double angleTo =  polar(p0p1);
//				double angle = angleFrom - angleTo;
//
//				
//				try {
//					
//					
//					
//					// Zoom-Geste
//	
//					
//					
//					
//				} catch (ConcurrentModificationException e) {
//					log.info("ERROR! ConcurrentModificationException: "+
//							e.getMessage());
//				} catch (NullPointerException e) {
//					log.info("ERROR! NullPointerException: "+
//							e.getMessage());
//				}
//
//			}
//
//		}
//		canvas.redraw();
//	}
//
//	public void addTuioCur(long session_id) {
//		touchCurList.put(new Long(session_id), new TouchCursor(session_id));
//	}
//
//	public void updateTuioCur(long session_id, float x, float y, float X, float Y, float m) {
//		if ( !touchCurList.containsKey(new Long(session_id))) {
//			addTuioCur(session_id);
//		}
//		TouchCursor tc = (TouchCursor) touchCurList.get(new Long(session_id));
//		tc.update(x, y, X, Y, (float) Math.sqrt(X * X + Y * Y), m);
//
//		refresh();
//	}
//
//	public void removeTuioCur(long session_id) {
//		touchCurList.remove(new Long(session_id));
//
//	}
//
//	public void addTuioObj(long session_id, int fiducial_id) {
//		// !!only for tuio-simulator!!
//		touchCurList.put(new Long(session_id), new TouchCursor(session_id));
//	}
//
//	public void updateTuioObj(long session_id,
//			int fiducial_id,
//			float x,
//			float y,
//			float a,
//			float X,
//			float Y,
//			float R,
//			float m,
//			float r) {
//
//		// !!only for tuio-simulator!!
//		if ( !touchCurList.containsKey(new Long(session_id))) {
//			addTuioCur(session_id);
//		}
//		TouchCursor tc = (TouchCursor) touchCurList.get(new Long(session_id));
//		tc.update(x, y, X, Y, (float) Math.sqrt(X * X + Y * Y), m);
//		refresh();
//	}
//
//	public void removeTuioObj(long session_id, int fiducial_id) {
//		touchCurList.remove(new Long(session_id));
//	}
//
//	private Position computePositionAtPoint(double pointX, double pointY)  {
//		Position position = null;
//		View view = canvas.getView();
//		if (view != null
//				&& this.canvas != null
//				&& this.canvas.getModel() != null
//				&& this.canvas.getModel().getGlobe() != null)  {
//			Globe globe = this.canvas.getModel().getGlobe();
//			Line line = view.computeRayFromScreenPoint(pointX, pointY);
//			if (line != null)  {
//				// Attempt to intersect with spheroid of scaled radius.
//				// This will simulate dragging the selected position more accurately.
//				double eyeElevation = view.getEyePosition().getElevation();
//				double selectedElevation = this.selectedPosition != null ? this.selectedPosition.getElevation() : 0;
//				// Intersect with the scaled spheroid, but only when the eye is not inside that spheroid.
//				if (eyeElevation > selectedElevation)  {
//					Intersection[] intersection = globe.intersect(line, selectedElevation);
//					if (intersection != null && intersection.length != 0)
//						position = globe.computePositionFromPoint(intersection[0].getIntersectionPoint());
//				}
//			}
//		}
//		return position;
//	}
//
//	private double computeDistance(Point2D p1, Point2D p2){
//		Position pos1 = computePositionAtPoint(p1.getX(), p1.getY());
//		Position pos2 = computePositionAtPoint(p2.getX(), p2.getY());
//		double ellipsoidaldistance=0.0;
//		if(pos1!=null || pos2!=null){
//			ellipsoidaldistance = LatLon.ellipsoidalDistance(pos1.getLatLon(), pos2.getLatLon(), 
//					canvas.getModel().getGlobe().getEquatorialRadius(),
//					canvas.getModel().getGlobe().getPolarRadius());
//		} else {
//			log.debug("Computing distance failed, Pos1: "+pos1+", Pos2: "+pos2);
//		}
//		return ellipsoidaldistance;
//	}
//
//	private double polar(Point2D p1){
//		double x,y, radius, angle;
//		double polar=0.0;
//		x=p1.getX();
//		y=p1.getY();
//		radius=Math.sqrt(x*x+y*y);
//		angle=Math.toDegrees(Math.acos(x/radius));
//
//		if(y<0){
//			polar=360-angle;
//		} else {
//			polar=angle;
//		}
//
//		return polar;
//	}
//
//	private double getCanvasX(double x){
//		x=x-wwjRec.getMinX()-myAppFrame.getInsets().left;
//		return x;
//	}
//
//	private double getCanvasY(double y){
//		y=y-wwjRec.getMinY()-myAppFrame.getInsets().top;
//		return y;
//	}
//
//}
