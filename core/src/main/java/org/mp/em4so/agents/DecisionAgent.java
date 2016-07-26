/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agents;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.LoggerFactory;

import com.almende.util.jackson.JOM;
import com.fasterxml.jackson.databind.node.ObjectNode;


// TODO: Auto-generated Javadoc
/**
 * The Class DecisionAgent.
 */
public abstract class DecisionAgent extends BaseAgent {
	
	/**
	 * Instantiates a new decision agent.
	 */
	public DecisionAgent(){
		super();
	}
		
	/* (non-Javadoc)
	 * @see org.mp.em4so.agents.BaseAgent#run()
	 */
	@Override
	public abstract void run();// {


	
	
	/**
 * Ask for reading.
 *
 * @param url the url
 * @return the string
 * @throws URISyntaxException the URI syntax exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
public String askForReading(String url)throws URISyntaxException, IOException{
		String reading;
		String method = "getReading";
		URI uri = new URI(url);
		LoggerFactory.getLogger(this.getClass()).debug("Ask for reading");
		reading = callSync(uri, method, null,String.class);
		LoggerFactory.getLogger(this.getClass()).debug("Received: "+reading);
		return reading;
	}
	
	/**
	 * Make decision.
	 *
	 * @param sData the s data
	 * @return the string
	 */
	public String makeDecision(String sData) {
		String result = null;
		if(Integer.parseInt(sData) > 300){
			LoggerFactory.getLogger(this.getClass()).debug("ALT-1");
			result = "ON";
		}else{
			LoggerFactory.getLogger(this.getClass()).debug("ALT-2");
			result = "OFF";
		}
		//loop
		this.getScheduler().schedule(Thread.currentThread().getStackTrace()[1].getMethodName(), 5000);
		LoggerFactory.getLogger(this.getClass()).debug("Final decision is:"+result);
		return result;
			
	}
	
	/**
	 * Request action.
	 *
	 * @param action the action
	 * @throws URISyntaxException the URI syntax exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void requestAction(String action)throws URISyntaxException, IOException{
		String method = "execute";
		ObjectNode params = JOM.createObjectNode();
		String urlStr = "http://localhost:8080/somanager/agents/actingAg";
		URI url = new URI(urlStr);
		params.put("action", action);
		LoggerFactory.getLogger(this.getClass()).debug("Request execution: "+params);
		call(url, method, params);
	}
	

	/**
	 * Gets the sensor agent.
	 *
	 * @return the sensor agent
	 */
	public String getSensorAgent() {
		return "http://localhost:8080/somanager/agents/sensorAg";
	}

}
