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
	protected final TelegramBot BOT = TelegramBotAdapter.build("262473126:AAGF5zvWXpssuP-SkedQkGUdbdn16A-sRiQ");

	public enum Action
	{
		REGISTER, DEREGISTER, INTERACTION, SEND_MESSAGE
	}

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
		LOG.trace("In listenToTelegram method");
		List<Update> updates = BOT.execute(new GetUpdates().limit(0).offset((int) lastUpdateId).timeout(0)).updates();
		LOG.trace("lastUpdateId : " + lastUpdateId);
		LOG.trace("taille liste update : " + updates.size());
		lastUpdateId = updates.get(updates.size() - 1).updateId() + 1;
		for (Update update : updates)
		{
			LOG.trace("$$" + update.message().chat().id() + " send a message : '" + update.message().text()
					+ "', update id : " + update.updateId());
			boolean hasUpdate = (update == null ? true : false);
			// TODO : Dispatcher
		}

	}
}
