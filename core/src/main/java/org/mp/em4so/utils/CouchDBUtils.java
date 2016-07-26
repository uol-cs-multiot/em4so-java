/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.mp.em4so.exceptions.DocNotSpecifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CouchDBUtils.
 */
public class CouchDBUtils {
	
	/** The db url. */
	private String dbUrl;
	
	/** The rest client. */
	private RESTClientUtils restClient;
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CouchDBUtils.class
			.getSimpleName());

	/**
	 * Instantiates a new couch DB utils.
	 *
	 * @param dbUrl the db url
	 * @param username the username
	 * @param password the password
	 */
	public CouchDBUtils(String dbUrl, String username, String password) {
		restClient = new RESTClientUtils(username + ":" + password); // TODO
																		// Change
		this.dbUrl = dbUrl;
	}

	/**
	 * Performs a query of all elements when the result is a simple String.
	 *
	 * @param <T> the generic type
	 * @param docToQuery the doc to query
	 * @param c the c
	 * @return the list
	 */
	
	public <T> List<T> queryAll(String docToQuery, Class<T> c) {
		return queryAll(docToQuery, c, true);	
	}
	
	
	/**
	 * Query all.
	 *
	 * @param <T> the generic type
	 * @param docToQuery the doc to query
	 * @param c the c
	 * @param includeDocs the include docs
	 * @return the list
	 */
	public <T> List<T> queryAll(String docToQuery, Class<T> c, boolean includeDocs) {
		String resultStr = null;
		String strUrl = null;
		if(!includeDocs){
			strUrl = dbUrl + "/_design/" + docToQuery + "/_list/all/all";
		}else{
			strUrl = dbUrl + "/_design/" + docToQuery + "/_list/all/all?include_docs=true";
		}
		LOG.trace("strUrl-> {}",strUrl);
		resultStr = restClient.doGet(strUrl);
		LOG.trace("resultStr->{}",resultStr);
		return JSONUtils.mapStringToObject(resultStr, c);
		
}
	
	
	/**
	 * Query doc.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @param args the args
	 * @return the t
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	public <T> T queryDoc(Class<T> c, Hashtable<String,?> args)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;

		strUrl = buildQueryString(c,args);
		
		LOG.debug("query is ->"+strUrl);
		resultStr = restClient.doGet(strUrl);
		LOG.debug("resultStr->"+resultStr);
		if(resultStr!=null && !resultStr.equals("") && !resultStr.equals("null")){
			return JSONUtils.<T>mapJsonToType(resultStr,c);
		}else{
			return null;
		}
		 
			
	}
	
	/**
	 * Query docs.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @param args the args
	 * @return the list
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	public <T> List<T> queryDocs(Class<T> c, Hashtable<String,?> args)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;

		strUrl = buildQueryString(c,args);
		
		LOG.debug("query is ->"+strUrl);
		resultStr = restClient.doGet(strUrl);
		LOG.debug("resultStr->"+resultStr);
		if(resultStr!=null && !resultStr.equals("") && !resultStr.equals("null")){
			return JSONUtils.<T>mapStringToObject(resultStr, c);
		}else{
			return null;
		}
		 
			
	}
	
	/**
	 * Generic method that query for the document with name c (lowercase) and using the (optional) arguments args. If no arguments are passed by default query all docs.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @param args the args
	 * @return the list
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	
	public <T> List<T> query(Class<T> c, Hashtable<String,?> args)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;
		
		strUrl = buildQueryString(c,args);
		resultStr = restClient.doGet(strUrl);
		LOG.debug("rest query"+c+"->"+strUrl);
		LOG.debug("resultStr->"+resultStr);	
		if(resultStr!=null){
			return JSONUtils.mapStringToObject(resultStr, c);
		}else{
			return null;
		}
		 
			
	}
	
	/**
	 * Query single.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @param args the args
	 * @return the t
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	public <T> T querySingle(Class<T> c, Hashtable<String,?> args)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;

		strUrl = buildQueryString(c,args);
		resultStr = restClient.doGet(strUrl);
		LOG.trace("rest query"+c+"->"+strUrl);
		LOG.trace("resultStr->"+resultStr);	
		System.out.println("rest query"+c+"->"+strUrl);
		System.out.println("resultStr->"+resultStr);	
		
		if(resultStr!=null && !resultStr.trim().equals("")){
			
			return JSONUtils.mapJsonToType(resultStr, c);
		}else{
			return null;
		}
	}

	/**
	 * Query single collection.
	 *
	 * @param <T> the generic type
	 * @param c the c
	 * @param args the args
	 * @return the t
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	public <T> T querySingleCollection(Class<T> c, Hashtable<String,?> args)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;

		strUrl = buildQueryString(c,args);
		resultStr = restClient.doGet(strUrl);
		
		LOG.trace("rest query"+c+"->"+strUrl);
		LOG.trace("resultStr->"+resultStr);
		
		if(resultStr!=null && !resultStr.trim().equals("")){
			return JSONUtils.mapJsonToType(resultStr, c);
		}else{
			return null;
		}
	}

	/**
	 * Generic method that query for the document with name c (lowercase) and using the (optional) arguments args. If no arguments are passed by default query all docs.
	 *
	 * @param <V> the value type
	 * @param args the args
	 * @param c the c
	 * @return the hash map
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	@SuppressWarnings("unchecked")
	public <V> HashMap<String,V> query(Hashtable<String,?> args,Class<V> c)throws DocNotSpecifiedException {
		String resultStr = null;
		String strUrl = null;
		strUrl = buildQueryString(c,args);
		LOG.trace("hst rest query->"+strUrl);
		resultStr = restClient.doGet(strUrl);
		LOG.trace("hst resultStr->"+resultStr);	
		
		return JSONUtils.<V>mapStringToObject(c,resultStr);
			
	}
	
	/**
	 * Update observation.
	 *
	 * @param strTailUrl the str tail url
	 */
	public void updateObservation(String strTailUrl) {
		
		String strUrl = null;
		
		// TODO CouchDB URL: to make it generic getting specific resources from
		// database configuration files
		strUrl = dbUrl +"/_design/observation/_update/onupdt/"+ strTailUrl;
		restClient.doPut(strUrl);
	}
	
	/**
	 * Update service.
	 *
	 * @param strTailUrl the str tail url
	 * @param jsonStr the json str
	 */
	public void updateService(String strTailUrl, String jsonStr) {
		try{
		String strUrl = null;
		strUrl = dbUrl +"/_design/service/_update/onupdt/"+ strTailUrl;
		LOG.trace(strUrl);
		restClient.doPutJson(strUrl, jsonStr);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Delete local service.
	 *
	 * @param key the key
	 */
	public void deleteLocalService(String key) {
		try{
		String strUrl = null;
		String body = null;
		strUrl = dbUrl +"/_design/service/_list/delete/local?key=\""+key+"\"";
		LOG.trace(strUrl);
		body = restClient.doGet(strUrl);
		LOG.trace(body);
		strUrl = dbUrl +"/_bulk_docs";
		restClient.doPostJSON(strUrl, body);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Update environment property.
	 *
	 * @param strTailUrl the str tail url
	 */
	public void updateEnvironmentProperty(String strTailUrl) {
		
		String strUrl = null;
		strUrl = dbUrl +"/_design/environment/_update/onupdt/"+ strTailUrl;
		restClient.doPut(strUrl);
	}
	
	/**
	 * Update service.
	 *
	 * @param strTailUrl the str tail url
	 */
	public void updateService(String strTailUrl) {
		
		String strUrl = null;
		strUrl = dbUrl +"/_design/service/_update/onupdt/"+ strTailUrl;
		restClient.doPut(strUrl);
	}
	
	/**
	 * Update property.
	 *
	 * @param strTailUrl the str tail url
	 * @param jsonStr the json str
	 */
	public void updateProperty(String strTailUrl, String jsonStr) {
		
		try{
			String strUrl = null;
			strUrl = dbUrl +"/_design/property/_update/onupdt/"+ strTailUrl;
			LOG.trace(strUrl);
			restClient.doPutJson(strUrl, jsonStr);
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
	
	/**
	 * Update message.
	 *
	 * @param strTailUrl the str tail url
	 * @param jsonStr the json str
	 */
	public void updateMessage(String strTailUrl, String jsonStr) {
		
		try{
			String strUrl = null;
			strUrl = dbUrl +"/_design/message/_update/onupdt/"+ strTailUrl;
			LOG.trace(strUrl);
			restClient.doPutJson(strUrl, jsonStr);
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
	
	
/**
 * Update host.
 *
 * @param strTailUrl the str tail url
 * @param jsonStr the json str
 */
public void updateHost(String strTailUrl, String jsonStr) {
		
	try{
		String strUrl = null;
		strUrl = dbUrl +"/_design/host/_update/onupdt/"+ strTailUrl;
		LOG.trace(strUrl);
		restClient.doPutJson(strUrl, jsonStr);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}
	}

	/**
	 * Gets the db url.
	 *
	 * @return the db url
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * Gets the rest client.
	 *
	 * @return the rest client
	 */
	public RESTClientUtils getRestClient() {
		return restClient;
	}

	/**
	 * Sets the db url.
	 *
	 * @param dbUrl the new db url
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * Sets the rest client.
	 *
	 * @param restClient the new rest client
	 */
	public void setRestClient(RESTClientUtils restClient) {
		this.restClient = restClient;
	}
	
	/**
	 * Builds the query string.
	 *
	 * @param c the c
	 * @param args the args
	 * @return the string
	 * @throws DocNotSpecifiedException the doc not specified exception
	 */
	private String buildQueryString(Class c,Hashtable <String,?> args)throws DocNotSpecifiedException{
		String strUrl = null,
				queryType = null
				,docToQuery = null
				,listType=null
				,includeDocs = null
				,reduce = null
				,group = null
				,propertyKey = null
				,doc = null;
		Collection<String> propertyKeys = null, params1 = null;
	
		if(c!= null){
			docToQuery = c.getSimpleName().toLowerCase();
		}else
			throw new DocNotSpecifiedException("Missing Class for Doc");
		
		if(args!=null){
			queryType = (String)args.get("queryType");
			listType = (String)args.get("listType");
			includeDocs = (String)args.get("includeDocs");
			propertyKey = (String)args.get("key");
			propertyKeys = (Collection<String>)args.get("keys");
			params1 = (Collection<String>)args.get("params1");
			reduce = (String)args.get("reduce");
			group = (String)args.get("group");
			doc = (String)args.get("doc");
		}
		if(doc!=null){
			docToQuery=doc;
		}
		if(queryType!=null && listType==null){
			strUrl = dbUrl + "/_design/" + docToQuery + "/_list/"+queryType+"/"+queryType;
		}else if(queryType!=null && listType!=null){
			strUrl = dbUrl + "/_design/" + docToQuery + "/_list/"+listType+"/"+queryType;
		}else{
			strUrl = dbUrl + "/_design/" + docToQuery + "/_list/all/all";
		}
		if(reduce!= null){
			strUrl += "?reduce="+reduce;
		}else{
			strUrl+="?reduce=false";
		}
		if(group!=null){
			strUrl+= "&group="+group;
		}
		if(includeDocs!=null)
			strUrl = strUrl + "&include_docs="+includeDocs;
		if(propertyKey!=null)
			if(!propertyKey.startsWith("["))
				strUrl = strUrl + "&key=\""+propertyKey+"\"";
			else
				strUrl = strUrl + "&key="+propertyKey;
		
		if(propertyKeys!=null && !propertyKeys.isEmpty()){	
			strUrl = strUrl + "&keys=[";
			for(String cKey: propertyKeys)
				strUrl += "\""+cKey+"\",";
			strUrl = strUrl.substring(0, strUrl.lastIndexOf(","));
			strUrl += "]";
		}
		if(params1!=null && !params1.isEmpty()){	
			strUrl = strUrl + "&params1=[";
			for(String param: params1)
				strUrl += "\""+param+"\",";
			strUrl = strUrl.substring(0, strUrl.lastIndexOf(","));
			strUrl += "]";
		}
		return strUrl;
	}
	
}
