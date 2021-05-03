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

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * {@code Food} class represents properties and behaviors of food objects which
 * extends Product, in the Product Management System. <br>
 * 
 * @version 0.6.1 Create Food and Drink Classes that Extend Product.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Food extends Product {

	/**
	 * Recommended best consume this product before this date.
	 */
	private LocalDate bestBefore;

	/**
	 * @param id     Product id.
	 * @param name   Product name.
	 * @param price  Product Price.
	 * @param rating Product Rating.
	 * @param bestBefore Best before date.
	 */
	public Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		super(id, name, price, rating);
		this.bestBefore = bestBefore;
	}

}
