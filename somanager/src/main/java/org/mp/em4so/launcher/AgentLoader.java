/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.mp.em4so.model.network.TransportConfig;
import org.mp.em4so.utils.NetworkUtils;
import org.mp.em4so.utils.SOMFileConfigUtils;
import org.mp.em4so.utils.SOManagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.config.YamlReader;




// TODO: Auto-generated Javadoc
/**
 * The Class AgentLoader.
 */
public class AgentLoader {
	
	/** The transport config. */
	private TransportConfig transportConfig;
	
	/** The SO name. */
	private String SOName;
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AgentLoader.class.getSimpleName());
	
	/**
	 * Instantiates a new agent loader.
	 */
	public AgentLoader(){
		transportConfig = new TransportConfig();
	}
	
	/**
	 * Load SOM config.
	 *
	 * @param somPath the som path
	 */
	public void loadSOMConfig(String somPath){
			
		
			LOG.trace("loading SOM configuration file '" + somPath
					+ "'...");
			
			
			InputStream som_is;
			try {
				som_is = new FileInputStream(somPath);
			
			
			
			SOMFileConfigUtils.setSomYaml(YamlReader.load(som_is));
			
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
	
	/**
	 * Load agents.
	 *
	 * @param transport the transport
	 */
	public void loadAgents(TransportConfig transport){
		LOG.trace("CLASSPATH here: -->"+System.getProperty("java.class.path")+"<--");
		SOManagerUtils.load(transport,Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 * Boot.
	 *
	 * @param somFilePath the som file path
	 */
	public void boot(String somFilePath){
	
		transportConfig.setServer(NetworkUtils.getHostAddress());
		transportConfig.setProtocol("http");
		transportConfig.setRootContext("somanager");
		transportConfig.setJmxPort("1099");
		
		
		LOG.trace("Loading SOM Configuration from: {}", somFilePath);
		
		loadSOMConfig(somFilePath);
		
		transportConfig.setPort(SOMFileConfigUtils.getServerPort());
		transportConfig.setAgentName(SOMFileConfigUtils.getName());
		jettyLoad();		
			
		loadAgents(transportConfig);
	}

	/**
	 * Jetty load.
	 */
	public void jettyLoad()
    {
		Handler[] handlers = null;
		AppContextBuilder acb = null;
		ContextHandlerCollection contexts = null;
		try {
			handlers = new Handler[1];
			acb = new AppContextBuilder();
			handlers[0] = acb.buildServletContextHandler(transportConfig);
			

			contexts = new ContextHandlerCollection();
            contexts.setHandlers(handlers);
 
//        contexts.setHandlers(new Handler[] {
//            (new AppContextBuilder()).buildServletContextHandler()
//        });
        
        JettyServer jettyServer = new JettyServer(Integer.parseInt(transportConfig.getPort()));
        jettyServer.setHandler(contexts);
        //JMX bean and rmi startup for monitoring
        //        acb.buildMBeanContainer(transportConfig.getAddress(), Integer.parseInt(transportConfig.getJmxPort()));
//        jettyServer.addMBean(acb.buildMBeanContainer());
        
        if(jettyServer.isStopped()){
			
				jettyServer.start();
			
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
    }
	
	
	/**
	 * Load SO name.
	 *
	 * @param soname the soname
	 */
	public void loadSOName (String soname){
		
		if(soname.contains("/")){
			SOName = soname.substring(soname.lastIndexOf("/")+1, soname.lastIndexOf("."));
		}else{
			SOName = soname.substring(0, soname.lastIndexOf("."));
		}
		
	}
	
	/**
	 * Gets the SO name.
	 *
	 * @return the SO name
	 */
	public String getSOName() {
		return SOName;
	}
	
}
