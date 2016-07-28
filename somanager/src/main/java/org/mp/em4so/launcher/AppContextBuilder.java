/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.launcher;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.mp.em4so.model.network.TransportConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.almende.eve.transport.http.traceServlet;


// TODO: Auto-generated Javadoc
//import com.almende.eve.transport.http.traceServlet;


/**
 * The Class AppContextBuilder.
 */
public class AppContextBuilder {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AppContextBuilder.class.getSimpleName());
	
	/**
 * Builds the servlet context handler.
 *
 * @param transportConfig the transport config
 * @return the servlet context handler
 */
public ServletContextHandler buildServletContextHandler(TransportConfig transportConfig){
		ServletContextHandler context = null;
		ServletHolder servletHolder = null;
		try {
		context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        
		
		
		
//        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        
        context.setContextPath("/"+transportConfig.getRootContext());
        Map<String,String> initParams = new TreeMap<String,String>();
        Map<String,String> initServletParams = new TreeMap<String,String>();
        
        context.setInitParams(initParams);
        
        initParams.put("eve_authentication","false" );
        initServletParams.put("ServletUrl",transportConfig.getAddress());
        
        
        // Add servlet
        context.setInitParams(initParams);
//			context.addServlet(new ServletHolder(new traceServlet(new URI("http://"+myAddress+":"+SOMFileConfigUtils.getServerPort()+"/"+SOMFileConfigUtils.getName()+"/agents/"))), "/agents/*");
        
        
        servletHolder = new ServletHolder();
        servletHolder.setInitParameters(initServletParams);
		servletHolder.setServlet(new traceServlet());
        
//        context.addServlet(servletHolder, "/"+transportConfig.getRootContext()+"/*");
		context.addServlet(servletHolder, "/*");
       
        LOG.trace("the context path is: -->"+context.getContextPath());
       
        
        LOG.trace("LA direcccion de aqui es:"+transportConfig.getAddress());
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return context;
	}
	
	/**
	 * Builds the M bean container.
	 *
	 * @param myAddress the my address
	 * @param jmxPort the jmx port
	 */
	public void buildMBeanContainer(String myAddress, int jmxPort){
		MBeanServer mbs = null;
		
		try {
//		Registry           reg  = LocateRegistry.createRegistry(jmxPort);
        JMXConnectorServer cs;
			mbs = ManagementFactory.getPlatformMBeanServer();
			cs = JMXConnectorServerFactory.newJMXConnectorServer(
                new JMXServiceURL(
                    "service:jmx:rmi://" + myAddress + ":" + jmxPort + "/" +
                    "jndi/rmi://" + myAddress + ":" + jmxPort + "/" +
                    "jmxrmi"
                ),
                initProperties(myAddress, jmxPort),
                mbs
            );
			cs.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        return new MBeanContainer(mbs);
	}
    
	/**
	 * Inits the properties.
	 *
	 * @param myAddress the my address
	 * @param jmxPort the jmx port
	 * @return the map
	 */
	private static Map<String,String>   initProperties(final String myAddress, final int jmxPort) {
        return (new HashMap<String,String>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            // overrides for the JNDI factory
            // mostly changing the INITIAL_CONTEXT_FACTORY from:
            //   "org.jboss.security.jndi.JndiLoginInitialContextFactory"
            // to:
//            put(Context.INITIAL_CONTEXT_FACTORY,
//                "org.jnp.interfaces.NamingContextFactory");
//            put(Context.PROVIDER_URL, "jnp://localhost/");

            // Required JMX properties and other things ...
            //  YMMV of course, these were for my current settings
            put("java.rmi.server.randomIDs", "true");
            put("java.rmi.server.hostname", myAddress); // host needs to be an IP
            put("com.sun.management.jmxremote.ssl", "false");
            put("com.sun.management.jmxremote.authenticate", "false");
            put("com.sun.management.jmxremote.local.only", "true");
            put("com.sun.management.jmxremote.port", String.valueOf(jmxPort));
        }});
    }

	
}