/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.control;

import java.util.AbstractMap.SimpleEntry;
import java.util.Hashtable;
import org.mp.em4so.agentManager.SmartObjectAgManager;
import org.mp.em4so.model.actuating.Activity;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.services.ServiceAssembler;
import org.mp.em4so.services.ServiceDiscoverer;
import org.mp.em4so.utils.JSONUtils;
//import org.mp.measurement.MeasurementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageProcessor.
 */
public class MessageProcessor {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class.getSimpleName());
	
	/**
	 * Carries out processing of the message.
	 *
	 * @param som the som
	 * @param params the params
	 * @param senderUrl the sender url
	 */
	public static synchronized void processMessage(SmartObjectAgManager som, ObjectNode params, String senderUrl){
		ObjectNode reply = null;
		String message = null;
		Service service = null;
		Host host = null;
		Host replyHost = null;
		Element resultTo = null;
		String resultToStr = null;
		SimpleEntry<String,Object> result=null;
		Hashtable<String,String> argValues = null;
		Object object = null;
		Activity activity=null;
		String msg = null;
		String resultStr = null;
		
		
			message = params.get("message").asText();
			LOG.debug("{} to process '{}' message from {}", som.getId(),message,senderUrl);
			LOG.debug("{} params received: {}", som.getId(),params);
			switch(message){
				
			case "execute": {
				try{
				service = MessageProcessor.<Service>unWrapParameter(params.get("service"),"service",Service.class);
				resultToStr = params.get("resultTo").asText();
				argValues = MessageProcessor.<Hashtable>unWrapParameter(params.get("argValues"),"argValues",Hashtable.class);
				LOG.trace("{} service '{}' received and ready to execute.", som.getId(),service.getName());
			
					object = ServiceAssembler.execute(
							service,
							argValues,
							ServiceAssembler.getRunningCapability(
									service.getExecutionInstance().getCapability()
									)
							);
				
					if (service.getResult()!= null && object!=null){
					result = new SimpleEntry<String,Object>("result",object);				
					reply = wrapReply(result);
					ServiceAssembler.deliverServiceReply(service,resultToStr,reply,senderUrl,"executeReply");
				}
				} catch (Exception e) {
					
					
					result = new SimpleEntry<String,Object>("error",e.getMessage());				
					reply = wrapReply(result);
					try {
						ServiceAssembler.deliverServiceReply(service,resultToStr,reply,senderUrl,"executeReply");
					} catch (Exception e1) {
						LOG.info("Unable to send error message back to requester {}",e.getMessage());
					}
				
					LOG.info("Exception caught error {} sent back to requester {}",e.getMessage(),senderUrl);
				}
					
			}
			
		
			break;
			case "executeReply": {
				
				service = MessageProcessor.<Service>unWrapParameter(params.get("service"),"service",Service.class);
				
				resultStr = (JSONUtils.<String>objectNodeToJavaType((params.get("reply")).get("key"),String.class));
				if(resultStr!=null && resultStr.equals("error")){
					resultStr = (JSONUtils.<String>objectNodeToJavaType((params.get("reply")).get("value"),String.class));
					LOG.trace("There is an error in the reply: {}",resultStr);
					processFailure(som, params, senderUrl,true);
				}else{
					resultToStr = MessageProcessor.<String>unWrapParameter(params.get("resultTo"),"resultTo",String.class);
					if(resultToStr!=null)
					resultTo = JSONUtils.mapJsonToType(resultToStr, Element.class);
					if(service.getResult()!= null)
						if(service.getResult().startsWith("java")){
							resultStr = (JSONUtils.<String>objectNodeToJavaType((params.get("reply")).get("value"),String.class));
						}
						else{ 
							resultStr = MessageProcessor.unWrapParameter((params.get("reply")),"value",java.lang.String.class);
						}
						LOG.trace("{} result '{}' for service '{}' received and ready to process. To store reply in: {}", som.getId(),result,service.getName());
						ServiceAssembler.doExecuteSuccess(service,resultTo,resultStr);
				}
			}
			break;
			case "query":{
				service = MessageProcessor.<Service>unWrapParameter(params.get("service"),"service",Service.class);
				LOG.trace("{} service '{}' received and ready to query.", som.getId(),service.getName());
				service = ServiceDiscoverer.query(service, params.get("ttl").asInt(), senderUrl);
				reply = wrapReply(service);
			}
			break;															
			case "queryHit":{
				service = MessageProcessor.<Service>unWrapParameter(params.get("service"),"service",Service.class);
				LOG.trace("{} service '{}' received and ready to queryHit.", som.getId(),service.getName());
				ServiceDiscoverer.queryHit(service);

			}
			break;
			case "ping":{
				try {
				LOG.trace("{} join call received", som.getId());
				host = MessageProcessor.<Host>unWrapParameter(params.get("host"),"host",Host.class);
				//replyHost =	
				
					ServiceDiscoverer.ping(host,params.get("ttl").asInt(),senderUrl);
				} catch (InterruptedException e) {
				LOG.error("Error on ping: {}",e.getMessage(),e);
				}
			}
			break;
			case "pong":{
				
				
				LOG.trace("{} pong call received", som.getId());
				host = MessageProcessor.<Host>unWrapParameter(params.get("host"),"host",Host.class);
				ServiceDiscoverer.pong(host,senderUrl);
				
//				MeasurementProxy.measureHwResources();
//				LOG.info("After pong: \n"+MeasurementProxy.getAllMeasurements()+"\n");
			}
			break;
			case "activityupdate":{
				LOG.trace("{} done activity from: {}", som.getId(),senderUrl);
				activity = MessageProcessor.<Activity>unWrapParameter(params.get("activity"),"activity",Activity.class);
				som.getAe().updateActivityStatus(activity);
			}
			break;
			}

	}
	
	/**
	 * Wrap reply.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @return the object node
	 */
	public static <T>ObjectNode wrapReply(T obj){
		ObjectNode on = null;
		try{
		LOG.trace("Start of reply wrapping of {} ",obj);
		if(obj!= null){
			on = JSONUtils.<T>objectToObjecNode(obj);
		}
		LOG.trace("End of reply wrapping : {}",on);
		}catch(Exception e){
			LOG.error("Error while wrappingReply: {}",e.getMessage(),e);
		}
		return on;
	}
	
	/**
	 * Un wrap parameter.
	 *
	 * @param <T> the generic type
	 * @param jsonNode the json node
	 * @param fieldName the field name
	 * @param c the c
	 * @return the t
	 */
	public static <T>T unWrapParameter(JsonNode jsonNode,String fieldName, Class<T> c){
		LOG.trace("Start of unwrapping of {}: {}",fieldName, jsonNode);
		T obj = null;
		if(jsonNode!=null)
			obj = JSONUtils.<T>jsonNodeToObject(jsonNode,c);
		LOG.trace("End of unwrapping of {}: {}",fieldName,obj);
		return obj;
	}
	
	/**
	 * Carries out processing of the failure.
	 *
	 * @param som the som
	 * @param args the args
	 * @param senderUrl the sender url
	 */
	public static void processFailure( SmartObjectAgManager som,ObjectNode args, String senderUrl){
		processFailure(som, args, senderUrl,false);
	}
	
	/**
	 * Carries out processing of the failure.
	 *
	 * @param som the som
	 * @param args the args
	 * @param senderUrl the sender url
	 * @param lastAttempt the last attempt
	 */
	public static void processFailure( SmartObjectAgManager som,ObjectNode args, String senderUrl, boolean lastAttempt){
		Service s = null;
		String newStatus = "pending";
		s = JSONUtils.mapJsonToType(args.get("service").toString(),  Service.class);
		if(lastAttempt) newStatus = "failure";
		if(s.getStatusKey()!=null)som.getAe().updateActionStatus(s,newStatus);
	}
	
	
	
	
}
