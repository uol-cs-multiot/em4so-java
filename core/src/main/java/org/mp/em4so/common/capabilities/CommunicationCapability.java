/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.common.capabilities;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeMap;

import org.mp.em4so.exceptions.HostNotFoundException;
import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.actuating.ExecutionInstance;
import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.MessagePeer;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.services.ServiceAssembler;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicationCapability.
 */
public class CommunicationCapability extends Capability {
	
	/**
	 * Transfer.
	 *
	 * @param msg the msg
	 * @param property the property
	 * @throws Exception the exception
	 */
	public void transfer(Element msg,Element property) throws Exception{
		LOG.trace("{}: Starting transfer of: {} to {}",soca.getId(),property,msg.getTo());
		Role role = null;
		Host host = null;
		MessagePeer from = null;
		boolean callSuccess = false;
		boolean byRole = false;
		boolean byHost = false;
		if(msg.getTo()!=null){
			if(msg.getTo().getRole()!=null){
				role = new Role();
				role.setId(msg.getTo().getRole());
				LOG.trace("Role: {}",role.getId());
				byRole = true;
			}else if (msg.getTo().getId()!=null){
				host = new Host();
				host.setId(msg.getTo().getId());
				byHost = true;
			}else if (msg.getTo().getUrl()!=null){
				//TODO Implement by URL
			}
		}
		
		//Prepare arguments for call
		TreeMap<String,String> argTypes = new TreeMap<String,String>();
		Hashtable<String,Object> argValues = new Hashtable<String,Object>();
		argTypes.put("msg", "org.mp.em4so.model.common.Element");
		argTypes.put("property", "org.mp.em4so.model.common.Element");
		Service service = new Service();
		service.setName("receiveTransfer");
		service.setArgTypes(argTypes);
		service.setResult(null);
		ExecutionInstance ei = new ExecutionInstance();
		ei.setCapability("org.mp.em4so.common.capabilities.CommunicationCapability");
		argValues.put("property", property);
		msg.setTo(null);
		from = new MessagePeer();
		from.setId(soca.getId());
		from.setUrl(soca.getSom().getMyHost().getUrls().get(0));
		from.setRole(getExecutingRoleId());
		msg.setFrom(from );
		argValues.put("msg", msg);
		
		while(!callSuccess){
			
			if(byRole) host = soca.getSom().getKbm().getRoleHost(role);
			else if(byHost)	host = soca.getSom().getKbm().getHost(host);
			LOG.trace("Host: {}",host);
			LOG.trace("Host Id: {} - {}",host.getId(), host.getUrls());
			
			if(host!=null && host.getUrls()!=null && !host.getUrls().isEmpty()){
				ei.setHost(host);
				ei.setRanking(host.getRanking()+"");
				service.setExecutionInstance(ei);
				try{
					callSuccess = ServiceAssembler.executeService(service, argValues,null);
				}catch(Exception e){
					LOG.info("Highest ranked host \"{}\" is not available. Trying if there is another.",host.getId());
				}
				if(!callSuccess){
					//TODO change threshold for a customisable parameter
					if(host.getRanking()<0.3){ //arbitrary threshold
						LOG.trace("Going to break execution...",host.getId());
						throw new HostNotFoundException("Unable to find a target host for the message transfer");
					}
					//TODO change to a customisable formula for reducing ranking
					host.setRanking(host.getRanking()/1.5);
					soca.getSom().getKbm().addProvider(host);
				}
				
			}
		}
		
		
		
		
		
	}
	
	/**
	 * Receive transfer.
	 *
	 * @param msg the msg
	 * @param property the property
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void receiveTransfer(Element msg,Element property) throws IOException{
		LOG.trace("{}: Starting reception of transfer: {} from {}",soca.getId(),property);
		try{
		soca.getSom().getKbm().updateProperty(property);
		msg.setReceivedDate(new Date());
		soca.getSom().getKbm().receiveMessage(msg);
		}catch(InterruptedException e){
			LOG.error("Error in: {}",e.getMessage(),e);
		}
	}
	
	
}
