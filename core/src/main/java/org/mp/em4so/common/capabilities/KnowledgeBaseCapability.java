/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.common.capabilities;

import org.mp.em4so.agentManager.KnowledgeBaseManager;
import org.mp.em4so.model.actuating.Capability;

// TODO: Auto-generated Javadoc
/**
 * The Class KnowledgeBaseCapability.
 */
public class KnowledgeBaseCapability extends Capability{
		
	/**
	 * Update environemnt property.
	 *
	 * @param scope the scope
	 * @param kind the kind
	 * @param propertyName the property name
	 * @param propertyValue the property value
	 */
	public void updateEnvironemntProperty(String scope, String kind,String propertyName, String propertyValue){
		KnowledgeBaseManager kbm = soca.getSom().getKbm();
		try{
		kbm.updateEnvironmentProperty(scope, kind,propertyName,propertyValue);
		}catch(InterruptedException e){
			LOG.error("Error: {}",e.getMessage(),e);
		}
		System.out.println(soca.getSom().getId()+": New "+propertyName+" value is:"+propertyValue);
	}
	
}
