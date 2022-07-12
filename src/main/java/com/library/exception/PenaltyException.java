package com.library.exception;

import java.lang.reflect.InvocationTargetException;

public class PenaltyException extends InvocationTargetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PenaltyException(String message) {
		super(null, message);
	}

}
