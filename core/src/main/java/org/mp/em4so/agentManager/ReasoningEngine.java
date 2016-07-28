/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agentManager;

import java.net.URI;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.agent.Goal;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.reasoning.Function;
import org.mp.em4so.model.reasoning.IOperation;
import org.mp.em4so.model.reasoning.Operation;
import org.mp.em4so.model.sensing.Observation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class ReasoningEngine.
 */
public class ReasoningEngine {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(ReasoningEngine.class
			.getName());
	
	/** The conditions. */
	private TreeSet<Function> conditions;
	
	
	/**
	 * Am I responsible of activity.
	 *
	 * @param kbm the kbm
	 * @param activity the activity
	 * @return true, if successful
	 */
	public boolean amIResponsibleOfActivity(KnowledgeBaseManager kbm, Activity activity){
		boolean amIResponsible = false;
		
		return amIResponsible;

	}
	
	

	
	
	
	/**
	 * Gets the next goal.
	 *
	 * @param kbm the kbm
	 * @return the next goal
	 */
	public Goal getNextGoal(KnowledgeBaseManager kbm){
		return kbm.getMemManager().getNextGoal();
	}

	/**
	 * Get the rating for the given service according to the factors passed through.
	 * @param som
	 * @param service
	 * @param factors
	 * @return
	 */
	public String getRating(SmartObjectAgManager som, Service service, Hashtable<String, String> factors){
		double previousRating = 0, newRating = 0;
		boolean local = false;
		
		previousRating = Double.parseDouble(service.getExecutionInstance().getRanking());
		
		for(String myuri:som.getMyHost().getUrls()){
			for(String hostUrl:service.getExecutionInstance().getHost().getUrls() ){
				if(myuri.equals(hostUrl)){
					local = true;
					break;
				}
			}
		}
		
		//location of the service
		if(!local){
			if(factors.get("availability").equals("true")){
				if(previousRating<1){
					newRating = previousRating + ((1- previousRating)/2);
				}else
					newRating = previousRating;
			}else{
				if(previousRating > 0 && previousRating < 1){
					newRating = previousRating - ((1- previousRating)/2); 
				}else if(previousRating >0){
					newRating = previousRating - (previousRating / 2);
				}
			}
						
		}else{
			newRating = previousRating;
		}
				
		return String.valueOf(newRating);
	}

	
	/**
 * It loads the values for the array attributes defined in the activity either for
 * input or output (knowledge).
 *
 * @param kbm the kbm
 * @param element the element
 * @return the attribute values for properties
 */
	public  Element getAttributeValuesForProperties(KnowledgeBaseManager kbm,Element element){
		return kbm.getElement(element);
	}
	
	
	/**
	 * Return de the actual value to use in the comparison for input triggers.
	 *
	 * @param kbm the kbm
	 * @param element the element
	 * @return the single property value
	 */
	public String getSinglePropertyValue(KnowledgeBaseManager kbm,Element element){
		String value = null;
		Observation o = null;
		String equivalent = null;
		Element foundElement = null;
		LOG.debug(" Getting property value for: {}",element);
		if(element != null){
			if(element.getName()!=null){
				if(element.getAttributeName()!=null && element.getAttributeName().equals("level")){
						o = kbm.getRecentObservation(element.getName());
						if(o!=null){ 
							value = o.getValue();
						}
				}else{
						foundElement = kbm.getElement(element); 
						if(foundElement!= null){
							value = element.getValue();
						}else{
							LOG.trace("Entering to get Equivalences");
							//TODO To complete identification of equivalent properties among different SOs 
							equivalent = getKnowledgeElement(kbm,"type",element.getScope());
							element.setScope(equivalent);
							value = getSinglePropertyValue(kbm,element);
							}
				}
	
				
			}else if(element.getValue()!=null){
				value = element.getValue();
			}
				
		}
		
		if (value!=null && (value.equals("null")|| value.equals(""))) value = null;
		return value;
	}
	
	/**
	 * Gets the knowledge element.
	 *
	 * @param kbm the kbm
	 * @param relation the relation
	 * @param entity the entity
	 * @return the knowledge element
	 */
	public String getKnowledgeElement (KnowledgeBaseManager kbm, String relation, String entity){
		return "";
	}
	
	/**
	 * Gets the single message value.
	 *
	 * @param kbm the kbm
	 * @param element the element
	 * @param message the message
	 * @return the single message value
	 */
	public String getSingleMessageValue(KnowledgeBaseManager kbm,Element element, String message){
		String value = null;
		Observation o = null;
		String equivalent = null;
		Element foundElement = null;
		
		element.setValue(message);
		foundElement = kbm.getLastPendingMessage(element);
		
		if (foundElement!= null && foundElement.getValue()!=null && !foundElement.getValue().equals("null") && !foundElement.getValue().equals(""))
			value = foundElement.getValue();
		return value;
	}
	
