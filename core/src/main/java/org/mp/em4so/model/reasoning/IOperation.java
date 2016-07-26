/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;

// TODO: Auto-generated Javadoc
/**
 * The Interface IOperation.
 *
 * @param <T> the generic type
 */
public interface IOperation<T> {
	
	/**
	 * Calculate.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the t
	 */
	public T calculate(Object x, Object y);

}
