/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.actuating;

import org.mp.em4so.model.network.Host;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecutionInstance.
 */
public class ExecutionInstance {
	
	/** The host. */
	private Host host;
	
	/** The last success. */
	private String lastSuccess;
	
	/** The ranking. */
	private String ranking;
	
	/** The local. */
	private String local;
	
	/** The capability. */
	private String capability;
	
	/**
	 * Instantiates a new execution instance.
	 *
	 * @param host the host
	 */
	public ExecutionInstance(Host host){
		this.host = host;
	}
	
	/**
	 * Instantiates a new execution instance.
	 */
	public ExecutionInstance(){
		
	}
	
	
	/**
	 * Gets the last success.
	 *
	 * @return the last success
	 */
	public String getLastSuccess() {
		return lastSuccess;
	}
	
	
	/**
	 * Sets the last success.
	 *
	 * @param lastSuccess the new last success
	 */
	public void setLastSuccess(String lastSuccess) {
		this.lastSuccess = lastSuccess;
	}

	/**
	 * Gets the ranking.
	 *
	 * @return the ranking
	 */
	public String getRanking() {
		return ranking;
	}
	
	/**
	 * Sets the ranking.
	 *
	 * @param ranking the new ranking
	 */
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	
	/**
	 * Gets the local.
	 *
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}
	
	/**
	 * Sets the local.
	 *
	 * @param local the new local
	 */
	public void setLocal(String local) {
		this.local = local;
	}
	
	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}
	
	/**
	 * Sets the host.
	 *
	 * @param host the new host
	 */
	public void setHost(Host host) {
		this.host = host;
	}
	
	/**
	 * Gets the capability.
	 *
	 * @return the capability
	 */
	public String getCapability() {
		return capability;
	}
	
	/**
	 * Sets the capability.
	 *
	 * @param capability the new capability
	 */
	public void setCapability(String capability) {
		this.capability = capability;
	}
	
}
