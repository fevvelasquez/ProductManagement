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
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.6.3. Create Product Manager Factory.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		return new Food(id, name, price, rating, bestBefore);

	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
		return new Drink(id, name, price, rating);

	}
}
