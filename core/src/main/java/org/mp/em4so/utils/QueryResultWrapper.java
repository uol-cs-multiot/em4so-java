/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryResultWrapper.
 *
 * @param <V> the value type
 */
public abstract class QueryResultWrapper<V>{
	
	/** The total rows. */
	protected double total_rows;
	
	/** The offset. */
	protected int offset;
	
	/**
	 * Gets the total rows.
	 *
	 * @return the total rows
	 */
	public double getTotal_rows() {
		return total_rows;
	}
	
	/**
	 * Gets the offset.
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Sets the total rows.
	 *
	 * @param total_rows the new total rows
	 */
	public void setTotal_rows(double total_rows) {
		this.total_rows = total_rows;
	}
	
	/**
	 * Sets the offset.
	 *
	 * @param offset the new offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	

	
	
	
}
