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
import static java.math.RoundingMode.*;

/**
 * {@code Product} class represents properties and behaviors of Product objects,
 * in the Product Management System. <br>
 * Each Product has an id, name and price. <br>
 * Each product can have a discount, calculated based on {@link DISCOUNT_RATE}
 * discount rate.
 * 
 * @version 0.5.1 Create Enumeration to Represent Product Rating.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Product {
	/**
	 * A constant that defines a {@link java.math.BigDecimal BigDecimal} of the
	 * discount rate value.<br>
	 * Discount rate of 0.10 is equivalent to 10%.
	 */
	public static final BigDecimal DISCOUNT_RATE = new BigDecimal(String.valueOf("0.10"));

	private int id;
	private String name;
	private BigDecimal price;
	private Rating rating;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the product rating.
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * Calculates discount based on product price and {@link DISCOUNT_RATE discount
	 * rate}.
	 * 
	 * @return a {@link java.math.BigDecimal BigDecimal} value of the discount, by
	 *         applying the discount rate to product price.<br>
	 *         Uses Rounding mode HALF_EVEN with scale of 2.
	 */
	public BigDecimal getDiscount() {
		return price.multiply(DISCOUNT_RATE).setScale(2, HALF_EVEN);

	}

}