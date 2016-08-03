package com.gochat.exceptions;

/**
 * @author mpcariaso
 *
 */
public class InvalidAcceptHeaderException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8889579383628423124L;

	public InvalidAcceptHeaderException(String acceptHeader) {
		super("Invalid Accept Header: " + acceptHeader);
	}
}
