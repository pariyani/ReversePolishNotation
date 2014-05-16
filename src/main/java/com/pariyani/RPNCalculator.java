package com.pariyani;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.pariyani.exception.InsufficientParametersException;
import com.pariyani.exception.OperationCannotBePerformedException;

/**
 * @author imran
 *
 */
public class RPNCalculator {

	/**
	 * Holds the current execution stack
	 */
	public Stack<BigDecimal> stack = new Stack<BigDecimal>();
	
	/**
	 * Holds the entire execution history.
	 */
	public Map<Integer,Stack<BigDecimal>> undoStack = new HashMap<Integer,Stack<BigDecimal>>();
	
	/**
	 * Holds the current position helping in traversing trough the stack history. 
	 */
	public int pointer;
	
	/**
	 * Executes the commands received and returns the result.
	 * 
	 * @param input
	 * @return {@link String}
	 * @throws InsufficientParametersException
	 * @throws OperationCannotBePerformedException
	 */
	public String calculate(String input) throws InsufficientParametersException, OperationCannotBePerformedException{
		int length=0;
		for (String param : input.trim().split(" ")) {
			Operator operator = Operator.findOperator(param);
			if (operator != null) {
				try{
					stack.push(operator.operate(stack));
					addToHistory();
				}catch(InsufficientParametersException e){
					throw new InsufficientParametersException("Operator "+operator.getName()+" (position: "+(length+1)+"): insufficient parameters");
				}
			} else if (param.equals("undo") || param.equals("u")) {
				undo();
			} else if (param.equals("redo") || param.equals("r")) {
				redo();
			} else if (param.equals("print") || param.equals("p")) {
				return getFormatedStack();
			} else if (param.equals("clear") || param.equals("c")) {
				clear();
			} else {
				stack.push(new BigDecimal(param));
				addToHistory();
			}
			length += param.length()+1;
		}
		return getFormatedStack();
	}
	
	/**
	 * Removes the last entry.
	 * 
	 * @return
	 * @throws OperationCannotBePerformedException
	 */
	private String undo() throws OperationCannotBePerformedException{
		if(pointer>0 && this.undoStack.containsKey(pointer-1)){
			--pointer;
			this.stack=getClone(this.undoStack.get(pointer));
		}else if(pointer==1){
			--pointer;
			this.stack.clear();
		}else 
			throw new OperationCannotBePerformedException("Undo");
		
		return getFormatedStack();
	}
	
	/**
	 * Adds the last removed entry.
	 * @return
	 * @throws OperationCannotBePerformedException
	 */
	private String redo() throws OperationCannotBePerformedException{
		if(this.undoStack.containsKey(pointer+1)){
			this.stack=getClone(this.undoStack.get(pointer+1));
			++pointer;
		}else
			throw new OperationCannotBePerformedException("redo");
		return getFormatedStack();
	}
	
	/**
	 * Clears the stack
	 * @return
	 */
	private String clear() {
		stack.clear();
		undoStack.clear();
		pointer=0;
		return getFormatedStack();
    }
	
	/**
	 * Internal method used to maintain the stack history.
	 * @return
	 */
	private String addToHistory(){
		++pointer;
		undoStack.put(pointer,getClone(this.stack));
		int removePointer = this.pointer+1;
		while(this.undoStack.containsKey(removePointer)){
			this.undoStack.remove(removePointer);
			removePointer++;
		}
		return getFormatedStack();
	}
	
	/**
	 * Generates the coln of a stack object before adding it to the history.
	 * @param stack
	 * @return
	 */
	private Stack<BigDecimal> getClone(Stack<BigDecimal> stack){
		Stack<BigDecimal> clone = new Stack<BigDecimal>();
		clone.addAll(stack);
		return clone;
	}
	
	/**
	 * Returns the formated string of the current stack.
	 * @return
	 */
	protected String getFormatedStack(){
		String returnString="";
		Iterator<BigDecimal> iterator = this.stack.iterator();
		while(iterator.hasNext()){
			BigDecimal value= iterator.next();
			returnString+=" "+((value.scale()>0 && value.stripTrailingZeros().scale()>0 )?value.setScale(10, RoundingMode.DOWN).stripTrailingZeros():value.setScale(0));
		}
		return returnString.trim();
	}
}
