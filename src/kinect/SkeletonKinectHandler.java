package kinect;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Intersection;
import gov.nasa.worldwind.geom.Line;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.swing.JPanel;

import org.OpenNI.Context;
import org.OpenNI.DepthGenerator;
import org.OpenNI.DepthMetaData;
import org.OpenNI.GeneralException;
import org.OpenNI.ImageGenerator;
import org.OpenNI.ImageMetaData;
import org.OpenNI.License;
import org.OpenNI.MapOutputMode;
import org.OpenNI.PixelFormat;
import org.OpenNI.Point3D;
import org.OpenNI.SceneMetaData;
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;

import de.ifgi.worldwind.amazon.AmazonDeforestation.AppFrame;
import de.ifgi.worldwind.amazon.AppFrameController;

public class SkeletonKinectHandler extends JPanel implements Runnable,
		GesturesWatcher {

	private static final int MAX_DEPTH_SIZE = 10000;

	private int armlen = 400;

	private BufferedImage image = null;
	private BufferedImage skelimg = null;
	private int imWidth, imHeight;

	private WorldWindowGLCanvas canvas;
	private AppFrame myAppFrame;
	private AppFrameController controller;
	private Rectangle wwjRec;

	private Position selectedPosition;

	private Point3D swipeStart;
	private Point3D swipeEnd;

	private boolean rightHandPan = true;

	private boolean leftHandUp = false;
	private boolean rightHandUp = false;

	// Kinect stuff
	private Context context;

	private volatile boolean isRunning;

	private Color USER_COLORS[] = { Color.RED, Color.BLUE, Color.CYAN,
			Color.GREEN, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.WHITE };
	/*
	 * colors used to draw each user's depth image, except the last (white)
	 * which is for the background
	 */

	private byte[] imgbytes;
	private float histogram[]; // for the depth values
	private int maxDepth = 0; // largest depth value

	private ImageGenerator imageGen;

	// OpenNI
	private DepthMetaData depthMD;

	private SceneMetaData sceneMD;
	/*
	 * used to create a labeled depth map, where each pixel holds a user ID (1,
	 * 2, etc.), or 0 to mean it is part of the background
	 */

	private Skeletons skels; // the users' skeletons

	private boolean handupPre = false;

	private boolean swiping = false;

	private boolean swipeConsumed = false;

	public SkeletonKinectHandler(AppFrame myAppFrame) {
		this.myAppFrame = myAppFrame;
		this.canvas = myAppFrame.getWwd();
		this.controller = myAppFrame.getController();

		wwjRec = canvas.getBounds();

		configOpenNI();

		histogram = new float[MAX_DEPTH_SIZE];

		imWidth = depthMD.getFullXRes();
		imHeight = depthMD.getFullYRes();
		System.out.println("Image dimensions (" + imWidth + ", " + imHeight
				+ ")");
		// create empty image bytes array of correct size and type
		imgbytes = new byte[imWidth * imHeight * 3];

		// start
		new Thread(this).start();
	}

	private void configOpenNI()
	/*
	 * create context, depth generator, depth metadata, user generator, scene
	 * metadata, and skeletons
	 */
	{
		try {
			context = new Context();

			// add the NITE Licence
			License license = new License("PrimeSense",
					"0KOIk2JeIBYClPWVnMoRKn5cdY4="); // vendor, key
			context.addLicense(license);

			// set up image and depth generators
			imageGen = ImageGenerator.create(context);
			// for displaying the scene

			DepthGenerator depthGen = DepthGenerator.create(context);
			MapOutputMode mapMode = new MapOutputMode(640, 480, 30); // xRes,
			// yRes,
			// FPS
			imageGen.setMapOutputMode(mapMode);
			depthGen.setMapOutputMode(mapMode);

			imageGen.setPixelFormat(PixelFormat.RGB24);

			ImageMetaData imageMD = imageGen.getMetaData();
			imWidth = imageMD.getFullXRes();
			imHeight = imageMD.getFullYRes();

			context.setGlobalMirror(true); // set mirror mode

			depthMD = depthGen.getMetaData();
			// use depth metadata to access depth info (avoids bug with
			// DepthGenerator)

			UserGenerator userGen = UserGenerator.create(context);
			sceneMD = userGen.getUserPixels(0);
			// used to return a map containing user IDs (or 0) at each depth
			// location

			skels = new Skeletons(userGen, depthGen, this);

			context.startGeneratingAll();
			System.out.println("Started context generating...");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	} // end of configOpenNI()

	private void updateCameraImage()
	// update Kinect camera's image
	{
		try {
			ByteBuffer imageBB = imageGen.getImageMap().createByteBuffer();
			image = bufToImage(imageBB);
		} catch (GeneralException e) {
			System.out.println(e);
		}
	} // end of updateCameraImage()

	private BufferedImage bufToImage(ByteBuffer pixelsRGB)
	/*
	 * Transform the ByteBuffer of pixel data into a BufferedImage Converts RGB
	 * bytes to ARGB ints with no transparency.
	 */
	{
		int[] pixelInts = new int[imWidth * imHeight];

		int rowStart = 0;
		// rowStart will index the first byte (red) in each row;
		// starts with first row, and moves down

		int bbIdx; // index into ByteBuffer
		int i = 0; // index into pixels int[]
		int rowLen = imWidth * 3; // number of bytes in each row
		for (int row = 0; row < imHeight; row++) {
			bbIdx = rowStart;
			// System.out.println("bbIdx: " + bbIdx);
			for (int col = 0; col < imWidth; col++) {
				int pixR = pixelsRGB.get(bbIdx++);
				int pixG = pixelsRGB.get(bbIdx++);
				int pixB = pixelsRGB.get(bbIdx++);
				pixelInts[i++] = 0xFF000000 | ((pixR & 0xFF) << 16)
						| ((pixG & 0xFF) << 8) | (pixB & 0xFF);
			}
			rowStart += rowLen; // move to next row
		}

		// create a BufferedImage from the pixel data
		BufferedImage im = new BufferedImage(imWidth, imHeight,
				BufferedImage.TYPE_INT_ARGB);
		im.setRGB(0, 0, imWidth, imHeight, pixelInts, 0, imWidth);
		return im;
	} // end of bufToImage()

	public Dimension getPreferredSize() {
		return new Dimension(imWidth, imHeight);
	}

	// -------------------- drawing ---------------------------------

	public void paintComponent(Graphics g)
	// Draw the depth image with coloured users, skeletons, and statistics info
	{
		// super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (image != null)
			// g2d.drawImage(image, 0, 0, this); // draw camera's image
			g2d.drawImage(image, 0, 0, 224, 168, 0, 0, 640, 480, null);
		// g2d.drawImage(image, 0, 0, 0, 0, 0, 0, 640, 480, null);
		if (skels.firstSkeletonIsReady()) {
			skelimg = new BufferedImage(640, 480, image.getType());
			skels.draw(skelimg.createGraphics());
			g2d.drawImage(skelimg, 0, 0, 224, 168, 0, 0, 640, 480, null);
			// g2d.drawImage(skelimg, 0, 0, 0, 0, 0, 0, 640, 480, null);
		} else {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(10));
			g2d.drawRect(5, 5, 214, 158);
		}

		// drawUserDepths(g2d);

	} // end of paintComponent()

	private void drawUserDepths(Graphics2D g2d)
	/*
	 * Create BufferedImage using the depth image bytes and a color model, then
	 * draw it
	 */
	{
		// define an 8-bit RGB channel color model
		ColorModel colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] { 8, 8, 8 }, false, false,
				ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);

		// fill the raster with the depth image bytes
		DataBufferByte dataBuffer = new DataBufferByte(imgbytes, imWidth
				* imHeight * 3);

		WritableRaster raster = Raster.createInterleavedRaster(dataBuffer,
				imWidth, imHeight, imWidth * 3, 3, new int[] { 0, 1, 2 }, null);

		// combine color model and raster to create a BufferedImage
		BufferedImage image = new BufferedImage(colorModel, raster, false, null);

		g2d.drawImage(image, 0, 0, null);

	} // end of drawUserDepths()

	/*
	 * private void refresh() { touchCurList = tracker.getHands() kinect.Hand
	 * tcur=hands.elements().nextElement();
	 * 
	 * Point2D direction= new Point2D.Double(tcur.getXdir(),tcur.getYdir());
	 * Point2D screenPosition = new Point2D.Double(tcur.getX(),tcur.getY());
	 * 
	 * System.out.println(direction.toString()+" "+screenPosition.toString());
	 * 
	 * //Point2D lastPosition; boolean isClicked = true; // boolean isClicked =
	 * tcur.isClicked(); // if(tcur.isClicked()==true) counterClick=0;
	 * 
	 * //aktuelle H�he int elevation = (int)
	 * canvas.getView().getEyePosition().getElevation() / 1000;
	 * 
	 * // single touch
	 * 
	 * if(touchCurList.size()==1){ double x = tcur.getX(); double y =
	 * tcur.getY();
	 * 
	 * // x=x-wwjRec.getMinX()-myAppFrame.getInsets().left; // y= heigth -
	 * (y-wwjRec.getMinY()-myAppFrame.getInsets().top);
	 * 
	 * 
	 * 
	 * final double dirY = tcur.getYdir(); final double dirX = tcur.getXdir();
	 * 
	 * System.out.println(x); System.out.println(dirX); System.out.println(y);
	 * System.out.println(dirY);
	 * 
	 * if (Math.abs(direction.getX()) < pointerAccurracy &&
	 * Math.abs(direction.getY()) < pointerAccurracy &&
	 * (screenPosition.distance(lastScreenPosition)) > 10.0 //2.0 && !isClicked)
	 * { if(compassRec.contains(x,y)){ controller.rotateToNorth(canvas);
	 * controller.unTilt(canvas); } else {
	 * 
	 * 
	 * x=x-wwjRec.getMinX()-myAppFrame.getInsets().left;
	 * y=height-(y-wwjRec.getMinY()-myAppFrame.getInsets().top);
	 * 
	 * 
	 * lastPointerTouch=tcur; }
	 * 
	 * } else if (Math.abs(direction.getX())>pointerAccurracy ||
	 * Math.abs(direction.getY())>pointerAccurracy) {
	 * 
	 * x=getCanvasX(x); y=getCanvasY(y); final double prevX = x+dirX; final
	 * double prevY = y+dirY;
	 * 
	 * this.selectedPosition=
	 * canvas.getView().computePositionFromScreenPoint(x,y);
	 * 
	 * Position prevPosition = computePositionAtPoint(prevX, prevY); Position
	 * curPosition = computePositionAtPoint(x, y);
	 * 
	 * //log.debug("x: "+x+", prevX: "+prevX+", y:"+y+", prevY: "+prevY+
	 * "\n prevPos: "+prevPosition+", currPos: "+curPosition ); //
	 * log.debug("( "+x+ " , "+y+" ) =>" + "( "+curPosition.getLatitude()+
	 * " , "+curPosition.getLongitude()+" ) =>"+ //
	 * "( "+canvas.getModel().getGlobe
	 * ().computePointFromPosition(curPosition).x+
	 * " , "+canvas.getModel().getGlobe
	 * ().computePointFromPosition(curPosition).y+" )");
	 * 
	 * if ((prevPosition != null && curPosition != null)
	 * &&(Math.abs(prevPosition
	 * .subtract(curPosition).getLatitude().degrees)<2.0&&
	 * Math.abs(prevPosition.subtract
	 * (curPosition).getLongitude().degrees)<2.0)){
	 * this.controller.pan(prevPosition, curPosition); } else {
	 * this.controller.pan(dirY, dirX); }
	 * 
	 * }
	 * 
	 * } // multi-touch else { Point2D[] directions; Point2D[] positions; int[]
	 * clustersize={1,1}; Enumeration<Hand> tcEnum = touchCurList.elements();
	 * 
	 * 
	 * // two touches if(touchCurList.size()==2){ directions = new Point2D[2];
	 * positions = new Point2D[2];
	 * 
	 * Hand tc1 = tcEnum.nextElement(); Hand tc2 = tcEnum.nextElement();
	 * 
	 * directions[0]=new Point2D.Double(tc1.getXdir(),tc1.getYdir());
	 * positions[0]= new Point2D.Double(tc1.getX(),tc1.getY());
	 * directions[1]=new Point2D.Double(tc2.getXdir(),tc2.getYdir());
	 * positions[1]= new Point2D.Double(tc2.getX(),tc2.getY());
	 * 
	 * } // multiple touches // else { // KMeansClustering clustering = new
	 * KMeansClustering(2); // clustering.cluster(touchCurList); //
	 * TouchCluster[]cluster= clustering.getCluster(); //cluster(2); // // // //
	 * directions = new Point2D[cluster.length]; // positions = new
	 * Point2D[cluster.length]; // // for(int i=0;i<cluster.length;i++) { //
	 * directions[i]=new
	 * Point2D.Double(cluster[i].avgXdir(),cluster[i].avgYdir()); //
	 * positions[i]= new
	 * Point2D.Double(cluster[i].avgXpos(),cluster[i].avgYpos()); //
	 * clustersize[i]=cluster[i].getSize(); // } // }
	 * 
	 * // TODO Globe navigation // check if clusters moving in the same
	 * direction final double xdir1 = directions[0].getX(); final double ydir1 =
	 * directions[0].getY(); final double xdir2 = directions[1].getX(); final
	 * double ydir2 = directions[1].getY();
	 * if(((Math.abs(xdir1-xdir2)<10)&&(Math.abs(ydir1-ydir2)<10)) ){ // pan
	 * final double avgX=(positions[0].getX()+positions[1].getX())/2; final
	 * double avgY=(positions[0].getY()+positions[1].getY())/2; final double
	 * prevAvgX = (((positions[0].getX()+directions[0].getX())
	 * +(positions[1].getX()+directions[1].getX())))/2; final double prevAvgY =
	 * (((positions[0].getY()+directions[0].getY())
	 * +(positions[1].getY()+directions[1].getY())))/2; final double avgDirX =
	 * (directions[0].getX()+directions[1].getX())/2; final double avgDirY =
	 * (directions[0].getY()+directions[1].getY())/2;
	 * 
	 * 
	 * this.selectedPosition=
	 * canvas.getView().computePositionFromScreenPoint(avgX,avgY); Position
	 * prevPosition = computePositionAtPoint(prevAvgX, prevAvgY); Position
	 * curPosition = computePositionAtPoint(avgX, avgY);
	 * 
	 * 
	 * // if (prevPosition != null && curPosition != null){ //
	 * this.controller.pan(prevPosition, curPosition); // } else { if
	 * ((prevPosition != null && curPosition != null)
	 * &&(Math.abs(prevPosition.subtract
	 * (curPosition).getLatitude().degrees)<2.0&&
	 * Math.abs(prevPosition.subtract(curPosition
	 * ).getLongitude().degrees)<2.0)){ this.controller.pan(avgDirY, avgDirX); }
	 * 
	 * 
	 * } else { // zoom || rotate double ratio=1.0; Angle
	 * tilt=Angle.fromDegrees(0.0);
	 * 
	 * boolean isZoom=true;//(dirX1>4 && dirY1>4) || (dirX2>4 && dirY2>4);
	 * 
	 * final Point2D p0 = positions[0]; final Point2D p1 = positions[1];
	 * 
	 * final Point2D prevP0 = new
	 * Point2D.Double(p0.getX()-xdir1,p0.getY()-ydir1) ; final Point2D prevP1 =
	 * new Point2D.Double(p1.getX()-xdir2,p1.getY()-ydir2) ;
	 * 
	 * double prevDist = prevP0.distance(prevP1); double actDist =
	 * p0.distance(p1);
	 * 
	 * //log.debug("prev. dist: "+prevDist+ ", act. dist:"+actDist);
	 * 
	 * final Point2D prevP0P1 = new
	 * Point2D.Double(prevP1.getX()-prevP0.getX(),prevP1.getY()-prevP0.getY()) ;
	 * final Point2D p0p1 = new
	 * Point2D.Double(p1.getX()-p0.getX(),p1.getY()-p0.getY()) ;
	 * 
	 * double angleFrom = polar(prevP0P1); double angleTo = polar(p0p1); double
	 * angle = angleFrom - angleTo;
	 * 
	 * 
	 * try {
	 * 
	 * 
	 * 
	 * // Zoom-Geste
	 * 
	 * 
	 * 
	 * 
	 * } catch (ConcurrentModificationException e) {
	 * log.info("ERROR! ConcurrentModificationException: "+ e.getMessage()); }
	 * catch (NullPointerException e) {
	 * log.info("ERROR! NullPointerException: "+ e.getMessage()); }
	 * 
	 * }
	 * 
	 * } canvas.redraw(); }
	 */

	private double dist(Point3D p1, Point3D p2) {
		return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())
				+ (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
				+ (p1.getZ() - p2.getZ()) * (p1.getZ() - p2.getZ()));
	}

	private void update() throws StatusException {

		if (skels.firstSkeletonIsReady()) {

			// System.out.println(System.currentTimeMillis()-time);
			// time = System.currentTimeMillis();
			//

			Point3D leftHandCoords = skels.getLeftHandCoords();
			Point3D rightHandCoords = skels.getRightHandCoords();
			Point3D leftPrevCoords = skels.getLeftHandPreviousCoords();
			Point3D rightPrevCoords = skels.getRightHandPreviousCoords();

			Point3D leftShoulder = skels.getLeftShoulder();
			Point3D rightShoulder = skels.getRightShoulder();

			double currHandsDist = dist(leftHandCoords, rightHandCoords);

			double prevHandsDist = dist(leftPrevCoords, rightPrevCoords);

			double distLeftHandtoShoulder = dist(leftShoulder, leftHandCoords);

			double distRightHandtoShoulder = dist(rightShoulder,
					rightHandCoords);
			// detection if the user wants to swipe through time
			if (handupPre && !swiping) {
				if (leftHandUp) {
					if (distRightHandtoShoulder > armlen - 50) {
						swipeStart = skels.getRightHandCoords();
						swiping = true;
					}
				} else if (rightHandUp) {
					if (distLeftHandtoShoulder > armlen - 50) {
						swipeStart = skels.getLeftHandCoords();
						swiping = true;
					}
				}
			} else {
				if (leftHandUp) {
					if (distRightHandtoShoulder < armlen - 50) {
						swipeEnd = skels.getRightHandCoords();
						swipeConsumed = false;
						swiping = false;
					}
				} else if (rightHandUp) {
					if (distLeftHandtoShoulder < armlen - 50) {
						swipeEnd = skels.getLeftHandCoords();
						swipeConsumed = false;
						swiping = false;
					}
				}

			}

			double rightDeltaX = rightPrevCoords.getX()
					- rightHandCoords.getX();
			double rightDeltaY = rightPrevCoords.getY()
					- rightHandCoords.getY();

			double leftDeltaX = leftPrevCoords.getX() - leftHandCoords.getX();
			double leftDeltaY = leftPrevCoords.getY() - leftHandCoords.getY();

			// time swipe detection
			if (handupPre && !swipeConsumed && !swiping && swipeStart != null
					&& swipeEnd != null) {

				if (dist(swipeStart, swipeEnd) > 100) {
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					if (swipeStart.getX() > swipeEnd.getX()) {
						System.out.println("right to left");
						controller.yearBackward();
					} else if (swipeStart.getX() < swipeEnd.getX()) {
						System.out.println("left to right");
						controller.yearForward();
					}
				}
				swipeConsumed = true;
				swipeStart = null;
				swipeStart = null;

			} else if (handupPre && swiping) {
				/*
				 * 
				 * 
				 * Do nothing and wait for swipe
				 */

			} else { // kein Swipe
				if ((rightHandPan ? (distRightHandtoShoulder > armlen && distLeftHandtoShoulder > armlen) //
						: (distRightHandtoShoulder > armlen && distLeftHandtoShoulder > armlen))) {
					if (Math.abs(prevHandsDist - currHandsDist) > 30) {
						if (prevHandsDist < currHandsDist) {
							this.controller.zoom(0.97);
						} else if (prevHandsDist > currHandsDist) {
							this.controller.zoom(1.03);
						}
					}
					return;
				} else if (distRightHandtoShoulder > armlen) { // mit der
																// rechten Hand
					this.controller.pan(rightDeltaY * 0.3, rightDeltaX * 0.3);
					rightHandPan = true;
				} else if (distLeftHandtoShoulder > armlen) { // mit der linken
																// Hand // Hand
					this.controller.pan(leftDeltaY * 0.3, leftDeltaX * 0.3);
					rightHandPan = false;
				}
			}
			canvas.redraw();
		} else {
			// controller.flyToPosition(AppFrame.MS_POS, AppFrame.GLOBE_ZOOM);
		}

	}

	private void calcHistogram(ShortBuffer depthBuf) {
		// reset histogram
		for (int i = 0; i <= maxDepth; i++)
			histogram[i] = 0;

		// record number of different depths in histogram[]
		int numPoints = 0;
		maxDepth = 0;
		while (depthBuf.remaining() > 0) {
			short depthVal = depthBuf.get();
			if (depthVal > maxDepth)
				maxDepth = depthVal;
			if ((depthVal != 0) && (depthVal < MAX_DEPTH_SIZE)) { // skip
																	// histogram[0]
				histogram[depthVal]++;
				numPoints++;
			}
		}
		// System.out.println("No. of numPoints: " + numPoints);
		// System.out.println("Maximum depth: " + maxDepth);

		// convert into a cummulative depth count (skipping histogram[0])
		for (int i = 1; i <= maxDepth; i++)
			histogram[i] += histogram[i - 1];

		/*
		 * convert cummulative depth into the range 0.0 - 1.0f which will later
		 * be used to modify a color from USER_COLORS[]
		 */
		if (numPoints > 0) {
			for (int i = 1; i <= maxDepth; i++)
				// skipping histogram[0]
				histogram[i] = 1.0f - (histogram[i] / (float) numPoints);
		}
	} // end of calcHistogram()

	private void updateUserDepths()
	/*
	 * build a histogram of 8-bit depth values, and convert it to depth image
	 * bytes where each user is coloured differently
	 */
	{
		ShortBuffer depthBuf = depthMD.getData().createShortBuffer();
		// calcHistogram(depthBuf);
		depthBuf.rewind();

		// use user IDs to colour the depth map
		ShortBuffer usersBuf = sceneMD.getData().createShortBuffer();
		/*
		 * usersBuf is a labeled depth map, where each pixel holds an user ID
		 * (e.g. 1, 2, 3), or 0 to denote that the pixel is part of the
		 * background.
		 */

		while (depthBuf.remaining() > 0) {
			int pos = depthBuf.position();
			short depthVal = depthBuf.get();
			short userID = usersBuf.get();

			imgbytes[3 * pos] = Color.TRANSLUCENT; // default colour is black
													// when there's no
			// depth data
			imgbytes[3 * pos + 1] = Color.TRANSLUCENT;
			imgbytes[3 * pos + 2] = Color.TRANSLUCENT;

			if (depthVal != 0) { // there is depth data
				// convert userID to index into USER_COLORS[]
				int colorIdx = userID % (USER_COLORS.length - 1); // skip last
																	// color

				if (userID == 0) // not a user; actually the background
					colorIdx = USER_COLORS.length - 1;

				// use last index: the position of white in USER_COLORS[]

				// convert histogram value (0.0-1.0f) to a RGB color
				float histValue = histogram[depthVal];
				imgbytes[3 * pos] = (byte) (histValue * USER_COLORS[colorIdx]
						.getRed());
				imgbytes[3 * pos + 1] = (byte) (histValue * USER_COLORS[colorIdx]
						.getGreen());
				imgbytes[3 * pos + 2] = (byte) (histValue * USER_COLORS[colorIdx]
						.getBlue());
			}
		}
	} // end of updateUserDepths()

	@Override
	public void run()
	/*
	 * update the Kinect info
	 */
	{
		isRunning = true;
		while (isRunning) {
			try {
				context.waitAnyUpdateAll();
				update();
			} catch (StatusException e) {
				System.out.println(e);
				System.exit(1);
			}
			// updateUserDepths();
			skels.update();
			updateCameraImage();
			repaint();
		}

		// close down
		// try {
		// context.stopGeneratingAll();
		// } catch (StatusException e) {
		// }
		// context.release();
		System.out.println("bye");
		// System.exit(1);
	}

	@Override
	public void pose(int userID, GestureName gest, boolean isActivated) {
		if (isActivated) {
			System.out.println(gest + " " + userID + " on");
			if (gest.name().equals("LH_UP")) {
				handupPre = true;
				leftHandUp = true;
				rightHandUp = false;
			} else if (gest.name().equals("RH_UP")) {
				handupPre = true;
				rightHandUp = true;
				leftHandUp = false;
			}
		} else {
			if (gest.name().equals("LH_UP") || gest.name().equals("RH_UP")) {
				handupPre = false;
				rightHandUp = false;
				leftHandUp = false;
			}

		}
	}

}
