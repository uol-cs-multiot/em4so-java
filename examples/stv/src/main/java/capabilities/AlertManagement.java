package capabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;

public class AlertManagement extends Capability
{

	// private TelegramBot BOT;
	private int lastUpdateId;

	/** Telegram */
	protected final TelegramBot BOT = TelegramBotAdapter.build("<TOKEN>");

	public enum Interaction
	{
		TEMPEARATURE, MOTION;
	}

	public enum Action
	{
		REGISTER, DEREGISTER, SEND_MESSAGE, ERROR, INTERACTION
	}

	private Action action;
	public List<Long> users;

	public AlertManagement()
	{
		super();
		lastUpdateId = 662306126 + 1;
		LOG.trace("In AlertManagement constructor");
		users = new ArrayList<Long>();
		users.add(new Long(224322144));
	}

	public void notifyUser(String content, Element user)
	{
		HashMap<String, String> userAttributes = user.getAttributes();
		LOG.info("message:" + content + userAttributes.get("message") + " displayed to user: "
				+ userAttributes.get("name"));
		System.out.println("message:" + content + userAttributes.get("message") + " displayed to user: "
				+ userAttributes.get("name"));
	}

	public void listenToTelegram()
	{

		List<Update> updates = BOT.execute(new GetUpdates().limit(100).offset((int) lastUpdateId).timeout(0)).updates();
		LOG.trace("taille liste update : " + updates.size());
		lastUpdateId = updates.get(updates.size() - 1).updateId() + 1;
		LOG.trace("LASTUDPATEID " + lastUpdateId);
		for (Update update : updates)
		{
			LOG.trace(update.message().chat().id() + " send a new message : '" + update.message().text()
					+ "', update id : " + update.updateId());
			System.out.println(update.message().chat().id() + " send a new message : '" + update.message().text()
					+ "', update id : " + update.updateId());

			// Dispatcher
			switch (action = textToAction(update.message().text()))
			{
				case DEREGISTER:
					System.out.println("Deregister");
					break;
				case REGISTER:
					System.out.println("Register");
					break;
				case INTERACTION:
					System.out.println("Interaction");
					break;
				default:
					System.out.println("Error");
			}
		}
	}

	private Action textToAction(String message)
	{
		if (message.toLowerCase().contains("deregister"))
			return Action.DEREGISTER;
		else if (message.toLowerCase().contains("register"))
			return Action.REGISTER;
		else if (message.toLowerCase().contains("interaction"))
			return Action.INTERACTION;
		return Action.ERROR;
	}

}
