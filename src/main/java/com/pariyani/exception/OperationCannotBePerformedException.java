package com.pariyani.exception;


/**
 * 
 * @author imran
 */
public class OperationCannotBePerformedException extends Exception {

    private static final long serialVersionUID = -9052666984682402353L;
    
	private String message;
    
	public OperationCannotBePerformedException() {
		super();
	}

	public OperationCannotBePerformedException(final String message) {
		super(message);
		this.message=message;
	}

	public OperationCannotBePerformedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public OperationCannotBePerformedException(final Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return message;
	}

}
