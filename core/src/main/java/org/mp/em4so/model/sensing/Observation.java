/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.sensing;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mp.em4so.model.common.CommonConstants;




// TODO: Auto-generated Javadoc
/**
 * Reading for a specific environment property.
 *
 * @author Based on OGC SensorThing Model
 * @version 1.0
 * @updated 17-Apr-2015 20:13:55
 */
public class Observation{

	/** Collection of values read for the corresponding property. */
	
	private Property property;
	
	/** The sensor. */
	private Sensor sensor;
	
	/** The time. */
	private Date time;
	
	/** The value. */
	private String value;
	
	/** The sdf. */
	private SimpleDateFormat sdf;
	
	/** The default date pattern. */
	private final String defaultDatePattern = CommonConstants.defaultDateTimeFormat;

	/**
	 * Instantiates a new observation.
	 *
	 * @param <T> the generic type
	 * @param op the op
	 * @param sensor the sensor
	 * @param date the date
	 * @param value the value
	 */
	public <T extends Sensor>Observation(Property op, T sensor, Date date, String value){
		property = op;
		this.time = date;
		this.value = value;
		this.sensor = sensor;
		sdf = new SimpleDateFormat();
	}
	
	/**
	 * Instantiates a new observation.
	 *
	 * @param op the op
	 */
	public Observation(Property op){
		property = op;
		sdf = new SimpleDateFormat();
	}
	
	/**
	 * Instantiates a new observation.
	 *
	 * @param <T> the generic type
	 * @param sensor the sensor
	 */
	public <T extends Sensor>Observation(T sensor){
		this.sensor = sensor;
		sdf = new SimpleDateFormat();
	}
	
	/**
	 * Instantiates a new observation.
	 *
	 * @param <T> the generic type
	 */
	public <T extends Sensor>Observation(){
		sdf = new SimpleDateFormat();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

	}

	

	/**
	 * Gets the property.
	 *
	 * @return the property
	 */
	public Property getProperty() {
		return property;
	}


	/**
	 * Sets the property.
	 *
	 * @param observableProperty the new property
	 */
	public void setProperty(Property observableProperty) {
		this.property = observableProperty;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	
	/**
	 * Gets the time.
	 *
	 * @param formatPattern the format pattern
	 * @return the time
	 */
	public String getTime(String formatPattern) {
		sdf.applyPattern(formatPattern);
		return sdf.format(this.getTime());
	}
	
	/**
	 * Gets the time in String with the default format.
	 *
	 * @param formatPattern the format pattern
	 * @return the time
	 */
	public String getStringTime() {
		sdf.applyPattern(defaultDatePattern);
		return sdf.format(this.getTime()).toString();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the time.
	 *
	 * @param date the new time
	 */
	public void setTime(Date date) {
		this.time = date;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Gets the sensor.
	 *
	 * @return the sensor
	 */
	public Sensor getSensor() {
		return sensor;
	}
	
	/**
	 * Sets the sensor.
	 *
	 * @param sensor the new sensor
	 */
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}


}