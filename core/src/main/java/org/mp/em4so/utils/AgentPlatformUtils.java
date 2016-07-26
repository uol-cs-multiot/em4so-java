/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import com.almende.util.jackson.JOM;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.almende.eve.capabilities.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentPlatformUtils.
 */
public class AgentPlatformUtils {

	/**
	 * Gets the agent configuration.
	 *
	 * @param servletUrl the servlet url
	 * @return the agent configuration
	 */
	public static Config getAgentConfiguration(String servletUrl){
		
		ObjectNode defaultAgent = JOM.createObjectNode();
		
		ObjectNode defaultState = JOM.createObjectNode();
		
		defaultState.put(AgentsConfigurationUtils.CLASS_LABEL, AgentsConfigurationUtils.STATE_CLASS);
				
		ObjectNode defaultTransport = JOM.createObjectNode();
		
		defaultTransport.put(AgentsConfigurationUtils.CLASS_LABEL, AgentsConfigurationUtils.TRANSPORT_CLASS);
		defaultTransport.put(AgentsConfigurationUtils.SERVLET_CLASS_LABEL, AgentsConfigurationUtils.SERVLET_CLASS);
		defaultTransport.put(AgentsConfigurationUtils.SERVLET_URL_LABEL, servletUrl);
		defaultTransport.put(AgentsConfigurationUtils.DO_SHORCUT_LABEL, AgentsConfigurationUtils.DO_SHORTCUT);
		defaultTransport.put(AgentsConfigurationUtils.DO_AUTHENTICATION_LABEL, AgentsConfigurationUtils.DO_AUTHENTICATION);
		
		ObjectNode defaultScheduler = JOM.createObjectNode();
		
		defaultScheduler.put(AgentsConfigurationUtils.CLASS_LABEL, AgentsConfigurationUtils.SCHEDULER_CLASS);
		
			
		defaultAgent.set("state",defaultState);
		defaultAgent.set("transport",defaultTransport);
		defaultAgent.set("scheduler",defaultScheduler);
		
		defaultAgent.put("id",SOMFileConfigUtils.getName()+"_agent");
		defaultAgent.put("class",AgentsConfigurationUtils.SOManagerClass);
		
		ObjectNode config = JOM.createObjectNode();
		config.set("agents", JOM.createArrayNode().add(defaultAgent));
		
		return new Config(config);
		
		
		
	}
	
}
