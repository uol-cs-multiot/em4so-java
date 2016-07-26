/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.somanager.logging;



import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

// TODO: Auto-generated Javadoc
/**
 * The Class LogConfigurator.
 */
public class LogConfigurator  {
	
	/**
	 * Configure.
	 *
	 * @param SOName the SO name
	 */
	public void configure(String SOName){
		try {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory(); 
		JoranConfigurator jc = new JoranConfigurator(); 
		jc.setContext(context); 
		context.reset(); // override default configuration // inject the name of the current application as "application-name" // property of the LoggerContext 
		context.putProperty("log-filename", SOName+".log");
		jc.doConfigure("logback.xml");
//		jc.doConfigure("/home/mp/workspace/java/somanager/src/main/resources/logback.xml");
		} catch (JoranException e) {
			e.printStackTrace();
		} 
	}
	
}
