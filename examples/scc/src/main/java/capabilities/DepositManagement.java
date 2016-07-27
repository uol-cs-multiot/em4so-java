package capabilities;


import org.mp.em4so.model.actuating.Capability;



public class DepositManagement extends Capability{
	
	
	public  String increaseHomeDemand(String model){
		String message = "2";
		LOG.info("{}: Home availability for resource of model {} is: {}",getSOControlAgent().getId(),model,message);
		return message;
	}
	public  String lookUpNearbyStores(String model, String location){
		String message = "the url of the market.."+" model: "+model+" location: "+location;
		LOG.info(getSOControlAgent().getId()+": "+message);
		return message;
	}
	
}
