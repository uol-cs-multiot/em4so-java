/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// TODO: Auto-generated Javadoc
/**
 * The Class NetworkUtils.
 *
 * @author mp
 */
public class NetworkUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(NetworkUtils.class.getSimpleName());
	
	/** Regular expresion for an IPv4 address. */
	private static final String ip4regex = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	/**
	 * Get host addresses from available NICs.
	 *
	 * @return the host address
	 */
	public static String getHostAddress() {
        Enumeration<NetworkInterface> nets = null;
        Enumeration<InetAddress> inetAddresses = null;
        String address = "127.0.0.1";
        List<NetworkInterface> netsColl;
        boolean useLoopback=false;
		try {
			
			nets = NetworkInterface.getNetworkInterfaces();
			netsColl = 	Collections.list(nets);
			if(netsColl.size()==1){
				useLoopback = true;
			}
        for (NetworkInterface netint : netsColl){
			inetAddresses = netint.getInetAddresses();
			if(!netint.isLoopback())
        	for (InetAddress inetAddress : Collections.list(inetAddresses)) {
        		if(isChosenAddress(inetAddress)){
        			address = inetAddress.getHostAddress();
        			LOG.trace("Found address: "+address+" is chosen? "+isChosenAddress(inetAddress));
        		}
        			
        }
        }
		} catch (SocketException e1) {
			LOG.error("Error getting Host address: {}",e1.getMessage(),e1);
		}
		return address;
    }

  /**
   * Identifies if the address passed through is the chosen based on the configured regex.
   *
   * @param inetAddress the inet address
   * @return true, if is chosen address
   */
public static boolean isChosenAddress(InetAddress inetAddress)  {
	  boolean result = false;
	   if(inetAddress.getHostAddress().matches( ip4regex)){
        	result = true;
        }
        return result;
     }
	  
/**
 * Checks if the host is available on the port given.
 *
 * @param host the host
 * @param port the port
 * @param timeout the timeout
 * @return true, if is host available
 */
public static boolean isHostAvailable(String host, int port, int timeout) {
		    try (Socket socket = new Socket()) {
		        socket.connect(new InetSocketAddress(host, port), timeout);
		        return true;
		    } catch (IOException e) {
		        return false; 
		    }
		}
}
