/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.services;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.mp.em4so.agentManager.KnowledgeBaseManager;
import org.mp.em4so.agentManager.SmartObjectAgManager;
import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.model.actuating.ExecutionInstance;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;


// TODO: Auto-generated Javadoc
/**
 * The Class ServiceDiscoverer.
 *
 * @author mp
 */
public class ServiceDiscoverer {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ServiceDiscoverer.class.getSimpleName());
	
	/** The soca. */
	private static SOControlAgent soca = null;
	
	/**
	 * Sets the SO control agent.
	 *
	 * @param soca the new SO control agent
	 */
	public static void setSOControlAgent (SOControlAgent soca){
		ServiceDiscoverer.soca = soca;
	}
	
	/**
	 * It replies PING calls of a given service.
	 *
	 * @param service the service
	 */
	public static void queryHit(Service service){
			ObjectNode params = null;
			String requesterUrl = null;
			params =JSONUtils.createObjectNode();
			try{
			params.set("service", JSONUtils.objectToObjecNode(service));
			params.put("message", "queryHit");
			
			//Add the service to my KB, by default we add it as remote
			
//			for(Service service:services){
				LOG.info(soca.getSom().getId()+": Adding new Service Provider: "+service.getExecutionInstance().getHost().getName());
//				service.getExecutionInstance().setLocal("false");
				soca.getSom().getKbm().addServiceProvider(service);
//			}
			
			//if senderUrl in memory, call: queryHint on the initial sender Url
					requesterUrl = soca.getSom().getKbm().<String>getShortTerm("query:"+service.getName(),String.class);
			
			LOG.trace(soca.getSom().getId()+": Need to send service to: "+requesterUrl);
			if(requesterUrl!=null){
				LOG.info(soca.getSom()+": Forwarding found service execution instance to:"+requesterUrl);
				soca.makeCall(URI.create(requesterUrl), params);
				//callQueryHint(service, requesterUrl);
			}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}

	/**
	 * query for the service given.
	 *
	 * @param service the service
	 * @param ttl the ttl
	 * @param senderUrl the sender url
	 * @return the service
	 */
	public static Service  query (Service service, int ttl, String senderUrl){
		//decrement TTL
		ttl -= 1;
		Service result = null;
//		List<Service> knownInstances = null;
		ExecutionInstance ei = null;
		List<String> avoidReqUrls = null;
		HashMap<String,Host> targetHosts= null;
		// query the service locally
		if(senderUrl!=null)
			ei = soca.getSom().getKbm().getExecutionInstance(service); 
	//	if(resultsService!=null)
	//		LOG.info(som.getId()+":"+" Starting query sent from: "+senderUrl+" for service: "+resultService.getName()+ " - TTL"+ttl+" - Execution Instance found: "+resultService.getExecutionInstance());
	//	else
	//		LOG.info(som.getId()+":"+" Starting query sent from: "+senderUrl+" for service: "+resultService+ " - TTL"+ttl+" - Execution Instance found: "+resultService);
		if((ei== null || ei.getHost()==null) && ttl>0){
				avoidReqUrls = new LinkedList<String>(soca.getSom().getMyHost().getUrls());
				if(senderUrl!= null){
					avoidReqUrls.add(senderUrl);
					if(soca.getSom().getKbm().<String>getShortTerm("query:"+service.getName(), String.class)==null)
						soca.getSom().getKbm().<String>putShortTerm("query:"+service.getName(), senderUrl);
				}
//				for(URI myuri:som.getUrls()){
//					avoidUrls.add(myuri.toString());
//				}
					//		unknown or not found host
					//	1. category search
					
					if(service.getCategories()!= null){
					targetHosts = soca.getSom().getKbm().getCategoryProviders(service,avoidReqUrls);
					if(targetHosts != null)LOG.trace(soca.getSom().getId()+":"+"Found "+targetHosts.size()+" hosts in categories of service:"+service.getName());
					}
					if(targetHosts != null && !targetHosts.isEmpty()){
						queryOthers(targetHosts, service, ttl);
					}else{
						targetHosts = soca.getSom().getKbm().getKnownProviders(avoidReqUrls);
						LOG.trace(soca.getSom().getId()+":"+" To do general Search on: "+targetHosts.size()+" hosts");
						//	2. general search
						//TODO general search
						queryOthers(targetHosts, service, ttl);
					}	
		}else if(ei!= null && ei.getHost()!=null){
				
			result = service;
			result.setExecutionInstance(ei);
	
			
			LOG.trace(soca.getSom().getId()+": to return host : "+ei.getHost().getName());
		}
			return result;
		}

	/**
	 * Triggers query of the service on other nodes .
	 *
	 * @param targetHosts the target hosts
	 * @param service the service
	 * @param ttl the ttl
	 */
	public static void queryOthers(HashMap<String,Host> targetHosts, Service service, int ttl){
			
		if(targetHosts!=null ){
			Iterator<String> it= targetHosts.keySet().iterator();
			Host targetHost = null;
			while(it.hasNext()){
				targetHost = targetHosts.get(it.next());
				LOG.trace(soca.getSom().getId()+": asking "+targetHost+" for service:"+service.getName()+". TTL="+ttl);
				callQuery(targetHost.getUrls().get(0), service,ttl); //TODO loop to check all host URLs
			}
	
		}
	
	
	}

	/**
	 * Make call using agent protocol.
	 *
	 * @param targetProvider the target provider
	 * @param service the service
	 * @param ttl the ttl
	 */
	public static void callQuery(String targetProvider, Service service, int ttl) {
		ObjectNode params = null;
		SimpleDateFormat sdf = null;
		String defaultDatePattern = null;
		try {
			params = JSONUtils.createObjectNode();
			params.putPOJO("service", service);
			params.put("ttl", ttl);
			params.put("message", "query");

			
			defaultDatePattern = "YYYY-MM-dd'T'HH:mm:ss.SSS";			
			sdf = new SimpleDateFormat();
			sdf.applyPattern(defaultDatePattern);
			
			if (!soca.getSom().getAskedHosts().containsKey(targetProvider + "::" + service.getName())) {
				soca.getSom().getAskedHosts().put((targetProvider + "::" + service.getName()), sdf.format(new Date()));
				soca.makeCall(URI.create(targetProvider), params);
			}
		} catch (Exception e) {
			LOG.error("{}: Error querying service {} : ",soca.getSom().getId(),service.getName(),e.getMessage(),e);
		}
	}


		/**
		 * Checks if the given url is reachable.
		 *
		 * @param url the url
		 * @return true, if is reachable
		 * @throws NumberFormatException the number format exception
		 * @throws UnknownHostException the unknown host exception
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public static boolean isReachable (String url) throws NumberFormatException, UnknownHostException, IOException{
			Socket socket = null;
			boolean reachable = false;
			String host = null, port = null;
			try {
				host = url.substring(0, url.lastIndexOf(":"));
				port = url.substring(url.lastIndexOf(":"),url.length());
				socket = new Socket(host, Integer.parseInt(port));
				reachable = true;
			} finally {            
				if (socket != null) 
					try { 
						socket.close(); 
					} catch(IOException e) {
						LOG.error(e.getMessage(),e);
					}
	
			}
			return reachable;
		}
		
		
		/**
		 * To execute when a ping call is received from the host passed as parameter.
		 *
		 * @param host the host
		 * @param ttl the ttl
		 * @param senderUrl the sender url
		 * @throws InterruptedException the interrupted exception
		 */
		public static void ping(Host host, int ttl, String senderUrl)throws InterruptedException{
			HashMap<String,Host> knownProviders = null;
			Host knownHost = null;
			List<String> urls = null;
			Iterator<String> itUrl = null;
			Set <String> keys;
			ObjectNode params = null;
			ObjectNode paramsReply = null;
			String theUrl = null;
			
			Host myHost = null;
			Host foundHost = null;
			boolean callOk;
			boolean replyOk;
			boolean newHost = false;
			
			LOG.trace("Host={}, ttl= {}, sender={}",host.getId(),ttl,senderUrl);
			
			SmartObjectAgManager som1 = soca.getSom();
			KnowledgeBaseManager kbm1 = som1.getKbm();
			foundHost = kbm1.getHost(host);
				LOG.trace("HOST FOUND in local KB is:"+foundHost+"<--");
				if(foundHost==null || // I do not know the host 
						soca.getSom().getMyHost().getId().equals(host.getId())) // It is my host
					newHost = true;
		
				
			
			myHost = new Host(); 
					
			myHost.setId(soca.getSom().getMyHost().getId());
			myHost.setName(soca.getSom().getMyHost().getName());
			myHost.setRoles(soca.getSom().getMyHost().getRoles());
			myHost.setUrls(soca.getSom().getMyHost().getUrls());
			
			
			if(newHost)
			{
				LOG.trace("It is new host!!");
				kbm1.addProvider(host);
			//Send my url to the node that originally started the ping, except if it is me
			if(	senderUrl!=null &&	!senderUrl.equals(soca.getSom().getMyHost().getUrls().get(0))	)
				sendHostReply(myHost,host.getUrls().get(0),"pong");
			
			
			//Should I propagate the ping?
			if(ttl>0){
				knownProviders = soca.getSom().getKbm().getMemManager().getKnownProviders(soca.getSom().getMyHost().getUrls());
				params = JSONUtils.createObjectNode();
				params.putPOJO("host", host);
				ttl = ttl -1;
				params.put("ttl", ttl);
				params.put("message", "ping");
				
				//If there other hosts to propagate the ping message
				if(knownProviders!=null && !knownProviders.isEmpty()){
					keys = knownProviders.keySet();
					for(String key:keys){
						LOG.trace(" key found: {} - my host: {} - host id: {}",key,myHost.getId(),host.getId());
						knownHost = knownProviders.get(key);
						if(!key.equals(myHost.getId()) && !key.equals(host.getId()) ){
							LOG.trace("To propagate ping message to {}",key);
							callOk = false;
							urls = knownHost.getUrls();
							itUrl = urls.iterator();
							while(itUrl.hasNext() && !callOk){
								theUrl = itUrl.next();
								if(senderUrl!=null && !senderUrl.equals(theUrl)){
									try{
										soca.makeCall(URI.create(theUrl), params);
										callOk = true;
										LOG.trace("{}: Pinging host {} to {}. Based on ping received from: {}",soca.getSom().getId(),host.getId(),knownHost.getId(),senderUrl);
									}catch(Exception e){
										callOk = false;
										LOG.trace("Url {} not available, trying another one");
										continue;
									}
								}
							}		
						}
					}
				}
			}
			}
//			return myHost;
		}
		
	/**
	 * To execute when a pong call received from the host given as parameter.
	 *
	 * @param host the host
	 * @param accessPeerUrl the access peer url
	 */
	public static void pong ( Host host, String accessPeerUrl){
			SmartObjectAgManager som = soca.getSom();
			try{
				host.setRanking(1d); //Set initial ranking
			
				if(som.getKbm().getHost(host)==null){
					LOG.trace("It is a new host!++");
					som.getKbm().addProvider(host);
					LOG.info("{}: host received from {} added.",som.getId(),accessPeerUrl);
				}
			}catch(InterruptedException e){
				LOG.error("Error InterruptedException in: {}",e.getMessage(),e);
			}
			
		}
	
	/**
	 * Execute the join P2P overlay procedure.
	 */
	public static void join(){
		LOG.trace("{} Starting protocol to join P2P network...",soca.getSom().getId());

		try {
			ping(soca.getSom().getMyHost(),soca.getSom().getTTL(),soca.getSom().getMyHost().getUrls().get(0));
		
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	} 
	
	
		/**
		 * Execute behaviour for the success in a query hit (found service).
		 *
		 * @param args the args
		 * @param reply the reply
		 */
		public static void doQueryHitSuccess(ObjectNode args, ObjectNode reply){

			Service service = null;
			try{
			service = JSONUtils.<Service>jsonNodeToObject(reply.get("service"));
			if(service!=null){
 			  LOG.trace("{}: Received host {} for service {}",soca.getSom().getId(),service.getExecutionInstance().getHost().getName(),service.getName());
				queryHit(service);
 		   }
			}catch(Exception e){
				LOG.error("{}: Error on success query {} : ",soca.getSom().getId(),e.getMessage(),e);
			}
			
		}
		

		
		
		/**
		 * Execute behaviour for the failure to find a service with the query call.
		 *
		 * @param service the service
		 */
		public static void serviceFailure(Service service){
				SmartObjectAgManager som = soca.getSom();
			   Hashtable<String,String> factors = null;
			   try{
				LOG.trace(som.getId()+": current ranking of "+service.getName()+" is: "+service.getExecutionInstance().getRanking()+" but will be decreased.");
				if(!service.getExecutionInstance().getRanking().equals("0")){			   
				factors = new Hashtable<String,String>();
			   //TODO to avoid fixed values
			   factors.put("availability", "false");
			   service.getExecutionInstance().setRanking(
							som.getRe().getRating(som,service, factors)
							);
	     	   	LOG.trace(som.getId()+" :"+JSONUtils.<Service>objectToString(service));
				som.getKbm().updateService(service);
				LOG.trace(som.getId()+": decreased ranking of "+service.getName()+" is: "+service.getExecutionInstance().getRanking()+".");
			   }
				if(service.getStatusKey()!=null)som.getAe().updateActionStatus(service,"pending");
			   }catch(Exception e){
				   LOG.error(e.getMessage(),e);
				   som.getAe().updateActionStatus(service,"pending");
			   }
			}
		
		/**
		 * Make reply call to the given host.
		 *
		 * @param host the host
		 * @param newNodeUrl the new node url
		 * @param message the message
		 */
		public static void sendHostReply(Host host,String newNodeUrl,String message){
			ObjectNode params = null;
			params = JSONUtils.createObjectNode();
		
			params.put("message", message);
			params.putPOJO("host", host);
			try{
				soca.makeCall(URI.create(newNodeUrl), params);
				LOG.trace("{}: Sending reply back to {}",soca.getSom().getId(),newNodeUrl);
			}catch(IOException e){
				LOG.error("Host {} is not longer available",newNodeUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("EXCEPTION IS: {}",e.getMessage());
			}
			
			
		}
	
}
