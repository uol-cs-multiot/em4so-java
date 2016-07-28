/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.common.capabilities;

import java.util.Date;

import org.mp.em4so.exceptions.UnImplementedCapabilityException;
import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.sensing.Observation;
import org.mp.em4so.model.sensing.Property;
import org.mp.em4so.model.sensing.Sensor;

// TODO: Auto-generated Javadoc
/**
 * The Class SensingCapability.
 */
public class SensingCapability extends Capability {

	/** The sensor. */
	protected Sensor sensor;
	
	/**
	 * Read.
	 *
	 * @param property the property
	 * @return the observation
	 */
	public final Observation read(Property property){
		Observation observation = new Observation();
		observation.setProperty(property);
		observation.setSensor(sensor);
		try {
			observation.setValue(readValue());
		} catch (UnImplementedCapabilityException e) {
			LOG.info("No ad hoc capability for property {} / {}",property.getScope(),property.getName());
			observation.setValue(readValue(property.getScope(),property.getName()));
			
			
		}
		observation.setTime(new Date());
		return observation;
		
	}
	
	
	/**
	 * Read value.
	 *
	 * @return the string
	 * @throws UnImplementedCapabilityException the un implemented capability exception
	 */
	public String readValue() throws UnImplementedCapabilityException{
		//TODO to review: It cannot be abstract because it is used as a generic POJO when using the read values, but in order to read, every child has its implementation
		throw new UnImplementedCapabilityException ("Capability Not found for reading the required property");
	}
	
	/**
	 * Read value.
	 *
	 * @param scope the scope
	 * @param propertyName the property name
	 * @return the string
	 */
	private String readValue(String scope,String propertyName) {
		
//		String strUrl = SOMFileConfigUtils.getKBUrl()+"/"+SOMFileConfigUtils.getKBPrefix()+getSOControlAgent().getId()+ "/_design/environment/_list/currentValue/all?keys=[\""+propertyName+"\"]";
//		RESTClientUtils c = new RESTClientUtils(SOMFileConfigUtils.getKBUser() + ":" +SOMFileConfigUtils.getKBPass() );
//		String result = c.doGet(strUrl);
		LOG.trace("Starting reading of {}",propertyName);
		String result = null;
		Element element = new Element(scope,propertyName);
		LOG.trace("2- mid reading of {}",propertyName);
		element = soca.getSom().getKbm().getPropertyValue(element);
		LOG.trace("3- end reading of {}",propertyName);
		if(element!= null){
			result = element.getValue();
		}else{
			result = "";
		}
		LOG.trace("Reading {}: {}",propertyName,result);
		return result;
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
	};

}
