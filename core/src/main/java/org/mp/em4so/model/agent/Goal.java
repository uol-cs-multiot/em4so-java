/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.agent;

import java.util.Map;

import org.mp.em4so.model.sensing.Property;
import org.mp.em4so.utils.SOManagerUtils;


// TODO: Auto-generated Javadoc
/**
 * Binding of set of goals.
 *
 * @author me
 * @version 1.0
 * @updated 17-Apr-2015 20:13:57
 */
public class Goal {

	/** The committed. */
	private boolean committed;
	
	/** currentPriority of this Goal. */
	private boolean inExecution;
	
	/** Intended value for the state. */
	private Object intendedValue;
	
	/** The description. */
	private String description;
	
	/** The parent. */
	private Goal parent;
	
	/** The priority. */
	private int priority;
	
	/** State or property to check for the intended state. */
	private Property referenceProperty;
	
	/** The subgoals. */
	private Map<String,Goal> subgoals;
	
	/** The status. */
	private String status;
	
	/** The type. */
	private String type;
	
	/** The id. */
	private String id;
	
	/**
	 * Instantiates a new goal.
	 */
	public Goal(){
		status = SOManagerUtils.STATUS_ONPROGRESS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

	}

	/**
	 * Checks if is committed.
	 *
	 * @return true, if is committed
	 */
	public boolean isCommitted() {
		return committed;
	}

	/**
	 * Checks if is in execution.
	 *
	 * @return true, if is in execution
	 */
	public boolean isInExecution() {
		return inExecution;
	}

	/**
	 * Gets the intended value.
	 *
	 * @return the intended value
	 */
	public Object getIntendedValue() {
		return intendedValue;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Gets the reference property.
	 *
	 * @return the reference property
	 */
	public Property getReferenceProperty() {
		return referenceProperty;
	}



	/**
	 * Sets the committed.
	 *
	 * @param committed the new committed
	 */
	public void setCommitted(boolean committed) {
		this.committed = committed;
	}

	/**
	 * Sets the in execution.
	 *
	 * @param inExecution the new in execution
	 */
	public void setInExecution(boolean inExecution) {
		this.inExecution = inExecution;
	}

	/**
	 * Sets the intended value.
	 *
	 * @param intendedValue the new intended value
	 */
	public void setIntendedValue(Object intendedValue) {
		this.intendedValue = intendedValue;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Sets the reference property.
	 *
	 * @param referenceProperty the new reference property
	 */
	public void setReferenceProperty(Property referenceProperty) {
		this.referenceProperty = referenceProperty;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return id; 
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Goal getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(Goal parent) {
		this.parent = parent;
	}

	/**
	 * Gets the subgoals.
	 *
	 * @return the subgoals
	 */
	public Map<String, Goal> getSubgoals() {
		return subgoals;
	}

	/**
	 * Sets the subgoals.
	 *
	 * @param subgoals the subgoals
	 */
	public void setSubgoals(Map<String, Goal> subgoals) {
		this.subgoals = subgoals;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param status the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}