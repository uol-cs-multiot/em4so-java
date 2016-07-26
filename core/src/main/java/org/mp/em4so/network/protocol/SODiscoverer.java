/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.network.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.exceptions.UnsupportedQueryException;
import org.mp.em4so.model.agent.Role;
import org.mp.em4so.model.network.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SODiscoverer.
 */
public class SODiscoverer {

/** The Constant LOG. */
private static final Logger LOG = LoggerFactory.getLogger(SODiscoverer.class.getSimpleName());
	
	/** The soca. */
	private static SOControlAgent soca = null;
	
	/**
	 * Sets the SO control agent.
	 *
	 * @param soca the new SO control agent
	 */
	public static void setSOControlAgent (SOControlAgent soca){
		SODiscoverer.soca = soca;
	}
	
	/**
	 * Query.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @param ttl the ttl
	 * @param senderUrl the sender url
	 * @return the t
	 */
	public static<T> T query(T obj, int ttl, String senderUrl){
		T result = null;
		try{
		//decrement TTL
				ttl -= 1;
				
				
				List<String> avoidReqUrls = null;
				HashMap<String,Host> targetHosts= null;
				// query the service locally
//				if(senderUrl!=null)
					result = queryLocal(obj);
				if((result== null || result.equals("")) && ttl>0){
						LOG.trace("Avoid MyHost: {}",soca.getSom().getMyHost());
						
						avoidReqUrls = new LinkedList<String>(soca.getSom().getMyHost().getUrls());
						if(senderUrl!= null){
							avoidReqUrls.add(senderUrl);
							if(soca.getSom().getKbm().<String>getShortTerm("query:"+obj.toString(), String.class)==null)
								soca.getSom().getKbm().<String>putShortTerm("query:"+obj.toString(), senderUrl);
						}
							//		unknown or not found host
							
								targetHosts = soca.getSom().getKbm().getKnownProviders(avoidReqUrls);
								LOG.trace(soca.getSom().getId()+":"+" To do general Search on: "+targetHosts.size()+" hosts");
								//	2. general search
								//TODO general search
								queryOthers(targetHosts, obj, ttl);
								
				}
				LOG.trace("{}: to return object : {}",soca.getSom().getId(),result);
		}catch(UnsupportedQueryException e){
			LOG.error("{}: Error querying object {}. Exception is: {} ",soca.getSom().getId(),obj,e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Query local.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @return the t
	 * @throws UnsupportedQueryException the unsupported query exception
	 */
	public static<T> T queryLocal(T obj)throws UnsupportedQueryException{
		T result = null;
		
		if(obj instanceof Role){
			result =(T)soca.getSom().getKbm().getResponsibleRole((Role)obj);
		}else
			throw new UnsupportedQueryException("Query is not implemented for: "+obj);
			
		return result;
	}
	
	/**
	 * Query others.
	 *
	 * @param <T> the generic type
	 * @param targetHosts the target hosts
	 * @param obj the obj
	 * @param ttl the ttl
	 */
	public static <T>void queryOthers(HashMap<String,Host> targetHosts, T obj, int ttl){
		
		if(targetHosts!=null ){
			Iterator<String> it= targetHosts.keySet().iterator();
			Host targetHost = null;
			while(it.hasNext()){
				targetHost = targetHosts.get(it.next());
				LOG.trace(soca.getSom().getId()+": asking "+targetHost+" for service:"+obj+". TTL="+ttl);
				SOMessageSender.send(targetHost.getUrls().get(0), obj,"query",ttl); //TODO loop to check all host URLs
			}
	
		}
	
	
	}
	

	

}
