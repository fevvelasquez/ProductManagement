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
import java.time.LocalDate;

import me.fevvelasquez.pm.data.Drink;
import me.fevvelasquez.pm.data.Food;
import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.6.1 Create Food and Drink Classes that Extend Product.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		// Create 'Product', 'Food' and 'Drink' instances, all assigned to Product generic references:
		Product p1 = new Product(101, "Tea", BigDecimal.valueOf(1.99));
		Product p2 = new Drink(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STARS);
		Product p3 = new Food(103, "Cake", BigDecimal.valueOf(3.99), Rating.FIVE_STARS, LocalDate.now().plusDays(2));

		// Print products:
		printProduct(p1);
		printProduct(p2);
		printProduct(p3);

		// Apply product rating, creates a new Product instance because it is immutable:
		System.out.println("--- Rating applied copy:");
		printProduct(p3.applyRating(Rating.THREE_STARS));

	}

	/**
	 * Print Instance data values.
	 * 
	 * @param p Product instance to print.
	 */
	public static void printProduct(Product p) {
		System.out.println("[id=" + p.getId() + ", price=" + p.getPrice() + ", discount=" + p.getDiscount()
				+ ", rating=" + p.getRating().getStars() + ", name=" + p.getName() + "]");

	}

}