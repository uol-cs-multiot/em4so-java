/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agents;

import java.net.URI;
import java.util.Hashtable;

import org.mp.em4so.agentManager.SmartObjectAgManager;
import org.mp.em4so.control.MessageProcessor;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.network.protocol.SODiscoverer;
import org.mp.em4so.network.protocol.SOMessageSender;
import org.mp.em4so.services.ServiceAssembler;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.protocol.jsonrpc.annotation.Access;
import com.almende.eve.protocol.jsonrpc.annotation.AccessType;
import com.almende.eve.protocol.jsonrpc.annotation.Name;
import com.almende.eve.protocol.jsonrpc.annotation.Sender;
import com.almende.eve.protocol.jsonrpc.formats.Params;
import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO: Auto-generated Javadoc
/**
 * The Class SOControlAgent.
 */
public class SOControlAgent extends DecisionAgent{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SOControlAgent.class
			.getSimpleName());
	
	/** The som. */
	private SmartObjectAgManager som;
	
	/**
	 * Instantiates a new SO control agent.
	 */
	public SOControlAgent() {
		som = new SmartObjectAgManager(this.getId());
		ServiceAssembler.setSOControlAgent(this);
		ServiceDiscoverer.setSOControlAgent(this);
		SODiscoverer.setSOControlAgent(this);
		SOMessageSender.setSOControlAgent(this);
	}
	
	/* (non-Javadoc)
	 * @see org.mp.em4so.agents.DecisionAgent#run()
	 */
	@Override
	public void run() {
		LOG.info("==========================="+getId()+": Start of Run "+getCurrentRuns() + "===================== Task Id:"+getRunTaskId());
		
		if(isInitialize()) 
			setUp();
		som.run();
		LOG.trace("==========================="+getId()+": End of Run "+getCurrentRuns() + "=====================");
		
	}
	
	/* (non-Javadoc)
	 * @see org.mp.em4so.agents.BaseAgent#setUp()
	 */
	@Access(AccessType.PUBLIC)
	@Override
	public void setUp() {
		try{
		som.setUp(this);
		setInitialize(false);
		}catch(InterruptedException e){
			LOG.error(" Error Interrupted Exception in: "+e.getMessage());
		}
		
	}
	
	/**
	 * Make call.
	 *
	 * @param uri the uri
	 * @param args the args
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean makeCall(final URI uri, final ObjectNode args)throws Exception{
		Params params = new Params();
		params.set("params", args);
		boolean successCall = false;
			LOG.trace("{}: Making call to {} with params: {}",getId(), uri.toString(),params);
			

			if(NetworkUtils.isHostAvailable(uri.getHost(), uri.getPort(), 2000)){
				call(uri, "processMessage", params);
				successCall = true;
			}
			LOG.trace("callSuccess to return is {}",successCall);
			return successCall;

	}
	
	/**
	 * Do failure.
	 *
	 * @param args the args
	 * @param senderUrl the sender url
	 */
	public void doFailure(ObjectNode args, String senderUrl){
		MessageProcessor.processFailure(som,args, senderUrl);
	}
	
	/**
	 * Proxy to schedule execution .
	 *
	 * @param <T> the generic type
	 * @param method the method
	 * @param service the service
	 * @param argValues the arg values
	 * @param resultTo the result to
	 * @param delay the delay
	 * @return the string
	 * @throws Exception the exception
	 */
	public <T> String scheduleServiceExecution(String method, Service service, Hashtable<String,?> argValues,String resultTo, int delay) throws Exception {
		Params params = new Params();
		params.add("service", service);
		params.add("argValues", argValues);
		params.add("resultTo", resultTo);
		LOG.trace("before task creation:"+params.toString());
		String id = schedule(method,params,delay);
		return id;
	}	
	
	/**
	 * Carries out processing of the message.
	 *
	 * @param params the params
	 * @param senderUrl the sender url
	 * @throws Exception the exception
	 */
	@Access(AccessType.PUBLIC)
	public void processMessage(@Name("params")ObjectNode params, @Sender String senderUrl)throws Exception{
		if(som.getId()!=null) // ensure message are processed iff the initialisation has been done.
		MessageProcessor.processMessage(som, params, senderUrl);
	}
	
	/**
	 * Executes the service.
	 *
	 * @param service the service
	 * @param argValues the arg values
	 * @param resultTo the result to
	 * @throws Exception the exception
	 */
	public void executeService(@Name("service") Service service,@Name("argValues")Hashtable<String,?> argValues,@Name("resultTo")String resultTo)throws Exception{
		ServiceAssembler.executeService(service,argValues,resultTo);
	}
	
	/**
	 * Query hit.
	 *
	 * @param service the service
	 */
	public void queryHit(@Name("service")Service service){
		
		ServiceDiscoverer.queryHit(service);
	}

	/**
	 * Gets the som.
	 *
	 * @return the som
	 */
	public SmartObjectAgManager getSom() {
		return som;
	}

	/**
	 * Sets the som.
	 *
	 * @param som the new som
	 */
	public void setSom(SmartObjectAgManager som) {
		this.som = som;
	}
}
