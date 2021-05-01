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
 * @version 0.4.2
 * @author oracle GNU GPL / fevvelasquez
 */
public class Product {
	public static final BigDecimal DISCOUNT_RATE = new BigDecimal(String.valueOf("0.10"));

	private int id;
	private String name;
	private BigDecimal price;

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
	 * @return discount <br>
	 *         The discount value by applying the discount rate to product
	 *         price.<br>
	 *         Rounding mode HALF_EVEN with scale of 2 applied.
	 */
	public BigDecimal getDiscount() {
		return price.multiply(DISCOUNT_RATE).setScale(2, HALF_EVEN);

	}

}