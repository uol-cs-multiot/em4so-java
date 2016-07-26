/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.deviceManager;

import java.util.Collection;
import java.util.List;

import org.mp.em4so.model.common.Device;
import org.mp.em4so.model.sensing.Sensor;
import org.mp.em4so.services.ServiceAssembler;

// TODO: Auto-generated Javadoc
/**
 * Gather readings from the agent environment.
 *
 * @author me
 * @version 1.0
 * @updated 17-Apr-2015 20:14:00
 */
public class DeviceManager {

	/** The sensors. */
	private List<Sensor> sensors;
	
	/** The actuator. */
	private List<Actuator> actuator;
	
	/** The m device. */
	private Device m_Device;
	
	/** The m service broker. */
	private ServiceAssembler m_ServiceBroker;

	/**
	 * Instantiates a new device manager.
	 */
	public DeviceManager(){

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

	}

	/**
	 * get the list of available devices.
	 *
	 * @return the collection
	 */
	public Collection listDevices(){
		return null;
	}
	
//	public List<Sensor> getAvailableSensors(){
//		//TODO implement discovery of available sensors
//		sensors = new LinkedList<Sensor>();
//	//	sensors.add();
//		
//		
//		
//	}
//	

	/**
 * Publish device.
 */
public void publishDevice(){

	}

	/**
	 * Publish service for device.
	 *
	 * @param device the device
	 */
	public void publishServiceForDevice(Device device){

	}

	/**
	 * Sets the up device.
	 */
	public void setUpDevice(){

	}

	

}