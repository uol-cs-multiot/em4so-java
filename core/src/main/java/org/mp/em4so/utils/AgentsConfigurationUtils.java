/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentsConfigurationUtils.
 */
public interface AgentsConfigurationUtils {
	
	/** The state class. */
	public static  String STATE_CLASS = "com.almende.eve.state.memory.MemoryStateBuilder";
	
	/** The scheduler class. */
	public static String SCHEDULER_CLASS = "com.almende.eve.scheduling.SimpleSchedulerBuilder";
	
	/** The transport class. */
	public static String TRANSPORT_CLASS = "com.almende.eve.transport.http.HttpTransportBuilder";
	
	/** The servlet class. */
	public static String SERVLET_CLASS = "com.almende.eve.transport.http.traceServlet";
	
	/** The do shortcut. */
	public static String DO_SHORTCUT = "true";
	
	/** The do authentication. */
	public static String DO_AUTHENTICATION = "false";
	
	/** The SO manager class. */
	public static String SOManagerClass = "org.mp.em4so.agents.SOControlAgent"; 
	
	/** The class label. */
	public static String CLASS_LABEL = "class";
	
	/** The servlet class label. */
	public static String SERVLET_CLASS_LABEL = "servletClass";
	
	/** The servlet url label. */
	public static String SERVLET_URL_LABEL = "servletUrl";
	
	/** The do shorcut label. */
	public static String DO_SHORCUT_LABEL = "doShortcut";
	
	/** The do authentication label. */
	public static String DO_AUTHENTICATION_LABEL = "doShortcut";
	
	/** The id label. */
	public static String ID_LABEL = "id";
	
	/** The extends label. */
	public static String EXTENDS_LABEL = "extends";
	
		
}
