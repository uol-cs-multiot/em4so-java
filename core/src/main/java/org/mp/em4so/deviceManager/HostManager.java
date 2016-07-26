/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.deviceManager;

import org.mp.em4so.agentManager.SmartObjectAgManager;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.utils.SOMFileConfigUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class HostManager.
 */
public class HostManager {
	
	/**
	 * Creates the host.
	 *
	 * @param som the som
	 */
	public static void createHost (SmartObjectAgManager som){
		Host myHost = new Host();
		
		
		myHost.setName(SOMFileConfigUtils.getName());
		
		myHost.setId("host/"+SOMFileConfigUtils.getName());
		
	}
}
