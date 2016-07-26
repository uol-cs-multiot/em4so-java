/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agents;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.almende.eve.protocol.jsonrpc.annotation.Access;
import com.almende.eve.protocol.jsonrpc.annotation.AccessType;


// TODO: Auto-generated Javadoc
/**
 * The Class SensorAgent.
 */
public class SensorAgent extends BaseAgent{
	
	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(SensorAgent.class.getSimpleName());
	
	/** The reading. */
	private String reading;
	
	/** The delay. */
	private int delay;
	
	/**
	 * Instantiates a new sensor agent.
	 */
	public SensorAgent(){
		super();
//		this.reading = reading;
//		this.delay =  delay;
	}
	
	/* (non-Javadoc)
	 * @see org.mp.em4so.agents.BaseAgent#run()
	 */
	@Override
	public synchronized void run() {

		try {
			int movement = (int)(Math.random()*1000+1);
			//Use of synchronized set method to update reading
			setReading(""+movement);
			LOG.trace("Value read: "+ reading);
			getScheduler().schedule(Thread.currentThread().getStackTrace()[1].getMethodName(), 5000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
	}
	

	/* (non-Javadoc)
	 * @see org.mp.em4so.agents.BaseAgent#setUp()
	 */
	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gets the reading.
	 *
	 * @return the reading
	 */
	@Access(AccessType.PUBLIC)
	public String getReading() {
		return reading;
	}

	/**
	 * Sets the reading.
	 *
	 * @param reading the new reading
	 */
	public synchronized void setReading(String reading) {
		this.reading = reading;
	}

	
	
}
