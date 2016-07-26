/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.network;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.mp.em4so.model.agent.Role;

// TODO: Auto-generated Javadoc
/**
 * The Class Host.
 */
public class Host {

/** The id. */
private String id;

/** The name. */
private String name;
 
 /** The urls. */
 private List<String> urls;
 
 /** The categories. */
 private Set<String> categories;
 
 /** The roles. */
 private List<Role> roles ;
 
 /** The ranking. */
 private Double ranking;

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
 * Gets the name.
 *
 * @return the name
 */
public String getName() {
	return name;
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
 * Gets the urls.
 *
 * @return the urls
 */
public List<String> getUrls() {
	return urls;
}

/**
 * Sets the urls.
 *
 * @param urls the new urls
 */
public void setUrls(List<String> urls) {
	this.urls = urls;
}

/**
 * Gets the categories.
 *
 * @return the categories
 */
public Set<String> getCategories() {
	return categories;
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
 * Instantiates a new host.
 */
public Host(){
	this.urls = new LinkedList<String>();
}

/**
 * Gets the roles.
 *
 * @return the roles
 */
public List<Role> getRoles() {
	return roles;
}

/**
 * Sets the roles.
 *
 * @param roles the new roles
 */
public void setRoles(List<Role> roles) {
	this.roles = roles;
}

/**
 * Gets the ranking.
 *
 * @return the ranking
 */
public Double getRanking() {
	return ranking;
}

/**
 * Sets the ranking.
 *
 * @param ranking the new ranking
 */
public void setRanking(Double ranking) {
	this.ranking = ranking;
}
 
}
