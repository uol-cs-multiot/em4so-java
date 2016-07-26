/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;


	import java.text.SimpleDateFormat;
	import java.util.Hashtable;
	import java.util.List;
	import java.util.Map;

	import org.mp.em4so.exceptions.DocNotSpecifiedException;
	import org.mp.em4so.model.actuating.ExecutionInstance;
	import org.mp.em4so.model.agent.Goal;
	import org.mp.em4so.model.agent.Role;
	import org.mp.em4so.model.common.Element;
	import org.mp.em4so.model.common.Service;
	import org.mp.em4so.model.network.Host;
	import org.mp.em4so.model.reasoning.Scenario;
	import org.mp.em4so.model.sensing.Property;
	import org.mp.em4so.utils.CouchDBUtils;
	import org.mp.em4so.utils.JSONUtils;
	import org.mp.em4so.utils.SOMFileConfigUtils;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	import com.almende.eve.capabilities.CapabilityBuilder;
	import com.almende.eve.state.State;
	import com.almende.eve.state.couch.CouchStateConfig;
	import com.almende.eve.state.memory.MemoryStateConfig;

	// TODO: Auto-generated Javadoc
/**
	 * The Class MemoryManagerWriter.
	 */
	public class MemoryManagerWriter {
		
		/** The log. */
		private final Logger LOG = LoggerFactory.getLogger(MemoryManagerWriter.class
				.getName());
		
		/** The my state. */
		private State myState;
		
		/** The params. */
		private CouchStateConfig params;
		
		/** The documents. */
		private Hashtable<String, State> documents;
		
		/** The mm. */
		private static MemoryManagerWriter mm;
		
		/** The cdb. */
		private CouchDBUtils cdb;
		
		/** The stm params. */
		private MemoryStateConfig stmParams;
		
		/** The stm state. */
		private State stmState;

				
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
 * Instantiates a new memory manager writer.
 *
 * @param agentId the agent id
 */
MemoryManagerWriter(String agentId) {
			this(SOMFileConfigUtils.getKBUrl(),SOMFileConfigUtils.getKBUser(),SOMFileConfigUtils.getKBPass(),SOMFileConfigUtils.getKBPrefix()+agentId,agentId);
		}
		
		/**
		 * Instantiates a new memory manager writer.
		 *
		 * @param url the url
		 * @param user the user
		 * @param pass the pass
		 * @param db the db
		 * @param agentId the agent id
		 */
		MemoryManagerWriter(String url, String user, String pass, String db, String agentId) {
			
			
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
		public static MemoryManagerWriter getMm() {
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
		public static void setMm(MemoryManagerWriter mm) {
			MemoryManagerWriter.mm = mm;
		}

		/**
		 * Update.
		 *
		 * @param strTailUrl the str tail url
		 */
		public void update(String strTailUrl) {
			cdb.updateObservation(strTailUrl);

		}
		
		/**
		 * Update environment property.
		 *
		 * @param propertyScope the property scope
		 * @param propertyType the property type
		 * @param propertyName the property name
		 * @param propertyValue the property value
		 */
		public void updateEnvironmentProperty(String propertyScope, String propertyType, String propertyName, String propertyValue) {
			Element property = null;
			//TODO replace for a configuration file with the value: currentValue???
			property = new Element(propertyScope,propertyType,propertyName);
			property.setValue(propertyValue);
			updateProperty(property);

		}
		
		
		/**
		 * Update property.
		 *
		 * @param property the property
		 */
		public void updateProperty(Element property) {
			String strTailUrl = null;
			String jsonStr = null;
			
			strTailUrl = "property/"+property.getScope()+"/"+property.getName();
			property.setId(strTailUrl);
			jsonStr = JSONUtils.objectToObjecNode(property).put("type","property").toString();
			cdb.updateProperty(strTailUrl,jsonStr);
			LOG.trace("Property :"+property+" updated to: "+jsonStr);
			

		}
		
		/**
		 * Receive message.
		 *
		 * @param msg the msg
		 */
		public void receiveMessage(Element msg) {
			String strTailUrl = null;
			String jsonStr = null;
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("YYYYMMdd");
			
			strTailUrl = "message/"+sdf.format(msg.getReceivedDate())+"/"+msg.getFrom().getId();
			msg.setId(strTailUrl);
			jsonStr = JSONUtils.objectToObjecNode(msg).toString();
			cdb.updateMessage(strTailUrl,jsonStr);
			LOG.trace("Message :"+msg+" received and stored: "+jsonStr);
			

		}

		/**
		 * Update service.
		 *
		 * @param service the service
		 */
		public void updateService(Service service) {
			String strTailUrl;
			LOG.trace("service provider to update:"+service+" - "+service.getName()+" - "+service.getExecutionInstance());
			
			//By default services are added with 0.5 ranking
			
			strTailUrl = "service/" +service.getName();
			LOG.trace("To PUT: "+strTailUrl);
			cdb.updateService(strTailUrl,JSONUtils.objectToObjecNode(service).put("_id",strTailUrl ).put("type","service").toString());

		}
		
		/**
		 * Adds the service provider.
		 *
		 * @param service the service
		 * @param serviceToAdd the service to add
		 * @param hostToAdd the host to add
		 * @param eiToAdd the ei to add
		 */
		public void addServiceProvider(Service service, Service serviceToAdd, Host hostToAdd, ExecutionInstance eiToAdd) {
			String strTailUrl = null;;
		
			try{
			LOG.trace("service provider to add:"+service+" - "+service.getName()+" - "+service.getExecutionInstance().getHost().getName());
			
			if(hostToAdd==null){
				hostToAdd = service.getExecutionInstance().getHost(); 
				hostToAdd.setId("host/"+service.getExecutionInstance().getHost().getName());
				addHost(hostToAdd);
			}			
			eiToAdd.setHost(hostToAdd);
			
			if(serviceToAdd==null){
				 //New service
					serviceToAdd = service;
					serviceToAdd.setId("service/" +service.getName());				
			}
			
			strTailUrl = serviceToAdd.getId();
			
			serviceToAdd.setPotentialExIns(null);
			serviceToAdd.setExecutionInstance(eiToAdd);
					
			LOG.trace("Provider to Add: {}",eiToAdd.getHost().getId());
			cdb.updateService(strTailUrl,JSONUtils.objectToObjecNode(serviceToAdd).put("_id",strTailUrl ).put("type","service").toString());
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		
		/**
		 * Adds the host.
		 *
		 * @param host the host
		 */
		public void addHost(Host host) {
			String strTailUrl;
			try{
			
			strTailUrl = host.getId();
			LOG.trace("Host to add:"+host.getName());
			cdb.updateHost(strTailUrl,JSONUtils.objectToObjecNode(host).put("type","host").toString());
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
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
		 * Adds the local service.
		 *
		 * @param service the service
		 */
		public void addLocalService(Service service){
			try{
			cdb.deleteLocalService(service.getId());
			cdb.updateService(service.getId(),JSONUtils.objectToObjecNode(service).put("type","service").toString() );
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		
		
		
		 /** The is locked. */
 		private boolean isLocked = false;

		  /**
  		 * Lock.
  		 *
  		 * @throws InterruptedException the interrupted exception
  		 */
  		public synchronized void lock() throws InterruptedException{
		    while(isLocked){
		      this.wait();
		    }
		    isLocked = true;
		  }

		  /**
  		 * Unlock.
  		 */
  		public synchronized void unlock(){
		    isLocked = false;
		    this.notify();
		  }
		  
		  /**
  		 * Checks if is locked.
  		 *
  		 * @return true, if is locked
  		 */
  		public boolean isLocked(){
			  return isLocked;
		  }
		
		
	

	
}
