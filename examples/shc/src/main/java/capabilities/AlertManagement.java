package capabilities;

import org.mp.em4so.model.actuating.Capability;

public class AlertManagement extends Capability {

	
	
	public void notifyBySMS(String target, String message){
		LOG.info("message:"+message+" sent to: "+target);
		System.out.println(getSOControlAgent().getId()+": message: "+message+" was sent to: "+target+" ");
	}
	public void notifyByEmail(String to, String subject, String message){
		System.out.println(getSOControlAgent().getId()+": message: "+message+"and subject:"+subject+" was emailed to: "+to+" ");
	}

}
