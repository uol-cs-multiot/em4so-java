/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.actuating.ExecutionInstance;
import org.mp.em4so.model.agent.Goal;
import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Element;

import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.model.reasoning.Scenario;
import org.mp.em4so.model.sensing.Observation;
import org.mp.em4so.model.sensing.Property;
import org.mp.em4so.model.sensing.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class KnowledgeBaseManager.
 */
public class KnowledgeBaseManager  {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(KnowledgeBaseManager.class
			.getName());
	
	/** The agent id. */
	private String agentId;

/** The mem manager. */
private static MemoryManager memManager;

/** The mem manager writer. */
private static MemoryManagerWriter memManagerWriter;

	/**
 * Instantiates a new knowledge base manager.
 *
 * @param agentId the agent id
 */
public KnowledgeBaseManager(String agentId){
		this.agentId = agentId;
		memManager = new MemoryManager(agentId);
		memManagerWriter = new MemoryManagerWriter(agentId);
		
	}
	
	/**
	 * Instantiates a new knowledge base manager.
	 *
	 * @param url the url
	 * @param user the user
	 * @param pass the pass
	 * @param db the db
	 * @param agentId the agent id
	 */
	public KnowledgeBaseManager(String url, String user, String pass, String db, String agentId){
		this.agentId = agentId;
		memManager = new MemoryManager(url,user,pass, db,agentId);
		memManagerWriter = new MemoryManagerWriter(url,user,pass, db,agentId);
	}
	
	/**
	 * Gets the responsible role.
	 *
	 * @param role the role
	 * @return the responsible role
	 */
	public Role getResponsibleRole(Role role){
		return memManager.getResponsibleRole(role);
	}
	
	/**
	 * Gets the role host.
	 *
	 * @param role the role
	 * @return the role host
	 */
	public Host getRoleHost(Role role){
		return memManager.getRoleHost(role);
	}
	
	/**
	 * get the interesting properties for this particular smart object.
	 *
	 * @return the my properties
	 */
	public List<Property> getMyProperties(){
		return memManager.getMyProperties();
		
	}
	
	/**
	 * Update knowledge base with the sensor readings.
	 *
	 * @param readings the readings
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void update(List<Observation> readings)throws InterruptedException{
		memManagerWriter.lock();
		for(Observation r: readings){
			if (r!=null)
				updateReading(r);
		}
		memManagerWriter.unlock();
	}
	
	/**
	 * Updates one specific reading into the KDB.
	 *
	 * @param reading the reading
	 * @throws InterruptedException the interrupted exception
	 */
	public void updateReading(Observation reading)throws InterruptedException{
		String strTailUrl = null;
		LOG.trace("time"+reading.getTime("YYYYMMdd"));
		LOG.trace("property"+reading.getProperty().getName());
		LOG.trace("sensor"+reading.getSensor().getKind());
		
		//TODO Replace for instrospection of field names and values
		strTailUrl = "observations/" +reading.getTime("YYYYMMdd")+"/"+reading.getProperty().getName()+"?"+"type=observation&property="+reading.getProperty().getName()+"&time="+reading.getTime("YYYY-MM-dd'T'HH:mm:ss.SSS")+"&value="+reading.getValue()+"&sensor="+reading.getSensor().getKind()+"_"+reading.getSensor().getId();
		
		memManagerWriter.update(strTailUrl);
		
		
	}

	/**
	 * Gets the mem manager.
	 *
	 * @return the mem manager
	 */
	public MemoryManager getMemManager() {
		return memManager;
	}

	/**
	 * Sets the mem manager.
	 *
	 * @param memManager the new mem manager
	 */
	public void setMemManager(MemoryManager memManager) {
		KnowledgeBaseManager.memManager = memManager;
	}
	
	
	/**
	 * Obtain goals for the agent given the .
	 *
	 * @return the my goals
	 */
	public Map<String,Goal> getMyGoals(){
		return memManager.getGoals();
	}
	
	/**
	 * Gets the scenario.
	 *
	 * @param goal the goal
	 * @return the scenario
	 */
	public Scenario getScenario(Goal goal){
		return memManager.getScenario(goal);
	}
	
