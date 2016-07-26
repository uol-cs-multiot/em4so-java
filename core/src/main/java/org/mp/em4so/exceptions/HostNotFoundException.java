/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class HostNotFoundException.
 */
public class HostNotFoundException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new host not found exception.
	 *
	 * @param msg the msg
	 */
	public HostNotFoundException(String msg){
		super(msg);
	}
	
	/**
	 * Instantiates a new host not found exception.
	 */
	public HostNotFoundException(){
		super();
	}
}
