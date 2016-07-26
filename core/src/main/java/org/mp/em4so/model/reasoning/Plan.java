/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;


/**
 * Contains the actions to perform for achieving a goal
 * @author me
 * @version 1.0
 * @created 20-Nov-2014 11:10:34 AM
 */
import java.util.LinkedList;
import java.util.List;

import org.mp.em4so.model.actuating.Task;
import org.mp.em4so.model.agent.Goal;


// TODO: Auto-generated Javadoc
/**
 * The Class Plan.
 */
public class Plan {
	
	/** The tasks. */
	private List <Task>tasks;
	
	/** The goal. */
	private Goal goal;
	
	/** The times completed. */
	private long timesCompleted;
	
	/**
	 * Instantiates a new plan.
	 */
	public Plan(){
		tasks = new LinkedList<Task>();
	}
	
	/**
	 * Instantiates a new plan.
	 *
	 * @param goal the goal
	 * @param tasks the tasks
	 */
	public Plan(Goal goal,List<Task> tasks){
		this.goal = goal;
		this.tasks = tasks;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String plan = "Plan: actions {";
		
		for(Task t: tasks){
			plan += t.toString()+", ";
		}
		plan = plan.substring(0, plan.length()-2) + "}";
		
		return plan;
	}
	
	/**
	 * Gets the tasks.
	 *
	 * @return the tasks
	 */
	public List<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Gets the goal.
	 *
	 * @return the goal
	 */
	public Goal getGoal() {
		return goal;
	}
	
	/**
	 * Sets the tasks.
	 *
	 * @param tasks the new tasks
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * Sets the goal.
	 *
	 * @param goal the new goal
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	
	/**
	 * Gets the times completed.
	 *
	 * @return the times completed
	 */
	public long getTimesCompleted() {
		return timesCompleted;
	}
	
	/**
	 * Sets the times completed.
	 *
	 * @param timesCompleted the new times completed
	 */
	public void setTimesCompleted(long timesCompleted) {
		this.timesCompleted = timesCompleted;
	}
	
	
}