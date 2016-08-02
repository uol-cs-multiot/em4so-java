/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mp.em4so.exceptions.DocNotSpecifiedException;
import org.mp.em4so.model.ModelConstants;
import org.mp.em4so.model.actuating.Action;
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
import org.mp.em4so.utils.CouchDBUtils;
import org.mp.em4so.utils.SOMFileConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.capabilities.CapabilityBuilder;
import com.almende.eve.state.State;
import com.almende.eve.state.couch.CouchStateConfig;
import com.almende.eve.state.memory.MemoryStateConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class MemoryManager.
 */
public class MemoryManager {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(MemoryManager.class
			.getName());
	
	/** The my state. */
	private State myState;
	
	/** The params. */
	private CouchStateConfig params;
	
	/** The documents. */
	private Hashtable<String, State> documents;
	
	/** The mm. */
	private static MemoryManager mm;
	
	/** The cdb. */
	private CouchDBUtils cdb;
	
	/** The stm params. */
	private MemoryStateConfig stmParams;
	
	/** The stm state. */
	private State stmState;

		
	/**
	 * Initialize short term memory.
	 *
	 * @param agentId the agent id
	 */
	public void initializeShortTermMemory(String agentId){
		stmParams = new MemoryStateConfig();
		stmParams.setId(agentId);
		      
		stmState = new CapabilityBuilder<State>()
		   .withConfig(stmParams)
		   .build();

	
	}
	
