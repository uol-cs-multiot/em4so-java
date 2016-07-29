/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.mp.em4so.exceptions.UnachievableGoalException;
import org.mp.em4so.model.actuating.Action;
import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.agent.Goal;
import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.network.protocol.SOMessageSender;
import org.mp.em4so.services.ServiceAssembler;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;


// TODO: Auto-generated Javadoc
/**
 * The Class ActionExecutor.
 */
public class ActionExecutor {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(ActionExecutor.class
			.getName());
	
	/** The somanager. */
	SmartObjectAgManager somanager;

	/**
	 * Instantiates a new action executor.
	 *
	 * @param somanager the somanager
	 */
	ActionExecutor(SmartObjectAgManager somanager){
		this.somanager = somanager;
	}
	
	
	/**
	 * Execute action known by this Smart Object/agent.
	 *
	 * @param somanager SO controller agent which will execute the action
	 * @param activity Parent activity for the action
	 * @param action Action to execute
	 * @return Action updated after execution
	 * 				
	 * @throws UnachievableGoalException the unachievable goal exception
	 */
	public Action executeKnownAction(SmartObjectAgManager somanager, Activity activity,Action action)throws UnachievableGoalException{
		Service foundService = null;
		try{
		LOG.info("{}: Preparing execution of known action: {} ",this.somanager.getId(),action);
		foundService = action.getService();
		foundService.setExecutionInstance(ServiceAssembler.populateExecutionInstance(action.getService()));	
		if(foundService!= null //Only known services and execution instances
				&& foundService.getExecutionInstance()!=null 
				&& foundService.getExecutionInstance().getHost()!=null 
				&& foundService.getExecutionInstance().getHost().getUrls() != null) {
				
			LOG.trace(this.somanager.getId()+": Found service:{} to execute w role {} on: {} result: {}",foundService.getName(),activity.getRole().getId(),foundService.getExecutionInstance().getHost().getUrls(),action.getResult());
					foundService.setExecutingRoleId(activity.getRole().getId());
					ServiceAssembler.executeService(foundService,
							loadArgValues(action,activity).getArgValues(),
							loadResultElement(activity,action));
					action.setStatus("done"); //from done to onprogress since the call to execute (processMessage) is asyncrhonous no way to control success or not//from onprogress to done
			
		}else{
			action.setStatus("failed");
			//throw new UnachievableGoalException("Unknown Action: "+action.getService().getName());
		}
			
			
		//else --> No processing for unknown services
		//TODO See if it s worthwhile to query p2p here
		
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
		return action;

	}
	
	
	/**
	 * Look for and execute the service linked to an action.
	 *
	 * @param somanager the somanager
	 * @param action the action
	 * @param activity the activity
	 */
	public void executeAction(SmartObjectAgManager somanager, Action action, Activity activity){
		Service foundService = null;
		try{
		LOG.info(this.somanager.getId()+": Starting execution of SERVICE:"+action.getService().getId()+" - "+action.getService().getName());
		foundService = action.getService();
		foundService.setExecutionInstance(ServiceAssembler.populateExecutionInstance(action.getService()));	
		if(foundService!= null 
				&& foundService.getExecutionInstance()!=null 
				&& foundService.getExecutionInstance().getHost()!=null 
				&& foundService.getExecutionInstance().getHost().getUrls() != null) {
				
			LOG.trace(this.somanager.getId()+": Found service:"+foundService.getName()+" to execute on: "+foundService.getExecutionInstance().getHost().getUrls()+" result:"+action.getResult());
					foundService.setExecutingRoleId(activity.getRole().getId());
					ServiceAssembler.executeService(foundService,loadArgValues(action,activity).getArgValues(),loadResultElement(activity,action));
					action.setStatus("done");
			
		}else{
			ServiceDiscoverer.query( action.getService(), somanager.getTTL()+1,null);
		}
			
		
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
	
	}
	
	/**
	 * Look for result element data and load it, getting it ready for action execution.
	 *
	 * @param activity the activity
	 * @param action the action
	 * @return the string
	 * @throws UnachievableGoalException the unachievable goal exception
	 */
	public String loadResultElement(Activity activity,Action action)throws UnachievableGoalException{
		String resultTo= null;
		int indexO, indexI;
		Element element = null;
		Element elementIn = null;
		String attribute = null;
		
		LOG.trace(" The result will go to: {}",action);
		if(action.getResult()!=null && activity.getOutput()!=null && !activity.getOutput().isEmpty()){
			
			indexO = Integer.parseInt(action.getResult().substring(1, action.getResult().length()))-1;
			
			LOG.trace("To get knowledge index {}",indexO);
			element = activity.getOutput().get(indexO);
			LOG.trace("To get output element {}",element);
			if(element!= null && element.getName()!=null && element.getScope()!=null && element.getAttributeName()!=null && element.getKind()!=null){
				if(element.getName().contains(".")){
					indexI = Integer.parseInt(element.getName().substring(1, element.getName().indexOf(".")))-1;
					attribute = element.getName().substring(element.getName().indexOf(".")+1,element.getName().length());
					
					LOG.trace("To get knowledge index {} from {}",indexI,activity.getInputKnowledge().size());
					elementIn = activity.getInputKnowledge().get(indexI);
					element.setName(element.getScope()+"."+elementIn.getName());
					element.setAttributes(new HashMap<String,String>());
					element.getAttributes().put(attribute, (String)action.getArgValues().get(attribute));
				}
				LOG.trace("Element to serialize {}",element);
				ObjectNode on = (JSONUtils.<Element>objectToObjecNode(element));
				resultTo = on.toString();
				LOG.trace(" 1-Result of serialization	: {}",resultTo);
				
				
				
				
				
			}else{
				throw new UnachievableGoalException("Invalid output specification "+action.getResult()+"  for activity "+activity.getId());
			}
		
			
		}
		LOG.trace(" And the result goes to: {}",resultTo);
		return resultTo;
	}

	/**
	 * Load actual arguments for actions from knowledge base according to specified in the activity inputs and outputs.
	 *
	 * @param action the action
	 * @param activity the activity
	 * @return the action
	 */
	public Action loadArgValues(Action action,Activity activity){
		Iterator <String>it = null;
		String value = null;
		String key = null;
		
		Element element = null;
		List<Element> inputKnowledge = activity.getInputKnowledge();
		List<Element> outputKnowledge = activity.getOutput();
		List<Element> knowledgeList = null;
		Hashtable<String,Object> newArgValues = null;
		if(inputKnowledge!=null) LOG.trace(" There are {} elements as input knowledge",inputKnowledge.size());
		if(outputKnowledge!=null) LOG.trace(" There are {} elements as output knowledge",outputKnowledge.size());
		
		if(action!=null && action.getArgValues()!=null){ // action has no arguments
			
			it = action.getArgValues().keySet().iterator();
			newArgValues = new Hashtable<String,Object>();
			
			if(action.getInputElements()==null) action.setInputElements(new LinkedList<Element>());
			
			while(it.hasNext()){
				key = it.next();
				value = action.getArgValues().get(key).toString();
				
				if(value.startsWith("k")) // this argument comes from input knowledege list
					knowledgeList = inputKnowledge;
				else if (value.startsWith("o")) // this argument comes from output knowledege list
					knowledgeList = outputKnowledge;
				
				value = value.substring(1,value.length());
				LOG.trace("To get knowledge item {} order ={}",value, key);
				
				newArgValues = getKnowledgeItemForService(knowledgeList, value, action, key, outputKnowledge,newArgValues);
			}
			
			if(newArgValues.size()>0){
				action.setArgValues(newArgValues);
			}
			
		}
		return action;
	}
	
	/**
	 * Get Knowledge item data to use for action/service invocation.
	 *
	 * @param knowledgeList the knowledge list
	 * @param elementId the element id
	 * @param action the action
	 * @param key the key
	 * @param outputKnowledge the output knowledge
	 * @param newArgValues the new arg values
	 * @return the knowledge item for service
	 */
	public Hashtable<String,Object> getKnowledgeItemForService(List<Element> knowledgeList,String elementId,Action action, String key, List<Element> outputKnowledge, Hashtable<String,Object> newArgValues){
		int index;
		String attribute = null;
		Element element=null;
		
		if(elementId.contains(".")){
			index = Integer.parseInt(elementId.substring(0, elementId.indexOf(".")))-1;
			attribute = elementId.substring(elementId.indexOf(".")+1,elementId.length());
			
			LOG.trace("To get knowledge index value. {}",elementId);
			element = knowledgeList.get(index);
			
			if(element.getValue()==null){
				element = somanager.getKbm().getElement(element);
			}
			 if(element.getKind()!=null && !element.getKind().equals("message")){
				 action.getInputElements().add(element);
				 LOG.trace("Attributes is not NULL ->{}<- and attribute is not NULL {} ",element.getAttributes(),attribute);
				 LOG.trace("Attribute to pass through service: {}", element.getAttributes().get(attribute));
				 newArgValues.put(key,element.getAttributes().get(attribute));
			 }
			
		}else{
			
			LOG.trace("To get knowledge index value {}",elementId);
			index = Integer.parseInt(elementId)-1;
			
			
			
			element = knowledgeList.get(index);
			
			if(element.getValue()==null){
				element = somanager.getKbm().getElement(element);
			}
			
			//replace value scope with output
			if(element.getScope()!=null){
				 for(Element output:outputKnowledge){
					if(output.getName()!=null && output.getName().equals("k"+elementId)){
						element.setScope(output.getScope());
						LOG.trace("Set scope from {} to {}",element.getScope(),output.getScope());
					}
				 }
			 }
			
			
			newArgValues.put(key,element);
		}
		return newArgValues;
	}
	
	
	/**
	 *  Execute each activity with actions as services that can be remote.
	 *
	 * @param activity the activity
	 * @return true, if successful
	 */
//	public boolean executeActivity(Activity activity){
//		boolean pendingWork = true;
//		boolean prerequisite = false;
//		List<Action> actions = null;
//		int j = 0;
//		Action action;
//		actions = activity.getActions();
//		Action actionStatus;
//		List<String> prereq;
//		String prer = null;
//		LOG.trace("Processing activity {} with {} actions",activity.getId(),activity.getActions());
//		while(actions!=null && actions.size() > 0 ){
//			action = actions.get(j);
//			LOG.trace(somanager.getId()+": (1)executing action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()));
//			
//			//check if status has been updated
//			actionStatus = somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId());
//			if(actionStatus!=null){
//				action.setStatus(actionStatus.getStatus());
//			}else {
////				action.getService().setId(a.getSeq()+"."+action.getId());
//				actionStatus = action;
//				if(actionStatus.getStatus()==null){
//					actionStatus.setStatus("pending");
//				}
//				
//				addActionStatus(activity.getSeq()+"."+action.getId(), actionStatus);
//			}
////		
//			LOG.trace(somanager.getId()+": (2) executing action:"+actionStatus.getService().getName()+". Status: "+actionStatus.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()));
//			
//			//Pending Actions
//			if(action.getStatus().equals("pending")){
//				pendingWork = true;
//			
//			LOG.trace(somanager.getId()+": (3) executing action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()));
//			prereq = action.getPrereq();
//			LOG.trace(somanager.getId()+": (3-Inner) executing action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId())+" prerrequisites:"+prereq);
//			if(prereq!=null){
//				prerequisite = false;
//				
//				for(String pre:prereq){
//					prer = somanager.getActionsStatus().get(activity.getSeq()+"."+pre).getStatus();
//					LOG.trace(somanager.getId()+": (4-Inner) executing action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId())+" prerequiste: "+prer+ " = "+activity.getSeq()+"."+pre);
//					if(prer != null && prer.equals("done")){
//						prerequisite = true;
//					}else{
//						prerequisite = false;
//					}
//				}
//				if(prerequisite) executeAction(somanager,action,activity);
//			}else{
//				executeAction(somanager,action,activity);
//			}
//			//if onprogress it doesn't do anything, just continue and the in the next run probably it's done
//			
//		}else if (action.getStatus().equals("onprogress")){
//			pendingWork = true;
//			LOG.trace(somanager.getId()+": (5) action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()));
//		}
//			j++;
//			LOG.trace(somanager.getId()+": (6) action:"+action.getService().getName()+". Status: "+action.getStatus()+". ActionStatus: "+somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()) + " pendingWork:"+pendingWork);
//		}
//		
//		
//		return pendingWork;
//	}
	
	
	

	/**
	 * Execute activity defined in SO KB.
	 *
	 * @param goal
	 * @param activity the activity
	 * @return true, if successful
	 * @throws UnachievableGoalException the unachievable goal exception
	 */
	
	public boolean executeKnownActivity(Goal goal,Activity activity) throws UnachievableGoalException{
		boolean pendingWork = true;
		List<Action> actions = null;
		actions = activity.getActions();
		boolean [] activityStatus = new boolean[actions.size()];
		Arrays.fill(activityStatus, false);
		int i = 0;
		LOG.trace("{}: ******* Start processing local activity {} ",somanager.getId(),activity.getId());
		if(activity.getActions()!=null && actions.size() > 0){
			LOG.trace("{} actions *******",activity.getActions().size());
			for(Action action:actions){
				LOG.trace("Ready to process action Id is:{}, status: {} and Service name is {}",action.getId(),action.getStatus(),action.getService().getName());
				activityStatus[i]=processAction(goal,activity,action);
				i++;
			}
			for (i=0;i<activityStatus.length;i++){
				pendingWork = pendingWork && activityStatus[i]; 
			}
		
		}else {
			LOG.trace("No actions defined ********");
			pendingWork = false;
		}
		
		return pendingWork;
	}
	
	
	
	/**
	 * Gets the remote activity status.
	 *
	 * @param activity the activity
	 * @return the remote activity status
	 */
	public String getRemoteActivityStatus(Activity activity){
		LOG.trace("{}: Starting update status of remote activity: {}. Status found is: {}  ",somanager.getId(),activity.getId(),somanager.getRemoteActivitiesStatus().get(activity.getId()));
		
		String newStatus = null;
		newStatus = somanager.getRemoteActivitiesStatus().get(activity.getId());
		
		if(newStatus==null) newStatus = "pending";
		
			//re.updateRanking(host);
			//TODO if reach this line update OK, so rank host OK in availability
			//TODO check time, if taking so long then punish host in response time and change to "pending" 
		
		return newStatus;
	}
	
	
	/**
	 * Notify activity done.
	 *
	 * @param roleToNotify the role to notify
	 * @param activity the activity
	 */
	public void notifyActivityDone(Role roleToNotify,Activity activity){
		Host host = null;
		Activity doneActivity = null;
		
		host = somanager.getKbm().getRoleHost(roleToNotify);
		
		
		if(host!= null ){
			doneActivity = new Activity();
			doneActivity.setId(activity.getId());
			doneActivity.setStatus("done");
			SOMessageSender.send(host.getUrls().get(0), "activity",doneActivity, "activityupdate");
			LOG.trace("{}: Role {} notified of activity {} done.",somanager.getId(),roleToNotify.getId(),doneActivity.getId());
		}else{
			LOG.info("WARNING: Not available hosts for role {} to notify of activity {} done.",roleToNotify,activity.getId());
		}
		
	}
	
	/**
	 * Check status of the action and decides what action processing is required (execute, wait, error).
	 *
	 * @param goal
	 * @param activity the activity
	 * @param action the action
	 * @return true, if successful
	 * @throws UnachievableGoalException the unachievable goal exception
	 */
	public boolean processAction(Goal goal,Activity activity,Action action) throws UnachievableGoalException{
		
	boolean pendingWork = false;
		Action actionStatus = null;
		String statusKey = null;
				
		LOG.trace("{} : (1) Start executing action: {} with status: ->{} / {} <- and service: id: ->{}<-, name: ->{}<-", 
				somanager.getId(),
				action.getId(),
				action.getStatus(),
				somanager.getActionsStatus().get(activity.getId()+"."+activity.getSeq()+"."+action.getId()),
				action.getService().getId(),
				action.getService().getName()
				);
		
		//check if status has been updated
				statusKey = goal.getId()+"."+activity.getId()+"."+activity.getSeq()+"."+action.getId();
				actionStatus = somanager.getActionsStatus().get(statusKey);
				LOG.trace("ACTION STATUS KEY:{} AND VALUE: {}", statusKey,actionStatus);

				if(actionStatus!=null){
					action.setStatus(actionStatus.getStatus());
				}else {
					actionStatus = action;
					if(actionStatus.getStatus()==null){
						actionStatus.setStatus("pending");
					}
					action.getService().setStatusKey(statusKey);
					addActionStatus(action.getService().getStatusKey(), actionStatus);
					
				}
		
		LOG.trace("{} : (2) Processing by status action: {} with status: {} and {}", somanager.getId(),action.getService().getName(),action.getStatus(),somanager.getActionsStatus().get(activity.getSeq()+"."+action.getId()));
		
		switch (action.getStatus()){
			case "pending":{
				boolean prerequisite = false;
				List<String> prereq = null;
				String prer = null;
				pendingWork = true;//Still work to do
				
				//Check prerequisites for the action
				prereq = action.getPrereq();
				
				if(prereq!=null){
					prerequisite = false;
					LOG.trace("{}: (3) Prerequisites of action {} are : ",somanager.getId(),action.getService().getName());
					for(String pre:prereq){
						prer = somanager.getActionsStatus().get(activity.getSeq()+"."+pre).getStatus();
						LOG.trace("{}: (4) - {} Prerequisite {} w status: {}",somanager.getId(),pre,somanager.getActionsStatus().get(activity.getSeq()+"."+pre),prer);
						if(prer != null && prer.equals("done")){
							prerequisite = true;
						}else{
							prerequisite = false;
						}
					}
					
				}
				
				LOG.trace("{}: (5) Any pending prerequisite for action {}? {} ",somanager.getId(),action.getService().getName(),prerequisite);
				
				if(!prerequisite) action = executeKnownAction(somanager,activity,action);
			}
			break;
			case "onprogress":{
				pendingWork = true;//Still work to do
			}
			break;
			case "done":{
				pendingWork = false;//No work to do
			}
			break;
			case "failure":{
				throw new UnachievableGoalException("No way to get action completed");
			}
		}
		return pendingWork;
	
	}
	

	/**
	 * Gets the somanager.
	 *
	 * @return the somanager
	 */
	public SmartObjectAgManager getSomanager() {
		return somanager;
	}



	/**
	 * Sets the somanager.
	 *
	 * @param somanager the new somanager
	 */
	public void setSomanager(SmartObjectAgManager somanager) {
		this.somanager = somanager;
	}
	
	
	/**
	 * Update activity status.
	 *
	 * @param activity the activity
	 */
	public void updateActivityStatus(Activity activity){
		somanager.getRemoteActivitiesStatus().put(activity.getId(),activity.getStatus());
		LOG.trace("Updating status of activity {} to: {}",activity.getId(),somanager.getRemoteActivitiesStatus().get(activity.getId()));
	}
	
	/**
	 * Update action status.
	 *
	 * @param service the service
	 * @param status the status
	 */
	public void updateActionStatus(Service service, String status){
		Action foundAction = null;
		LOG.trace("Updating status for service {} to {}",service.getStatusKey(),status);
		foundAction = somanager.getActionsStatus().get(service.getStatusKey());
		if(foundAction!=null){
			foundAction.setStatus(status);
			somanager.getActionsStatus().put(service.getStatusKey(),foundAction);
		}
	}
	
	/**
	 * Adds the action status.
	 *
	 * @param fullSequence the full sequence
	 * @param action the action
	 */
	public void addActionStatus(String fullSequence, Action action){
		LOG.trace("Added action status for: {} ",fullSequence);
		somanager.getActionsStatus().put(fullSequence, action);
	}
	

}
