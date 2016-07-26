/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agents;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.agent.Agent;
import com.almende.eve.protocol.jsonrpc.annotation.Access;
import com.almende.eve.protocol.jsonrpc.annotation.AccessType;
import com.almende.eve.protocol.jsonrpc.annotation.Name;


// TODO: Auto-generated Javadoc
/**
 * The Class ActingAgent.
 */
public class ActingAgent extends Agent {
	
	/** The logger. */
	private Logger logger = null;
	
	/**
	 * Instantiates a new acting agent.
	 */
	public ActingAgent(){
		
	}
	
	/**
	 * Executes the.
	 *
	 * @param action the action
	 */
	@Access(AccessType.PUBLIC)
	public void execute(@Name("action")String action){
		logger = LoggerFactory.getLogger(this.getClass());
		if(action!=null ){
			if(action.equals("ON")){
				logger.info("LED is ON");
			}else{
				logger.info("LED is OFF");
			}
		}else{
			logger.error("Action is null");
		}
	}


}
