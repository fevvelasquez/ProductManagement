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
import java.util.Comparator;
import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.ProductManager;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.13.2. Bulk-Load Data from Files.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		// Create products factory using LOCALE
		var pm = new ProductManager("en-GB");
		// ----------------------------------------------------------------------

		// From files:
		pm.printProductReport(1101);
		pm.printProductReport(1102);
		// ----------------------------------------------------------------------

		// From Shop class:
		pm.createProduct(2201, "Cammomile Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Nice hot cup of tea");
		pm.reviewProduct(2201, Rating.TWO_STARS, "Rather weak tea");
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Fine");
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Good tea!");
		pm.reviewProduct(2201, Rating.FIVE_STARS, "Perfect.");
		pm.reviewProduct(2201, Rating.THREE_STARS, "Just add some lemon");
		pm.printProductReport(2201);
		// ----------------------------------------------------------------------
		pm.createProduct(2202, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED, LocalDate.now());
		pm.reviewProduct(2202, Rating.THREE_STARS, "Just another cookie");
		pm.reviewProduct(2202, Rating.THREE_STARS, "Meh");
		pm.printProductReport(2202);
		// ----------------------------------------------------------------------

		// STREAM EXAMPLES: =======================================================
		System.out.println("\n=== Print Products:");
		// Comparator<Product> ratingAsc = (p1, p2) -> p1.getRating().ordinal() - p2.getRating().ordinal();
		Comparator<Product> nameLengthAsc = (p1, p2) -> p1.getName().length() - p2.getName().length();
		pm.printProducts(p -> true, nameLengthAsc);
		// ----------------------------------------------------------------------

	}

}