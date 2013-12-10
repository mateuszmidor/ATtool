package com.mateuszmidor;

/**
 * The most general exception to be thrown within ATtool
 * @author mateusz
 *
 */
public class ATtoolException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ATtoolException(String message) {
		super(message);
	}
	
	public ATtoolException(Throwable cause) {
		super(cause);
	}
	
	public ATtoolException(String message, Throwable cause) {
		super(message, cause);
	}
}
