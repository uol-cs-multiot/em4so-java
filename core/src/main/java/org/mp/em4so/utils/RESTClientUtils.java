/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * This class encapsulates calls to REST Client API. It can be replaced by
 * manually HTTP Calls.
 * @author mp
 * @version 1.0
 * @updated 17-Apr-2015 20:13:51
 */
public class RESTClientUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RESTClientUtils.class.getSimpleName());
	
	/** The mime type. */
	private String mimeType;
	
	/** The basic auth. */
	private String basicAuth;

	/**
	 * Instantiates a new REST client utils.
	 *
	 * @param userpass the userpass
	 */
	public RESTClientUtils(String userpass) {
		this.mimeType = "application/json";
		basicAuth = "Basic "
				+ new String(new Base64().encode(userpass.getBytes()));
		// username and
		// password to a
		// file

	}
	


	/**
	 * Execute a GET method call with the strUrl query received. 
	 *
	 * @param strUrl the str url
	 * @return the string
	 */
	public String doGet(String strUrl){
		HttpURLConnection conn = null;
		String result = null;

		try {

			conn = setUpConnection(strUrl, "GET");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", mimeType);
			result = convertInputStreamToString((InputStream) conn.getContent());
			checkResponseCode(conn);
			
		} catch (MalformedURLException e) {

			LOG.error(e.getMessage(),e);

		} 
		catch (IOException e) {
			result = null;
			LOG.trace("Warning: {}: {}",e.getMessage(),e);

		} 
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		return result;
	}
	
	/**
	 * Execute a PUT method call with the strUrl query received.
	 *
	 * @param strUrl the str url
	 */
	public  void doPut(String strUrl) {
		HttpURLConnection conn = null;
		
		try {
			
			strUrl = strUrl.replaceAll(" ","%20");		
			conn = setUpConnection(strUrl, "PUT");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", mimeType);
			conn.setDoOutput(true);

			LOG.trace("PUT ----->"+strUrl);
			
			InputStream is = (InputStream)conn.getContent();
			
			checkResponseCode(conn);

		}  catch (IOException e) {

			LOG.error(e.getMessage(),e);

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

	}
	
	/**
	 * Execute a PUT method call for a JSON form with the strUrl url and JSON form received.
	 *
	 * @param strUrl the str url
	 * @param jsonStr the json str
	 */
	public  void doPutJson(String strUrl,String jsonStr){
		HttpURLConnection conn = null;
		try {
		conn = setUpConnection(strUrl, "PUT");
		conn.setRequestMethod("PUT");
		conn.setDoOutput(true);
		conn.addRequestProperty("Content-Type", "application/json");
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		
			out.write(jsonStr);
			out.close();
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			LOG.trace("response PUT: {}",response);
			
			checkResponseCode(conn);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		
	}

	/**
	 * Execute a POST method call for a JSON form with the strUrl url and JSON form received.
	 *
	 * @param strUrl the str url
	 * @param bodyStr the body str
	 */
	public  void doPostJSON(String strUrl, String bodyStr) {
		HttpURLConnection conn = null;
		
		try {
			strUrl = strUrl.replaceAll(" ","%20");
			strUrl = strUrl.replaceAll("\"","'");
			conn = setUpConnection(strUrl, "POST");
			conn.setRequestProperty("Authorization", basicAuth);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", mimeType);
			conn.addRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("Content-Type", "multipart/form-data;enctype="+mimeType);
		
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(bodyStr);
			out.close();
			
			LOG.trace(strUrl);
		
		
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			LOG.trace(response.toString());
			
			checkResponseCode(conn);

		}  catch (IOException e) {

			LOG.error(e.getMessage(),e);

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

	}
	
	/**
	 * Convert the input stream received to a String.
	 *
	 * @param is the is
	 * @return the string
	 */
	public String convertInputStreamToString(InputStream is) {
		String result = null;
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(is));
			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
//			LOG.trace(sb.toString());

		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(),e);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Open a new connection for the given url and method.
	 *
	 * @param strUrl the str url
	 * @param method the method
	 * @return the http URL connection
	 */
	public HttpURLConnection setUpConnection(String strUrl, String method) {

		HttpURLConnection conn = null;
		URL url;
		try {
			url = new URL(strUrl);
			
			conn = (HttpURLConnection) url.openConnection();
			// repository
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {

			LOG.error(e.getMessage(),e);
		}
		return conn;
	}

	/**
	 * Check response code received after query.
	 *
	 * @param conn the conn
	 * @throws RuntimeException the runtime exception
	 */
	public void checkResponseCode(HttpURLConnection conn)
			throws RuntimeException {
		try {
			if (conn.getResponseCode() >=300 && conn.getResponseCode() <200 ) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
		} catch (IOException e) {

			LOG.error(e.getMessage(),e);
		}
	}
}
