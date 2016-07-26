/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.network;

// TODO: Auto-generated Javadoc
/**
 * The Class TransportConfig.
 */
public class TransportConfig {
	
	/** The protocol. */
	private String protocol;
	
	/** The server. */
	private String server;
	
	/** The port. */
	private String port;
	
	/** The agent name. */
	private String agentName;
	
	/** The root context. */
	private String rootContext; 
	
	/** The jmx port. */
	private String jmxPort;

	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	
	/**
	 * Sets the protocol.
	 *
	 * @param protocol the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress(){
		if(rootContext==null || rootContext.equals(""))
			return protocol+"://"+server+":"+port+"/"+agentName+"/";
		else
			return protocol+"://"+server+":"+port+"/"+rootContext+"/";
	}
	
	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public String getServer() {
		return server;
	}
	
	/**
	 * Sets the server.
	 *
	 * @param server the new server
	 */
	public void setServer(String server) {
		this.server = server;
	}
	
	/**
	 * Gets the agent name.
	 *
	 * @return the agent name
	 */
	public String getAgentName() {
		return agentName;
	}
	
	/**
	 * Sets the agent name.
	 *
	 * @param agentName the new agent name
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	/**
	 * Gets the root context.
	 *
	 * @return the root context
	 */
	public String getRootContext() {
		return rootContext;
	}
	
	/**
	 * Sets the root context.
	 *
	 * @param rootContext the new root context
	 */
	public void setRootContext(String rootContext) {
		this.rootContext = rootContext;
	}
	
	/**
	 * Gets the jmx port.
	 *
	 * @return the jmx port
	 */
	public String getJmxPort() {
		return jmxPort;
	}
	
	/**
	 * Sets the jmx port.
	 *
	 * @param jmxPort the new jmx port
	 */
	public void setJmxPort(String jmxPort) {
		this.jmxPort = jmxPort;
	}

}
