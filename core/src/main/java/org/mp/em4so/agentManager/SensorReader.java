/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.sensing.Observation;
import org.mp.em4so.model.sensing.Property;
import org.mp.em4so.model.sensing.Sensor;
import org.mp.em4so.services.ServiceAssembler;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SensorReader.
 */
public class SensorReader {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(SensorReader.class
			.getSimpleName());
	
	/** The somanager. */
	private SmartObjectAgManager somanager;
	
	/**
	 * Instantiates a new sensor reader.
	 *
	 * @param somanager the somanager
	 */
	public SensorReader(SmartObjectAgManager somanager){
		this.somanager = somanager;
	}
	
	
	
	/**
	 * Gets the readings.
	 *
	 * @param properties properties of interest
	 * @param sensingServices known sensing services
	 * @return list of observations for every property if a sensing service is known
	 */
	public List<Observation> getReadings(List<Property> properties, HashMap<String,Service> sensingServices){
		
		List<Observation> readings = new LinkedList<Observation>();
		Observation observation = null;
		Service sensingService = null;
		try {
		LOG.trace("the prop-->"+properties+"<--");
		LOG.trace("the sensingServices-->"+sensingServices+"<--");
		for(Property op : properties){
			observation = null;	
			sensingService = sensingServices.get(op.getName());
			
			LOG.trace("Sensing service for property {}-'{}'",op.getId(),op.getName());
			if(sensingService!=null){
				LOG.trace("is {} with args: {}",sensingService.getName(),sensingService.getArgTypes());
					observation = sense(op,sensingService);	
			}
			
			if(observation!=null){
			readings.add(
					observation
					);
			}
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return readings;
	
	}
	
/**
 * Gets the async readings.
 *
 * @param properties the properties
 * @param sensingServices the sensing services
 * @return the async readings
 */
public void getAsyncReadings(List<Property> properties, HashMap<String,Service> sensingServices){
		
		
		Observation observation = null;
		Service sensingService = null;
		try {
		LOG.trace("las prop-->"+properties+"<--");
		
		for(Property op : properties){
			observation = null;	
			sensingService = sensingServices.get(op.getName());
			LOG.trace("Sensing service es:"+sensingService);
			if(sensingService!=null){
				
//					ServiceAssembler.execute(
//							sensingService.getName(),
//							ServiceAssembler.getRunningCapability(
//									somanager, 
//									sensingService.getExecutionInstance().getCapability()
//							)
//					);
				
					senseAsync(op,sensingService);	
			}
			
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		
	
	}

/**
 * Sense async.
 *
 * @param property the property
 * @param sensingService the sensing service
 */
public  void senseAsync(Property property, Service sensingService){
	
	Service service;
	Observation result=null;
	String resultTo = null;
	String originalServiceName = null;
	String originalResultType = null;
	TreeMap<String,Object> argValues;
	TreeMap<String,String> originalArgTypes = null;
	
//	Observation observation = null;
	try {
		if(sensingService!=null)
			LOG.trace("sensor in query&sense: propertyName: {}, propertyScope: {} and service {} ",property.getName(),property.getScope(),sensingService.getName());
		else
			LOG.trace("sensor in query&sense: propertyName: {}, propertyScope: {} and service {} ",property.getName(),property.getScope(),sensingService);
//		service = ServiceAssembler.query(somanager,sensingService, somanager.getTTL(), "local:method");
		service = sensingService;
		service.setExecutionInstance(ServiceAssembler.populateExecutionInstance(sensingService));
		
		if(service.getExecutionInstance()!=null){
			originalServiceName = service.getName();
			originalResultType = service.getResult();
			originalArgTypes = service.getArgTypes();
			service.setResult("org.mp.em4so.model.sensing.Observation");
			service.setName("read"); //TODO Make this a parameter of a config file
			
			service.setArgTypes(new TreeMap<String, String>());
			service.getArgTypes().put("property", "org.mp.em4so.model.sensing.Property");
			argValues = new TreeMap<String, Object>();
			argValues.put("property", property);
			
			resultTo = JSONUtils.<Element>objectToObjecNode(new Element(null,"level",property.getName())).asText();
			
			
			
			ServiceAssembler.executeService(service, argValues, resultTo);
			service.setName(originalServiceName);
			service.setArgTypes(originalArgTypes);
			service.setResult(originalResultType);
//		observation = JSONUtils.<Observation>mapJsonToType(result);
		}else{
			ServiceDiscoverer.query(sensingService, somanager.getTTL(), null);
		}
	} catch (Exception e) {
		LOG.error(e.getMessage(),e);
	}
//	if (result!= null){
//		observation=new Observation();
//	}
//	return observation;
}
	
	/**
	 * Sense.
	 *
	 * @param property the property
	 * @param sensingService the sensing service
	 * @return the observation
	 */
	public  Observation sense(Property property, Service sensingService){
		
		Service service;
		Observation result=null;
		String originalServiceName = null;
		TreeMap<String,Object> argValues;
		TreeMap<String,String> originalArgTypes = null;
		String originalResultType = null;
		
//		Observation observation = null;
		try {
			if(sensingService!=null)
				LOG.trace("sensor in query&sense: property:"+property.getName()+" - service:"+sensingService.getName());
			else
				LOG.trace("sensor in query&sense: property:"+property.getName()+" - service:"+sensingService);
//			service = ServiceAssembler.query(somanager,sensingService, somanager.getTTL(), "local:method");
			service = sensingService;
			service.setExecutionInstance(ServiceAssembler.populateExecutionInstance(sensingService));
			
			if(service.getExecutionInstance()!=null){
				originalServiceName = service.getName();
				service.setName("read"); //TODO Make this a parameter of a config file
				originalResultType = service.getResult();
				service.setResult("org.mp.em4so.model.sensing.Observation"); //TODO Make this a parameter of a config file
				originalArgTypes = service.getArgTypes();
				service.setArgTypes(new TreeMap<String, String>());
				service.getArgTypes().put("property", "org.mp.em4so.model.sensing.Property"); //TODO Make this a parameter of a config file
				argValues = new TreeMap<String, Object>();
				argValues.put("property", property);
				//String.class.getSimpleName()
				result = (Observation)JSONUtils.mapObjectToType(ServiceAssembler.executeServiceSync(service, argValues,null),Observation.class);
				service.setName(originalServiceName);
				service.setArgTypes(originalArgTypes);
				service.setResult(originalResultType);
//			observation = JSONUtils.<Observation>mapJsonToType(result);
			}else{
				ServiceDiscoverer.query(sensingService, somanager.getTTL(), null);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
//		if (result!= null){
//			observation=new Observation();
//		}
//		return observation;
		return result;
	}

	
	/**
 * Gets the sensor details.
 *
 * @param sensor the sensor
 * @return the sensor details
 */
public Sensor getSensorDetails(Sensor sensor){
		return somanager.getKbm().getSensor(sensor);
	}
	
	/**
	 * Gets the observation.
	 *
	 * @param observationStr the observation str
	 * @return the observation
	 */
	public Observation getObservation(String observationStr){
		return (Observation)JSONUtils.mapObjectToType(observationStr,Observation.class);
	}
}
