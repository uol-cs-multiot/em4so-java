package org.ogc.sensorthings.tasking;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class STDefinition.
 */
public class STDefinition {

/** The unit of measurement. */
private String unitOfMeasurement;

/** The allowed values. */
private List<String> allowedValues;

/** The input type. */
private STInputType inputType;

/**
 * Gets the unit of measurement.
 *
 * @return the unit of measurement
 */
public String getUnitOfMeasurement() {
	return unitOfMeasurement;
}

/**
 * Gets the allowed values.
 *
 * @return the allowed values
 */
public List<String> getAllowedValues() {
	return allowedValues;
}

/**
 * Gets the input type.
 *
 * @return the input type
 */
public STInputType getInputType() {
	return inputType;
}

/**
 * Sets the unit of measurement.
 *
 * @param unitOfMeasurement the new unit of measurement
 */
public void setUnitOfMeasurement(String unitOfMeasurement) {
	this.unitOfMeasurement = unitOfMeasurement;
}

/**
 * Sets the allowed values.
 *
 * @param allowedValues the new allowed values
 */
public void setAllowedValues(List<String> allowedValues) {
	this.allowedValues = allowedValues;
}

/**
 * Sets the input type.
 *
 * @param inputType the new input type
 */
public void setInputType(STInputType inputType) {
	this.inputType = inputType;
}
}
