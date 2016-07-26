/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.services;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.mp.em4so.agentManager.SmartObjectAgManager;
import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.common.capabilities.CommunicationCapability;
import org.mp.em4so.common.capabilities.SensingCapability;
import org.mp.em4so.exceptions.ServiceAssemblingException;
import org.mp.em4so.model.actuating.Action;
import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.actuating.ExecutionInstance;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.common.Service;
import org.mp.em4so.model.network.Host;
import org.mp.em4so.model.sensing.Sensor;
import org.mp.em4so.utils.JSONUtils;
import org.mp.em4so.utils.SOMFileConfigUtils;
import org.mp.em4so.utils.SOManagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.Paranamer;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceAssembler.
 *
 * @author mp
 */
public  class ServiceAssembler {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ServiceAssembler.class.getSimpleName());
	
	/** The soca. */
	private static SOControlAgent soca = null;
	
	/**
	 * Sets the SO control agent.
	 *
	 * @param soca the new SO control agent
	 */
	public static void setSOControlAgent (SOControlAgent soca){
		ServiceAssembler.soca = soca;
	}
	
	
	/**
	 * Executes the service.
	 *
	 * @param service the service
	 * @param argValues actual values for capability execution
	 * @param capability the capability
	 * @return the object
	 * @throws ServiceAssemblingException the service assembling exception
	 */
	public  static Object execute( Service service, Map <String,?> argValues , Capability capability)throws ServiceAssemblingException{
		Object result=null;
		//		Service service = null;
		Class<?>[] parameters;
		Class c = null;
		Method method = null;
		Object[] values;

		int i = 0;
		//		service = (Service)argValues.get("service");
		//		service = (capability.getServices()).get(serviceName);


		try {
			LOG.trace(service.getName()+" arg values are: "+argValues+" and arg types: "+service.getArgTypes());
			
			if(argValues != null && service.getArgTypes()!=null){
				parameters = (Class[]) Array.newInstance(Class.class, service.getArgTypes().size());
				values = (Object[])Array.newInstance(Object.class, argValues.size());
				if (parameters.length != values.length){
					throw new ServiceAssemblingException("Wrong Number of Params and Values");
				}
				for(Entry<String,String> param:service.getArgTypes().entrySet() ){
					LOG.trace("param {} is {} - {} ",i,param.getKey(),param.getValue());
					LOG.trace("argValues.get {} is {} ",i,argValues.get(param.getKey()));
					LOG.trace("return value to cast to: {}",service.getResult());
					LOG.trace("Executing role is: {}",service.getExecutingRoleId());
					c = (Class.forName(param.getValue()));
					
					
					parameters[i]= c;
					values[i]=parameters[i].cast(JSONUtils.mapObjectToType(argValues.get(param.getKey()), c));
					i++;
				}
				method = capability.getClass().getMethod(service.getName(), parameters);
				if(values!= null && parameters != null && values.length>0 && parameters.length > 0)
					LOG.trace("The values: {} - the parameters: {}",values[0],parameters[0]);
				capability.setExecutingRoleId(service.getExecutingRoleId());
				result = method.invoke(capability,values);
				if(result!= null) result = Class.forName(service.getResult()).cast(result);
			}else {
				method = capability.getClass().getMethod(service.getName());
				result = method.invoke(capability);
				if(result!= null) result = Class.forName(service.getResult()).cast(result);
			}
		} catch (SecurityException e) {
			LOG.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			//LOG.trace("The cause is: "+e.getCause());
			LOG.info("WARNING: Execution of {} failed. Reason: {}",service.getName(),e.getMessage(),e);
			throw new ServiceAssemblingException("Unable to run the service with the input provided");
		} catch (ClassNotFoundException e) {
			LOG.error("Error casting result '{}' to proper type '{}': {}",result,service.getResult(),e.getMessage(),e);
		} catch (Exception e) {
			LOG.trace("Error caught here: ",e.getMessage(),e);
			LOG.error(e.getMessage(),e);
		} 

		
			return result;

	}

	/**
	 * Executes the service.
	 *
	 * @param service the service
	 * @param capability the capability
	 * @return the object
	 * @throws ServiceAssemblingException the service assembling exception
	 */
	public  static Object execute(Service service, Capability capability)throws ServiceAssemblingException{
		return execute (service,null,capability);
	}

	/**
	 * Executes the action.
	 *
	 * @param action the action
	 * @param capability the capability
	 * @return the object
	 * @throws ServiceAssemblingException the service assembling exception
	 */
	public  static Object execute( Action action,  Capability capability)throws ServiceAssemblingException{
		return execute(action.getService(), action.getArgValues(), capability);
	}

	/**
	 * Gets the running capability.
	 *
	 * @param className the class name
	 * @return the running capability
	 * @throws ServiceAssemblingException the service assembling exception
	 */
	public static Capability getRunningCapability(String className) throws ServiceAssemblingException {
		Capability capability=null;
		HashMap<String, Capability> loadedCapabilities = null;
		Capability existingCapability = null;
		//		Sensor device,newDevice = null;
		SmartObjectAgManager somanager = soca.getSom();
		Sensor sensor = null;
		try {
			LOG.trace(" Start looking for capability for class : {}. There are capabilities {}.",className,somanager.getCapabilities());
			LOG.trace(" Start looking for capability for class : {}. There are {} capabilities.",className,somanager.getCapabilities().size());
			loadedCapabilities = somanager.getCapabilities();
			if(loadedCapabilities != null ){
				existingCapability = loadedCapabilities.get(className); 
				if(existingCapability != null){
					capability = existingCapability;
				}else{
					try{

						capability = (Capability) Class.forName(className).newInstance();
						//						Class<Capability>clazz = (Class<Capability>) Class.forName(className);
						//						constructor = clazz.getConstructor();
						//						capability = (Capability)
						//						        constructor.newInstance();//pass the SmartObjectManager to enable access from capabilities
					}catch(ClassNotFoundException e){
						throw new ServiceAssemblingException("Class not found");
					}

					//					//Sensing capability
					if(capability instanceof SensingCapability){
						sensor = new Sensor();
						sensor.setCapability(className);
						sensor = somanager.getSr().getSensorDetails(sensor);
						((SensingCapability) capability).setSensor(sensor);
					}
					LOG.trace("4-mid of capability");
					capability.setSOControlAgent(soca);
					somanager.getCapabilities().put(className, capability);
					LOG.trace("end of capability");
				}
			}

		} catch (InstantiationException e) {
			LOG.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			LOG.error(e.getMessage(),e);
		}  catch (IllegalArgumentException e) {
			LOG.error(e.getMessage(),e);
		} catch (SecurityException e) {
			LOG.error(e.getMessage(),e);
		}

		return capability;
	}


	/**
	 * Populate execution instance.
	 *
	 * @param service the service
	 * @return the execution instance
	 */
	public static ExecutionInstance populateExecutionInstance(Service service){
		SmartObjectAgManager som = soca.getSom();

		//TODO return result of map reduce for service using the ranking
		return som.getKbm().getExecutionInstance(service);	

	}




	//	
	//		public static void queryAndExecuteService(SmartObjectAgManager somanager,Action action) throws IOException {
	//		Service service;
	//		try {
	////			LOG.trace("action in query&schedule:"+action);
	//			
	//			service = query(somanager,action.getService(), "0", "local:method");
	//			if(service!=null && service.getExecutionInstance()!=null)
	//				action.getService().setExecutionInstance(service.getExecutionInstance());
	//			somanager.createActionTask("executeAction",action,0);
	//
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			LOG.error(e.getMessage(),e);
	//		}
	//
	//	}



	//	public Params buildCallParams(String urlStr, String method, HashMap<String,?> paramshm){
	//		URI url = URI.create(urlStr);
	//		Params params = new Params();
	//		for(String key : paramshm.keySet()){
	//			params.add(key, paramshm.get(key));
	//		}
	//		return params;
	//		
	//	}



	

	/**
	 * Executes the service.
	 *
	 * @param service the service
	 * @param argValues the arg values
	 * @param resultTo the result to
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean executeService(Service service,Map<String,?> argValues, String resultTo)throws Exception{
		ObjectNode params = null;
		boolean serviceCallOk = false;
		Iterator<String> urlIt = null;

		LOG.trace("{}: Preparing execute message for {} w argTypes: {} n return: {}",soca.getId(),service.getName(),service.getArgTypes(),service.getResult());
			params = JSONUtils.createObjectNode(); 
//			params.put("name",service.getName());
//			params.set("argTypes",JSONUtils.collectionToObjecNode(service.getArgTypes()));
			params.put("resultTo",resultTo);
			params.set("service", JSONUtils.<Service>objectToObjecNode(service));
			params.set("argValues", JSONUtils.collectionToObjecNode(argValues));
			params.put("message","execute");

			urlIt = service.getExecutionInstance().getHost().getUrls().iterator();
			
			while(urlIt.hasNext() && !serviceCallOk)
			{
				serviceCallOk = soca.makeCall(URI.create(urlIt.next()),params);
			}
			if(service.getStatusKey()!=null)
				if(serviceCallOk)
//					if(service.getName().equalsIgnoreCase("receiveTransfer"))
						soca.getSom().getAe().updateActionStatus(service,"done"); // if receveTransfer call is completed, the transfer is done. 
//					else
//						soca.getSom().getAe().updateActionStatus(service,"onprogress"); // change to onprogress because it is an asyncrhonous call//onprogress is not longer valid since a known activity is always done or not
				else
					soca.getSom().getAe().updateActionStatus(service,"failed");
			return serviceCallOk;
	}


	/**
	 * Execute a service synchronously .
	 *
	 * @param service the service
	 * @param argValues the arg values
	 * @param resultTo the result to
	 * @return the object
	 */
	public static Object executeServiceSync(Service service,Map<String,?> argValues, String resultTo){

	
		Object result = null;
		SmartObjectAgManager somanager = soca.getSom();
		try {

			if(service!=null && service.getExecutionInstance()!= null ){
				LOG.trace(" Host to query: "+service.getExecutionInstance().getHost()+" - my url is: "+somanager.getMyHost().getUrls().get(0).toString());
				//			if(service.getExecutionInstance().getUrl().equalsIgnoreCase(somanager.getUrls().get(0).toString()))
				//				result = somanager.callExecuteSync(service, argValues,Object.class); //TO REVIEW !!!
				Capability capability = ServiceAssembler.getRunningCapability(service.getExecutionInstance().getCapability());
				LOG.trace(" Capability found : {}",capability);
				result = ServiceAssembler.execute(service, argValues,capability);
				//			else{

			}
			//			}else{
			//				service = somanager.getKbm().getExecutionDetails(service);
			//				service.setExecutionInstance(service.getExecutionInstance());
			//				if(service!=null && service.getExecutionInstance()!= null ){
			//					result = somanager.callExecuteSync(service, argValues,Object.class);
			//				}
			//			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * Load of local services.
	 */
	public static void loadObjectCapabilities(){
		SmartObjectAgManager somanager = soca.getSom();
		int i = 0;
		String capabilitiesPkg = null;
		List<Class<?>> capabilities;
		Paranamer paranamer = new AdaptiveParanamer();
		String []paraNames; 
		Class<?>[] paraTypes;
		Service localService=null;
		TreeMap<String,String> args;
		Host minimalHost;
		List<Sensor> sensors = null;
		try{

			capabilitiesPkg = SOMFileConfigUtils.getCapabilitiesPkg();
			LOG.trace("Starting search of capabilities for: "+capabilitiesPkg);
			if(capabilitiesPkg!=null && (capabilitiesPkg.endsWith(".jar")||(capabilitiesPkg.endsWith("/jar")))){
				LOG.trace("In External Jar detected...");
				capabilitiesPkg = capabilitiesPkg.replaceAll("/jar", ".jar");
				capabilities = SOManagerUtils.findJarClass(capabilitiesPkg);
			}else{
				LOG.trace("In directory/jar detected...");
				capabilities = SOManagerUtils.findClass(capabilitiesPkg);
			}

			i=0;
			Method[] methods;
			Sensor sensor;
			minimalHost = new Host();
			minimalHost.setId(somanager.getMyHost().getId());
			minimalHost.setName(somanager.getMyHost().getName());
			
			//By default capabilities
			capabilities.add(CommunicationCapability.class);
			capabilities.add(SensingCapability.class);
			
			
			for(Class<?> capability : capabilities){
				methods = capability.getDeclaredMethods();
				args = new TreeMap<String,String>();
				sensor = null;
				
				LOG.trace("Capability_"+i+" - "+capability.getName()+" -> "+methods.length);
				if(SensingCapability.class.isAssignableFrom(capability)){
					sensor = new Sensor();
					sensor.setCapability(capability.getName());
					
					sensor = somanager.getKbm().getSensor(sensor);
					
					if(sensor==null ){
						methods = new Method[0]; // Remove all methods to avoid loading sensing capabilities since sensor is not defined
					}else{
						
						
						LOG.trace(" Found sensor: {}",sensor);
					}
				
				}
				
				for(int j=0;j<methods.length;j++){
					args.clear();
					if(sensor==null){
						localService = new Service( methods[j].getName(),new ExecutionInstance(minimalHost));
					}else if (methods[j].getName().equalsIgnoreCase(SOMFileConfigUtils.getSensingMethod())){
						localService = new Service(capability.getSimpleName(), new ExecutionInstance(minimalHost));
						localService.setKind("sensor");
						localService.setProperty(sensor.getPropertyNames());
						LOG.trace("Created SensingService for properties: {}",sensor.getProperty());
					}
					
					
					if(methods[j].getModifiers()==Modifier.PUBLIC && localService!=null){
						paraTypes = methods[j].getParameterTypes();
						paraNames = paranamer.lookupParameterNames(methods[j]);
						LOG.trace(j+":"+methods[j].getName()+" - "+paraTypes.length +" - "+paraNames.length);
						for(int k=0;k<paraTypes.length;k++){
							LOG.trace(k+":"+paraTypes[k].getName() + ": "+paraNames[k]);
							args.put(paraNames[k], paraTypes[k].getName());
						}
						localService.setArgTypes(args);
						localService.getExecutionInstance().setLocal("true");
						localService.getExecutionInstance().setRanking("1");
						localService.getExecutionInstance().setCapability(capability.getName());
						localService.setCategories(somanager.getMyHost().getCategories());

						if(!methods[j].getReturnType().equals(Void.TYPE)){
							localService.setResult(methods[j].getReturnType().getName());
						}else{
							localService.setResult("");
						}

						LOG.trace(localService.getName()+ " - "+localService.getKind());
						somanager.getKbm().addLocalService(localService);
					}
				}
				i++;
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}

	}

	






	
	/**
	 * Do execute success.
	 *
	 * @param service the service
	 * @param resultTo the result to
	 * @param result the result
	 */
	public static void doExecuteSuccess(Service service, Element resultTo, Object result){
		SimpleDateFormat sdf = null;
		Date time = null;
		Hashtable<String,String> factors =null;
		SmartObjectAgManager som = soca.getSom();
		try {

			LOG.trace(som.getId()+": Result of: "+service.getName()+ " is :"+result+"of type:"+service.getResult()+" - to be updated on: "+resultTo);
			time = null;
			factors = new Hashtable<String,String>();		   		   
			time = new Date();
			sdf = new SimpleDateFormat();
			
			if(result!=null && resultTo != null && result!=null && !result.equals("")){
					resultTo.setValue((String)result);
					som.getKbm().updateProperty(resultTo);
				}
			


			service.getExecutionInstance().setLastSuccess(sdf.format(time));

			//TODO to avoid fixed values
			factors.put("availability", "true");
			factors.put("location", "true");

			if(!service.getExecutionInstance().getRanking().equals("1"))
				service.getExecutionInstance().setRanking(
						som.getRe().getRating(som,service, factors)
						);

			LOG.trace(som.getId()+"Execution Instance to update : {}",service.getExecutionInstance().getHost().getId());

			som.getKbm().updateService(service);
			if(service.getStatusKey()!=null)
				som.getAe().updateActionStatus(service,"done"); 

			LOG.trace(som.getId()+": after service success:"+service.getName()+" result: "+ result+": resultTo:"+resultTo);
		} catch (Exception e) {
			som.getAe().updateActionStatus(service,"pending");
			LOG.error("{}: Error executing success: {}",som.getId(),e.getMessage(),e);

		}
	}

	/**
	 * Deliver service reply.
	 *
	 * @param service the service
	 * @param resultToStr the result to str
	 * @param reply the reply
	 * @param serviceConsumerUrl the service consumer url
	 * @param message the message
	 * @throws Exception the exception
	 */
	public static void deliverServiceReply(Service service,String resultToStr,ObjectNode reply,String serviceConsumerUrl,String message)throws Exception{
		ObjectNode params = null;
		params = JSONUtils.createObjectNode();
	
		params.put("message", message);
		params.putPOJO("service", service);
		params.put("resultTo", resultToStr);
		params.set("reply",reply);
		soca.makeCall(URI.create(serviceConsumerUrl), params);
		LOG.trace("{}: Sending reply back to {}",soca.getSom().getId(),serviceConsumerUrl);
		
	}
}
