/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.network.protocol;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO: Auto-generated Javadoc
/**
 * The Class SOMessageSender.
 */
public class SOMessageSender {

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
		SOMessageSender.soca = soca;
	}

	/**
	 * Send.
	 *
	 * @param <T> the generic type
	 * @param targetProvider the target provider
	 * @param objectLabel the object label
	 * @param obj the obj
	 * @param message the message
	 */
	public static <T>void send(String targetProvider, String objectLabel,T obj, String message) {
		ObjectNode params = null;
		SimpleDateFormat sdf = null;
		String defaultDatePattern = null;
		Date dateSent = null;
		try {
			params = JSONUtils.createObjectNode();
			params.putPOJO(objectLabel, obj);
			params.put("message", message);

			
			defaultDatePattern = "YYYY-MM-dd'T'HH:mm:ss.SSS";			
			sdf = new SimpleDateFormat();
			sdf.applyPattern(defaultDatePattern);
			
			if (!soca.getSom().getAskedHosts().containsKey(targetProvider + "::" + message+":"+obj)) {
				soca.makeCall(URI.create(targetProvider), params);
				dateSent = new Date();
				soca.getSom().getAskedHosts().put((targetProvider + "::" + message+":"+obj), sdf.format(dateSent));
			}
			
		} catch (Exception e) {
			LOG.error("{}: Error sending message (no TTL): {} containing object : {} to {}. Error is : {}",soca.getSom().getId(),message,obj,targetProvider,e.getMessage(),e);
		}
		
		
	}
	
	/**
	 * Send.
	 *
	 * @param <T> the generic type
	 * @param targetProvider the target provider
	 * @param obj the obj
	 * @param message the message
	 * @param ttl the ttl
	 */
	public static <T>void send(String targetProvider, T obj, String message,int ttl) {
		ObjectNode params = null;
		SimpleDateFormat sdf = null;
		String defaultDatePattern = null;
		try {
			params = JSONUtils.createObjectNode();
			params.putPOJO("targetobj", obj);
			params.put("ttl", ttl);
			params.put("message", message);

			
			defaultDatePattern = "YYYY-MM-dd'T'HH:mm:ss.SSS";			
			sdf = new SimpleDateFormat();
			sdf.applyPattern(defaultDatePattern);
			
			if (!soca.getSom().getAskedHosts().containsKey(targetProvider + "::" + message+":"+obj)) {
				soca.getSom().getAskedHosts().put((targetProvider + "::" + message+":"+obj), sdf.format(new Date()));
				soca.makeCall(URI.create(targetProvider), params);
			}
		} catch (Exception e) {
			LOG.error("{}: Error sending message (w TTL): {} containing object : {} to {}. Error is : {}",soca.getSom().getId(),message,obj,targetProvider,e.getMessage(),e);
		}
	}
	

}
