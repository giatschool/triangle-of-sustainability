package kinect;

//Skeletons.java
//Andrew Davison, December 2011, ad@fivedots.psu.ac.th

/* Skeletons sets up four 'observers' (listeners) so that 
 when a new user is detected in the scene, a standard pose for that 
 user is detected, the user skeleton is calibrated in the pose, and then the
 skeleton is tracked. The start of tracking adds a skeleton entry to userSkels.

 Each call to update() updates the joint positions for each user's
 skeleton.

 Each call to draw() draws each user's skeleton, with a rotated HEAD_FNM
 image for their head, and status text at the body's center-of-mass.


 ========== Changes (December 2011) ================

 Added SkeletonsGestures and GestureSequences gesture detector objects.
 SkeletonsGestures looks for the starting and stopping of basic gestures 
 in a user's skeleton, and notifies a watcher (TrackerPanel in this code).

 GestureSequences stores the sequence of a user's gestures so that more
 sub-sequences of gestures, making up higher-level gestures, can
 be detected, and reported to the watcher.

 CalibrationCompleteObserver and LostUserObserver have been modified to add and
 remove users to the detectors.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Toolkit;

import javax.imageio.ImageIO;

import org.OpenNI.CalibrationProgressEventArgs;
import org.OpenNI.CalibrationProgressStatus;
import org.OpenNI.DepthGenerator;
import org.OpenNI.FieldOfView;
import org.OpenNI.IObservable;
import org.OpenNI.IObserver;
import org.OpenNI.Point3D;
import org.OpenNI.PoseDetectionCapability;
import org.OpenNI.PoseDetectionEventArgs;
import org.OpenNI.SkeletonCapability;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;
import org.OpenNI.SkeletonProfile;
import org.OpenNI.StatusException;
import org.OpenNI.UserEventArgs;
import org.OpenNI.UserGenerator;

public class Skeletons {

	private static final String HEAD_FNM = "images/gorilla.png";

	private BufferedImage headImage;

	private static final int MAX_POINTS = 4;

	// used to color a user's limbs so they're different from the user's body
	// color
	private Color USER_COLORS[] = { Color.RED, Color.BLUE, Color.CYAN,
			Color.GREEN, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.WHITE };
	// same user colors as in TrackersPanel

	// OpenNI
	private UserGenerator userGen;
	private DepthGenerator depthGen;

	// OpenNI capabilities used by UserGenerator
	private SkeletonCapability skelCap;
	// to output skeletal data, including the location of the joints
	private PoseDetectionCapability poseDetectionCap;
	// to recognize when the user is in a specific position

	private String calibPoseName = null;

	private HashMap<Integer, HashMap<SkeletonJoint, SkeletonJointPosition>> userSkels;

	private boolean skelReady = false;
	private ArrayList<Point3D> leftHandCoords;
	private ArrayList<Point3D> rightHandCoords;
	private Point3D headPoint;
	private Point3D leftShoulder;
	private Point3D rightShoulder;

	private float rightArmLength = 10000f;
	private float leftArmLength = 10000f;;

	public boolean user1 = false;
	public boolean userE = false;
	public boolean userL = false;
	/*
	 * userSkels maps user IDs --> a joints map (i.e. a skeleton) skeleton maps
	 * joints --> positions (was positions + orientations)
	 */

	// gesture detectors (NEW)
	private GestureSequences gestSeqs;
	private SkeletonsGestures skelsGests;

	public Skeletons(UserGenerator userGen, DepthGenerator depthGen,
			GesturesWatcher watcher) {
		this.userGen = userGen;
		this.depthGen = depthGen;

		headImage = loadImage(HEAD_FNM);

		configure();
		userSkels = new HashMap<Integer, HashMap<SkeletonJoint, SkeletonJointPosition>>();

		// create the two gesture detectors, and tell them who to notify (NEW)
		gestSeqs = new GestureSequences(watcher);
		skelsGests = new SkeletonsGestures(watcher, userSkels, gestSeqs);

		leftHandCoords = new ArrayList<Point3D>(MAX_POINTS);
		rightHandCoords = new ArrayList<Point3D>(MAX_POINTS);
		headPoint = new Point3D();
		leftShoulder = new Point3D();
		rightShoulder = new Point3D();
	} // end of Skeletons()

	private BufferedImage loadImage(String fnm)
	// load the image from fnm
	{
		BufferedImage im = null;
		try {
			im = ImageIO.read(new File(fnm));
			System.out.println("Loaded image from " + fnm);
		} catch (Exception e) {
			System.out.println("Unable to load image from " + fnm);
		}

		return im;
	}

	private void configure()
	/*
	 * create pose and skeleton detection capabilities for the user generator,
	 * and set up observers (listeners)
	 */
	{
		try {
			// setup UserGenerator pose and skeleton detection capabilities;
			// should really check these using
			// ProductionNode.isCapabilitySupported()
			poseDetectionCap = userGen.getPoseDetectionCapability();

			skelCap = userGen.getSkeletonCapability();
			calibPoseName = skelCap.getSkeletonCalibrationPose(); // the 'psi'
																	// pose
			skelCap.setSkeletonProfile(SkeletonProfile.ALL);
			// other possible values: UPPER_BODY, LOWER_BODY, HEAD_HANDS

			skelCap.setSmoothing(0.2f);

			// set up four observers
			if (user1 == false) {
				userGen.getNewUserEvent().addObserver(new NewUserObserver()); // new
																				// user
																				// found
				userGen.getLostUserEvent().addObserver(new LostUserObserver()); // lost
																				// a
																				// user

				userGen.getUserExitEvent().addObserver(new UserExitObserver());

				poseDetectionCap.getPoseDetectedEvent().addObserver(
						new PoseDetectedObserver());
				// for when a pose is detected

				skelCap.getCalibrationCompleteEvent().addObserver(
						new CalibrationCompleteObserver());
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	} // end of configure()

	// --------------- updating ----------------------------

	public void update()
	// update skeleton of each user
	{
		 if (userE == false && userL == false)
		 user1 = false;
		 
		System.out.println("User1: " + user1);
		System.out.println("UserE: " + userE);
		System.out.println("UserL: " + userL);
//		if(userE == false && userL ==
//				 true)Toolkit.getDefaultToolkit().beep();
		try {
			int[] userIDs = userGen.getUsers(); // there may be many users in
												// the scene
			for (int i = 0; i < userIDs.length; ++i) {
				int userID = userIDs[i];
				if (skelCap.isSkeletonCalibrating(userID)) {
					skelReady = false;
					continue; // test to avoid occassional crashes with
								// isSkeletonTracking()
				}
				if (skelCap.isSkeletonTracking(userID)) {
					updateJoints(userID);

					addLeftHandPoint(getJointPos(userSkels.get(userID),
							SkeletonJoint.LEFT_HAND));
					addRightHandPoint(getJointPos(userSkels.get(userID),
							SkeletonJoint.RIGHT_HAND));
					headPoint = getJointPos(userSkels.get(userID),
							SkeletonJoint.HEAD);
					leftShoulder = getJointPos(userSkels.get(userID),
							SkeletonJoint.LEFT_SHOULDER);
					rightShoulder = getJointPos(userSkels.get(userID),
							SkeletonJoint.RIGHT_SHOULDER);

					// gesture start/finish
					gestSeqs.checkSeqs(userID); // NEW
					skelsGests.checkGests(userID);
					skelReady = true;
				}
				break; // fix for multiple users??
			}
		} catch (StatusException e) {
			System.out.println(e);
		}
	} // end of update()

	private void updateJoints(int userID)
	// update all the joints for this userID in userSkels
	{
		HashMap<SkeletonJoint, SkeletonJointPosition> skel = userSkels
				.get(userID);

		updateJoint(skel, userID, SkeletonJoint.HEAD);
		updateJoint(skel, userID, SkeletonJoint.NECK);

		updateJoint(skel, userID, SkeletonJoint.LEFT_SHOULDER);
		updateJoint(skel, userID, SkeletonJoint.LEFT_ELBOW);
		updateJoint(skel, userID, SkeletonJoint.LEFT_HAND);

		updateJoint(skel, userID, SkeletonJoint.RIGHT_SHOULDER);
		updateJoint(skel, userID, SkeletonJoint.RIGHT_ELBOW);
		updateJoint(skel, userID, SkeletonJoint.RIGHT_HAND);

		updateJoint(skel, userID, SkeletonJoint.TORSO);

		updateJoint(skel, userID, SkeletonJoint.LEFT_HIP);
		updateJoint(skel, userID, SkeletonJoint.LEFT_KNEE);
		updateJoint(skel, userID, SkeletonJoint.LEFT_FOOT);

		updateJoint(skel, userID, SkeletonJoint.RIGHT_HIP);
		updateJoint(skel, userID, SkeletonJoint.RIGHT_KNEE);
		updateJoint(skel, userID, SkeletonJoint.RIGHT_FOOT);
		// computeArmLength(userID);
	} // end of updateJoints()

	private void updateJoint(
			HashMap<SkeletonJoint, SkeletonJointPosition> skel, int userID,
			SkeletonJoint joint)
	/*
	 * update the position of the specified user's joint by looking at the
	 * skeleton capability
	 */
	{
		try {
			// report unavailable joints (should not happen)
			if (!skelCap.isJointAvailable(joint)
					|| !skelCap.isJointActive(joint)) {
				System.out.println(joint + " not available for updates");
				return;
			}

			SkeletonJointPosition pos = skelCap.getSkeletonJointPosition(
					userID, joint);
			if (pos == null) {
				System.out.println("No update for " + joint);
				return;
			}

			SkeletonJointPosition jPos = null;
			if (pos.getPosition().getZ() != 0) // has a depth position
				jPos = new SkeletonJointPosition(
						depthGen.convertRealWorldToProjective(pos.getPosition()),
						pos.getConfidence());
			else
				// no info found for that user's joint
				jPos = new SkeletonJointPosition(new Point3D(), 0);
			skel.put(joint, jPos);
		} catch (StatusException e) {
			System.out.println(e);
		}
	} // end of updateJoint()

	public synchronized void addLeftHandPoint(Point3D realPt) {
		leftHandCoords.add(realPt);
		if (leftHandCoords.size() > MAX_POINTS) // get rid of the oldest
			leftHandCoords.remove(0);
	}

	public synchronized void addRightHandPoint(Point3D realPt) {
		rightHandCoords.add(realPt);
		if (rightHandCoords.size() > MAX_POINTS) // get rid of the oldest point
			rightHandCoords.remove(0);

	}

	// -------------------- drawing --------------------------------

	public void draw(Graphics2D g2d)
	// draw skeleton of each user, with a head image, and user status
	{
		if (skelReady) {
			g2d.setStroke(new BasicStroke(8));

			try {
				int[] userIDs = userGen.getUsers();
				for (int i = 0; i < userIDs.length; ++i) {
					setLimbColor(g2d, userIDs[i]);
					if (skelCap.isSkeletonCalibrating(userIDs[i])) {

					} // test to avoid occassional crashes with
						// isSkeletonTracking()
					else if (skelCap.isSkeletonTracking(userIDs[i])) {
						HashMap<SkeletonJoint, SkeletonJointPosition> skel = userSkels
								.get(userIDs[i]);
						drawSkeleton(g2d, skel);
						// drawHead(g2d, skel);
					}
					// drawUserStatus(g2d, userIDs[i]);
				}
			} catch (StatusException e) {
				System.out.println(e);
			}
		}
	} // end of draw()

	private void drawHead(Graphics2D g2d,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel)
	// draw a head image rotated around the z-axis to follow the neck-->head
	// line
	{
		if (headImage == null)
			return;

		Point3D headPt = getJointPos(skel, SkeletonJoint.HEAD);
		Point3D neckPt = getJointPos(skel, SkeletonJoint.NECK);
		if ((headPt == null) || (neckPt == null))
			return;
		else {
			int angle = 90 - ((int) Math.round(Math.toDegrees(Math.atan2(
					neckPt.getY() - headPt.getY(),
					headPt.getX() - neckPt.getX()))));
			// System.out.println("Head image rotated from vertical by " + angle
			// + " degrees");
			drawRotatedHead(g2d, headPt, headImage, angle);
		}
	}

	private void drawRotatedHead(Graphics2D g2d, Point3D headPt,
			BufferedImage headImage, int angle) {
		AffineTransform origTF = g2d.getTransform(); // store original
														// orientation
		AffineTransform newTF = (AffineTransform) (origTF.clone());

		// center of rotation is the head joint
		newTF.rotate(Math.toRadians(angle), (int) headPt.getX(),
				(int) headPt.getY());
		g2d.setTransform(newTF);

		// draw image centered at head joint
		int x = (int) headPt.getX() - (headImage.getWidth() / 2);
		int y = (int) headPt.getY() - (headImage.getHeight() / 2);
		g2d.drawImage(headImage, x, y, null);

		g2d.setTransform(origTF); // reset original orientation
	}

	private void setLimbColor(Graphics2D g2d, int userID)
	/*
	 * use the 'opposite' of the user ID color for the limbs, so they stand out
	 * against the colored body
	 */
	{
		Color c = USER_COLORS[userID % USER_COLORS.length];
		Color oppColor = new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
		g2d.setColor(oppColor);
	} // end of setLimbColor()

	public boolean firstSkeletonIsReady() {
		return skelReady;
		// && userSkels.get(UserID).get(SkeletonJoint.LEFT_HAND) != null
		// && userSkels.get(UserID).get(SkeletonJoint.RIGHT_HAND) != null;
	}

	public Point3D getHeadPos() {
		return headPoint;
	}

	public Point3D getLeftShoulder() {
		return leftShoulder;
	}

	public Point3D getRightShoulder() {
		return rightShoulder;
	}

	public Point3D getLeftHandCoords() {
		if (leftHandCoords.size() >= MAX_POINTS) {
			return leftHandCoords.get(MAX_POINTS - 1);
		} else {
			return leftHandCoords.get(0);
		}
	}

	public Point3D getLeftHandPreviousCoords() {

		return leftHandCoords.get(0);
	}

	public Point3D getRightHandCoords() {
		if (rightHandCoords.size() == MAX_POINTS) {
			return rightHandCoords.get(MAX_POINTS - 1);
		} else {
			return rightHandCoords.get(0);
		}
	}

	public Point3D getRightHandPreviousCoords() {
		return rightHandCoords.get(0);
	}

	private void drawSkeleton(Graphics2D g2d,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel)
	// draw skeleton as lines (limbs) between its joints;
	// hardwired to avoid non-implemented joints
	{
		drawLine(g2d, skel, SkeletonJoint.HEAD, SkeletonJoint.NECK);

		drawLine(g2d, skel, SkeletonJoint.LEFT_SHOULDER, SkeletonJoint.TORSO);
		drawLine(g2d, skel, SkeletonJoint.RIGHT_SHOULDER, SkeletonJoint.TORSO);

		drawLine(g2d, skel, SkeletonJoint.NECK, SkeletonJoint.LEFT_SHOULDER);
		drawLine(g2d, skel, SkeletonJoint.LEFT_SHOULDER,
				SkeletonJoint.LEFT_ELBOW);
		drawLine(g2d, skel, SkeletonJoint.LEFT_ELBOW, SkeletonJoint.LEFT_HAND);

		drawLine(g2d, skel, SkeletonJoint.NECK, SkeletonJoint.RIGHT_SHOULDER);
		drawLine(g2d, skel, SkeletonJoint.RIGHT_SHOULDER,
				SkeletonJoint.RIGHT_ELBOW);
		drawLine(g2d, skel, SkeletonJoint.RIGHT_ELBOW, SkeletonJoint.RIGHT_HAND);

		drawLine(g2d, skel, SkeletonJoint.LEFT_HIP, SkeletonJoint.TORSO);
		drawLine(g2d, skel, SkeletonJoint.RIGHT_HIP, SkeletonJoint.TORSO);
		drawLine(g2d, skel, SkeletonJoint.LEFT_HIP, SkeletonJoint.RIGHT_HIP);

		drawLine(g2d, skel, SkeletonJoint.LEFT_HIP, SkeletonJoint.LEFT_KNEE);
		drawLine(g2d, skel, SkeletonJoint.LEFT_KNEE, SkeletonJoint.LEFT_FOOT);

		drawLine(g2d, skel, SkeletonJoint.RIGHT_HIP, SkeletonJoint.RIGHT_KNEE);
		drawLine(g2d, skel, SkeletonJoint.RIGHT_KNEE, SkeletonJoint.RIGHT_FOOT);

	} // end of drawSkeleton()

	private void drawLine(Graphics2D g2d,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel,
			SkeletonJoint j1, SkeletonJoint j2)
	// draw a line (limb) between the two joints (if they have positions)
	{
		Point3D p1 = getJointPos(skel, j1);
		Point3D p2 = getJointPos(skel, j2);
		if ((p1 != null) && (p2 != null))
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(),
					(int) p2.getY());
	} // end of drawLine()

	private Point3D getJointPos(
			HashMap<SkeletonJoint, SkeletonJointPosition> skel, SkeletonJoint j)
	// get the (x, y, z) coordinate for the joint (or return null)
	{
		SkeletonJointPosition pos = skel.get(j);
		if (pos == null)
			return null;

		// if (pos.getConfidence() == 0)
		// return null; // don't draw a line to a joint with a zero-confidence
		// pos

		return pos.getPosition();
	} // end of getJointPos()

	// --------------------- 4 observers -----------------------
	/*
	 * user detection --> pose detection --> skeleton calibration --> skeleton
	 * tracking (and creation of userSkels entry) + may also lose a user (and so
	 * delete its userSkels entry)
	 * 
	 * ===== Changes (December 2011) ============= LostUserObserver and
	 * CalibrationCompleteObserver update the gesture detectors
	 */

	class NewUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			System.out.println("Detected new user " + args.getId());
			try {
				// try to detect a pose for the new user
				poseDetectionCap
						.StartPoseDetection(calibPoseName, args.getId()); // big-S
				// ?
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	} // end of NewUserObserver inner class

	class LostUserObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("Lost track of user " + userID);
			userL = false;
			// remove user from the gesture detectors (NEW)
			// userSkels.remove(userID);
			// gestSeqs.removeUser(userID);
			// skelReady = false;
		}
	} // end of LostUserObserver inner class

	class PoseDetectedObserver implements IObserver<PoseDetectionEventArgs> {
		public void update(IObservable<PoseDetectionEventArgs> observable,
				PoseDetectionEventArgs args) {
			int userID = args.getUser();
			System.out.println(args.getPose() + " pose detected for user "
					+ userID);
			try {
				// finished pose detection; switch to skeleton calibration
				poseDetectionCap.StopPoseDetection(userID); // big-S ?
				skelCap.requestSkeletonCalibration(userID, true);
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}
	} // end of PoseDetectedObserver inner class

	class CalibrationCompleteObserver implements
			IObserver<CalibrationProgressEventArgs> {
		public void update(
				IObservable<CalibrationProgressEventArgs> observable,
				CalibrationProgressEventArgs args) {
			int userID = args.getUser();
			System.out.println("Calibration status: " + args.getStatus()
					+ " for user " + userID);
			try {
				if (args.getStatus() == CalibrationProgressStatus.OK) {
					// calibration succeeded; move to skeleton tracking
					System.out.println("Starting tracking user " + userID);
					skelCap.startTracking(userID);

					// add user to the gesture detectors (NEW)
					userSkels
							.put(new Integer(userID),
									new HashMap<SkeletonJoint, SkeletonJointPosition>());
					// create new skeleton map for the user
					gestSeqs.addUser(userID);
					user1 = true;
					userE = true;
					userL = true;
				} else
					// calibration failed; return to pose detection
					poseDetectionCap.StartPoseDetection(calibPoseName, userID); // big-S
																				// ?
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}

	} // end of CalibrationCompleteObserver inner class

	class UserExitObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("User " + userID + " exit");

			// remove user from the gesture detectors (NEW)
			userSkels.remove(userID);
			gestSeqs.removeUser(userID);
			userE = false;
			// if (userSkels.containsKey(userGen)) {
			try {

				skelCap.stopTracking(userID);
				skelCap.clearSkeletonCalibrationData(userID);
				skelCap.reset(userID);
			} catch (StatusException e) {
				// e.printStackTrace();
			}
			// }
			skelReady = false;
		}
	}

	private void computeArmLength(int userID) {
		Point3D rightHand = getJointPos(userSkels.get(userID),
				SkeletonJoint.RIGHT_HAND);
		Point3D rightElbow = getJointPos(userSkels.get(userID),
				SkeletonJoint.RIGHT_ELBOW);
		Point3D rightShoulder = getJointPos(userSkels.get(userID),
				SkeletonJoint.RIGHT_SHOULDER);

		Point3D leftHand = getJointPos(userSkels.get(userID),
				SkeletonJoint.LEFT_HAND);
		Point3D leftElbow = getJointPos(userSkels.get(userID),

		SkeletonJoint.LEFT_ELBOW);
		Point3D leftShoulder = getJointPos(userSkels.get(userID),
				SkeletonJoint.LEFT_SHOULDER);

		// System.out.println("hand to Elbow "+distApart(rightHand,
		// rightElbow)+" elbow to shoulder "+distApart(rightElbow,
		// rightShoulder)+ " hand to shoulder "+distApart(rightHand,
		// rightShoulder));
		rightArmLength = distApart(rightHand, rightElbow)
				+ distApart(rightElbow, rightShoulder);
		leftArmLength = distApart(leftHand, leftElbow)
				+ distApart(leftElbow, leftShoulder);

	}

	private float distApart(Point3D p1, Point3D p2)
	// the Euclidian distance between the two points
	{
		float dist = (float) Math.sqrt((p1.getX() - p2.getX())
				* (p1.getX() - p2.getX()) + (p1.getY() - p2.getY())
				* (p1.getY() - p2.getY()) + (p1.getZ() - p2.getZ())
				* (p1.getZ() - p2.getZ()));
		return dist;
	} // end of distApart()

} // end of Skeletons class

