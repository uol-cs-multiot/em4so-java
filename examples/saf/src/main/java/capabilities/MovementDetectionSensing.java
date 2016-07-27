package capabilities;

import org.mp.em4so.common.capabilities.SensingCapability;

public class MovementDetectionSensing extends SensingCapability {
	
	@Override
	public String readValue() {
		int movementDetected = (int)(Math.random()*1000+1);
		boolean m = false;
		if(movementDetected > 500) m = true;
//		return "{\"value\":\""+String.valueOf(m)+"\",\"time\":\"\"}";
		return String.valueOf(m);
	
	}
	

}
