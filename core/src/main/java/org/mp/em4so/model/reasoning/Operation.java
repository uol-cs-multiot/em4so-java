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
@SuppressWarnings("rawtypes")
public class Operation {
	
	/** The Constant LOG. */
	private final static Logger LOG = LoggerFactory.getLogger(Operation.class
			.getName());
	
	/** The Constant opByName. */
	public static final Map<String,IOperation> opByName = new HashMap<String,IOperation>();
	
	/** The operation. */
	private static Operation operation;
	
	/**
	 * Instantiates a new operation.
	 */
	Operation(){
		
		Operation.opByName.put("AND", new And<Boolean>());
		Operation.opByName.put("OR", new Or<Boolean>());
		Operation.opByName.put(">", new BiggerThan<Boolean>());
		Operation.opByName.put(">=", new BiggerEqualsTo<Boolean>());
		Operation.opByName.put("<=", new SmallerEqualsTo<Boolean>());
		Operation.opByName.put("<", new SmallerThan<Boolean>());
		Operation.opByName.put("==", new EqualsTo<Boolean>());
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

class And<T> implements IOperation<T>{
	private static final Logger LOG = LoggerFactory.getLogger(And.class.getSimpleName());
	Boolean x;
	Boolean y;
	Boolean result;
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
			this.x = (Boolean)x;
		if(y!=null)
			this.y = (Boolean)y;
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.booleanValue() && this.y.booleanValue();
		else
			result = null;
		LOG.trace("Result is:"+result);
		return (T)result;
	}
	
}

class Or<T> implements IOperation<T>{
	private static final Logger LOG = LoggerFactory.getLogger(Or.class.getSimpleName());
	Boolean x;
	Boolean y;
	Boolean result;
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
			this.x = (Boolean)x;
		if(y!=null)	
			this.y = (Boolean)y;
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.booleanValue() || this.y.booleanValue();
		else
			result = false;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}
	
class BiggerThan<T> implements IOperation<T>{
	private static final Logger LOG = LoggerFactory.getLogger(BiggerThan.class.getSimpleName());
	Long x;
	Long y;
	Boolean result;
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
			this.x = Long.parseLong((String)x);
		if(y!=null)
			this.y = Long.parseLong((String)y);
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.intValue() > this.y.intValue();
		else
			result = false;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}

class SmallerThan<T> implements IOperation<T>{
	private static final Logger LOG = LoggerFactory.getLogger(SmallerThan.class.getSimpleName());
	Long x;
	Long y;
	Boolean result;
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
		this.x = Long.valueOf((String)x).longValue();
		if(y!=null)
		this.y = Long.valueOf((String)y).longValue();
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.longValue() < this.y.longValue();
		else
			result = false;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}

class EqualsTo<T> implements IOperation<T>{
	Boolean result;
	private static final Logger LOG = LoggerFactory.getLogger(EqualsTo.class.getSimpleName());
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		LOG.trace("x->"+x+"<- & y->"+y+"<-");
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x != null && !x.equals("null"))
			if(y!=null && !y.equals("null"))
				result = x.equals(y);
			else
				result = false;
		else
			if(y!=null && !y.equals("null"))
				result = false;
			else
				result = true;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}

class BiggerEqualsTo<T> implements IOperation<T>{
	private static final Logger LOG = LoggerFactory.getLogger(BiggerEqualsTo.class.getSimpleName());
	Long x;
	Long y;
	Boolean result;
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
			this.x = Long.valueOf((String)x).longValue();
		if(y!=null)
			this.y = Long.valueOf((String)y).longValue();
		LOG.trace("x->"+x+"<- & y->"+x+"<-");
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.longValue() >= this.y.longValue();
		else
			result = false;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}
class SmallerEqualsTo<T> implements IOperation<T>{
	Long x;
	Long y;
	Boolean result;
	private static final Logger LOG = LoggerFactory.getLogger(SmallerEqualsTo.class.getSimpleName());
	@Override
	@SuppressWarnings("unchecked")
	public T calculate(Object x, Object y) {
		if(x!=null)
			this.x = Long.valueOf((String)x).longValue();
		if(y!=null)
			this.y = Long.valueOf((String)y).longValue();
		LOG.trace("x->"+x+"<- & y->"+x+"<-");
		LOG.trace("Calculating.. "+x+" "+this.getClass().getSimpleName()+" "+y);
		if(x!=null && !x.equals("null") && y!=null && !y.equals("null"))
			result = this.x.longValue() <= this.y.longValue();
		else
			result = false;
		LOG.trace("Result is:"+result);
		return (T)result;
		
	}
	
}

