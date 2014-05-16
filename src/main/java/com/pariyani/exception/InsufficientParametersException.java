package com.pariyani.exception;


/**
 * 
 * @author imran
 */
public class InsufficientParametersException extends Exception {

    private static final long serialVersionUID = -2857516066309613143L;

	public InsufficientParametersException() {
		super();
	}

	public InsufficientParametersException(final String message) {
		super(message);
	}

	public InsufficientParametersException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InsufficientParametersException(final Throwable cause) {
		super(cause);
	}

}
