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
import static me.fevvelasquez.pm.data.Rating.*;

/**
 * {@code Product} class represents properties and behaviors of Product objects,
 * in the Product Management System. <br>
 * Each Product has an id, name and price. <br>
 * Each product can have a discount, calculated based on {@link DISCOUNT_RATE}
 * discount rate.
 * 
 * @version 0.5.2 Add Custom Constructors to the Product Class.
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
	 * Main constructor.
	 * 
	 * @param id     Product id.
	 * @param name   Product name.
	 * @param price  Product Price.
	 * @param rating Product Rating.
	 */
	public Product(int id, String name, BigDecimal price, Rating rating) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.rating = rating;
	}

	/**
	 * Calls {@link Product(int id, String name, BigDecimal price, Rating rating)
	 * main constructor} setting the default value for the rating argument as
	 * {@link Rating.NOT_RATED NOT_RATED}.
	 * 
	 * @param id    Product id.
	 * @param name  Product name.
	 * @param price Product Price.
	 * 
	 */
	public Product(int id, String name, BigDecimal price) {
		this(id, name, price, NOT_RATED);

	}

	/**
	 * @return the Product id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the Product id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the Product name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the Product name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the Product price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the Product price to set
	 */
	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the Product rating.
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
	 *         Rounding mode HALF_EVEN with scale of 2.
	 */
	public BigDecimal getDiscount() {
		return price.multiply(DISCOUNT_RATE).setScale(2, HALF_EVEN);

	}

}