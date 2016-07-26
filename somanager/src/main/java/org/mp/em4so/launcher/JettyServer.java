/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.launcher;

//import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletHandler;
// TODO: Auto-generated Javadoc
//import org.eclipse.jetty.util.log.Log;

/**
 * The Class JettyServer.
 */
public class JettyServer {

	/** The server. */
	private Server server;
	
	/**
	 * Instantiates a new jetty server.
	 *
	 * @param runningPort the running port
	 */
	public JettyServer(Integer runningPort) {
		server = new Server(runningPort);
	}
	
	/**
	 * Sets the handler.
	 *
	 * @param contexts the new handler
	 */
	public void setHandler(ContextHandlerCollection contexts) {
		server.setHandler(contexts);
	}
	
	/**
	 * Sets the handler.
	 *
	 * @param servletHandler the new handler
	 */
	public void setHandler(ServletHandler servletHandler) {
		server.setHandler(servletHandler);
	}
	
	/**
	 * Start.
	 *
	 * @throws Exception the exception
	 */
	public void start() throws Exception {
		server.start();
	}
	
	/**
	 * Stop.
	 *
	 * @throws Exception the exception
	 */
	public void stop() throws Exception {
		server.stop();
		server.join();
	}
	
	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	public boolean isStarted() {
		return server.isStarted();
	}
	
	/**
	 * Checks if is stopped.
	 *
	 * @return true, if is stopped
	 */
	public boolean isStopped() {
		return server.isStopped();
	}
	
//	public void addMBean(MBeanContainer mbc){
//		server.getContainer().addEventListener(mbc);
//		server.getContainer().addBean(mbc);
//		server.getContainer().addBean(Log.getLog());
//	}
}