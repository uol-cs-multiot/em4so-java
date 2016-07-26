/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.common;

import java.util.List;

import org.mp.em4so.model.sensing.Property;



// TODO: Auto-generated Javadoc
/**
 * The Class Device.
 *
 * @author me
 * @version 1.0
 * @created 20-Nov-2014 11:05:14 AM
 */
public abstract class Device {


	
	/**
	 * Type of sensor/actuator: temperature, light, movement, etc..
	 */
	protected String kind;
	
	/** The id. */
	protected String id;
	
	/** The description. */
	protected String description;
	
	/** The location. */
	protected Location location;
	
	/** The capability. */
	protected String capability;
	
	/** Indicates whether the device is active (reads/act upon request) or passive (reads/acts permanently). */
	protected String mode;
	
	/** The property. */
	protected List<Property> property;
	
	
	/**
	 * Instantiates a new device.
	 */
	public Device(){
	}
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

	}

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the kind.
	 *
	 * @param type the new kind
	 */
	public void setKind(String type) {
		this.kind = type;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Gets the property.
	 *
	 * @return the property
	 */
	public List<Property> getProperty() {
		return property;
	}

	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty(List<Property> property) {
		this.property = property;
	}




	/**
	 * Gets the capability.
	 *
	 * @return the capability
	 */
	public String getCapability() {
		return capability;
	}




	/**
	 * Sets the capability.
	 *
	 * @param capability the new capability
	 */
	public void setCapability(String capability) {
		this.capability = capability;
	}
	


}