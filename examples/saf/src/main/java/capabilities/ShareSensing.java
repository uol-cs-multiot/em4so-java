package capabilities;

import java.util.Date;
import java.util.Map;

import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.sensing.Observation;

public class ShareSensing extends Capability
{
	/** This methods writes in the property "lastValueSensing" the difference
	 * between 2 dates. */
	public void shareRecentObservation(Element element)
	{
		LOG.trace("In shareRecentObservation");
		Observation observation = soca.getSom().getKbm().getRecentObservation("activity");
		LOG.trace("Observation content : " + observation.getStringTime() + " , " + observation.getTime() + ", "
				+ observation.getValue() + " , " + observation.getProperty() + ", " + observation.getSensor());
		if (element == null)
			LOG.error("Element null shareRecentObservation");
		if (element.getAttributes() == null)
			LOG.error("getAttributes() null shareRecentObservation");
		else
			LOG.error("getAttributes not null shareRecentObservation, size : " + element.getAttributes().size());

		LOG.trace("BEFORE");
		for (Map.Entry entry : element.getAttributes().entrySet())
		{
			LOG.debug("ELEMENT[{}] : {}", entry.getKey(), entry.getValue());
		}
		if (element.getAttributes().get("lock").equals("true"))
		{
			//TODO Remove after tests
			if (observation.getValue().equals("true"))
			//if (true)
			{
				element.addAttribute("value", "5");
				element.addAttribute("lock", "false");
				element.addAttribute("startTime", "null");
			}
			else
			{
				element.addAttribute("startTime", observation.getStringTime());
				//TODO Replace with the good value
				//element.setValue(String.valueOf(getMinutesDiff(observation.getTime().getTime())));
				element.addAttribute("value", "5");
			}
		}
		else
		{
			if (observation.getValue().equals("true"))
			{
				element.addAttribute("lock", "true");
				element.addAttribute("startTime", observation.getStringTime());
				element.addAttribute("value", "5");
				//TODO Replace with the good value
				//element.setValue(String.valueOf(getMinutesDiff(observation.getTime().getTime())));
			}
		}
		//		LOG.debug("timestamp value : " + observation.getTime().getTime());
		//		LOG.debug("timestamp value now : " + System.currentTimeMillis());
		//		LOG.debug("value datediff : " + String.valueOf(getMinutesDiff(observation.getTime().getTime())));
		LOG.trace("AFTER");
		for (Map.Entry entry : element.getAttributes().entrySet())
		{
			LOG.debug("ELEMENT[{}] : {}", entry.getKey(), entry.getValue());
		}
		try
		{
			soca.getSom().getKbm().updateProperty(element);
		}
		catch (InterruptedException e)
		{
			LOG.debug("ERROR : Catch - shareRecentObservation : {}", e.getMessage(), e);
			e.printStackTrace();
		}
		LOG.debug("Return value : shareRecentObservation : " + getMinutesDiff(observation.getTime().getTime()));
	}

	@SuppressWarnings("deprecation")
	public void timeToMoment(Element element)
	{
		LOG.trace("In timeToMoment");
		Date date = new Date(System.currentTimeMillis());
		LOG.trace("date.getHours : {} | element.getAttributes : {}", date.getHours(),
				Integer.parseInt(element.getAttributes().get("endMorning")));
		if (date.getHours() >= Integer.parseInt(element.getAttributes().get("endMorning")))
		{
			if (!Boolean.parseBoolean(element.getAttributes().get("valueEndMorning")))
			{
				element.addAttribute("valueEndMorning", "true");
				try
				{
					soca.getSom().getKbm().updateProperty(element);
				}
				catch (InterruptedException e)
				{
					LOG.error("Exception : {},{}", e.getMessage(), e);
				}
			}
		}
		else
		{
			if (Boolean.parseBoolean(element.getAttributes().get("valueEndMorning")))
			{
				element.addAttribute("valueEndMorning", "false");
				try
				{
					soca.getSom().getKbm().updateProperty(element);
				}
				catch (InterruptedException e)
				{
					LOG.error("Exception : {},{}", e.getMessage(), e);
				}
			}
		}
		if (date.getHours() >= Integer.parseInt(element.getAttributes().get("startMorning")))
		{
			if (!Boolean.parseBoolean(element.getAttributes().get("valueStartMorning")))
			{
				element.addAttribute("valueStartMorning", "true");
				try
				{
					soca.getSom().getKbm().updateProperty(element);
				}
				catch (InterruptedException e)
				{
					LOG.error("Exception : {},{}", e.getMessage(), e);
				}
			}
		}
		else
		{
			if (Boolean.parseBoolean(element.getAttributes().get("valueStartMorning")))
			{
				element.addAttribute("valueStartMorning", "false");
				try
				{
					soca.getSom().getKbm().updateProperty(element);
				}
				catch (InterruptedException e)
				{
					LOG.error("Exception : {},{}", e.getMessage(), e);
				}
			}
		}
	}

	public void isWokenUp(Element element)
	{
		Observation observation = soca.getSom().getKbm().getRecentObservation("activity");
		if (!Boolean.parseBoolean(element.getAttributes().get("value")))
		{
			if (Boolean.parseBoolean(observation.getValue()))
			{
				element.addAttribute("value", "true");
				try
				{
					soca.getSom().getKbm().updateProperty(element);
				}
				catch (InterruptedException e)
				{
					LOG.error("Exception : {}, {}", e.getMessage(), e);
				}
			}
		}
	}

	/** This methods reset the count time in the property "lastSensingValue" */
	public void resetCountTime()
	{
		LOG.debug("In resetCOuntTime");
		Element element = new Element("saf_agent", "feature", "lastSensingValue");
		element.setValue("-1");
		try
		{
			soca.getSom().getKbm().updateProperty(element);
		}
		catch (InterruptedException e)
		{
			LOG.debug(e.getMessage());
		}
	}

	private static int getMinutesDiff(long time)
	{
		return (int) Math.abs((double) ((System.currentTimeMillis() - time) / (1000 * 60)));
	}

}