	/**
	 * Solve function.
	 *
	 * @param function the function
	 * @param kbm the kbm
	 * @return the object
	 */
	public Object solveFunction(Function function, KnowledgeBaseManager kbm){
		Object result = null;
		String operand1=null, operand2=null,operator = null;
		operator = function.getOperator();
		LOG.debug("Solving for: {} {} {} or {} operands",function.getOperand1(),operator,function.getOperand2(), function.getOperands());

		
		if(function.getOperands()==null ){
			
			
			
			//Message checking
			if((function.getOperand1()!=null && function.getOperand1()!=null && function.getOperand1().getKind().equals("message")) || 
					(function.getOperand2()!=null && function.getOperand2().getKind()!=null && function.getOperand2().getKind().equals("message"))){
				
				if(function.getOperand1().getValue()!=null){
					operand1 = function.getOperand1().getValue();
					operand2 = getSingleMessageValue(kbm,function.getOperand2(),operand1);
					LOG.debug("Solving if for:"+operand1+" "+operator+" "+operand2);
				}else {
					operand2 = function.getOperand2().getValue();
					operand1 = getSingleMessageValue(kbm,function.getOperand1(),operand2);
					LOG.debug("Solving else 1 for:"+operand1+" "+operator+" "+operand2);
				}
			}else{
				operand1 = getSinglePropertyValue(kbm,function.getOperand1());
				operand2 = getSinglePropertyValue(kbm,function.getOperand2());
				LOG.debug("Solving else 2 for operand 1 = {}: {} {} operand2 = {}: {}",function.getOperand1(),operand1, operator,function.getOperand2(), operand2);
			}
			
			
			LOG.debug("P1- o1: "+operand1+" "+operator+" o2:"+operand2);
			Map<String,IOperation> theMap= null;

			IOperation op;
			Operation.getInstance();
			theMap = Operation.opByName;
			LOG.debug("the map:"+theMap+" and operator to find->"+operator+"<- and the o1:"+operand1+" and the o2:"+operand2);
			op = theMap.get(operator);
			LOG.debug("The operation:"+op);
			if(op!=null){
				result = op.calculate(operand1, operand2);
			}else{
				result = true;
			}
			LOG.debug("P1 -the result:"+result);
			//result = (Operation.getInstance().opByName.get(operator)).calculate(operand1, operand2);
			LOG.trace("The result of {} {} {} is: {}",operand1,operator,operand2,result);
		}else if(function.getOperands()!=null){
			
			
			
			
			LOG.trace("P2- o1: "+function.getOperands().toArray()[0]+" "+operator+" o2:"+function.getOperands().toArray()[1]);
			Function fop1, fop2 = null;

			fop1 = (Function)function.getOperands().toArray()[0];
			fop2 = (Function)function.getOperands().toArray()[1];
			
			
			LOG.trace(" operator is: "+operator);
			Object x = solveFunction(fop1,kbm);
			LOG.trace(" fop1: "+fop1);
			Object y = solveFunction(fop2,kbm);
			LOG.trace(" fop2: "+fop2);
			
			Operation.getInstance();
			result = Operation.opByName.get(operator).calculate(x,y);
			LOG.trace("The result of {} {} {} is: {}",x,operator,y,result);
		}
		
		return result;
	}

	
	/**
 * Check available hosts.
 *
 * @param activities the activities
 * @param kbm the kbm
 * @return the list
 */
public List<Activity> checkAvailableHosts(List<Activity> activities, KnowledgeBaseManager kbm){
//		kbm.getMemManager().
		return null;
	}
		
	/**
	 * Checks for access rights.
	 *
	 * @param service the service
	 * @param myUrls the my urls
	 * @param senderUrl the sender url
	 * @param kbm the kbm
	 * @return true, if successful
	 */
	public boolean hasAccessRights(Service service, List<URI> myUrls,String senderUrl, KnowledgeBaseManager kbm){
		
		URI uriSender = URI.create(senderUrl);
		boolean grantedRights = false;
//		Local call
		for(URI url:myUrls){
			if(url.equals(uriSender)){
				grantedRights = true;
				LOG.trace("Local call of service:"+service.getName());
				return grantedRights; 
			}
		}
		
		grantedRights = kbm.hasAccessRightsService(service,senderUrl);
		LOG.trace("Use of service: "+service.getName()+" to "+senderUrl+" allowed? = "+grantedRights);
		return grantedRights;
	}
	
}
