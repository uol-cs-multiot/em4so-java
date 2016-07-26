/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;

import org.mp.em4so.model.sensing.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class Condition.
 */
public class Condition {
	
	/** The property. */
	private Property property;
	
	/** The comparator. */
	private String comparator;
	
	/** The reference. */
	private String reference;
	
	/**
	 * Gets the property.
	 *
	 * @return the property
	 */
	public Property getProperty() {
		return property;
	}
	
	/**
	 * Gets the comparator.
	 *
	 * @return the comparator
	 */
	public String getComparator() {
		return comparator;
	}
	
	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	
	/**
	 * Sets the property.
	 *
	 * @param property the new property
	 */
	public void setProperty(Property property) {
		this.property = property;
	}
	
	/**
	 * Sets the comparator.
	 *
	 * @param comparator the new comparator
	 */
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Sets the reference.
	 *
	 * @param reference the new reference
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

}
