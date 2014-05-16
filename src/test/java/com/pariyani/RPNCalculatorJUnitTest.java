package com.pariyani;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.pariyani.exception.InsufficientParametersException;
import com.pariyani.exception.OperationCannotBePerformedException;

/**
 * JUnit test
 * @author imran
 *
 */
public class RPNCalculatorJUnitTest{

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private RPNCalculator calculator;

	@Before
	public void setUp() throws Exception {
		this.calculator = new RPNCalculator();
	}

	@Test
	public void testSenarioA() throws InsufficientParametersException, OperationCannotBePerformedException {
		Assert.assertEquals(calculator.calculate("5 2"), "5 2");
	}
	
	@Test
	public void testSenarioB() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("2 sqrt"), "1.4142135623");
		Assert.assertEquals(calculator.calculate("clear 9 sqrt"), "3");
	}
		
	@Test
	public void testSenarioC() throws InsufficientParametersException, OperationCannotBePerformedException {
		Assert.assertEquals(calculator.calculate("5 2 -"), "3");
		Assert.assertEquals(calculator.calculate("3 -"), "0");
		Assert.assertEquals(calculator.calculate("clear"), "");
	}
	
	@Test
	public void testSenarioD() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("5 4 3 2"), "5 4 3 2");
		Assert.assertEquals(calculator.calculate("undo undo *"), "20");
		Assert.assertEquals(calculator.calculate("5 *"), "100");
		Assert.assertEquals(calculator.calculate("undo"), "20 5");
	}
	
	@Test
	public void testSenarioE() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("7 12 2 /"), "7 6");
		Assert.assertEquals(calculator.calculate("*"), "42");
		Assert.assertEquals(calculator.calculate("4 /"), "10.5");
	}
	
	@Test
	public void testSenarioF() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("1 2 3 4 5"), "1 2 3 4 5");
		Assert.assertEquals(calculator.calculate("*"), "1 2 3 20");
		Assert.assertEquals(calculator.calculate("clear 3 4 -"), "-1");
	}
	
	@Test
	public void testSenarioG() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("1 2 3 4 5"), "1 2 3 4 5");
		Assert.assertEquals(calculator.calculate("* * * *"), "120");
	}
	
	@Test
	public void testSenarioH() throws OperationCannotBePerformedException, InsufficientParametersException {
		calculator.calculate("c");
		exception.expect(InsufficientParametersException.class);
		exception.expectMessage("Operator * (position: 15): insufficient parameters");
		Assert.assertEquals(calculator.calculate("1 2 3 * 5 + * * 6 5"), "");
		exception.expect(InsufficientParametersException.class);
	}
	
	@Test
	public void testSenarioRedo() throws OperationCannotBePerformedException, InsufficientParametersException {
			calculator.calculate("c");
			Assert.assertEquals(calculator.calculate("10"), "10");
			Assert.assertEquals(calculator.calculate("sqrt"), "3.1622776601");
			Assert.assertEquals(calculator.calculate("3"), "3.1622776601 3");
			Assert.assertEquals(calculator.calculate("u"), "3.1622776601");
			
			Assert.assertEquals(calculator.calculate("3"), "3.1622776601 3");
			Assert.assertEquals(calculator.calculate("+"), "6.1622776601");
			Assert.assertEquals(calculator.calculate("3"), "6.1622776601 3");
			Assert.assertEquals(calculator.calculate("+"), "9.1622776601");
			Assert.assertEquals(calculator.calculate("u"), "6.1622776601 3");
			Assert.assertEquals(calculator.calculate("u"), "6.1622776601");
			Assert.assertEquals(calculator.calculate("u"), "3.1622776601 3");
			Assert.assertEquals(calculator.calculate("u"), "3.1622776601");
			Assert.assertEquals(calculator.calculate("u"), "10");
			Assert.assertEquals(calculator.calculate("u"), "");
			
			//Redo
			Assert.assertEquals(calculator.calculate("r"), "10");
			Assert.assertEquals(calculator.calculate("r"), "3.1622776601");
			Assert.assertEquals(calculator.calculate("r"), "3.1622776601 3");
			Assert.assertEquals(calculator.calculate("r"), "6.1622776601");
			Assert.assertEquals(calculator.calculate("r"), "6.1622776601 3");
			Assert.assertEquals(calculator.calculate("r"), "9.1622776601");
			Assert.assertEquals(calculator.calculate("p"), "9.1622776601");
			Assert.assertEquals(calculator.calculate("u"), "6.1622776601 3");
	}
	
	@Test  
	public void testSenarioInvalidInput() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		exception.expect(NumberFormatException.class);
		Assert.assertEquals(calculator.calculate("asdf fasdf"), "");
	}
	
	@Test  
	public void testSenarioInscParam() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("10"), "10");
		Assert.assertEquals(calculator.calculate("sqrt"), "3.1622776601");
		Assert.assertEquals(calculator.calculate("3"), "3.1622776601 3");
		Assert.assertEquals(calculator.calculate("u"), "3.1622776601");
		exception.expect(InsufficientParametersException.class);
		Assert.assertEquals(calculator.calculate("+"), "");
	}
	
	@Test  
	public void testSenarioOperPerExc() throws InsufficientParametersException, OperationCannotBePerformedException {
		calculator.calculate("c");
		Assert.assertEquals(calculator.calculate("10"), "10");
		Assert.assertEquals(calculator.calculate("sqrt"), "3.1622776601");
		Assert.assertEquals(calculator.calculate("3"), "3.1622776601 3");
		Assert.assertEquals(calculator.calculate("u"), "3.1622776601");
		
		Assert.assertEquals(calculator.calculate("r"), "3.1622776601 3");
		exception.expect(OperationCannotBePerformedException.class);
		Assert.assertEquals(calculator.calculate("r"), "");
	}
	
}