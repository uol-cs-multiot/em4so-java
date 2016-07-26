/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.listeners;

import java.io.File;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mp.em4so.utils.SOMFileConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.capabilities.Config;
import com.almende.eve.config.YamlReader;
import com.almende.eve.deploy.Boot;
import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO: Auto-generated Javadoc
public class SOMListener implements ServletContextListener{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SOMListener.class.getSimpleName());
	
	/** The my config. */
	protected ObjectNode		myConfig	= null;
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final ServletContext sc = sce.getServletContext();
		File directory ;
		
//		URL location = Thread.currentThread().getClass().getProtectionDomain().getCodeSource().getLocation();
//		directory = new File(location.getFile());
//		String rootPath =  
//				directory.getPath();
		loadSOMConfig(sc);
		loadEveConfig(sc);
		
	}
	
	/**
	 * Load SOM config.
	 *
	 * @param somSc the som sc
	 */
	public void loadSOMConfig(ServletContext somSc){
		final Config somConfig = null;
		
		// Get the eve.yaml file:
				String path = somSc.getInitParameter("som_config");
		
		
		if ( path != null && !path.isEmpty()) {
			final String fullname = SOMFileConfigUtils.RUNNING_PATH+"/WEB-INF/" + path;
			
			LOG.trace("loading SOM configuration file '" + fullname
					+ "'...");
			System.out.println("loading SOM configuration file '" + fullname
					+ "'...-->"+somSc.getContextPath()+"<--");
			final InputStream is = somSc.getResourceAsStream(fullname);
			System.out.println("Lo leido"+is);
			SOMFileConfigUtils.setSomYaml(YamlReader.load(is));
			
	}
	}
		
	/**
	 * Load eve config.
	 *
	 * @param sc the sc
	 */
	public void loadEveConfig( ServletContext sc){
		
		// Get the eve.yaml file:
		String path = sc.getInitParameter("eve_config");
		if (path != null && !path.isEmpty()) {
			final String fullname = SOMFileConfigUtils.RUNNING_PATH+"/WEB-INF/" + path;
			LOG.info("loading configuration file '" + sc.getRealPath(fullname)
					+ "'...");
			final InputStream is = sc.getResourceAsStream(fullname);
			if (is == null) {
				LOG.error("Can't find the given configuration file:"
						+ sc.getRealPath(fullname));
				return;
			}
			myConfig = Boot.boot(is);
			//LOG.fine("After booting.."+myConfig);
		}

		
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	

}
