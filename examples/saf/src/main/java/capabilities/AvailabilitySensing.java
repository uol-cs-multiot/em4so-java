package capabilities;

import org.mp.em4so.common.capabilities.SensingCapability;
import org.mp.em4so.utils.JSONUtils;
import org.mp.em4so.utils.RESTClientUtils;
import org.mp.em4so.utils.SOMFileConfigUtils;


public class AvailabilitySensing extends SensingCapability{
	public String readValue(){
		
		String strUrl = SOMFileConfigUtils.getKBUrl()+"/"+SOMFileConfigUtils.getKBPrefix()+getSOControlAgent().getId()+ "/_design/environment/_list/currentValue/all?keys=[\"knownAvailability\"]";
		RESTClientUtils c = new RESTClientUtils(SOMFileConfigUtils.getKBUser() + ":" +SOMFileConfigUtils.getKBPass() );
		String result = c.doGet(strUrl);
		LOG.trace("Reading KnownAvailability: "+ strUrl);
		if(result != null && !result.equals("null")){
//			
////			strResult = result+"";
//			System.out.println("Sensor result for knowava:"+strResult);
			result = JSONUtils.<String>mapJsonToType(result, String.class); 
		}else{
			result = "";
		}
		return result;
	}
}
