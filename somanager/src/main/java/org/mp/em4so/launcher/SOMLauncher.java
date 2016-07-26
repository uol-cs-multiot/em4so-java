/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.launcher;

import org.mp.somanager.logging.LogConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO: Auto-generated Javadoc
//import org.mp.measurement.MeasurementProxy;


/**
 * The Class SOMLauncher.
 */
public class SOMLauncher {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SOMLauncher.class.getSimpleName());
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		AgentLoader agentLoader = new AgentLoader();
		agentLoader.loadSOName(args[0]);
		new LogConfigurator().configure(agentLoader.getSOName());
		LOG.info("Starting Smart Object Manager for {} ...",agentLoader.getSOName());
		try {
			
//          ******Uncomment for hardware measurement******
//			MeasurementProxy.measureHwResources();
//			LOG.info("Hw Specs: "+MeasurementProxy.getHwSpecifications());
//			LOG.info("Before Boot: "+ MeasurementProxy.getAllMeasurements());
			agentLoader.boot(args[0]);
//          ******Uncomment for hardware measurement******
//			MeasurementProxy.measureHwResources();
//			LOG.info("After Boot: "+MeasurementProxy.getAllMeasurements());
		} catch (Exception e) {
			LOG.error("Error Launching SO Server: ",e.getMessage(),e);
		}
		
	}
	
}