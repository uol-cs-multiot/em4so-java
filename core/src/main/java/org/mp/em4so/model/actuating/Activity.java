/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.actuating;

import java.util.HashSet;
import java.util.List;

import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.reasoning.Function;

// TODO: Auto-generated Javadoc
/**
 * To express in a general way the responsibilities of the role.
 *
 */
public class Activity {

/** The id. */
private String id;

/** The seq. */
private String seq;

/** The description. */
private String description;

/** The input knowledge. */
private List<Element> inputKnowledge;

/** The output. */
private List<Element> output;

/** The actions. */
private List <Action> actions;

/** The status. */
private String status;

/** The input. */
private Function input;

/** The categories. */
private HashSet<String> categories;

/** The role. */
private Role role;

/** The previous. */
private Activity previous;


/**
 * Instantiates a new activity.
 *
 * @param id the id
 */
public Activity(String id){
	this.id = id;
}

/**
 * Instantiates a new activity.
 */
public Activity(){
	status = "pending";
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
 * Gets the description.
 *
 * @return the description
 */
public String getDescription() {
	return description;
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
 * Sets the description.
 *
 * @param description the new description
 */
public void setDescription(String description) {
	this.description = description;
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
 * Gets the actions.
 *
 * @return the actions
 */
public List<Action> getActions() {
	return actions;
}

/**
 * Sets the actions.
 *
 * @param actions the new actions
 */
public void setActions(List<Action> actions) {
	this.actions = actions;
}

/**
 * Gets the seq.
 *
 * @return the seq
 */
public String getSeq() {
	return seq;
}

/**
 * Sets the seq.
 *
 * @param seq the new seq
 */
public void setSeq(String seq) {
	this.seq = seq;
}

/**
 * Gets the role.
 *
 * @return the role
 */
public Role getRole() {
	return role;
}

/**
 * Sets the role.
 *
 * @param role the new role
 */
public void setRole(Role role) {
	this.role = role;
}

/**
 * Gets the previous.
 *
 * @return the previous
 */
public Activity getPrevious() {
	return previous;
}

/**
 * Sets the previous.
 *
 * @param previous the new previous
 */
public void setPrevious(Activity previous) {
	this.previous = previous;
}

/**
 * Gets the input knowledge.
 *
 * @return the input knowledge
 */
public List<Element> getInputKnowledge() {
	return inputKnowledge;
}

/**
 * Sets the input knowledge.
 *
 * @param inputKnowledge the new input knowledge
 */
public void setInputKnowledge(List<Element> inputKnowledge) {
	this.inputKnowledge = inputKnowledge;
}

/**
 * Gets the output.
 *
 * @return the output
 */
public List<Element> getOutput() {
	return output;
}

/**
 * Sets the output.
 *
 * @param output the new output
 */
public void setOutput(List<Element> output) {
	this.output = output;
}

/**
 * Gets the input.
 *
 * @return the input
 */
public Function getInput() {
	return input;
}

/**
 * Sets the input.
 *
 * @param input the new input
 */
public void setInput(Function input) {
	this.input = input;
}

}