	/**
	 * Obtain all the roles playable by this agent.
	 *
	 * @return the playable roles
	 */
	public List<Role> getPlayableRoles(){
		return memManager.getRoles();
	}
	
//	public Hashtable<String,Capability> getCapabilities(){
//		return memManager.getCapabilities();
/**
 * Gets the recent observation.
 *
 * @param propertyKey the property key
 * @return the recent observation
 */
//	}
	public Observation getRecentObservation(String propertyKey){
		return	memManager.getRecentObservation(propertyKey);
		
	}
	
	/**
	 * Gets the all observations.
	 *
	 * @return the all observations
	 */
	public List<Observation> getAllObservations(){
		List<Observation> results =	memManager.getAllObservations();
		return results;
	}
	
	/**
	 * Gets the recent observations.
	 *
	 * @return the recent observations
	 */
	public List<Observation> getRecentObservations(){
		return memManager.getRecentObservations(null);
	}
	
	/**
	 * Gets the activity.
	 *
	 * @param activity the activity
	 * @return the activity
	 */
	public Activity getActivity(Activity activity){
		return memManager.getActivity(activity);
	}
	
	/**
	 * Populate role activities.
	 *
	 * @param roles the roles
	 * @return the list
	 */
	public List<Role> populateRoleActivities(List<Role> roles){
		
		for(Role r: roles){
			r.setActivity(memManager.getActivitiesByRol(r));
		}
		return roles;
	}
	
//	public List<Service> getKnownExecutionInstances(Service service){
//		
//		return memManager.getKnownExecutionInstances(service);
//	}
	
	/**
 * Gets the category providers.
 *
 * @param service the service
 * @param avoidUrls the avoid urls
 * @return the category providers
 */
public HashMap<String,Host> getCategoryProviders(Service service, List<String> avoidUrls){
		return memManager.getCategoryProviders(service, avoidUrls);
	}
	
	/**
	 * Gets the known providers.
	 *
	 * @param avoidUrls the avoid urls
	 * @return the known providers
	 */
	public HashMap<String,Host> getKnownProviders(List<String> avoidUrls){
		return memManager.getKnownProviders(avoidUrls);
	}
	
	/**
	 * Gets the providers.
	 *
	 * @param service the service
	 * @return the providers
	 */
	public List<Service> getProviders(Service service){
		return memManager.getProviders(service);
	}
	
	
	
	/**
	 * Put short term.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param value the value
	 */
	public <T>void putShortTerm(String key,T value){
		memManager.getStmState().put(key, value);
		LOG.trace(agentId+" has put: key:"+key+" - value:"+value +" in shortime memory");
	}
	
	/**
	 * Gets the short term.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param type the type
	 * @return the short term
	 */
	public <T>T getShortTerm(String key,Class<T> type){
		LOG.trace(agentId+": Shortime memory: "+memManager.getStmState().get(key, type));
		return memManager.getStmState().get(key,type);
	}
	
	/**
	 * Removes the short term.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @return the t
	 */
	public <T>T removeShortTerm(String key){
		T t = (T) memManager.getStmState().remove(key);
		LOG.trace("el valor guardado:"+t);
		return t;
	}
	
	/**
	 * Adds the service provider.
	 *
	 * @param service the service
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void addServiceProvider(Service service)throws InterruptedException{
		ExecutionInstance eiToAdd;
		Service serviceToAdd;
		Host hostToAdd;
		service.setId(null);
		eiToAdd = service.getExecutionInstance();
		//By default services are added with 0.5 ranking
		eiToAdd.setRanking("0.5");
		serviceToAdd = memManager.getService(service); 

		hostToAdd = memManager.getHostFromUrl(eiToAdd.getHost());
		memManagerWriter.lock();
		memManagerWriter.addServiceProvider(service,serviceToAdd,hostToAdd,eiToAdd);
		memManagerWriter.unlock();
	}
	
	/**
	 * Adds the provider.
	 *
	 * @param host the host
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void addProvider(Host host)throws InterruptedException{
		memManagerWriter.lock();
		memManagerWriter.addHost(host);
		memManagerWriter.unlock();
	}
	
	/**
	 * Update service.
	 *
	 * @param service the service
	 * @throws InterruptedException the interrupted exception
	 */
	public void updateService(Service service)throws InterruptedException{
		memManagerWriter.lock();
		memManagerWriter.updateService(service);
		memManagerWriter.unlock();
	}
	
