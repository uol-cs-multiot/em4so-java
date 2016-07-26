/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.agent;

import java.util.List;
import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.reasoning.Function;

// TODO: Auto-generated Javadoc
/**
 * The Class Role.
 */
public class Role {
	
	/** The id. */
	private String id;
	
	/** The function. */
	private Function function;
	
	/** The goals. */
	private List<Goal> goals;
	
	/** The activity. */
	private List<Activity> activity;

	
	/**
	 * Instantiates a new role.
	 */
	public Role(){
		
	}
	
	/**
	 * Instantiates a new role.
	 *
	 * @param id the id
	 */
	public Role(String id){
		this.id = id;
	}
	
	/**
	 * Gets the goals.
	 *
	 * @return the goals
	 */
	public List<Goal> getGoals() {
		return goals;
	}
	
	
	/**
	 * Sets the goals.
	 *
	 * @param goals the new goals
	 */
	public void setGoals(List<Goal> goals) {
		this.goals = goals;
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
	 * Gets the activity.
	 *
	 * @return the activity
	 */
	public List<Activity> getActivity() {
		return activity;
	}

	/**
	 * Sets the activity.
	 *
	 * @param activity the new activity
	 */
	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}


	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	public Function getFunction() {
		return function;
	}


	/**
	 * Sets the function.
	 *
	 * @param function the new function
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	

	

}
