package capabilities;

import java.util.HashMap;

import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;

public class AlertManagement extends Capability {

	
	
	public void notifyUser(String content, Element user){
		HashMap<String,String> userAttributes =  user.getAttributes();
		LOG.info("message:"+content+userAttributes.get("message")+" displayed to user: "+userAttributes.get("name"));
		System.out.println("message:"+content+userAttributes.get("message")+" displayed to user: "+userAttributes.get("name"));
	}
	
	public void listenToTelegram(){
		LOG.info("Listening to Telegram.....");
	}
}
