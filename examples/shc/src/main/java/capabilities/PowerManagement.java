package capabilities;

import org.mp.em4so.model.actuating.Capability;

public class PowerManagement extends Capability {
	public void turnOff(){
		System.out.println(getSOControlAgent().getId()+": going off...");
	}
}
