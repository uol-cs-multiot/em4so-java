/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Scenario.
 */
public class Scenario {
	
	/** The id. */
	private String id;
	
	/** The description. */
	private String description;
	
	/** The steps. */
	private List<Step> steps;
	
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
	 * Gets the steps.
	 *
	 * @return the steps
	 */
	public List<Step> getSteps() {
		return steps;
	}
	
	/**
	 * Sets the steps.
	 *
	 * @param steps the new steps
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	
}
