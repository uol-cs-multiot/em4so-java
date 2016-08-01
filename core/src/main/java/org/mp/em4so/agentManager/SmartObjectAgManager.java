/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.mp.em4so.network.protocol.SODiscoverer;
import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.behaviour.reasoning.ReasoningEngine;
import org.mp.em4so.exceptions.UnachievableGoalException;
import org.mp.em4so.model.ModelConstants;
import org.mp.em4so.model.actuating.Action;
import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.agent.Goal;
import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Device;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.model.reasoning.Scenario;
import org.mp.em4so.model.reasoning.Step;
import org.mp.em4so.model.sensing.Observation;
import org.mp.em4so.model.sensing.Property;
import org.mp.em4so.services.ServiceAssembler;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.SOMFileConfigUtils;
import org.mp.em4so.utils.SOManagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
//import org.mp.measurement.MeasurementProxy;



/**
 * The Class SmartObjectAgManager.
 */
public class SmartObjectAgManager {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SmartObjectAgManager.class.getSimpleName());

	/** The kbm. */
	private KnowledgeBaseManager kbm;
	
	/** The ae. */
	private ActionExecutor ae;
	
	/** The sr. */
	private SensorReader sr;
	
	/** The re. */
	private ReasoningEngine re;

	/** The read properties. */
	private List<Property> readProperties;
	
	/** The playable roles. */
	private List<Role> playableRoles;

	/** The sensing services. */
	private HashMap<String, Service> sensingServices = null;
	
	/** The scenarios to play. */
	private HashMap<String, Scenario> scenariosToPlay;

	/** The actions status. */
	// private List<Role> rolesToPlay=null;
	private Hashtable<String, Action> actionsStatus;
	
	/** The asked hosts. */
	private Hashtable<String, String> askedHosts;

	/** The role hosts. */
	private Hashtable<String, List<Host>> roleHosts;
	
	/** The activity roles. */
	private Hashtable<String, Role> activityRoles;
	
	/** The my host. */
	private Host myHost;
	
	/** The root goal id. */
	private String rootGoalId = null;
	
	/** The lock. */
	static Lock lock = new Lock();

	/** On object capabilities. */
	HashMap<String, Capability> capabilities; // No need to pre-load all
												// capabilities when booting,
												// just when using them.
	/** On-object devices. */
	HashMap<String, Device> devices;
	
	/** The i. */
	int i = 0;
	
	/** The ttl. */
	private int TTL = 2;
	
	/** The action retry. */
	private int actionRetry = 3;
	
	/** The remote activities status. */
	protected HashMap<String, String> remoteActivitiesStatus = null;

	/** The id. */
	private String id;

	/** The goals. */
	private HashMap<String, Goal> goals;
	
	/** The goals children. */
	private HashMap<String, Set<String>> goalsChildren = new HashMap<String, Set<String>>();

	/**
	 * Instantiates a new smart object ag manager.
	 *
	 * @param id the id
	 */
	public SmartObjectAgManager(String id) {
		super();
		this.id = id;
	}
	
	/**
	 * Run.
	 */
	public void run() {
		// LogManager.getLogManager().reset();

		try {
			
			//Start sensing
			SensorLoop sl = new SensorLoop(kbm,sr,readProperties,sensingServices);
			Thread sensor = new Thread(sl);
			sensor.start();
			
			
			checkPendingGoals(rootGoalId);


		} catch (Exception e) {

			LOG.error(getId() + ": " + e.getMessage(), e);
		}
		
	}

	/**
	 * Sets the up.
	 *
	 * @param soca the new up
	 * @throws InterruptedException the interrupted exception
	 */
	public void setUp(SOControlAgent soca) throws InterruptedException{
		this.id = soca.getId();
		kbm = new KnowledgeBaseManager(this.getId());
		capabilities = new HashMap<String, Capability>();
		
		ae = new ActionExecutor(this);
		sr = new SensorReader(this);
		re = new ReasoningEngine();
		
		remoteActivitiesStatus = new HashMap<String, String>();
		askedHosts = new Hashtable<String, String>();
		scenariosToPlay = new HashMap<String, Scenario>();
		
		loadMyHost(soca.getUrls());
		rootGoalId = loadGoals();
		roleHosts = new Hashtable<String, List<Host>>();
		LOG.info(this.getId() + ": Started.");
		
		
//		MeasurementProxy.measureHwResources();
//		LOG.info("Start Loading Capabilities: \n"+MeasurementProxy.getAllMeasurements()+"\n");
		
		ServiceAssembler.loadObjectCapabilities();
		
//		MeasurementProxy.measureHwResources();
//		LOG.info("End loading capabilities: \n"+MeasurementProxy.getAllMeasurements()+"\n");
		
		if (readProperties == null) {
			readProperties = kbm.getMyProperties();
		}
		if (sensingServices == null) {
			sensingServices = kbm.getSensingServicesProperties();
		}
		if (playableRoles == null) {
			playableRoles = kbm.getPlayableRoles();
		}

		if (getActionsStatus() == null) {
			setActionsStatus(new Hashtable<String, Action>());
		}
		// kbm.putShortTerm("actions", new Hashtable<String,Action>());

		
//		MeasurementProxy.measureHwResources();
//		LOG.info("Before Service Discovery: \n"+MeasurementProxy.getAllMeasurements());
		
		ServiceDiscoverer.join();
		
//		MeasurementProxy.measureHwResources();
//		LOG.info("After Service Discovery: \n"+MeasurementProxy.getAllMeasurements());
		
		// TODO Advertise roles of each discovered host
		//activityRoles = RoleDiscoverer.discoverRoles();
		LOG.trace(this.getId() + ": Ended.");
	}

	/**
	 * Returns the root goal.
	 *
	 * @return the string
	 */
	private String loadGoals() {
		Goal g;
		Set<String> gChildren;
		String rootGoalId = null;

		goals = new HashMap<String, Goal>();
		goals.putAll(kbm.getMyGoals());

		for (Entry<String, Goal> e : goals.entrySet()) {
			g = e.getValue();
			LOG.trace("processing goal: {} ",g);
			if (g.getParent() != null) {
				LOG.trace("with parent: {} ",g.getParent().getId());
				gChildren = goalsChildren.get(g.getParent().getId());
				if (gChildren == null) {
					gChildren = new HashSet<String>();
				}
				LOG.trace("existing children: {} ",gChildren.size());
				gChildren.add(g.getId());
				goalsChildren.put(g.getParent().getId(), gChildren);
			} else {
				rootGoalId = g.getId();
			}
		}
			
		LOG.trace("Found {} goals and children {}", goals.size(),goalsChildren.get(rootGoalId));
		return rootGoalId;
	}
	
	
	
	/**
	 * Check pending goals.
	 *
	 * @param rootGoalId the root goal id
	 */
	private void checkPendingGoals(String rootGoalId) {
		LOG.trace("Checking pending goals with root goal: {}", rootGoalId);
		levelTraversal(goals.get(rootGoalId));
	}

	/**
	 * Level traversal.
	 *
	 * @param g the g
	 */
	public void levelTraversal(Goal g) {
		if (g == null)
			return;
		if (goalsChildren.get(g.getId()) == null) {
			LOG.trace("{}: load activities for goal: {} - {}", getId(), g.getId(), g.getGoalType());
			workForGoal(g);
		} else {
			for (String gc : goalsChildren.get(g.getId()))
				levelTraversal(goals.get(gc));
				LOG.trace("{}: working for goal: {}", getId(), g);
		}
	}

	/**
	 * Execute activities for achieving one goal.
	 *
	 * @param goal the goal
	 */
	private void workForGoal(final Goal goal) {
		Runnable r1 = new Runnable() {
			Scenario scenario = null;
//			List<Activity> activities = null;
			Role queryRole = null;
			Activity activity = null;
			

			@Override
			public void run() {
				Queue<Activity> pendingActivities, originalPendingActivities;
				Role role = null;

				try {
					
				
					LOG.info("{}: the goal is: {} and its status is: {}", getId(), goal.getId(),
							goals.get(goal.getId()).getStatus());
					// TODO the following line should be replaced by checking
					// the properties of interest instead of status
					// TODO ...or.. these threads always running, checking the
					// properties, but properties are always being updated? or
					// just when a goal is to be evaluated
					// while
					// (!goals.get(goal.getId()).getStatus().equals(SOManagerUtils.STATUS_DONE))
					// {
					pendingActivities = new LinkedBlockingQueue<Activity>();
					scenario = kbm.getScenario(goal);
					// checkScenarioForGoal(goal);
					LOG.trace("scenario for goal: {}, has {} steps", scenario.getId(), scenario.getSteps().size());
					queryRole = new Role();
					queryRole.setActivity(new LinkedList<Activity>());
					for (Step step : scenario.getSteps()) {
						getExecutionPlan(role,queryRole,activity, pendingActivities, step);
					}


					LOG.trace("Starting cycle, there are {} pending activities.", pendingActivities.size());

					originalPendingActivities = new LinkedBlockingQueue<Activity>();
					originalPendingActivities.addAll(pendingActivities);

					//TODO change true for boolean variable to be changed when goal is achieved and the controller is shutdown
					while (goal.getStatus().equals(SOManagerUtils.STATUS_ONPROGRESS)) { 
						LOG.trace("+++++++++WORKING FOR GOAL {} - {} - {} ++++++++++++",goal.getId(),goal.getStatus(),goal.getGoalType());
						lock.lock();
						executeGoalActivities(goal, pendingActivities, activity);
						LOG.trace("End processing activity, remaining activities: {}",pendingActivities.size());
						if(pendingActivities.isEmpty() )
							if(goal.getGoalType()== null || !goal.getGoalType().equals(ModelConstants.GOAL_TYPE_MAINTENANCE)) 
								goal.setStatus(SOManagerUtils.STATUS_DONE);
							else{
								pendingActivities = new LinkedBlockingQueue<Activity>(originalPendingActivities);
								removeStatusGoalActivities(goal,pendingActivities);
							}
						lock.unlock();
						Thread.sleep(5000L);
					}

				} catch (InterruptedException iex) {
					LOG.error("{}: Error processing goal {}: {}", getId(), goal, iex.getMessage(), iex);
				} catch (UnachievableGoalException e) {
					goals.get(goal.getId()).setStatus("failure");
					lock.unlock();
					// TODO What to do when role is not found...
					LOG.error("{}: Error processing goal {}: {}", getId(), goal, e.getMessage(), e);
				}
			}
			
			
		};
		Thread thr1 = new Thread(r1);
		thr1.start();
	}

	
	/**
	 * Check roles and activities to generate the queue of activities to execute
	 * 
	 * @param role
	 * @param queryRole
	 * @param activity
	 * @param pendingActivities
	 * @param step
	 * @throws UnachievableGoalException
	 */
	private void getExecutionPlan(Role role,Role queryRole,Activity activity, Queue<Activity> pendingActivities, Step step)throws UnachievableGoalException {
		role = null;
		queryRole.getActivity().clear();
		queryRole.getActivity().add(step.getActivity());
		role = SODiscoverer.<Role> query(queryRole, getTTL() + 1, null);
		LOG.trace("{}: Found role after local query: {} ", getId(), role);

		if (role == null) {
			throw new UnachievableGoalException(
					"Role not found locally for activity: " + step.getActivity().getId());
		} else {

			activity = kbm.getActivity(step.getActivity());

		
			if (activity != null && activity.getActions() != null) { // Activity
																		// is
																		// carried
																		// out
																		// locally
				step.setActivity(activity);
				if (step.getActivity().getActions() != null)
					step.getActivity()
							.setActions(Collections.synchronizedList(step.getActivity().getActions()));

				activity.setRole(role); // Local activity
				pendingActivities.add(step.getActivity());
			}

		}
	}
	
	
	private void executeGoalActivities(Goal goal, Queue<Activity> pendingActivities, Activity activity) throws UnachievableGoalException{
		if (!pendingActivities.isEmpty()) {
			// synchronized(goals){
			LOG.trace("{}: the goal is: {} and its status is: {}", getId(), goal.getId(),
					goals.get(goal.getId()).getStatus());
			LOG.trace("{}: the activity is: {} and its status is: {}", getId(),
					pendingActivities.element().getId(), pendingActivities.element().getStatus());
			activity = pendingActivities.element();
			if (activity.getRole() != null) { // Role found

				if (activity.getStatus() != null && !activity.getStatus().equalsIgnoreCase("done")) { // Activity has not ended

					if (activity.getInput() != null)
						LOG.trace("Before evaluating activity {} : {}", activity.getId(),
								activity.getInput().getOperator());

					if (activity.getInput() != null && activity.getInput().getOperator() != null
							&& !activity.getInput().getOperator().equals("")) { // There is trigger condition

						LOG.info("Evaluating activity {}", activity.getId());

						if (((Boolean) re.solveBooleanFunction(activity.getInput(), kbm)).booleanValue()) { // check trigger condition
							LOG.trace(" (A) To play activity: " + activity.getId());
							pendingActivities = executeActivity(goal,activity, pendingActivities);
						}
					} else { // There is no trigger (input)
								// condition
						pendingActivities = executeActivity(goal,activity, pendingActivities);
					}
				}

			}else{//all activities are done
				LOG.info("NO MORE ACTIVITIES TO DO");
			}

		}
		LOG.trace("Ending processing of activity");
	}
	
	/**
	 * Executes the activity.
	 *
	 * @param activity the activity
	 * @param pendingActivities the pending activities
	 * @return the queue
	 * @throws UnachievableGoalException the unachievable goal exception
	 */
	private Queue<Activity> executeActivity(Goal goal,Activity activity, Queue<Activity> pendingActivities)throws UnachievableGoalException{
		Activity previous;
		if (!ae.executeKnownActivity(goal,activity)) {
			LOG.info("{}: Completed known activity: {} with trigger condition", getId(),	activity.getId());
			previous = pendingActivities.remove();
			if (!pendingActivities.isEmpty()) {
				pendingActivities.element().setPrevious(previous);
			}
		}
		return pendingActivities;
	}
	
	/**
	 * Removes all the information about status of previous executions of activities for the given goal
	 * @param goal
	 * @param pendingActivities
	 */
	private void removeStatusGoalActivities(Goal goal, Queue<Activity> pendingActivities){
		for (Entry<String,Action> entry : getActionsStatus().entrySet()){
		    if ((entry.getKey()).startsWith(goal.getId())) {
		    	LOG.trace("ACTION STATUS KEY: "+entry.getKey());
		    	getActionsStatus().remove(entry.getKey());
		    }
		}
		Iterator<Activity> it = pendingActivities.iterator();
		Iterator<Action> itaction;
		Activity activity;
		Action action;
		while(it.hasNext()){
			activity =it.next();
			itaction = activity.getActions().iterator();
			while(itaction.hasNext()){
				action = itaction.next();
				LOG.trace("Changing action status from {} to null",action.getStatus());
				action.setStatus(null);
			}
		}
		
	}
	
	/**
	 * Load my host.
	 *
	 * @param myUrls the my urls
	 * @throws InterruptedException the interrupted exception
	 */
	private void loadMyHost(List<URI> myUrls)throws InterruptedException {
		List<Role> roles;
		myHost = new Host();
		myHost.setName(SOMFileConfigUtils.getName());
		myHost.setCategories(new HashSet<String>(Arrays.asList(SOMFileConfigUtils.getCategories().split(", "))));
		roles = new LinkedList<Role>();
		for (String s : Arrays.asList(SOMFileConfigUtils.getRoles().split(", "))) {
			roles.add(new Role(s));
		}
		myHost.setRoles(roles);
		for (URI myuri : myUrls) {
			if (!myuri.toString().startsWith("local:"))
				myHost.getUrls().add(myuri.toString());
		}
		myHost.setId("host/" + myHost.getName());
		myHost.setRanking(1d);
		getKbm().addProvider(myHost);
		myHost.setRoles(getKbm().getMemManager().getRoles());
	}

	

	/**
	 * Get the Knowledge base reference.
	 * @return
	 */
	public KnowledgeBaseManager getKbm() {
		return kbm;
	}

	/**
	 * Sets the kbm.
	 *
	 * @param kbm the new kbm
	 */
	public void setKbm(KnowledgeBaseManager kbm) {
		this.kbm = kbm;
	}

	/**
	 * Gets the ae.
	 *
	 * @return the ae
	 */
	public ActionExecutor getAe() {
		return ae;
	}

	/**
	 * Sets the ae.
	 *
	 * @param ae the new ae
	 */
	public void setAe(ActionExecutor ae) {
		this.ae = ae;
	}

	/**
	 * Gets the sr.
	 *
	 * @return the sr
	 */
	public SensorReader getSr() {
		return sr;
	}

	/**
	 * Sets the sr.
	 *
	 * @param sr the new sr
	 */
	public void setSr(SensorReader sr) {
		this.sr = sr;
	}

	/**
	 * Gets the re.
	 *
	 * @return the re
	 */
	public ReasoningEngine getRe() {
		return re;
	}

	/**
	 * Sets the re.
	 *
	 * @param re the new re
	 */
	public void setRe(ReasoningEngine re) {
		this.re = re;
	}

	/**
	 * Gets the read properties.
	 *
	 * @return the read properties
	 */
	public List<Property> getReadProperties() {
		return readProperties;
	}

	/**
	 * Sets the read properties.
	 *
	 * @param readProperties the new read properties
	 */
	public void setReadProperties(List<Property> readProperties) {
		this.readProperties = readProperties;
	}

	/**
	 * Gets the playable roles.
	 *
	 * @return the playable roles
	 */
	public List<Role> getPlayableRoles() {
		return playableRoles;
	}

	/**
	 * Sets the playable roles.
	 *
	 * @param playableRoles the new playable roles
	 */
	public void setPlayableRoles(List<Role> playableRoles) {
		this.playableRoles = playableRoles;
	}

	/**
	 * Gets the sensing services.
	 *
	 * @return the sensing services
	 */
	public HashMap<String, Service> getSensingServices() {
		return sensingServices;
	}

	/**
	 * Sets the sensing services.
	 *
	 * @param sensingServices the sensing services
	 */
	public void setSensingServices(HashMap<String, Service> sensingServices) {
		this.sensingServices = sensingServices;
	}

	

	/**
	 * Gets the actions status.
	 *
	 * @return the actions status
	 */
	public Hashtable<String, Action> getActionsStatus() {
		return actionsStatus;
	}

	/**
	 * Sets the actions status.
	 *
	 * @param actionsStatus the actions status
	 */
	public void setActionsStatus(Hashtable<String, Action> actionsStatus) {
		this.actionsStatus = actionsStatus;
	}

	/**
	 * Gets the asked hosts.
	 *
	 * @return the asked hosts
	 */
	public Hashtable<String, String> getAskedHosts() {
		return askedHosts;
	}

	/**
	 * Sets the asked hosts.
	 *
	 * @param askedHosts the asked hosts
	 */
	public void setAskedHosts(Hashtable<String, String> askedHosts) {
		this.askedHosts = askedHosts;
	}

	/**
	 * Gets the my host.
	 *
	 * @return the my host
	 */
	public Host getMyHost() {
		return myHost;
	}

	/**
	 * Sets the my host.
	 *
	 * @param myHost the new my host
	 */
	public void setMyHost(Host myHost) {
		this.myHost = myHost;
	}

	/**
	 * Gets the capabilities.
	 *
	 * @return the capabilities
	 */
	public HashMap<String, Capability> getCapabilities() {
		return capabilities;
	}

	/**
	 * Sets the capabilities.
	 *
	 * @param capabilities the capabilities
	 */
	public void setCapabilities(HashMap<String, Capability> capabilities) {
		this.capabilities = capabilities;
	}

	/**
	 * Gets the devices.
	 *
	 * @return the devices
	 */
	public HashMap<String, Device> getDevices() {
		return devices;
	}

	/**
	 * Sets the devices.
	 *
	 * @param devices the devices
	 */
	public void setDevices(HashMap<String, Device> devices) {
		this.devices = devices;
	}

	/**
	 * Gets the ttl.
	 *
	 * @return the ttl
	 */
	public int getTTL() {
		return TTL;
	}

	/**
	 * Sets the ttl.
	 *
	 * @param tTL the new ttl
	 */
	public void setTTL(int tTL) {
		TTL = tTL;
	}

	/**
	 * Gets the action retry.
	 *
	 * @return the action retry
	 */
	public int getActionRetry() {
		return actionRetry;
	}

	/**
	 * Sets the action retry.
	 *
	 * @param actionRetry the new action retry
	 */
	public void setActionRetry(int actionRetry) {
		this.actionRetry = actionRetry;
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
	 * Gets the remote activities status.
	 *
	 * @return the remote activities status
	 */
	public HashMap<String, String> getRemoteActivitiesStatus() {
		return remoteActivitiesStatus;
	}

	/**
	 * Sets the remote activities status.
	 *
	 * @param remoteActivitiesStatus the remote activities status
	 */
	public void setRemoteActivitiesStatus(HashMap<String, String> remoteActivitiesStatus) {
		this.remoteActivitiesStatus = remoteActivitiesStatus;
	}
	

	
	 /**
 	 * The Class SensorLoop.
 	 */
 	private static class SensorLoop implements Runnable {
		 
		 /** The sr. */
 		SensorReader sr;
		 
 		/** The read properties. */
 		List<Property>readProperties;
		 
 		/** The sensing services. */
 		HashMap<String,Service> sensingServices;
		 
 		/** The kbm. */
 		KnowledgeBaseManager kbm;
		 
		  /**
  		 * Instantiates a new sensor loop.
  		 *
  		 * @param kbm the kbm
  		 * @param sr the sr
  		 * @param readProperties the read properties
  		 * @param sensingServices the sensing services
  		 */
  		SensorLoop(KnowledgeBaseManager kbm,SensorReader sr,List<Property> readProperties,HashMap<String,Service> sensingServices){
			 this.kbm = kbm;
			 this.sr = sr;
			 this.readProperties = readProperties;
			 this.sensingServices = sensingServices;
		
			 
		 }
     
     /* (non-Javadoc)
      * @see java.lang.Runnable#run()
      */
     @Override
	public void run() {
         try {
            while(true){
                 // Print a message
            	lock.lock();
                 if(readProperties!=null && !readProperties.isEmpty()){
                	 LOG.trace("To get readings for {} properties",readProperties.size());
                	 List<Observation> l = 	sr.getReadings(readProperties, sensingServices);
                	 kbm.update(l);
                	 lock.unlock();
                	 Thread.sleep(10000L);//Reading frequency TBD: to be parametrised
                 }else {
                	 LOG.trace("To find out my properties of interest");
                	 readProperties = kbm.getMyProperties();
                	 lock.unlock();
                	 Thread.sleep(10000L);
                 }
            	
            }
             
         } catch (Exception e) {
             LOG.error("Error sensing properties: {}",e.getMessage());
         }
     }
 }
}

class Lock{

	  private boolean isLocked = false;

	  public synchronized void lock() throws InterruptedException{
	    while(isLocked){
	      this.wait();
	    }
	    isLocked = true;
	  }

	  public synchronized void unlock(){
	    isLocked = false;
	    this.notify();
	  }
	  
	  public boolean isLocked(){
		  return isLocked;
	  }
	}

