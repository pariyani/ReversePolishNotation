package com.pariyani.exception;


/**
 * 
 * @author imran
 */
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 1070531993672017071L;

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(final String message) {
		super(message);
	}

	public InvalidInputException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(final Throwable cause) {
		super(cause);
	}

}
