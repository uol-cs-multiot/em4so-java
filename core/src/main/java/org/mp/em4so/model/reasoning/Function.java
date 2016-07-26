/*
 * Copyright: mperhez (2015)
 * License: The Apache Software License, Version 2.0
 */
package org.mp.em4so.model.reasoning;



import java.util.LinkedHashSet;
import org.mp.em4so.model.common.Element;


// TODO: Auto-generated Javadoc
/**
 * The Class Function.
 */
public class Function /*implements Comparable*/{
	
	/** The operator. */
	private String operator;
	
	/** The operands. */
	private LinkedHashSet<Function> operands;
	
	/** The operand 1. */
	private Element operand1;
	
	/** The operand 2. */
	private Element operand2;
	
	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	
	/**
	 * Gets the operands.
	 *
	 * @return the operands
	 */
	public LinkedHashSet<Function> getOperands() {
		return operands;
	}
	
	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/**
	 * Sets the operands.
	 *
	 * @param operands the new operands
	 */
	public void setOperands(LinkedHashSet<Function> operands) {
		this.operands = operands;
	}
	
	/**
	 * Gets the operand 1.
	 *
	 * @return the operand 1
	 */
	public Element getOperand1() {
		return operand1;
	}
	
	/**
	 * Gets the operand 2.
	 *
	 * @return the operand 2
	 */
	public Element getOperand2() {
		return operand2;
	}
	
	/**
	 * Sets the operand 1.
	 *
	 * @param operand1 the new operand 1
	 */
	public void setOperand1(Element operand1) {
		this.operand1 = operand1;
	}
	
	/**
	 * Sets the operand 2.
	 *
	 * @param operand2 the new operand 2
	 */
	public void setOperand2(Element operand2) {
		this.operand2 = operand2;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
