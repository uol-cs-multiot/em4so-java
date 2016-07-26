/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.common;

import java.util.Date;



// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 *
 * @author based on OGC SensorThing Model
 */
public class Location {
	
	/** Time of location. */
	private Date time;
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	
	/**
	 * Sets the time.
	 *
	 * @param time the new time
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}
