package org.mp.em4so.model.reasoning;

import org.slf4j.Logger;

public abstract class Operator implements IOperation {
	
	protected boolean checkOperandNotNull(String operand){
		if(operand==null)
			return false;
		else
			if (operand.equalsIgnoreCase("null") || operand.trim().equals(""))
				return false;
			else
				return true;
	}
	

}
