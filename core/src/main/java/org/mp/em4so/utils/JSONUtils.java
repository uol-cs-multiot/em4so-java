/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;



// TODO: Auto-generated Javadoc
/**
 * The Class JSONUtils.
 */
public abstract class JSONUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(JSONUtils.class.getSimpleName());
	
	/** The mapper. */
	private static ObjectMapper mapper;
	
	static{
		getInstance();
	}
	
	/**
	 * Gets the single instance of JSONUtils.
	 *
	 * @return single instance of JSONUtils
	 */
	private static void getInstance(){
		if(mapper == null) mapper = new ObjectMapper();
	}
	
	/**
	 * Creates the object node.
	 *
	 * @return the object node
	 */
	public static ObjectNode createObjectNode(){
		return mapper.createObjectNode();
	}
	
	/**
	 * Map string to object.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param classType the class type
	 * @return the list
	 */
	public static <T> List<T> mapStringToObject(String jsonStr, Class<T> classType){
		
		List<T> results = null;
		
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
//			mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		//	JavaType type = mapper.getTypeFactory().constructType(classType);
			//results = mapper.readValue(jsonStr.getBytes(), type);
			try {
				results = mapper.readValue(jsonStr.getBytes(), 
						mapper.getTypeFactory().constructCollectionType(List.class, classType)
						);
				
				
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage(),e);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage(),e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOG.error(e.getMessage(),e);
			}
			
		
		

