package capabilities;


import org.mp.em4so.model.actuating.Capability;



public class StoreServices extends Capability{
	
	
	public  String checkBestPriceStore(String product, String model ){
		String message = "ALDI";
		LOG.info("{}: Best price store for product {} model {} is: {} ",getSOControlAgent().getId(),product,model,message);
		return message;
	}
	public  String getNearbyStore(String storeName, String location){
		String message = "-1232132,53543543";
		LOG.info("{}: the nearest {} store to your current location is: {} ",getSOControlAgent().getId(),storeName,message);
		return message;
	}
	
}
