
package org.ogc.sensorthings.sensing;

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * The Class STObservedProperty.
 */
public class STObservedProperty {

/** The urn. */
private URI urn;

/** The unit of measurement. */
private String unitOfMeasurement;

/**
 * Gets the urn.
 *
 * @return the urn
 */
public URI getUrn() {
	return urn;
}

/**
 * Gets the unit of measurement.
 *
 * @return the unit of measurement
 */
public String getUnitOfMeasurement() {
	return unitOfMeasurement;
}

/**
 * Sets the urn.
 *
 * @param urn the new urn
 */
public void setUrn(URI urn) {
	this.urn = urn;
}

/**
 * Sets the unit of measurement.
 *
 * @param unitOfMeasurement the new unit of measurement
 */
public void setUnitOfMeasurement(String unitOfMeasurement) {
	this.unitOfMeasurement = unitOfMeasurement;
}
}