//		LOG.trace(results.toString());//
		
		return results;
	}
	
	
	
	/**
	 * Collection to objec node.
	 *
	 * @param args the args
	 * @return the object node
	 */
	public static ObjectNode collectionToObjecNode(Map<String,?> args){
		
		return mapper.valueToTree(args);
		
	}
	
	/**
	 * List to objec node.
	 *
	 * @param <T> the generic type
	 * @param args the args
	 * @param c the c
	 * @return the object node
	 */
	public static <T>ObjectNode listToObjecNode(List<T> args,Class <T>c){
		JavaType type = mapper.getTypeFactory().constructCollectionLikeType(List.class, c);
		return mapper.convertValue(args,type);
		
	}
	
	/**
	 * Object to objec node.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the object node
	 */
	public static <T> ObjectNode objectToObjecNode(T object){
		ObjectNode on = null;
		try{
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		on = mapper.valueToTree(object);
		}catch (Exception e){
			LOG.error("Error parsing Object: {}: {}",object,e.getMessage(),e);
		}
		return on; 	
	}
	
	/**
	 * Object node to java type.
	 *
	 * @param <T> the generic type
	 * @param objectNode the object node
	 * @param c the c
	 * @return the t
	 */
	public static <T>T objectNodeToJavaType(JsonNode objectNode,Class <T> c){
		T result = null;
		JavaType type = mapper.getTypeFactory().constructType(c);
		try {
			result = mapper.readValue(objectNode.toString(), type);
		} catch (JsonParseException e) {
			LOG.error("Error parsing JsonNode to Object: {}: {}",objectNode,e.getMessage(),e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOG.error("Error parsing JsonNode to Object: {}: {}",objectNode,e.getMessage(),e);
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("Error parsing JsonNode to Object: {}: {}",objectNode,e.getMessage(),e);
			e.printStackTrace();
		}
		return result;
			
	}
	
	/**
	 * Json node to object.
	 *
	 * @param <T> the generic type
	 * @param objectNode the object node
	 * @param c the c
	 * @return the t
	 */
	public static <T> T jsonNodeToObject(JsonNode objectNode,Class <T> c){
		T result = null;
		try {
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false); 
			result = (T)mapper.reader(c).readValue(objectNode);
		} catch (JsonProcessingException e) {
			LOG.error("Error parsing JsonNode to Object: {}",objectNode,e.getMessage(),e);
		} catch (IOException e) {
			LOG.error("Error reading JsonNode to Object: {}",objectNode,e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Json node to object.
	 *
	 * @param <T> the generic type
	 * @param objectNode the object node
	 * @return the t
	 */
	public static <T> T jsonNodeToObject(JsonNode objectNode){
		T result = null;
		try {
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false); 
			result = (T)mapper.reader().readValue(objectNode);
		} catch (JsonProcessingException e) {
			LOG.error("Error parsing JsonNode to Object: {}",objectNode,e.getMessage(),e);
		} catch (IOException e) {
			LOG.error("Error reading JsonNode to Object: {}",objectNode,e.getMessage(),e);
		}
		return result;
	}
	
	/**
	 * Object to string.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the string
	 */
	public static <T>String objectToString(T object){
		String str = null;
		try{
		str = mapper.valueToTree(object).asText();
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
		return str;
		
	}
	
	/**
	 * Convert object to string.
	 *
	 * @param <T> the generic type
	 * @param object the object
	 * @return the string
	 */
	public static <T>String convertObjectToString(T object){
		String str = null;
		try{
		str = mapper.writeValueAsString(object);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
		return str;
		
	}
	
	/**
	 * Map string to object.
	 *
	 * @param <V> the value type
	 * @param classType the class type
	 * @param jsonStr the json str
	 * @return the hash map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<V> HashMap<String,V> mapStringToObject(Class<V> classType,String jsonStr){
		
		HashMap<String,V> results = null;
			
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			try {
				JavaType type = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, classType);
				results = mapper.readValue(jsonStr.getBytes(), type);
				
			} catch (JsonParseException e) {
				LOG.error(e.getMessage(),e);
			} catch (JsonMappingException e) {
				LOG.error(e.getMessage(),e);
			} catch (IOException e) {
				LOG.error(e.getMessage(),e);
			}
			
		
		for(String key:results.keySet()){
			LOG.trace("the result keys are: "+key);//
		}
		
		return results;
	}
	
	public static<V> Hashtable<String,V>tableJsonNToObject(Class<V> classType,JsonNode objectNode){
		Hashtable<String,V> results = null;
		
		mapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		try {
			JavaType type = mapper.getTypeFactory().constructMapType(Hashtable.class, String.class, classType);
			results = mapper.reader(type).readValue(objectNode);
			
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
		
	
	for(String key:results.keySet()){
		LOG.debug("the result keys are: "+key);//
	}
	
	return results;
		
	}
	
/**
 * Table string to object.
 *
 * @param <V> the value type
 * @param classType the class type
 * @param jsonStr the json str
 * @return the hashtable
 */
public static<V> Hashtable<String,V> tableStringToObject(Class<V> classType,String jsonStr){
		
		Hashtable<String,V> results = null;
			
			mapper.configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			try {
				JavaType type = mapper.getTypeFactory().constructMapType(Hashtable.class, String.class, classType);
				results = mapper.readValue(jsonStr.getBytes(), type);
				
			} catch (JsonParseException e) {
				LOG.error(e.getMessage(),e);
			} catch (JsonMappingException e) {
				LOG.error(e.getMessage(),e);
			} catch (IOException e) {
				LOG.error(e.getMessage(),e);
			}
			
		
		for(String key:results.keySet()){
			LOG.trace("the result keys are: "+key);//
		}
		
		return results;
	}
	
	
	/**
	 * Map json to type.
	 *
	 * @param <T> the generic type
	 * @param json the json
	 * @param c the c
	 * @return the t
	 */
	public static <T>T mapJsonToType(String json, Class<T> c){
		T result = null;
		try {
//		try {
//			result = (T)c.newInstance();
//		} catch (InstantiationException e) {
//			LOG.error(e.getMessage(),e);
//		} catch (IllegalAccessException e) {
//			LOG.error(e.getMessage(),e);
//		}
				
		
		mapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		LOG.trace("json to map: "+json+" <--class:---> "+c.getName());
		result = mapper.readValue(json, c);
		} catch (JsonParseException e) {
			LOG.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
		return result; 
	}
	

	/**
	 * Map object to type.
	 *
	 * @param object the object
	 * @param c the c
	 * @return the object
	 */
	public static Object mapObjectToType(Object object, Class<?> c){
		Object result = null;
		
		mapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		result = mapper.convertValue(object, c);
		
		return result; 
	}
	

}
