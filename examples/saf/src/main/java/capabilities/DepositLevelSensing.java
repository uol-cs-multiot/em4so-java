package capabilities;

import org.mp.em4so.common.capabilities.SensingCapability;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.utils.JSONUtils;
import org.mp.em4so.utils.RESTClientUtils;
import org.mp.em4so.utils.SOMFileConfigUtils;

public class DepositLevelSensing extends SensingCapability {
//public DepositLevelSensor(){
//	super("depl001","","To sense deposit level","active");
//}
	
	public String readValue(){
	String result;
	Element element = new Element(soca.getSom().getId(),"freshener");
	element.setAttributeName("value");
	element = this.getSOControlAgent().getSom().getKbm().getElement(element);
	LOG.trace("Sensor result for deposit:"+element);
	if(element!=null && element.getValue()!=null){
		result = element.getValue();
	}else{
		String strUrl = SOMFileConfigUtils.getKBUrl()+"/"+SOMFileConfigUtils.getKBPrefix()+getSOControlAgent().getId()+ "/_design/environment/_list/currentValue/all?keys=[\"freshener\"]";
		RESTClientUtils c = new RESTClientUtils(SOMFileConfigUtils.getKBUser() + ":" +SOMFileConfigUtils.getKBPass() );
		result = c.doGet(strUrl);
	}
		
		
	return JSONUtils.<String>mapJsonToType(result, String.class);

}

}
