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

import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * {@code Drink} class represents properties and behaviors of drink objects
 * which extends Product, in the Product Management System. <br>
 * 
 * @version 0.6.3. Create Product Manager Factory.
 * @author oracle GNU GPL / fevvelasquez
 */
public final class Drink extends Product {

	/**
	 * @param id     Product id.
	 * @param name   Product name.
	 * @param price  Product Price.
	 * @param rating Product Rating.
	 */
	Drink(int id, String name, BigDecimal price, Rating rating) {
		super(id, name, price, rating);
	}

	@Override
	public BigDecimal getDiscount() {
		var now = LocalTime.now();
		return (now.isAfter(LocalTime.of(17, 30)) && now.isBefore(LocalTime.of(22, 30))) ? super.getDiscount()
				: BigDecimal.ZERO.setScale(2, HALF_EVEN);
	}

	@Override
	public Product applyRating(Rating newRating) {
		return new Drink(getId(), getName(), getPrice(), newRating);
	}

}
