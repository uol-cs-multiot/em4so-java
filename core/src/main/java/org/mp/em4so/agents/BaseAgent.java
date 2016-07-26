/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.agents;
import java.io.IOException;
import java.net.URI;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.almende.eve.agent.Agent;
import com.almende.eve.capabilities.handler.SimpleHandler;
import com.almende.eve.scheduling.Scheduler;
import com.almende.eve.scheduling.SchedulerBuilder;
import com.almende.eve.scheduling.SimpleSchedulerConfig;
import com.almende.eve.transport.Receiver;
import com.fasterxml.jackson.databind.node.ObjectNode;



// TODO: Auto-generated Javadoc
/**
 * The Class BaseAgent.
 */
public abstract class BaseAgent extends Agent{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BaseAgent.class.getSimpleName());
	
	/** The scheduler. */
	protected Scheduler scheduler = null;
	
	/** The max runs. */
	protected int maxRuns = 2;
	
	/** The current runs. */
	protected int currentRuns = 0;
	
	/** The run task id. */
	protected String runTaskId;
	
	//protected Logger logger = null;
	/** The initialize. */
	protected boolean initialize = true;
	
	
	/**
	 * Instantiates a new base agent.
	 */
	public BaseAgent(){

		initialize();
//		scheduler.schedule("initialize", 5000);
		LOG.trace("My id is::"+this.getId());
	}
	
	
	/**
	 * Run.
	 */
	public abstract void run();
	
	
	/**
	 * Sets the up.
	 */
	public abstract void setUp();
		
	/**
	 * Initialize.
	 */
	public void initialize(){

		final SimpleSchedulerConfig params = new SimpleSchedulerConfig();
		params.setSenderUrl("local:scheduler");
		
//		if(scheduler == null){
		scheduler = new SchedulerBuilder()
		   .withConfig(params)
		   .withHandle(new SimpleHandler<Receiver>(new Receiver(){
		      @Override
			public void receive(final Object msg, final URI senderUrl,final String tag) {
		    	  run();
		      	  LOG.trace("message received by scheduler:"+msg);	  
		      }
		   }))
		   .build();
//		}  
		
		//each agents trigger its own run method in order to keep control
//		scheduler.schedule(Thread.currentThread().getStackTrace()[1].getMethodName(), DateTime.now());
		if(initialize)continueProcessing();
		LOG.trace("Started. My id is:-:"+this.getId());
	}


	
	/**
	 * Continue processing.
	 */
	protected void continueProcessing(){
//		if(currentRuns < maxRuns-1) {
		runTaskId = scheduler.schedule("run", 5000);
//		}
//		if(currentRuns >= maxRuns ){
//			scheduler.cancel(runTaskId);
//		}else{
		currentRuns += 1;
//		}
	}





	/**
	 * Checks if is initialize.
	 *
	 * @return true, if is initialize
	 */
	public boolean isInitialize() {
		return initialize;
	}


	/* (non-Javadoc)
	 * @see com.almende.eve.agent.Agent#setScheduler(com.almende.eve.scheduling.Scheduler)
	 */
	@Override
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}


	/**
	 * Sets the initialize.
	 *
	 * @param initialize the new initialize
	 */
	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
	}

	/**
	 * Call.
	 *
	 * @param url the url
	 * @param params the params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void call(URI url, ObjectNode params) throws IOException{
		call(url, "execute", params);
	}

	/**
	 * Gets the current runs.
	 *
	 * @return the current runs
	 */
	public int getCurrentRuns(){
		return currentRuns;
	}
	
	/**
	 * Gets the run task id.
	 *
	 * @return the run task id
	 */
	public String getRunTaskId(){
		return runTaskId;
	}


	/**
	 * Gets the max runs.
	 *
	 * @return the max runs
	 */
	public int getMaxRuns() {
		return maxRuns;
	}


	/**
	 * Sets the max runs.
	 *
	 * @param maxRuns the new max runs
	 */
	public void setMaxRuns(int maxRuns) {
		this.maxRuns = maxRuns;
	}


	/* (non-Javadoc)
	 * @see com.almende.eve.agent.Agent#getScheduler()
	 */
	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}


	/**
	 * Sets the current runs.
	 *
	 * @param currentRuns the new current runs
	 */
	public void setCurrentRuns(int currentRuns) {
		this.currentRuns = currentRuns;
	}


	/**
	 * Sets the run task id.
	 *
	 * @param runTaskId the new run task id
	 */
	public void setRunTaskId(String runTaskId) {
		this.runTaskId = runTaskId;
	}

	
	
}
