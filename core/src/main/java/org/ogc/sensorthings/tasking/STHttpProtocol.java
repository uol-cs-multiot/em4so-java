package org.ogc.sensorthings.tasking;

import java.net.URI;
import java.util.Map.Entry;

// TODO: Auto-generated Javadoc
/**
 * The Class STHttpProtocol.
 */
public class STHttpProtocol {

/** The HTTP method. */
private String HTTPMethod;

/** The absolute resource path. */
private URI absoluteResourcePath;

/** The query string. */
private String queryString;

/** The fragment. */
private String fragment;

/** The message body. */
private String messageBody;

/** The headers. */
private Entry<String,String> headers;

/**
 * Gets the HTTP method.
 *
 * @return the HTTP method
 */
public String getHTTPMethod() {
	return HTTPMethod;
}

/**
 * Gets the absolute resource path.
 *
 * @return the absolute resource path
 */
public URI getAbsoluteResourcePath() {
	return absoluteResourcePath;
}

/**
 * Gets the query string.
 *
 * @return the query string
 */
public String getQueryString() {
	return queryString;
}

/**
 * Gets the fragment.
 *
 * @return the fragment
 */
public String getFragment() {
	return fragment;
}

/**
 * Gets the message body.
 *
 * @return the message body
 */
public String getMessageBody() {
	return messageBody;
}

/**
 * Gets the headers.
 *
 * @return the headers
 */
public Entry<String, String> getHeaders() {
	return headers;
}

/**
 * Sets the HTTP method.
 *
 * @param hTTPMethod the new HTTP method
 */
public void setHTTPMethod(String hTTPMethod) {
	HTTPMethod = hTTPMethod;
}

/**
 * Sets the absolute resource path.
 *
 * @param absoluteResourcePath the new absolute resource path
 */
public void setAbsoluteResourcePath(URI absoluteResourcePath) {
	this.absoluteResourcePath = absoluteResourcePath;
}

/**
 * Sets the query string.
 *
 * @param queryString the new query string
 */
public void setQueryString(String queryString) {
	this.queryString = queryString;
}

/**
 * Sets the fragment.
 *
 * @param fragment the new fragment
 */
public void setFragment(String fragment) {
	this.fragment = fragment;
}

/**
 * Sets the message body.
 *
 * @param messageBody the new message body
 */
public void setMessageBody(String messageBody) {
	this.messageBody = messageBody;
}

/**
 * Sets the headers.
 *
 * @param headers the headers
 */
public void setHeaders(Entry<String, String> headers) {
	this.headers = headers;
}
}
