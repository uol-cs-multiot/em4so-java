/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.actuating;

import java.util.Map;
import java.util.TreeMap;

import org.mp.em4so.model.agent.Belief;

// TODO: Auto-generated Javadoc
/**
 * The Class Task.
 */
public class Task {
 
 /** The trigger. */
 private String trigger; 
 
 /** The action. */
 private String action;
 
 /** The purpose. */
 private String purpose;


/**
 * Receives the a JSON object containing the method, arguments - from the knowledge base  
 * To evaluate if the action can be performed (precondition) of it has already finished (postcondition)...
 *
 * @param current the current
 * @param desirable the desirable
 * @return true, if successful
 */
public boolean evaluate(Map<String,Belief> current,Map<String, Belief> desirable ){
	
	//TODO Implement evaluation of pre and pos conditions using parameters provided by developer in JSON. 
	// Verify if the following code works the idea is to receive two maps with key as the Fact name and the Belief Object, 
	//it will compare if both states are equals in that case returns true, otherwise false. The idea is call this method
	//to verify preconditions: current should be gathered from Knowledge database while desirable is where actually the expected values
	// --in terms of the beliefs existing in KB-- are defined.
	
	boolean result;
	//logger.debug(conditionData);
	result = true;
	Belief currentBelief, desirableBelief;
	String bKey;
	TreeMap <String, Belief>currentTM = new TreeMap<String, Belief>(current);
	TreeMap <String, Belief>desirableTM = new TreeMap<String, Belief>(desirable);
	
	
	while(!currentTM.isEmpty() && !desirableTM.isEmpty()){
		bKey = currentTM.firstKey();
		currentBelief = currentTM.remove(bKey);
		desirableBelief = desirableTM.remove(bKey);
		if(!currentBelief.getValue().equals(desirableBelief.getValue())){
			return false;
		}
	}
	
	return true;
}


/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override 
public String toString(){
	 return "action: "+ action + "- purpose: "+purpose;
 }


/**
 * Gets the trigger.
 *
 * @return the trigger
 */
public String getTrigger() {
	return trigger;
}


/**
 * Gets the action.
 *
 * @return the action
 */
public String getAction() {
	return action;
}


/**
 * Sets the trigger.
 *
 * @param trigger the new trigger
 */
public void setTrigger(String trigger) {
	this.trigger = trigger;
}


/**
 * Sets the action.
 *
 * @param action the new action
 */
public void setAction(String action) {
	this.action = action;
}


/**
 * Gets the purpose.
 *
 * @return the purpose
 */
public String getPurpose() {
	return purpose;
}


/**
 * Sets the purpose.
 *
 * @param purpose the new purpose
 */
public void setPurpose(String purpose) {
	this.purpose = purpose;
}



}
