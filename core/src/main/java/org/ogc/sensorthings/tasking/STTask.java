package org.ogc.sensorthings.tasking;


import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class STTask.
 */
public class STTask {

/** The input values. */
private Map<STParameter, ?> inputValues;

/** The status. */
private STStatus status;

/**
 * Gets the input values.
 *
 * @return the input values
 */
public Map<STParameter, ?> getInputValues() {
	return inputValues;
}

/**
 * Gets the status.
 *
 * @return the status
 */
public STStatus getStatus() {
	return status;
}

/**
 * Sets the input values.
 *
 * @param inputValues the input values
 */
public void setInputValues(Map<STParameter, ?> inputValues) {
	this.inputValues = inputValues;
}

/**
 * Sets the status.
 *
 * @param status the new status
 */
public void setStatus(STStatus status) {
	this.status = status;
}

}
