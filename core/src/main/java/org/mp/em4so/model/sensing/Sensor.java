/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.sensing;

import java.util.List;

import org.mp.em4so.model.common.Device;


// TODO: Auto-generated Javadoc
/**
 * The Class Sensor.
 */
public class Sensor extends Device {
	
	/** The property names. */
	private List<String> propertyNames;
	
	/**
	 * Instantiates a new sensor.
	 */
	public Sensor(){
		super();
	}

	/**
	 * Gets the property names.
	 *
	 * @return the property names
	 */
	public List<String> getPropertyNames() {
		return propertyNames;
	}

	/**
	 * Sets the property names.
	 *
	 * @param propertyNames the new property names
	 */
	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
	
}
