package com.pariyani;

import java.util.Scanner;

import com.pariyani.exception.InsufficientParametersException;
import com.pariyani.exception.OperationCannotBePerformedException;

/**
 * @author imran
 * 
 */
public class RPNCalculatorTestCommandLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RPNCalculator rpn = new RPNCalculator();
		Scanner in = new Scanner(System.in);
		String command = null;
		while (true) {
			System.out.print("$ ");
			command = in.nextLine();
			if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("e") || command.equalsIgnoreCase("quit")
			        || command.equalsIgnoreCase("q"))
				break;
			else {
				try {
					System.out.println(rpn.calculate(command));
				} catch (NumberFormatException e) {
					System.out.println("Invalid command. You can input numbers or one of the following +, -, *, /, sqrt, undo, clear");
				} catch (InsufficientParametersException e) {
					System.out.println(e.getMessage());
					System.out.println(rpn.getFormatedStack());
				} catch (OperationCannotBePerformedException e) {
					System.out.println("Operation "+e.getMessage()+" Cannot be performed as the stack dosent have any items left.");
                }
			}
		}
	}
}
