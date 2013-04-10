package kinect;

//Skeletons.java
//Andrew Davison, December 2011, ad@fivedots.psu.ac.th
//heavily modified by Gerald Pape June 2012.


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.OpenNI.CalibrationProgressEventArgs;
import org.OpenNI.CalibrationProgressStatus;
import org.OpenNI.DepthGenerator;
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

	private static final int MAX_POINTS = 4;

	// used to color a user's limbs so they're different from the user's body
	// color
	private Color USER_COLORS[] = { Color.RED, Color.BLUE, Color.CYAN,
			Color.GREEN, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.WHITE };

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

	private SkeletonsGestures skelsGests;

	public Skeletons(UserGenerator userGen, DepthGenerator depthGen,
			GesturesWatcher watcher) {
		this.userGen = userGen;
		this.depthGen = depthGen;

		configure();
		userSkels = new HashMap<Integer, HashMap<SkeletonJoint, SkeletonJointPosition>>();

		skelsGests = new SkeletonsGestures(watcher, userSkels);

		leftHandCoords = new ArrayList<Point3D>(MAX_POINTS);
		rightHandCoords = new ArrayList<Point3D>(MAX_POINTS);
		headPoint = new Point3D();
		leftShoulder = new Point3D();
		rightShoulder = new Point3D();
	}

	/*
	 * create pose and skeleton detection capabilities for the user generator,
	 * and set up observers (listeners)
	 */
	private void configure() {
		try {
			// setup UserGenerator pose and skeleton detection capabilities;
			// should really check these using
			// ProductionNode.isCapabilitySupported()
			poseDetectionCap = userGen.getPoseDetectionCapability();

			skelCap = userGen.getSkeletonCapability();
			calibPoseName = skelCap.getSkeletonCalibrationPose(); // the 'psi'
																	// pose
			skelCap.setSkeletonProfile(SkeletonProfile.ALL);

			skelCap.setSmoothing(0.2f);

			// set up four observers
			userGen.getNewUserEvent().addObserver(new NewUserObserver()); // new
																			// user
																			// found

			userGen.getUserExitEvent().addObserver(new UserExitObserver());

			// for when a pose is detected
			poseDetectionCap.getPoseDetectedEvent().addObserver(
					new PoseDetectedObserver());

			skelCap.getCalibrationCompleteEvent().addObserver(
					new CalibrationCompleteObserver());

		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	// --------------- updating ----------------------------

	// update skeleton of each user
	public void update() {
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

					skelsGests.checkGests(userID);
					skelReady = true;
				}
				break; // fix for multiple users??
			}
		} catch (StatusException e) {
			System.out.println(e);
		}
	}

	// update all the joints for this userID in userSkels
	private void updateJoints(int userID) {
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
	}

	/*
	 * update the position of the specified user's joint by looking at the
	 * skeleton capability
	 */
	private void updateJoint(
			HashMap<SkeletonJoint, SkeletonJointPosition> skel, int userID,
			SkeletonJoint joint) {
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
	}

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

	// draw skeleton of each user, with a head image, and user status
	public void draw(Graphics2D g2d) {
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
					}
				}
			} catch (StatusException e) {
				System.out.println(e);
			}
		}
	}

	/*
	 * use the 'opposite' of the user ID color for the limbs, so they stand out
	 * against the colored body
	 */
	private void setLimbColor(Graphics2D g2d, int userID) {
		Color c = USER_COLORS[userID % USER_COLORS.length];
		Color oppColor = new Color(255 - c.getRed(), 255 - c.getGreen(),
				255 - c.getBlue());
		g2d.setColor(oppColor);
	}

	public boolean firstSkeletonIsReady() {
		return skelReady;
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

	// draw skeleton as lines (limbs) between its joints;
	// hardwired to avoid non-implemented joints
	private void drawSkeleton(Graphics2D g2d,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel)
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

	}

	// draw a line (limb) between the two joints (if they have positions)
	private void drawLine(Graphics2D g2d,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel,
			SkeletonJoint j1, SkeletonJoint j2){
		Point3D p1 = getJointPos(skel, j1);
		Point3D p2 = getJointPos(skel, j2);
		if ((p1 != null) && (p2 != null))
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(),
					(int) p2.getY());
	}

	// get the (x, y, z) coordinate for the joint (or return null)
	private Point3D getJointPos(
			HashMap<SkeletonJoint, SkeletonJointPosition> skel, SkeletonJoint j){
		SkeletonJointPosition pos = skel.get(j);
		if (pos == null)
			return null;

		return pos.getPosition();
	}

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
	}

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
	}

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
				} else
					// calibration failed; return to pose detection
					poseDetectionCap.StartPoseDetection(calibPoseName, userID); // big-S
																				// ?
			} catch (StatusException e) {
				e.printStackTrace();
			}
		}

	}

	class UserExitObserver implements IObserver<UserEventArgs> {
		public void update(IObservable<UserEventArgs> observable,
				UserEventArgs args) {
			int userID = args.getId();
			System.out.println("User " + userID + " exit");
			userSkels.remove(userID);
			try {

				skelCap.stopTracking(userID);
				skelCap.clearSkeletonCalibrationData(userID);
				skelCap.reset(userID);
			} catch (StatusException e) {
				// e.printStackTrace();
			}
			skelReady = false;
		}
	}

}