	/**
	 * Gets the execution instance.
	 *
	 * @param service the service
	 * @return the execution instance
	 */
	public ExecutionInstance getExecutionInstance(Service service){
		return memManager.getExecutionInstance(service);
	}
	
	/**
	 * Update environment property.
	 *
	 * @param scope the scope
	 * @param kind the kind
	 * @param property the property
	 * @param propertyValue the property value
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void updateEnvironmentProperty(String scope, String kind,String property, String propertyValue)throws InterruptedException{
		
		//TODO replace for a configuration file with the value: currentValue
		memManagerWriter.lock();
		memManagerWriter.updateEnvironmentProperty(scope,kind,property, propertyValue);
		memManagerWriter.unlock();
		
	}
	
	/**
	 * Update property.
	 *
	 * @param property the property
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void updateProperty(Element property)throws InterruptedException{
		memManagerWriter.lock();
		memManagerWriter.updateProperty(property);
		memManagerWriter.unlock();
	}
	
	/**
	 * Receive message.
	 *
	 * @param msg the msg
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void receiveMessage(Element msg)throws InterruptedException{
		memManagerWriter.lock();
		memManagerWriter.receiveMessage(msg);
		memManagerWriter.unlock();
	}
	
	/**
	 * Gets the sensing services properties.
	 *
	 * @return the sensing services properties
	 */
	public HashMap<String,Service> getSensingServicesProperties(){
		return memManager.getServicesPropertiesByType("sensor");
	}
	
	
	/**
	 * Gets the sensors.
	 *
	 * @param sensor the sensor
	 * @return the sensors
	 */
	public List<Sensor> getSensors(Sensor sensor){
		String docKey = null;
		String query = null;
		List<Sensor> sensors = null;
		if(sensor.getCapability()!= null){
			docKey = sensor.getCapability();
			query = "read_capability";
		}
		sensors = memManager.<Sensor>getDocuments(Sensor.class,"device", query,docKey);
		return sensors;
	}
	
	
	/**
	 * Gets the sensor.
	 *
	 * @param sensor the sensor
	 * @return the sensor
	 */
	public Sensor getSensor(Sensor sensor){
		String docKey = null;
		String query = null;
		if(sensor.getCapability()!= null){
			docKey = sensor.getCapability();
			query = "read_capability";
		}
		return memManager.<Sensor>getDocument(Sensor.class,"device", query,docKey);
	}
	
	/**
	 * Checks for access rights service.
	 *
	 * @param service the service
	 * @param senderUrl the sender url
	 * @return true, if successful
	 */
	public boolean hasAccessRightsService(Service service,String senderUrl){
		return Boolean.parseBoolean(memManager.getAccessRightsService (service,senderUrl));
	}
	
	/**
	 * Adds the local service.
	 *
	 * @param service the service
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void addLocalService(Service service)throws InterruptedException{
		try{
		memManagerWriter.lock();
		memManagerWriter.addLocalService(service);
		memManagerWriter.unlock();
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Gets the element.
	 *
	 * @param element the element
	 * @return the element
	 */
	public Element getElement(Element element){
		Element found = null;
		try{
			if(element.getAttributeName()!=null)
				found = memManager.getSingleElementValue(element);
			else 
				found =  memManager.getElement(element);
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		return found;
	}
	
	/**
	 * Gets the property value.
	 *
	 * @param element the element
	 * @return the property value
	 */
	public Element getPropertyValue(Element element){
		Element found = null;
		try{
			
			found =  memManager.getPropertyValue(element);
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		return found;
	}
	
	/**
	 * Gets the last pending message.
	 *
	 * @param element the element
	 * @return the last pending message
	 */
	public Element getLastPendingMessage(Element element){
		Element found = null;
		found =  memManager.getPendingMessage(element);
		return found;
	}
	
	/**
	 * Gets the host.
	 *
	 * @param host the host
	 * @return the host
	 */
	public Host getHost(Host host){
		return memManager.getDocument(Host.class,"host","all",host.getId());
	}

	
}
