package capabilities;

import org.mp.em4so.model.actuating.Capability;
import org.mp.em4so.model.common.Element;
import org.mp.em4so.model.sensing.Observation;

public class FragranceManagement extends Capability{
	
	public void spray(){
		int dlevel = 0;
		int maxLevel = 10;
		Observation depositLevel = this.getSOControlAgent().getSom().getKbm().getRecentObservation("freshener");
//		System.out.println("El depositLevel a parsear:"+depositLevel.getValue()+"<--");
		if(depositLevel!=null){
		dlevel = Integer.parseInt(depositLevel.getValue());
//		System.out.println("El depositLevel parseado:"+dlevel+"<--");
		if(dlevel>=maxLevel){
			LOG.info(getSOControlAgent().getId()+": Current deposit level: "+ dlevel);
			dlevel -= 10;
			LOG.info(getSOControlAgent().getId()+": Fragance sprayed. New deposit level: "+ dlevel);
			try{
			Element element = new Element(soca.getSom().getId(),"freshener");
			element.setValue(""+dlevel);
			this.getSOControlAgent().getSom().getKbm().updateProperty(element);
			}catch(InterruptedException e){
				LOG.error("Error: {}",e.getMessage(),e);
			}
			depositLevel = this.getSOControlAgent().getSom().getKbm().getRecentObservation("freshener");
			System.out.println("deposit "+depositLevel);
		}else{
			LOG.info(getSOControlAgent().getSom().getId()+": Empty Canister please refill.");
		}
		}else{
			
		}
	}
		
	
}
