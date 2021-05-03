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
import java.time.LocalDate;

/**
 * {@code Food} class represents properties and behaviors of food objects which
 * extends Product, in the Product Management System. <br>
 * 
 * @version 0.6.3. Create Product Manager Factory.
 * @author oracle GNU GPL / fevvelasquez
 */
public final class Food extends Product {

	/**
	 * Recommended best consume this product before this date.
	 */
	private LocalDate bestBefore;

	/**
	 * @param id         Product id.
	 * @param name       Product name.
	 * @param price      Product Price.
	 * @param rating     Product Rating.
	 * @param bestBefore Best before date.
	 */
	Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		super(id, name, price, rating);
		this.bestBefore = bestBefore;
	}

	@Override
	public String toString() {
		return super.toString() + " Food[bestBefore=" + bestBefore + "]";
	}

	@Override
	public BigDecimal getDiscount() {
		return bestBefore.isEqual(LocalDate.now()) ? super.getDiscount() : BigDecimal.ZERO.setScale(2, HALF_EVEN);
	}

	@Override
	public Product applyRating(Rating newRating) {
		return new Food(getId(), getName(), getPrice(), newRating, bestBefore);
	}

	/**
	 * @return the bestBefore
	 */
	@Override
	public LocalDate getBestBefore() {
		return bestBefore;
	}

}
