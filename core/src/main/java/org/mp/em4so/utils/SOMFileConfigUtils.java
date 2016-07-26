/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.capabilities.Config;



// TODO: Auto-generated Javadoc
/**
 * The Class SOMFileConfigUtils.
 */
public class SOMFileConfigUtils {

/** The som yaml. */
private static Config somYaml; 

/** The Constant LOG. */
private static final Logger LOG = LoggerFactory.getLogger(SOMFileConfigUtils.class.getSimpleName());

/** The Constant ltmemoryField. */
private static final String ltmemoryField = "ltmemory";

/** The Constant urlField. */
private static final String urlField = "url";

/** The Constant userField. */
private static final String userField = "user";

/** The Constant passField. */
private static final String passField = "pass";

/** The Constant kbPrefixField. */
private static final String kbPrefixField = "kb_prefix";

/** The Constant capabilitiesSection. */
private static final String capabilitiesSection = "capabilities";

/** The Constant somanagerSection. */
private static final String somanagerSection = "som";

/** The Constant transportSection. */
private static final String transportSection = "transport";

/** The Constant capabilitiesPkg. */
private static final String capabilitiesPkg = "pkg";

/** The Constant sensing. */
private static final String sensing = "sense";

/** The Constant SERVER_PORT_FIELD. */
private static final String SERVER_PORT_FIELD = "port";

/** The Constant SOM_NAME_FIELD. */
private static final String SOM_NAME_FIELD = "name";

/** The Constant SOM_CATEGORIES_FIELD. */
private static final String SOM_CATEGORIES_FIELD = "categories";

/** The Constant SOM_HOST_ROLES. */
private static final String SOM_HOST_ROLES = "roles";

/** The running path. */
public static String RUNNING_PATH = ""; 


/**
 * Gets the KB url.
 *
 * @return the KB url
 */
public static String getKBUrl(){
	return somYaml.get(ltmemoryField).get(urlField).asText();
	
}

/**
 * Gets the KB user.
 *
 * @return the KB user
 */
public static String getKBUser(){
	return somYaml.get(ltmemoryField).get(userField).asText();
	
}

/**
 * Gets the KB pass.
 *
 * @return the KB pass
 */
public static String getKBPass(){
	return somYaml.get(ltmemoryField).get(passField).asText();
	
}

/**
 * Gets the KB prefix.
 *
 * @return the KB prefix
 */
public static String getKBPrefix(){
	return somYaml.get(ltmemoryField).get(kbPrefixField).asText();
	
}

/**
 * Gets the capabilities pkg.
 *
 * @return the capabilities pkg
 */
public static String getCapabilitiesPkg(){
	return somYaml.get(capabilitiesSection).get(capabilitiesPkg).asText();
	
}

/**
 * Gets the sensing method.
 *
 * @return the sensing method
 */
public static String getSensingMethod(){
	return somYaml.get(capabilitiesSection).get(sensing).asText();
}

/**
 * Gets the server port.
 *
 * @return the server port
 */
public static String getServerPort(){
	return somYaml.get(transportSection).get(SERVER_PORT_FIELD).asText();
}

/**
 * Gets the name.
 *
 * @return the name
 */
public static String getName(){
	return somYaml.get(somanagerSection).get(SOM_NAME_FIELD).asText();
}

/**
 * Gets the categories.
 *
 * @return the categories
 */
public static String getCategories(){
	return somYaml.get(capabilitiesSection).get(SOM_CATEGORIES_FIELD).asText();
}

/**
 * Gets the roles.
 *
 * @return the roles
 */
public static String getRoles(){
	return somYaml.get(somanagerSection).get(SOM_HOST_ROLES).asText();
}

/**
 * Gets the som yaml.
 *
 * @return the som yaml
 */
public static Config getSomYaml() {
	return SOMFileConfigUtils.somYaml;
}

/**
 * Sets the som yaml.
 *
 * @param somYaml the new som yaml
 */
public static void setSomYaml(Config somYaml) {
	SOMFileConfigUtils.somYaml = somYaml;
}

}
