/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.common.capabilities;

import org.mp.em4so.model.actuating.Capability;

// TODO: Auto-generated Javadoc
/**
 * The Class HumanInterfaceCapability.
 */
public class HumanInterfaceCapability extends Capability{

/**
 * Prints the.
 *
 * @param message the message
 */
public void print(String message){
//	if(message.startsWith("$") && message.endsWith("$")){
//		this.getSomanagerAgent().getKbm().
//	}else{
	System.out.println(soca+": "+message);
//	}
}

}
