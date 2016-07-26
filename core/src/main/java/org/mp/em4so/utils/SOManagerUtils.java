/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mp.em4so.model.network.TransportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almende.eve.deploy.Boot;

// TODO: Auto-generated Javadoc
/**
 * The Class SOManagerUtils.
 */
public class SOManagerUtils {
	
	
	/** The Constant LOG. */
private static final Logger LOG = LoggerFactory.getLogger(SOManagerUtils.class.getSimpleName());
    
    /** The Constant DOT. */
    private static final char DOT = '.';

    /** The Constant SLASH. */
    private static final char SLASH = '/';

    /** The Constant CLASS_SUFFIX. */
    private static final String CLASS_SUFFIX = ".class";

    /** The Constant BAD_PACKAGE_ERROR. */
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";
    
    /** The Constant STATUS_ONPROGRESS. */
    public static final String STATUS_ONPROGRESS = "onprogress";
    
    /** The Constant STATUS_DONE. */
    public static final String STATUS_DONE = "done";

    
    /** The port. */
    public static String port ="8080";
	
	/**
	 * Gets the short action name.
	 *
	 * @param longName the long name
	 * @return the short action name
	 */
	public static String getShortActionName(String longName){
		System.out.println("long name:"+longName+" - lindex: "+longName.lastIndexOf("/")+" - length:"+longName.length());
		
		return longName.substring(longName.lastIndexOf("/")+1, longName.length());
	}
	    

/**
 * Find class.
 *
 * @param scannedPackage the scanned package
 * @return the list
 */
//		}
    public static List<Class<?>> findClass(String scannedPackage) {
    	
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }
	
	/**
	 * Find jar class.
	 *
	 * @param scannedPath the scanned path
	 * @return the list
	 */
	public static List<Class<?>> findJarClass(String scannedPath){
		String className = null;
		ZipInputStream zip = null;
		List<Class<?>> classes = null;
		try {
			zip = new ZipInputStream(new FileInputStream(scannedPath));
			classes = new ArrayList<Class<?>>();
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			
		    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
		        // This ZipEntry represents a class. Now, what class does it represent?
		        className = entry.getName().replace(SLASH, DOT); // including ".class"
		        className = className.substring(0, className.length() - ".class".length());
		        LOG.trace("class: "+className +" found in jar "+scannedPath+"!");
//		        classes.add(Class.forName(className,true,Thread.currentThread().getContextClassLoader()));
		        LOG.trace("CLASSPATH here: -->"+System.getProperty("java.class.path")+"<--");
		        classes.add(Class.forName(className,true,ClassLoader.getSystemClassLoader()));
		        
		    }
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
		return classes;

	}

	    /**
    	 * Find.
    	 *
    	 * @param file the file
    	 * @param scannedPackage the scanned package
    	 * @return the list
    	 */
    	private static List<Class<?>> find(File file, String scannedPackage) {
	        List<Class<?>> classes = new ArrayList<Class<?>>();
	        String resource = scannedPackage + DOT + file.getName();
	        if (file.isDirectory()) {
	            for (File child : file.listFiles()) {
	                classes.addAll(find(child, resource));
	            }
	        } else if (resource.endsWith(CLASS_SUFFIX)) {
	            int endIndex = resource.length() - CLASS_SUFFIX.length();
	            String className = resource.substring(0, endIndex);
	            try{
	            	System.out.println(" en utils"+Thread.currentThread().getContextClassLoader());
	            	//Thread.currentThread().getContextClassLoader().loadClass(className);
	            classes.add(Class.forName(className,true,Thread.currentThread().getContextClassLoader()));
//	            	classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
	            }catch(ClassNotFoundException cnfe){
	            	LOG.error(cnfe.getMessage(),cnfe);
	            }
	            
	        }
	        return classes;
	    }
	    
	    /**
    	 * Load.
    	 *
    	 * @param transportConfig the transport config
    	 * @param cl the cl
    	 */
    	public static void load(TransportConfig transportConfig, ClassLoader cl){
	    	
	    	Boot.boot(AgentPlatformUtils.getAgentConfiguration(transportConfig.getAddress()), cl);
	    }
	    
	    
}
