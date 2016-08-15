package capabilities;

import java.util.HashMap;
import java.util.List;

import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

public class AlertManagement extends Capability
{

	// private TelegramBot BOT;
	private int lastUpdateId;

	/** Telegram */

	public AlertManagement()
	{
		super();
		lastUpdateId = 0;
		LOG.trace("In AlertManagement constructor");
	}

	public void notifyUser(String content, Element user)
	{
		HashMap<String, String> userAttributes = user.getAttributes();
		LOG.info("message:" + content + userAttributes.get("message") + "displayed to user: "
				+ userAttributes.get("name"));
		System.out.println("message:" + content + userAttributes.get("message") + " displayed to user: "
				+ userAttributes.get("name"));
	}

	private void sendSimpleTelegramMessage(String content, String telegramId)
	{
		BOT.execute(new SendMessage(telegramId, format(content)).parseMode(ParseMode.HTML));
	}

	//TODO : See with Marco the utility or not --> User friendly ??
	//	private void sendKeyboardsTelegramMessage(String content, String telegram, String[] textKeyboards)
	//	{
	//		Keyboard keyboards = new ReplyMarkupKeyboard();
	//		String[][] tk = new String[textKeyboards.length][1];
	//		
	//			BOT.execute(new SendMessage(telegramId, format("content")).parseMode(ParseMode.HTML).replyMarkup(replyMarkup)
	//	}

	/** Static methods in order to format message in bold in Telegram
	 * 
	 * @param message : message to format
	 * @return formated message */
	private static String format(String message)
	{
		return "<b>&#128172 " + message + "</b>";
	}

	private void updateNewMessage(boolean action)
	{
		Element element = new Element("stv_agent", "feature", "newMessage");
		element.setValue(String.valueOf(action));
		try
		{
			soca.getSom().getKbm().updateProperty(element);
		}
		catch (InterruptedException e)
		{
			LOG.error("Impossible to access to the Database");
		}
	}

	public void notifyUserByTelegram(Element content, String telegramId)
	{
		LOG.info("In notifyUserByTelegram");
		String text = "newMessage";
		Element element = content;
		if (content == null)
			text = "content null";

		//		if (content.getAttributes() == null)
		//			text = "attributesNull";
		//		else
		//			text = "attributes NOT Null";

		//		HashMap<String, String> attributes;
		//		if (content.getAttributes() == null)
		//			attributes = content.getAttributes();
		//		else
		//		{
		//			attributes = new HashMap<String, String>();
		//		}
		//		String text = "";
		//		if (attributes.containsKey("text"))
		//			text = attributes.get("text");
		//		else
		//			text = attributes.get("value");
		//		int ind = 0;
		//		if (text.contains("%"))
		//		{
		//			while (text.indexOf("%", ind) != -1)
		//			{
		//				ind = text.indexOf("%", ind) + 1;
		//				String param = text.substring(ind, text.indexOf("%", ind));
		//				if (attributes.containsKey(param))
		//					text = text.replace("%" + param + "%", attributes.get(param));
		//			}
		//		}
		try
		{
			BOT.execute(new SendMessage(telegramId, format(content.toString())).parseMode(ParseMode.HTML));
			//updateNewMessage(false);
		}
		catch (Exception e)
		{
			System.out.println("ERROR");
			LOG.debug("ERROR : EXCEPTION notifyUserByTelegram");
			LOG.debug(e.getMessage());
		}
	}

	// TODO : DELETE after debbugage
	//	private static void main(String[] args)
	//	{
	//		AlertManagement aM = new AlertManagement();
	//		HashMap<String, String> attributes = new HashMap<String, String>(3);
	//		attributes.put("value", "59");
	//		attributes.put("startTime", "2016-08-09T16:36:55.414");
	//		attributes.put("text",
	//				"Mary is staying in the room more than {value} minutes. She entered in the room at {startTime}.");
	//		aM.notifyUserByTelegram(attributes, "224322144");
	//	}

	public void listenToTelegram(String user)
	{
		LOG.debug("In listenToTelegram");

		List<Update> updates = BOT.execute(new GetUpdates().limit(100).offset((int) lastUpdateId).timeout(0)).updates();
		if (!updates.isEmpty())
			lastUpdateId = updates.get(updates.size() - 1).updateId() + 1;
		for (Update update : updates)
		{
			// Verification if the user who send message is the same saved in the database
			LOG.debug("USER : " + user);
			if (Long.parseLong(user) == update.message().chat().id())
			{
				// TODO : Just for simple scenario : after, make a dispatcher
				if (update.message().text().toLowerCase().contains("movement"))
				{
					LOG.trace("new Message from " + update.message().chat().id() + ", content : "
							+ update.message().text());
					updateNewMessage(true);
				}
			}
			else
			{
				sendSimpleTelegramMessage("You are not allowed to access to this bot.",
						String.valueOf(update.message().chat().id()));
			}
		}
	}
}
