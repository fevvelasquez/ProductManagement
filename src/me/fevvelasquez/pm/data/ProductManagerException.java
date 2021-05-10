/**
 * 
 * ===============================================================
 * fevvelasquez 2021, coding practices from 
 * Java SE 11 Programming Complete Course at Oracle University.
 * ===============================================================
 * 
 * 
 * 
 * This program is free software: 
 * you can redistribute and/or modify it under the terms of 
 * GNU General Public License as published by the 
 * Free Software Foundation. <http://www.gnu.org/licenses/>
 * 
 */
package me.fevvelasquez.pm.data;

/**
 * {@code ProductManagerException} class represents an exception related to the
 * Product Manager operation processes.
 * 
 * @version 0.12.1. Use Exception Handling to Fix Logical Errors.
 * @author oracle GNU GPL / fevvelasquez
 */
@SuppressWarnings("serial")
public class ProductManagerException extends Exception {

	/**
	 * 
	 */
	public ProductManagerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProductManagerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ProductManagerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
