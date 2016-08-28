/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO: Auto-generated Javadoc

/**
 * The Class Operation.
 */

public class Operation {
	
	/** The Constant LOG. */
	private final static Logger LOG = LoggerFactory.getLogger(Operation.class
			.getName());
	
	/** The Constant opByName. */
	public static final Map<String,Operator> opByName = new HashMap<String,Operator>();
	
	/** The operation. */
	private static Operation operation;
	
	/**
	 * Instantiates a new operation.
	 */
	Operation(){
		//These are only comparison operations to determine whether to trigger an action or not. They always return boolean value.
		
		
		//basic logic
		Operation.opByName.put("AND", new And());
		Operation.opByName.put("OR", new Or());
		Operation.opByName.put("XOR", new Xor()); 
		Operation.opByName.put("NOT", new Not()); 
		
		//comparison
		Operation.opByName.put(">", new BiggerThan());
		Operation.opByName.put(">=", new BiggerEqualsTo());
		Operation.opByName.put("<=", new SmallerEqualsTo());
		Operation.opByName.put("<", new SmallerThan());
		Operation.opByName.put("==", new EqualsTo());
		Operation.opByName.put("!=", new NotEqualsTo()); 
		
		LOG.trace(" The table is"+opByName);
	
	}
	
	/**
	 * Gets the single instance of Operation.
	 *
	 * @return single instance of Operation
	 */
	public static Operation getInstance(){
		if(operation == null){
			operation = new Operation();
			LOG.trace("constructor: "+operation);
		}
		
	return operation;
	}
	
}

class And extends Operator {

	@Override
	public boolean calculateBoolean(String... x) {
		return Boolean.parseBoolean(x[0])&&Boolean.parseBoolean(x[1]);
	}
}

class Or extends Operator {
	@Override
	public boolean calculateBoolean(String... x) {
		return Boolean.parseBoolean(x[0])||Boolean.parseBoolean(x[1]) ;
	}
}

class Xor extends Operator {
	@Override
	public boolean calculateBoolean(String... x) {
		return Boolean.parseBoolean(x[0])^Boolean.parseBoolean(x[1]) ;
	}
}
class Not extends Operator {
	@Override
	public boolean calculateBoolean(String... x) {
		return Boolean.parseBoolean(x[0]) ? false:true;
	}
}
	
class BiggerThan extends Operator {
	
	
	/**
	 * true if the first operand is bigger than the second
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public boolean calculateBoolean(String... x) {
		int r;
		boolean result = false;
		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
			result = r > 0 ? true : false;
		}
		return result;
	}
}

class SmallerThan extends Operator {
	public boolean calculateBoolean(String... x) {
		int r;
		boolean result = false;
		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
			result = r < 0 ? true : false;
		}
		return result;
	}


}

class EqualsTo extends Operator {
//	public boolean calculateBoolean(String... x) {
//		int r;
//		boolean result = false;
//		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
//			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
//			result = r == 0 ? true : false;
//		}
//		return result;
//	}
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(EqualsTo.class.getSimpleName());
	@Override
	public boolean calculateBoolean(String... x) {
		boolean result;
		LOG.trace("x->"+x[0]+"<- & y->"+x[1]+"<-");
		LOG.trace("Calculating.. "+x[0]+" "+this.getClass().getSimpleName()+" "+x[1]);
		if(x[0] != null && !x[0].equals("null"))
			if(x[1]!=null && !x[1].equals("null"))
				result = x[0].equals(x[1]);
			else
				result = false;
		else
			if(x[1]!=null && !x[1].equals("null"))
				result = false;
			else
				result = true;
		LOG.trace("Result is:"+result);
		return result;
		
	}
}

class BiggerEqualsTo extends Operator{
	@Override
	public boolean calculateBoolean(String... x) {
		int r;
		boolean result = false;
		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
			result = r >= 0 ? true : false;
		}
		return result;
	}
	
}
class SmallerEqualsTo extends Operator {

	@Override
	public boolean calculateBoolean(String... x) {
		int r;
		boolean result = false;
		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
			result = r <= 0 ? true : false;
		}
		return result;
	}
	
}
class NotEqualsTo extends Operator {
	@Override
	public boolean calculateBoolean(String... x) {
		int r;
		boolean result = false;
		if(checkOperandNotNull(x[0]) && checkOperandNotNull(x[1])){
			r = Long.compare(Long.parseLong(x[0]),Long.parseLong(x[1]));
			result = r != 0 ? true : false;
		}
		return result;
	}
	
}

