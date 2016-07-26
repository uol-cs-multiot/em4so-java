/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.actuating;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




// TODO: Auto-generated Javadoc
/**
 * The Class Action.
 *
 * @author me
 * @version 1.0
 * @created 20-Nov-2014 11:10:33 AM
 */
public class Action {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(Action.class.getSimpleName());
	
	/** Sequence in which this action will be executed within the plan. */
	private int sequence;
	
	/** The id. */
	private String id;
	
	/** The executing role. */
	private Role executingRole;
	/**
	 * No. times action has been executed
	 */
	private int timesExecuted;
	
	/** The prereq. */
	private List<String> prereq; 
//	private Service service;
	
	/** The status. */
	private String status;
	
/** The arg values. */
private Hashtable<String,?> argValues; 

	/** The result. */
	private String result;
	
	/** The service. */
	private Service service;
	/**
	 * Categories for the activity that this action belongs to. 
	 */
	private HashSet<String> categories;
	
	/** The input elements. */
	private List<Element> inputElements;
	
	/**
	 * Instantiates a new action.
	 */
	public Action(){
		status = "pending";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

	}

	

	/**
	 * Gets the sequence.
	 *
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * Gets the times executed.
	 *
	 * @return the times executed
	 */
	public int getTimesExecuted() {
		return timesExecuted;
	}

	

	/**
	 * Sets the sequence.
	 *
	 * @param sequence the new sequence
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * Sets the times executed.
	 *
	 * @param timesExecuted the new times executed
	 */
	public void setTimesExecuted(int timesExecuted) {
		this.timesExecuted = timesExecuted;
	}


	/**
	 * Gets the arg values.
	 *
	 * @return the arg values
	 */
	public Hashtable<String, ?> getArgValues() {
		return argValues;
	}
	
	/**
	 * Gets the args.
	 *
	 * @return the args
	 */
	public Hashtable<String, ?> getArgs() {
		return argValues;
	}
	
	/**
	 * Sets the args.
	 *
	 * @param argValues the arg values
	 */
	public void setArgs(Hashtable<String, ?> argValues) {
		this.argValues = argValues;
	}
	
	/**
	 * Sets the arg values.
	 *
	 * @param argValues the arg values
	 */
	public void setArgValues(Hashtable<String, ?> argValues) {
		this.argValues = argValues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String str = "ACTION: #{";
		try{
		str = "\n service name: "+service.getName()+"\n argTypes:\n";
		if(service!=null && service.getArgTypes()!=null)
		for(Entry <String,String>e:service.getArgTypes().entrySet()){
			str+=""+e.getKey()+"-"+e.getValue()+" \n";
		}
		str += "\n argValues: \n";
		if(service!=null && argValues!=null)
		for(Entry <String,?>e:argValues.entrySet()){
			str+=e.getKey()+"-"+e.getValue()+" \n";
		}
		
		str+= " result: {"+this.getResult()+"}";
		str = str + "}#:END\n";
		
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
		
		return str;
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Sets the service.
	 *
	 * @param service the new service
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public HashSet<String> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(HashSet<String> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the prereq.
	 *
	 * @return the prereq
	 */
	public List<String> getPrereq() {
		return prereq;
	}

	/**
	 * Sets the prereq.
	 *
	 * @param prereq the new prereq
	 */
	public void setPrereq(List<String> prereq) {
		this.prereq = prereq;
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
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the executing role.
	 *
	 * @return the executing role
	 */
	public Role getExecutingRole() {
		return executingRole;
	}

	/**
	 * Sets the executing role.
	 *
	 * @param executingRole the new executing role
	 */
	public void setExecutingRole(Role executingRole) {
		this.executingRole = executingRole;
	}

	/**
	 * Gets the input elements.
	 *
	 * @return the input elements
	 */
	public List<Element> getInputElements() {
		return inputElements;
	}

	/**
	 * Sets the input elements.
	 *
	 * @param inputElements the new input elements
	 */
	public void setInputElements(List<Element> inputElements) {
		this.inputElements = inputElements;
	}
	
	


}