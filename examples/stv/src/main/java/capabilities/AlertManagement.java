package capabilities;

import java.util.ArrayList;
import java.util.List;

import org.mp.em4so.model.actuating.Capability;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;

public class AlertManagement extends Capability
{

	private TelegramBot bot;
	private int lastUpdateId;

	public enum Action
	{
		REGISTER, DEREGISTER, INTERACTION, SEND_MESSAGE
	};

	public List<Long> users;

	/*
	 * public void notifyUser(String content, Element user) { HashMap<String,
	 * String> userAttributes = user.getAttributes(); LOG.info("message:" +
	 * content + userAttributes.get("message") + " displayed to user: " +
	 * userAttributes.get("name")); System.out.println("message:" + content +
	 * userAttributes.get("message") + " displayed to user: " +
	 * userAttributes.get("name")); }
	 */

	public void listenerTelegram(String msg)
	{
		System.out.println(
				")))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
		LOG.trace("-- IN THE METHOD NOTIFYUSER --");
		bot = TelegramBotAdapter.build("262473126:AAGF5zvWXpssuP-SkedQkGUdbdn16A-sRiQ");
		lastUpdateId = 662306098;
		users = new ArrayList<Long>();
		users.add(new Long(224322144));

		Update update = bot.execute(new GetUpdates().limit(0).offset((int) lastUpdateId).timeout(0)).updates().get(0);
		boolean hasUpdate = (update == null ? true : false);
		LOG.info("Listener --> has update : " + hasUpdate);
		if (update != null)
		{
			lastUpdateId = update.updateId() + 1;

			// DISPATCHER
			switch (textToAction(update.message().text()))
			{
				case REGISTER:
					System.out.println("Register");
					break;
				case DEREGISTER:
					System.out.println("Deregister");
					break;
				case INTERACTION:
					System.out.println("Interaction");
					break;
				default:
					System.err.println("ERROR : Unknow message");
			}
		}
	}

	private Action textToAction(String message)
	{
		if (message.toLowerCase().contains("deregister"))
			return Action.DEREGISTER;
		else if (message.toLowerCase().contains("register"))
			return Action.REGISTER;
		return Action.INTERACTION;
	}


}