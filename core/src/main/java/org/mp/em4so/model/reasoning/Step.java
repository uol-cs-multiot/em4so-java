/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;

import org.mp.em4so.model.actuating.Activity;

// TODO: Auto-generated Javadoc
/**
 * The Class Step.
 */
public class Step {

/** The sequence. */
private int sequence[];

/** The activity. */
private Activity activity;

/**
 * Gets the sequence.
 *
 * @return the sequence
 */
public int[] getSequence() {
	return sequence;
}

/**
 * Sets the sequence.
 *
 * @param sequence the new sequence
 */
public void setSequence(int[] sequence) {
	this.sequence = sequence;
}

/**
 * Gets the activity.
 *
 * @return the activity
 */
public Activity getActivity() {
	return activity;
}

/**
 * Sets the activity.
 *
 * @param activity the new activity
 */
public void setActivity(Activity activity) {
	this.activity = activity;
}

}
