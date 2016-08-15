/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.actuating;

import java.util.HashMap;

import org.mp.em4so.agents.SOControlAgent;
import org.mp.em4so.model.common.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

// TODO: Auto-generated Javadoc
/** The Class Capability. */
public abstract class Capability
{

	/** The services. */
	protected HashMap<String, Service> services;

	/** The log. */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/** Telegram . */ //TODO : Change
	protected final TelegramBot BOT = TelegramBotAdapter.build("262473126:AAGF5zvWXpssuP-SkedQkGUdbdn16A-sRiQ");

	/** The soca. */
	protected SOControlAgent soca;

	/** The sender url. */
	protected String senderUrl;

	/** The executing role id. */
	protected String executingRoleId;

	/** Instantiates a new capability. */
	public Capability()
	{

	}

	/** Instantiates a new capability.
	 *
	 * @param soca the soca */
	public Capability(SOControlAgent soca)
	{
		this.soca = soca;
	}

	/** Gets the SO control agent.
	 *
	 * @return the SO control agent */
	public SOControlAgent getSOControlAgent()
	{
		return soca;
	}

	/** Sets the SO control agent.
	 *
	 * @param soca the new SO control agent */
	public void setSOControlAgent(SOControlAgent soca)
	{
		this.soca = soca;
	}

	/** Gets the sender url.
	 *
	 * @return the sender url */
	public String getSenderUrl()
	{
		return senderUrl;
	}

	/** Sets the sender url.
	 *
	 * @param senderUrl the new sender url */
	public void setSenderUrl(String senderUrl)
	{
		this.senderUrl = senderUrl;
	}

	/** Gets the services.
	 *
	 * @return the services */
	public HashMap<String, Service> getServices()
	{
		return services;
	}

	/** Sets the services.
	 *
	 * @param services the services */
	public void setServices(HashMap<String, Service> services)
	{
		this.services = services;
	}

	/** Gets the executing role id.
	 *
	 * @return the executing role id */
	public String getExecutingRoleId()
	{
		return executingRoleId;
	}

	/** Sets the executing role id.
	 *
	 * @param executingRoleId the new executing role id */
	public void setExecutingRoleId(String executingRoleId)
	{
		this.executingRoleId = executingRoleId;
	}

}
