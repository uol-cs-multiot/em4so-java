
package org.ogc.sensorthings.sensing;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class STObservation.
 */
public class STObservation {
	
	/** The date. */
	private Date date;
	
	/** The value. */
	private String value;
	
	/** By default "measure". */
	private STResultType resultType;
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		
		return value;
	}
	
	/**
	 * Gets the result type.
	 *
	 * @return the result type
	 */
	public STResultType getResultType() {
		return resultType;
	}
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Sets the result type.
	 *
	 * @param resultType the new result type
	 */
	public void setResultType(STResultType resultType) {
		this.resultType = resultType;
	}
	

}
