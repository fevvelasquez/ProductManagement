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
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.5.2 Add Custom Constructors to the Product Class.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		// Create 3 'Product' instances using constructor initialization:
		Product p1 = new Product(101, "Tea", BigDecimal.valueOf(1.99));
		Product p2 = new Product(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STARS);
		Product p3 = new Product(103, "Cake", BigDecimal.valueOf(3.99), Rating.FIVE_STARS);
		// Print Instance data values:
		System.out.println("[id=" + p1.getId() + ", price=" + p1.getPrice() + ", discount=" + p1.getDiscount() +", rating=" + p1.getRating().getStars() + ", name=" + p1.getName() + "]");
		System.out.println("[id=" + p2.getId() + ", price=" + p2.getPrice() + ", discount=" + p2.getDiscount() +", rating=" + p2.getRating().getStars() + ", name=" + p2.getName() + "]");
		System.out.println("[id=" + p3.getId() + ", price=" + p3.getPrice() + ", discount=" + p3.getDiscount() +", rating=" + p3.getRating().getStars() + ", name=" + p3.getName() + "]");

	}

}