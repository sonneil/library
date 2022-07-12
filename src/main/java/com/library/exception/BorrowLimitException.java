package com.library.exception;

import java.lang.reflect.InvocationTargetException;

public class BorrowLimitException extends InvocationTargetException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BorrowLimitException(String message) {
		super(null, message);
	}

}
