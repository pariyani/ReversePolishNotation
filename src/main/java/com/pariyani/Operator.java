package com.pariyani;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.pariyani.exception.InsufficientParametersException;

/**
 * Supports following operations +, -, *, /, sqrt
 * 
 * @author imran
 * 
 */
public enum Operator {
	PLUS("+") {
		@Override
		public BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException {
			if(stack.size()<2)
				throw new InsufficientParametersException();
			BigDecimal first = stack.pop();
			return stack.pop().add(first);
		}
	},
	MINUS("-") {
		@Override
		public BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException {
			if(stack.size()<2)
				throw new InsufficientParametersException();
			BigDecimal first = stack.pop();
			return stack.pop().subtract(first);
		}
	},
	MULTIPLY("*") {
		@Override
		public BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException {
			if(stack.size()<2)
				throw new InsufficientParametersException();
			BigDecimal first = stack.pop();
			return stack.pop().multiply(first);
		}
	},
	DIVIDE("/") {
		@Override
		public BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException {
			if(stack.size()<2)
				throw new InsufficientParametersException();
			BigDecimal first = stack.pop();
			return stack.pop().divide(first);
		}
	},
	SQRT("sqrt") {
		@Override
		public BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException {
			if(stack.isEmpty())
				throw new InsufficientParametersException();
			BigDecimal x1 = stack.pop();
			BigDecimal x = new BigDecimal(Math.sqrt(x1.doubleValue()));
			return(x.add(new BigDecimal(x1.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0))).setScale(15, RoundingMode.HALF_UP));
		}
	};

	private String	name;

	/**
	 * @param stack
	 * @return Result
	 * @throws InsufficientParametersException
	 */
	public abstract BigDecimal operate(Stack<BigDecimal> stack) throws InsufficientParametersException;

	private static Map<String, Operator>	operators;
	static {
		operators = new HashMap<String, Operator>();
		for (Operator operator : Operator.values())
			operators.put(operator.getName(), operator);
	}

	private Operator(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static Operator findOperator(String operator){
		return operators.get(operator);
	}
}
