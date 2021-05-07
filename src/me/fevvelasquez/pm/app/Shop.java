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
import java.util.Locale;
import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.ProductManager;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.9.1. Organize Products and Reviews into a HashMap.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		// Create products factory with LOCALE
		var pm = new ProductManager(Locale.UK);
		// ----------------------------------------------------------------------

		// Test p1, NOT RATED case
		Product p1 = pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		pm.printProductReport(p1);
		// ----------------------------------------------------------------------

		// Test p1, Multiple reviews
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Nice hot cup of tea");
		p1 = pm.reviewProduct(p1, Rating.TWO_STARS, "Rather weak tea");
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Fine");
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Good tea!");
		p1 = pm.reviewProduct(p1, Rating.FIVE_STARS, "Perfect.");
		p1 = pm.reviewProduct(p1, Rating.THREE_STARS, "Just add some lemon");
		pm.printProductReport(p1);
		// ----------------------------------------------------------------------

		// Test p2, Multiple reviews
		Product p2 = pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		p2 = pm.reviewProduct(p2, Rating.THREE_STARS, "Coffe was ok");
		p2 = pm.reviewProduct(p2, Rating.ONE_STAR, "Where is the milk?");
		p2 = pm.reviewProduct(p2, Rating.FIVE_STARS, "It's perfect with ten spoons of sugar");
		pm.printProductReport(p2);
		// ----------------------------------------------------------------------

		// Test p3, Multiple reviews
		Product p3 = pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.NOT_RATED,
				LocalDate.now().plusDays(2));
		p3 = pm.reviewProduct(p3, Rating.FIVE_STARS, "Very nice cake");
		p3 = pm.reviewProduct(p3, Rating.FOUR_STARS, "It's good, but I've expected more chocolate");
		p3 = pm.reviewProduct(p3, Rating.FIVE_STARS, "This cake it's perfect!");
		pm.printProductReport(p3);
		// ----------------------------------------------------------------------

		// Test p4, Multiple reviews
		Product p4 = pm.createProduct(104, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED, LocalDate.now());
		p4 = pm.reviewProduct(p4, Rating.THREE_STARS, "Just another cookie");
		p4 = pm.reviewProduct(p4, Rating.THREE_STARS, "Meh");
		pm.printProductReport(p4);
		// ----------------------------------------------------------------------

		// Test p5, Multiple reviews
		Product p5 = pm.createProduct(105, "Hot Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED);
		p5 = pm.reviewProduct(p5, Rating.FOUR_STARS, "Tasty!");
		p5 = pm.reviewProduct(p5, Rating.FOUR_STARS, "Not bad at all");
		pm.printProductReport(p5);
		// ----------------------------------------------------------------------

		// Test p6, Multiple reviews
		Product p6 = pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED,
				LocalDate.now().plusDays(3));
		p6 = pm.reviewProduct(p6, Rating.TWO_STARS, "Too sweet");
		p6 = pm.reviewProduct(p6, Rating.THREE_STARS, "Better than cookie :s");
		p6 = pm.reviewProduct(p6, Rating.TWO_STARS, "Too bitter");
		p6 = pm.reviewProduct(p6, Rating.ONE_STAR, "I don't get it!");
		pm.printProductReport(p6);
		// ----------------------------------------------------------------------

	}

}