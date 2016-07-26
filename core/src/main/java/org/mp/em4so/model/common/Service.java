/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.mp.em4so.model.actuating.ExecutionInstance;


// TODO: Auto-generated Javadoc
/**
 * The Class Service.
 */
public class Service {
	
	/** The categories. */
	private Set<String> categories;
	
	/** The kind. */
	private String kind;
	
	/** The result. */
	private String result;
	
	/** The id. */
	private String id;
	
	/** The executing role id. */
	private String executingRoleId;
	
	/** The execution instance. */
	private ExecutionInstance executionInstance;
	
	/** The potential ex ins. */
	private Map<String,ExecutionInstance> potentialExIns;
	
	/** The property. */
	private List<String> property;
	
	/** The status key. */
	private String statusKey;
	
	/**
	 * Instantiates a new service.
	 */
	//	private Service service;
	public Service(){
		
	}
	
	/**
	 * Instantiates a new service.
	 *
	 * @param name the name
	 * @param ei the ei
	 */
	public Service( String name, ExecutionInstance ei){
		this.executionInstance = ei;
		this.name = (name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toLowerCase()));
		this.id = "service/"+this.name;
		
	}
	
	/** The arg types. */
	private TreeMap<String,String> argTypes;
	
	/** id of the corresponding service. */
	private String name;
	
	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public Set<String> getCategories() {
		return categories;
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
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(Set<String> categories) {
		this.categories = categories;
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
	 * Gets the execution instance.
	 *
	 * @return the execution instance
	 */
	public ExecutionInstance getExecutionInstance() {
		return executionInstance;
	}
	
	/**
	 * Sets the execution instance.
	 *
	 * @param executionInstance the new execution instance
	 */
	public void setExecutionInstance(ExecutionInstance executionInstance) {
		this.executionInstance = executionInstance;
	}

	/**
	 * Gets the arg types.
	 *
	 * @return the arg types
	 */
	public TreeMap<String, String> getArgTypes() {
		return argTypes;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the arg types.
	 *
	 * @param argTypes the arg types
	 */
	public void setArgTypes(TreeMap<String, String> argTypes) {
		this.argTypes = argTypes;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the kind.
	 *
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind the new kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
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
	 * Gets the property.
	 *
	 * @return the property
	 */
	public List<String> getProperty() {
		return property;
	}
	
	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty(List<String> property) {
		this.property = property;
	}
	
	/**
	 * Gets the potential ex ins.
	 *
	 * @return the potential ex ins
	 */
	public Map<String,ExecutionInstance> getPotentialExIns() {
		return potentialExIns;
	}
	
	/**
	 * Sets the potential ex ins.
	 *
	 * @param potentialExIns the potential ex ins
	 */
	public void setPotentialExIns(Map<String,ExecutionInstance> potentialExIns) {
		this.potentialExIns = potentialExIns;
	}
	
	/**
	 * Gets the executing role id.
	 *
	 * @return the executing role id
	 */
	public String getExecutingRoleId() {
		return executingRoleId;
	}
	
	/**
	 * Sets the executing role id.
	 *
	 * @param executingRoleId the new executing role id
	 */
	public void setExecutingRoleId(String executingRoleId) {
		this.executingRoleId = executingRoleId;
	}
	
	/**
	 * Gets the status key.
	 *
	 * @return the status key
	 */
	public String getStatusKey() {
		return statusKey;
	}
	
	/**
	 * Sets the status key.
	 *
	 * @param statusKey the new status key
	 */
	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}
}
