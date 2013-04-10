package kinect;

// SkeletonsGestures.java
//Andrew Davison, December 2011, ad@fivedots.psu.ac.th
//heavily modified by Gerald Pape June 2012.

import java.util.HashMap;

import org.OpenNI.Point3D;
import org.OpenNI.SkeletonJoint;
import org.OpenNI.SkeletonJointPosition;

public class SkeletonsGestures {

	// object that is notified of an gesture start/stop by calling its pose()
	// method
	private GesturesWatcher watcher;

	/*
	 * skeleton joints for each user; uses screen coordinate system i.e.
	 * positive z-axis is into the scene; positive x-axis is to the right;
	 * positive y-axis is down
	 */
	private HashMap<Integer, HashMap<SkeletonJoint, SkeletonJointPosition>> userSkels;

	// booleans set when gestures are being performed
	private boolean isRightHandUp = false; // right hand
	private boolean isLeftHandUp = false; // left hand

	public SkeletonsGestures(
			GesturesWatcher aw,
			HashMap<Integer, HashMap<SkeletonJoint, SkeletonJointPosition>> uSkels) {
		watcher = aw;
		userSkels = uSkels;
	}

	/*
	 * check the static gestures
	 */
	public void checkGests(int userID) {
		HashMap<SkeletonJoint, SkeletonJointPosition> skel = userSkels
				.get(userID);
		if (skel == null)
			return;

		rightHandUp(userID, skel);

		leftHandUp(userID, skel);

	}

	// -------------------------- right hand ----------------------------------
	/*
	 * the right hand gesture checking methods notify the GestureSequences
	 * object of an gesture start so that it can update the user's gesture
	 * sequence.
	 * 
	 * All the other gesture checking methods could do this, but I've kept
	 * things simple by only considering the right hand.
	 */

	// is the user's right hand at head level or above?
	private void rightHandUp(int userID,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel) {
		Point3D rightHandPt = getJointPos(skel, SkeletonJoint.RIGHT_HAND);
		Point3D headPt = getJointPos(skel, SkeletonJoint.HEAD);
		if ((rightHandPt == null) || (headPt == null))
			return;

		if (rightHandPt.getY() <= headPt.getY()) { // above
			if (!isRightHandUp) {
				watcher.pose(userID, GestureName.RIGHTHAND_UP, true); // started

				isRightHandUp = true;
			}
		} else { // not above
			if (isRightHandUp) {
				watcher.pose(userID, GestureName.RIGHTHAND_UP, false); // stopped
				isRightHandUp = false;
			}
		}
	}

	// -------------------------- left hand ----------------------------------

	// is the user's left hand at head level or above?
	private void leftHandUp(int userID,
			HashMap<SkeletonJoint, SkeletonJointPosition> skel) {
		Point3D leftHandPt = getJointPos(skel, SkeletonJoint.LEFT_HAND);
		Point3D headPt = getJointPos(skel, SkeletonJoint.NECK);
		if ((leftHandPt == null) || (headPt == null))
			return;

		if (leftHandPt.getY() <= headPt.getY()) { // above
			if (!isLeftHandUp) {
				watcher.pose(userID, GestureName.LEFTHAND_UP, true); // started
				isLeftHandUp = true;
			}
		} else { // not above
			if (isLeftHandUp) {
				watcher.pose(userID, GestureName.LEFTHAND_UP, false); // stopped
				isLeftHandUp = false;
			}
		}
	}

	// ----------------------------- support -------------------------

	// get the (x, y, z) coordinate for the joint (or return null)
	private Point3D getJointPos(
			HashMap<SkeletonJoint, SkeletonJointPosition> skel, SkeletonJoint j) {
		SkeletonJointPosition pos = skel.get(j);
		if (pos == null)
			return null;

		if (pos.getConfidence() == 0)
			return null;

		return pos.getPosition();
	}

}