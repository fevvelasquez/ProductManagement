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
package me.fevvelasquez.pm.app;

import java.math.BigDecimal;

import me.fevvelasquez.pm.data.Product;

/**
 * @version 0.4.1
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Instance example of 'Product' class:
		Product p1 = new Product();
		// Instance Initialization:
		p1.setId(101);
		p1.setName("Tea");
		p1.setPrice(BigDecimal.valueOf(1.99));
		// Print Instance data values:
		System.out.println("[id=" + p1.getId() + ", name=" + p1.getName() + ", price=" + p1.getPrice() + "]");

	}
}