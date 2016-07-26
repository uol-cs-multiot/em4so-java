/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.sensing;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * Each one property of the environment that the Smart Object is able to sense.
 *
 * @author me
 * @version 1.0
 * @updated 17-Apr-2015 20:13:55
 */
public class Property {

	/** The id. */
	private String id;
	
	/** The name. */
	private String name;
	
	/** The scope. */
	private String scope;
	
	/** The units. */
	private String units;
	
	/** The custom units. */
	private List<String> customUnits;

	/**
	 * Instantiates a new property.
	 */
	public Property(){
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {

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
	 * Gets the units.
	 *
	 * @return the units
	 */
	public String getUnits() {
		return units;
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
	 * Sets the units.
	 *
	 * @param units the new units
	 */
	public void setUnits(String units) {
		this.units = units;
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
	 * Gets the custom.
	 *
	 * @return the custom
	 */
	public List<String> getCustom() {
		return customUnits;
	}

	/**
	 * Sets the custom units.
	 *
	 * @param custom the new custom units
	 */
	public void setCustomUnits(List<String> custom) {
		this.customUnits = custom;
	}
	

	/**
	 * Gets the scope.
	 *
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Sets the scope.
	 *
	 * @param scope the new scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "property: [scope: "+scope + ", "+name+"]";
	}

}