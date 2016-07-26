/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.sensing;

import java.util.List;

import org.mp.em4so.model.common.Location;

// TODO: Auto-generated Javadoc
/**
 * The Interface FeatureOfInterest.
 */
public interface FeatureOfInterest {

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation();
	
	/**
	 * Gets the observable properties.
	 *
	 * @return the observable properties
	 */
	public List<Property> getObservableProperties();
}
