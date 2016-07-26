
package org.ogc.sensorthings.sensing;

import java.util.List;

import org.mp.em4so.model.sensing.Observation;

// TODO: Auto-generated Javadoc
/**
 * The Class STDataStream.
 */
public class STDataStream {
 
 /** The description. */
 private String description;
 
 /** The observations. */
 private List<Observation> observations;
 
 /** The observed property. */
 private STObservedProperty observedProperty;

/**
 * Gets the description.
 *
 * @return the description
 */
public String getDescription() {
	return description;
}

/**
 * Gets the observations.
 *
 * @return the observations
 */
public List<Observation> getObservations() {
	return observations;
}

/**
 * Gets the observed property.
 *
 * @return the observed property
 */
public STObservedProperty getObservedProperty() {
	return observedProperty;
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
 * Sets the observations.
 *
 * @param observations the new observations
 */
public void setObservations(List<Observation> observations) {
	this.observations = observations;
}

/**
 * Sets the observed property.
 *
 * @param observedProperty the new observed property
 */
public void setObservedProperty(STObservedProperty observedProperty) {
	this.observedProperty = observedProperty;
}
}
