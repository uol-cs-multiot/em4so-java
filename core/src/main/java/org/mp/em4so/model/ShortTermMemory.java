/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model;

import java.util.List;

import org.mp.em4so.deviceManager.Actuator;
import org.mp.em4so.model.agent.Belief;
import org.mp.em4so.model.sensing.Sensor;

// TODO: Auto-generated Javadoc
/**
 * The Class ShortTermMemory.
 */
public class ShortTermMemory {
	
	/** The beliefs. */
	private List<Belief> beliefs;
	
	/** The sensors. */
	private List<Sensor> sensors;
	
	/** The actuators. */
	private List<Actuator> actuators;
	
	
	/**
	 * Gets the beliefs.
	 *
	 * @return the beliefs
	 */
	public List<Belief> getBeliefs() {
		return beliefs;
	}
	
	/**
	 * Gets the sensors.
	 *
	 * @return the sensors
	 */
	public List<Sensor> getSensors() {
		return sensors;
	}
	
	/**
	 * Gets the actuators.
	 *
	 * @return the actuators
	 */
	public List<Actuator> getActuators() {
		return actuators;
	}
	
	/**
	 * Sets the beliefs.
	 *
	 * @param beliefs the new beliefs
	 */
	public void setBeliefs(List<Belief> beliefs) {
		this.beliefs = beliefs;
	}
	
	/**
	 * Sets the sensors.
	 *
	 * @param sensors the new sensors
	 */
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	/**
	 * Sets the actuators.
	 *
	 * @param actuators the new actuators
	 */
	public void setActuators(List<Actuator> actuators) {
		this.actuators = actuators;
	}
}