	/**
	 * Gets the responsible role.
	 *
	 * @param role the role
	 * @return the responsible role
	 */
	public Role getResponsibleRole(Role role){
		
		Hashtable <String,String> args = null;
		try {
		args = new Hashtable<String,String>();
		args.put("queryType", "byactivity");
		args.put("listType", "single");
		args.put("reduce", "false");
		args.put("group", "false");
		args.put("doc", "role");
		args.put("key", role.getActivity().get(0).getId());
		
			role = cdb.querySingle(Role.class,args);
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return role;
	}
	
	/**
	 * Gets the role host.
	 *
	 * @param role the role
	 * @return the role host
	 */
	public Host getRoleHost(Role role){
		Host host = null;
		Hashtable <String,String> args = null;
		
		try {
		args = new Hashtable<String,String>();
		args.put("queryType", "byrole");
		args.put("listType", "single");
		args.put("reduce", "true");
		args.put("group", "true");
		args.put("key", role.getId());
				host = cdb.querySingle(Host.class,args);
			} catch (DocNotSpecifiedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return host;
	}
	
	/**
	 * Gets the next goal.
	 *
	 * @return the next goal
	 */
	public Goal getNextGoal() {
		// TODO Dinamic adding of goals
		// params.setId("goal0");
		// documents.put("goal0",
		// new StateBuilder()
		// .withConfig(params)
		// .build());
		// Goal goal = documents.get("goal0").get("goal",Goal.class);
		// goal = new Goal("live");
		Goal goal = null;

		return goal;

	}
	
	/**
	 * Gets the goals.
	 *
	 * @return the goals
	 */
	public Map<String,Goal> getGoals() {
		Hashtable <String,String> args = null;
		Map<String, Goal> goals = null;
		try {
			args = new Hashtable<String,String>();
			args.put("queryType", "all");
			args.put("listType", "all");
			args.put("reduce", "false");
			args.put("group", "false");
			goals = cdb.query(args, Goal.class);
			
			
		} catch (DocNotSpecifiedException e) {
			
			LOG.error(e.getMessage(),e);
		}
		return goals;

	}
	
	/**
	 * Gets the scenario.
	 *
	 * @param goal the goal
	 * @return the scenario
	 */
	public Scenario getScenario(Goal goal){
		Hashtable <String,String> args = null;
		Scenario scenario = null;
		try {
			args = new Hashtable<String,String>();
			args.put("queryType", "all");
			args.put("listType", "single");
			args.put("key", goal.getId());
			args.put("reduce", "false");
			args.put("group", "false");
			scenario = cdb.querySingleCollection(Scenario.class, args);
			
			
		} catch (DocNotSpecifiedException e) {
			
			LOG.error(e.getMessage(),e);
		}
		return scenario;
	}

	
	/**
 * Instantiates a new memory manager.
 *
 * @param agentId the agent id
 */
MemoryManager(String agentId) {
		this(SOMFileConfigUtils.getKBUrl(),SOMFileConfigUtils.getKBUser(),SOMFileConfigUtils.getKBPass(),SOMFileConfigUtils.getKBPrefix()+agentId,agentId);
	}
	
	/**
	 * Instantiates a new memory manager.
	 *
	 * @param url the url
	 * @param user the user
	 * @param pass the pass
	 * @param db the db
	 * @param agentId the agent id
	 */
	MemoryManager(String url, String user, String pass, String db, String agentId) {
		
		
		params = new CouchStateConfig();
		params.setUrl(url);
		params.setUsername(user);
		params.setPassword(pass);
		/* default value, therefor optional: */
		params.setDatabase(db); // Database name

		// initialize goals
		params.setId(agentId);
		
		documents = new Hashtable<String, State>();
		State st = new CapabilityBuilder<State>().withConfig(params).build();
		

		cdb = new CouchDBUtils(url+"/"+db, user,
				pass);
		LOG.trace(agentId+": Initialized Long term memory");
		initializeShortTermMemory(agentId);
		LOG.trace(agentId+": Initialized Short term memory");

	}

	

	/**
 * Gets the my properties.
 *
 * @return the my properties
 */
public List<Property> getMyProperties() {
		List<Property> properties = cdb.queryAll("property", Property.class);
		LOG.trace("Getting properties {}",properties);
		return properties;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Role> getRoles() {
		Hashtable<String, Object> args = null;
		List<Role> roles=null;
		try {
			args = new Hashtable<String, Object>();
			args.put("includeDocs", "true");
			roles = cdb.query(Role.class, args);
		} catch (DocNotSpecifiedException e) {
			LOG.error(e.getMessage(),e);
		}
		return roles;

	}

	
	
	
	/**
 * Gets the my state.
 *
 * @return the my state
 */
public State getMyState() {
		return myState;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public CouchStateConfig getParams() {
		return params;
	}

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public Hashtable<String, State> getDocuments() {
		return documents;
	}

	/**
	 * Gets the mm.
	 *
	 * @return the mm
	 */
	public static MemoryManager getMm() {
		return mm;
	}

	/**
	 * Sets the my state.
	 *
	 * @param myState the new my state
	 */
	public void setMyState(State myState) {
		this.myState = myState;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the new params
	 */
	public void setParams(CouchStateConfig params) {
		this.params = params;
	}

	/**
	 * Sets the documents.
	 *
	 * @param documents the documents
	 */
	public void setDocuments(Hashtable<String, State> documents) {
		this.documents = documents;
	}

	/**
	 * Sets the mm.
	 *
	 * @param mm the new mm
	 */
	public static void setMm(MemoryManager mm) {
		MemoryManager.mm = mm;
	}

	
	/**
	 * Gets the cdb.
	 *
	 * @return the cdb
	 */
	public CouchDBUtils getCdb() {
		return cdb;
	}

	/**
	 * Sets the cdb.
	 *
	 * @param cdb the new cdb
	 */
	public void setCdb(CouchDBUtils cdb) {
		this.cdb = cdb;
	}

	/**
	 * Gets the recent observations.
	 *
	 * @param propertyKey the property key
	 * @return the recent observations
	 */
	public List<Observation> getRecentObservations(String propertyKey) {
		Hashtable<String, String> args = null;
		List<Observation> results = null;
		try {
			args = new Hashtable<String, String>();
			args.put("reduce", "true");
			args.put("group", "true");
			if(propertyKey!=null)args.put("key", propertyKey);
			results = cdb.query(Observation.class, args);
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass().getName()).error(
					e.getMessage());
		}
		return results;

	}
	
	/**
	 * Gets the all observations.
	 *
	 * @return the all observations
	 */
	public List<Observation> getAllObservations() {
		Hashtable<String, String> args = null;
		List<Observation> results = null;
		try {
			args = new Hashtable<String, String>();
			args.put("reduce", "false");
			args.put("group", "false");
			results = cdb.query(Observation.class, args);
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass().getName()).error(
					e.getMessage());
		}
		return results;

	}

	
	/**
	 * Gets the activities by rol.
	 *
	 * @param role the role
	 * @return the activities by rol
	 */
	public List<Activity> getActivitiesByRol(Role role){
		List<Activity> activities = null;
		Hashtable<String, Object> args=null;
		List<Activity> newActivities = null;
		List<String> strKeys = null;
		try {
			if(role!=null){
				activities = role.getActivity();
			}
			args = new Hashtable<String, Object>();
			
			//TODO Improvement: to send attribute to get in order to pass it to the query 
			strKeys = new LinkedList<String>();
			for(Activity a: activities){
				strKeys.add(a.getId());
			}
				args.put("keys", strKeys);
				args.put("queryType", "all_n_actions");
//				args.put("includeDocs", "true");

			
			activities = cdb.query(Activity.class, args);
			
			for(Activity a: activities){
				a.setActions(Collections.synchronizedList(a.getActions()));
			}
//			stmState.put("activities",activities);
			
//			activities = getExecutionDetails(activities);
			

		
			LOG.trace("are they equal->"+activities.equals(newActivities));
		} catch (DocNotSpecifiedException e) {

			LOG.error(e.getMessage(),e);
		}
		return activities;
	}
	

	
	/**
 * Gets the action.
 *
 * @param key the key
 * @return the action
 */
public Action getAction(String key){
		List<Action> actions = null;
		Hashtable<String, String> args = new Hashtable<String,String>();
		args.put("key", key);
		Action result = null;
		try {
			actions = cdb.query(Action.class, args);
			if (actions != null && actions.size()>0)
				result = actions.get(0);
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		
		return result;
	}
	
	
	/**
	 * Gets the activity.
	 *
	 * @param activity the activity
	 * @return the activity
	 */
	public Activity getActivity(Activity activity){
		List<Activity> result = null;
		Hashtable<String, String> args = new Hashtable<String,String>();
		
		try {
			
			args.put("key", activity.getId());
			args.put("queryType", "all_n_actions");
			args.put("listType", "all_n_actions");
			
			result = cdb.query(Activity.class,args);
			if(result!=null && result.size()==1){
				activity = result.get(0);
			}
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		
		return activity;
	}
	
	
	
	/**
	 * Gets the category providers.
	 *
	 * @param service the service
	 * @param avoidUrls the avoid urls
	 * @return the category providers
	 */
	public HashMap<String,Host> getCategoryProviders(Service service, List<String> avoidUrls){
		HashMap<String,Host> results=null;
		
		Hashtable<String,Object> args = new Hashtable<String,Object>();
			
		args.put("keys",service.getCategories());
		args.put("reduce", "false");
		args.put("group", "false");
		args.put("queryType", "categories_host");
		args.put("listType", "remote_host");
		args.put("doc", "host");
		args.put("params1",avoidUrls);

		try {
			results = cdb.query(args,Host.class);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return results;
	}
	
	/**
	 * Gets the known providers.
	 *
	 * @param avoidUrls the avoid urls
	 * @return the known providers
	 */
	public HashMap<String,Host> getKnownProviders(List<String> avoidUrls){
		HashMap<String,Host> results=null;
		
		Hashtable<String,Object> args = new Hashtable<String,Object>();
			
		args.put("reduce", "false");
		args.put("group", "false");
		args.put("queryType", "host_url");
		args.put("listType", "remote_host");
		args.put("doc", "host");
		args.put("params1",avoidUrls);
		
		try {
			results = cdb.query(args, Host.class);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return results;
	}
	
	
	/**
	 * Get providers of a category.
	 *
	 * @param service the service
	 * @return the providers
	 */
	public List<Service> getProviders(Service service){//TO review to send to smart object host
		
		//TODO get All providers...
		List<Service> results=null;
		
		Hashtable<String,Object> args = new Hashtable<String,Object>();
			
		args.put("keys",service.getCategories());
		args.put("reduce", "true");
		args.put("group", "true");
		args.put("queryType", "categories_ranking");
		args.put("listType", "all");
		// TODO Im here
		try {
			results = cdb.query(Service.class, args);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return results;
	}

	
	/**
	 * Gets the stm state.
	 *
	 * @return the stm state
	 */
	public State getStmState() {
		return stmState;
	}

	/**
	 * Sets the stm state.
	 *
	 * @param stmState the new stm state
	 */
	public void setStmState(State stmState) {
		this.stmState = stmState;
	}
	

	
	/**
	 * Gets the services by type.
	 *
	 * @param type the type
	 * @return the services by type
	 */
	public List<Service> getServicesByType(String type){
		//TODO get All providers...
				List<Service> results=null;
				
				Hashtable<String,Object> args = new Hashtable<String,Object>();
					
				args.put("key", type);
				args.put("reduce", "true");
				args.put("group", "true");
				args.put("queryType", "kind_ranking");
				args.put("listType", "all");
//				args.put("doc", "service");
				
				try {
					results = cdb.query(Service.class, args);
					
					
				} catch (DocNotSpecifiedException e) {
					// TODO Auto-generated catch block
					LOG.error(e.getMessage(),e);
				}
				return results;
	}
	
	/**
	 * Gets the service.
	 *
	 * @param service the service
	 * @return the service
	 */
	public Service getService(Service service){
	
				
				String strContract = null;
				Hashtable<String,Object> args = new Hashtable<String,Object>();
				
				if(service.getId()!=null){
					args.put("key", service.getId());	
					args.put("queryType", "byid");
				}else if(service.getName()!= null ){
					strContract = "[\""+service.getName()+"\",\""+service.getResult()+"\",[";
					for(String arg:service.getArgTypes().keySet()){
						strContract += "\""+service.getArgTypes().get(arg) +"\",";
					}
					strContract = strContract.substring(0,strContract.lastIndexOf(","))+"]]";
					args.put("queryType", "bycontract");
					args.put("key", strContract);
				}
				args.put("reduce", "false");
				args.put("group", "false");
				args.put("listType", "single");
				
				try {
					service = cdb.querySingle(Service.class, args);
					
					
				} catch (DocNotSpecifiedException e) {
					// TODO Auto-generated catch block
					LOG.error(e.getMessage(),e);
				}
				return service;
	}
	
	/**
	 * Gets the services properties by type.
	 *
	 * @param type the type
	 * @return the services properties by type
	 */
	public HashMap<String,Service> getServicesPropertiesByType(String type){
		//TODO get All providers...
				HashMap<String,Service> results=null;
				
				Hashtable<String,Object> args = new Hashtable<String,Object>();
				args.put("reduce", "false");
				args.put("group", "false");
				args.put("queryType", "sensor_properties");
				args.put("listType", "all_table");
//				args.put("doc", "service");
				
				try {
					results = cdb.query(args, Service.class);
//					if(results!=null && !results.isEmpty()){
//						args.put("reduce", "true");
//						args.put("group", "true");
//						results = cdb.query(args, Service.class);
//					}
					
				} catch (DocNotSpecifiedException e) {
					// TODO Auto-generated catch block
					LOG.error(e.getMessage(),e);
				}
				return results;
	}
	

	
	/**
 * Gets the documents.
 *
 * @param <T> the generic type
 * @param clazz the clazz
 * @param document the document
 * @param query the query
 * @param docId the doc id
 * @return the documents
 */
public <T> List<T>  getDocuments(Class<T> clazz, String document,String query,String docId){
		List<T> result=null;
		
		try {
			HashMap<String,Service> results=null;
			
			Hashtable<String,Object> args = new Hashtable<String,Object>();
			args.put("key", docId);
			args.put("doc", document);
			args.put("queryType", query);
			args.put("listType", "all");
			
			result = cdb.<T>queryDocs(clazz,args);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Gets the document.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param document the document
	 * @param query the query
	 * @param docId the doc id
	 * @return the document
	 */
	public <T>T  getDocument(Class<T> clazz, String document,String query,String docId){
		T result=null;
		
		try {
			HashMap<String,Service> results=null;
			
			Hashtable<String,Object> args = new Hashtable<String,Object>();
			args.put("key", docId);
			args.put("doc", document);
			args.put("queryType", query);
			args.put("listType", "single");
			
			result = cdb.<T>queryDoc(clazz,args);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Gets the access rights service.
	 *
	 * @param service the service
	 * @param senderUrl the sender url
	 * @return the access rights service
	 */
	public String getAccessRightsService(Service service, String senderUrl){
		String result = "false";
		
		try {
			
			
			Hashtable<String,String> args = new Hashtable<String,String>();
			args.put("key", service.getName());
			args.put("param1", senderUrl);
			args.put("doc", "accesspolicy");
			args.put("queryType","all" );
			args.put("listType", "single_url");
			
			result = cdb.queryDoc(String.class,args);
			
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Gets the execution instance.
	 *
	 * @param service the service
	 * @return the execution instance
	 */
	public ExecutionInstance getExecutionInstance(Service service){
		String strContract;
		ExecutionInstance ei = null;
		try {
			
			strContract = "[\""+service.getName()+"\",\""+service.getResult()+"\",[";
			if(service.getArgTypes()!=null && !service.getArgTypes().isEmpty()){
				for(String arg:service.getArgTypes().keySet()){
					strContract += "\""+service.getArgTypes().get(arg) +"\",";
				}
			strContract = strContract.substring(0,strContract.lastIndexOf(","));
			}
			strContract+= "]]";
			Hashtable<String,String> args = new Hashtable<String,String>();
			args.put("key", strContract);
			args.put("queryType","exin_bycontract" );
			args.put("reduce", "true");
			args.put("group", "true");
			args.put("listType", "exin_single");
			args.put("doc","service");
			//complete method with getting hosts 
			ei = cdb.queryDoc(ExecutionInstance.class,args);
			if(ei!=null){
				ei.setHost(
						getHost(ei.getHost())
						);
				
			}else
				service.setExecutionInstance(null);
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return ei;
	}
	
	/**
	 * Gets the host.
	 *
	 * @param host the host
	 * @return the host
	 */
	public Host getHost(Host host){
		Host found = null;		
		try {
			
			
			
			Hashtable<String,String> args = new Hashtable<String,String>();
			args.put("key", host.getId());
			args.put("queryType","all" );
			args.put("reduce", "false");
			args.put("group", "false");
			args.put("listType", "single");
			 
			found = cdb.queryDoc(Host.class,args);
			
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return found;
	}
	
	/**
	 * Gets the host from url.
	 *
	 * @param host the host
	 * @return the host from url
	 */
	public Host getHostFromUrl(Host host){
		Host found = null;
		Hashtable<String,String> args = null;
		try {
			
			
			if(host!=null && host.getUrls()!=null&&!host.getUrls().isEmpty()){
			args = new Hashtable<String,String>();
			
			args.put("reduce", "false");
			args.put("group", "false");
			args.put("listType", "single_host_url");
			
			for(String url:host.getUrls()){
			args.put("key", url);
			args.put("queryType","host_url" );
			LOG.trace("Looking for existing host with url: {}",url);
			found = cdb.queryDoc(Host.class,args);
			if(found!=null) break;
			}
			}
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
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
	public Element getPropertyValue(Element element) {
		Element found = null;
		Hashtable<String, Object> args = null;
		
		String query = null;
		String key = null;
		try {

			if (element != null) {
				args = new Hashtable<String, Object>();

				args.put("reduce", "false");
				args.put("group", "false");
				query = "by_sco";
				key = "[\"" + element.getScope() + "\"";

				if (element.getName() != null) {
					query += "_prop";
					key += ",\"" + element.getName() + "\"";
				} else if (element.getKind() != null) {
					query += "_kind";
					key += ",\"" + element.getKind() + "\"";
				}
				args.put("listType", "single_property");
				key += "]";
				
				args.put("queryType", query);
				args.put("key", key);
				args.put("doc", "property");
				LOG.trace("Looking for property {}.{} ->{}", element.getScope(), element.getName(),
						args.get("listType"));
				found = cdb.queryDoc(Element.class, args);
				}	
			} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}
		return found;

	}
	
	
	/**
	 * Gets the element.
	 *
	 * @param element the element
	 * @return the element
	 */
	public Element getElement(Element element) {
		Element found = null;
		Hashtable<String, Object> args = null;
		List<String> params = null;
		String levelValue = null;
		Observation o = null;
		String query = null;
		String key = null;
		try {

			if (element != null) {
				args = new Hashtable<String, Object>();
				
				args.put("reduce", "false");
				args.put("group", "false");
				query = "by_sco";
				key = "[\"" + element.getScope() + "\"";

				if (element.getName() != null) {
					query += "_prop";
					key += ",\"" + element.getName() + "\"";
				} else if (element.getKind() != null) {
					query += "_kind";
					key += ",\"" + element.getKind() + "\"";
				}

				if (element.getAttributeName() != null) {
					if (element.getAttributeName().equals(ModelConstants.PROPERTY_VALUE) || //Getting Observation in case of these properties
							element.getAttributeName().equals(ModelConstants.PROPERTY_TIME)	||
							element.getAttributeName().equals(ModelConstants.PROPERTY_SENSOR) ) {
							o = getRecentObservation(element.getName());
						}else{
							args.put("listType", "single");  //To query single attribute in any other case
							key += ",\"" + element.getAttributeName() + "\"";
							query += "_attr";
						}
				} else if (element.getAttrNames() != null) {
					
					for (int i = 0; i < element.getAttrNames().length; i++) {
						if (element.getAttrNames()[i].equals(ModelConstants.PROPERTY_VALUE) ||  //Getting Observation in case of these properties
							element.getAttrNames()[i].equals(ModelConstants.PROPERTY_TIME)	||
							element.getAttrNames()[i].equals(ModelConstants.PROPERTY_SENSOR) ) {
							if(o!=null)
								o = getRecentObservation(element.getName());
						}else{ //To query single attribute in any other case
							if(params==null){  
								args.put("listType", "multi_attribute");
								params = new LinkedList<String>();
							}
							params.add(element.getAttrNames()[i]);
						}
					}
					if(params!=null)
						args.put("params1", params);
				} 
				
				key += "]";
				
				args.put("queryType", query);
				args.put("key", key);
				args.put("doc", "property");
				LOG.trace("Looking for element {}.{} ->{}", element.getScope(), element.getName(),
						args.get("listType"));
				found = cdb.queryDoc(Element.class, args);
				
				LOG.trace("Element found: {}",found);
				
				if(found==null) found = element; 

				if (found.getAttributes()==null)
					found.setAttributes(new HashMap<String, String>());
				if(o!=null){
					found.getAttributes().put(ModelConstants.PROPERTY_VALUE, o.getValue());
					found.getAttributes().put(ModelConstants.PROPERTY_TIME, o.getStringTime());
					found.getAttributes().put(ModelConstants.PROPERTY_SENSOR, o.getSensor().getId());
				}

			}
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(), e);
		}
		return found;
	}
	
	
	/**
	 * Gets the pending message.
	 *
	 * @param element the element
	 * @return the pending message
	 */
	public Element getPendingMessage(Element element){
		Element found = null;
		Hashtable<String,String> args = null;
		try {
			
			
			if(element!=null){
			args = new Hashtable<String,String>();
			
			args.put("reduce", "true");
			args.put("group", "true");
			args.put("listType", "single");
			args.put("queryType","pending_message" );
			args.put("doc", "message");
			args.put("key",element.getValue() );
			LOG.trace("Looking for message: {}",element.getValue());
			found = cdb.queryDoc(Element.class,args);
			}
		} catch (DocNotSpecifiedException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return found;
	}
	
	/**
	 * Gets the recent observation.
	 *
	 * @param propertyKey the property key
	 * @return the recent observation
	 */
	public Observation getRecentObservation(String propertyKey){
		List<Observation> results =	getRecentObservations(propertyKey);
		if(results!=null && results.size()>0) return results.get(0);
		else return null;
	}
	
}
