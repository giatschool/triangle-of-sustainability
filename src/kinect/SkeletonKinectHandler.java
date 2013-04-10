package kinect;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

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
import org.OpenNI.StatusException;
import org.OpenNI.UserGenerator;

import de.ifgi.worldwind.amazon.AmazonDeforestation.AppFrame;
import de.ifgi.worldwind.amazon.AppFrameController;

@SuppressWarnings("serial")
public class SkeletonKinectHandler extends JPanel implements Runnable,
		GesturesWatcher {

	private int armlen = 400; // this value is used to determine if the user
								// wants to interact or not
								// 400 is a good value for adults

	private BufferedImage image = null;
	private BufferedImage skelimg = null;
	private int imWidth, imHeight;

	private WorldWindowGLCanvas canvas;
	private AppFrameController controller;

	private Point3D swipeStart;
	private Point3D swipeEnd;

	private boolean rightHandPan = true;

	private boolean leftHandUp = false;
	private boolean rightHandUp = false;

	// Kinect stuff
	private Context context;

	private volatile boolean isRunning;

	/*
	 * colors used to draw each user's depth image, except the last (white)
	 * which is for the background
	 */

	private ImageGenerator imageGen;

	// OpenNI
	private DepthMetaData depthMD;

	private Skeletons skels; // the users' skeletons

	private boolean handupPre = false;

	private boolean swiping = false;

	private boolean swipeConsumed = false;

	public SkeletonKinectHandler(AppFrame myAppFrame) {
		this.canvas = myAppFrame.getWwd();
		this.controller = myAppFrame.getController();

		configOpenNI();

		imWidth = depthMD.getFullXRes();
		imHeight = depthMD.getFullYRes();
		System.out.println("Image dimensions (" + imWidth + ", " + imHeight
				+ ")");

		// start
		new Thread(this).start();
	}

	/*
	 * create context, depth generator, depth metadata, user generator, scene
	 * metadata, and skeletons
	 */
	private void configOpenNI() {
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

			// use depth metadata to access depth info (avoids bug with
			// DepthGenerator)
			depthMD = depthGen.getMetaData();

			UserGenerator userGen = UserGenerator.create(context);

			skels = new Skeletons(userGen, depthGen, this);

			context.startGeneratingAll();
			System.out.println("Started context generating...");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	// update Kinect camera's image
	private void updateCameraImage() {
		try {
			ByteBuffer imageBB = imageGen.getImageMap().createByteBuffer();
			image = bufToImage(imageBB);
		} catch (GeneralException e) {
			System.out.println(e);
		}
	}

	/*
	 * Transform the ByteBuffer of pixel data into a BufferedImage Converts RGB
	 * bytes to ARGB ints with no transparency.
	 */
	private BufferedImage bufToImage(ByteBuffer pixelsRGB) {
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
	}

	// -------------------- drawing ---------------------------------

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (image != null)
			g2d.drawImage(image, 0, 0, 224, 168, 0, 0, 640, 480, null);

		if (skels.firstSkeletonIsReady()) {
			skelimg = new BufferedImage(640, 480, image.getType());
			skels.draw(skelimg.createGraphics());
			g2d.drawImage(skelimg, 0, 0, 224, 168, 0, 0, 640, 480, null);
		} else {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(10));
			g2d.drawRect(5, 5, 214, 158);
		}

	}

	private double dist(Point3D p1, Point3D p2) {
		return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())
				+ (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
				+ (p1.getZ() - p2.getZ()) * (p1.getZ() - p2.getZ()));
	}

	private void update() throws StatusException {

		if (skels.firstSkeletonIsReady()) {

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
		}
	}

	/*
	 * update the Kinect info
	 */
	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			try {
				context.waitAnyUpdateAll();
				update();
			} catch (StatusException e) {
				System.out.println(e);
				System.exit(1);
			}
			skels.update();
			updateCameraImage();
			repaint();
		}
		System.out.println("bye");

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
