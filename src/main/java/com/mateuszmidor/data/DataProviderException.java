package com.mateuszmidor.data;

import com.mateuszmidor.ATtoolException;

/**
 * Exception class for DataProviders area
 * @author mateusz
 *
 */
public class DataProviderException extends ATtoolException {
	private static final long serialVersionUID = 1L;
	
	
	public DataProviderException(String message) {
		super(message);
	}
	
	public DataProviderException(Throwable cause) {
		super(cause);
	}
	
	public DataProviderException(String message, Throwable cause) {
		super(message, cause);
	}
}
