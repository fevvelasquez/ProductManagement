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
import java.util.Locale;
import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.ProductManager;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.8.1. Allow Multiple Reviews for a Product.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		// Create instances of Product with Product Manager Factory:
		var pm = new ProductManager(Locale.UK);
		Product p1 = pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		// Print report:
		pm.printProductReport();
		// Write reviews:
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Nice hot cup of tea");
		p1 = pm.reviewProduct(p1, Rating.TWO_STARS, "Rather weak tea");
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Fine");
		p1 = pm.reviewProduct(p1, Rating.FOUR_STARS, "Good tea!");
		p1 = pm.reviewProduct(p1, Rating.FIVE_STARS, "Perfect.");
		p1 = pm.reviewProduct(p1, Rating.THREE_STARS, "Just add some lemon");
		// Print report:
		pm.printProductReport();

//		Product p2 = pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.FOUR_STARS);
//		Product p3 = pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.FIVE_STARS,
//				LocalDate.now().plusDays(2));
//		Product p4 = pm.createProduct(104, "Cookie", BigDecimal.valueOf(3.99), Rating.TWO_STARS, LocalDate.now());
//		Product p5_d = pm.createProduct(105, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STARS);
//		Product p5_d2 = pm.createProduct(105, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STARS);
//		Product p5_f = pm.createProduct(105, "Chocolate", BigDecimal.valueOf(2.99), Rating.FIVE_STARS,
//				LocalDate.now().plusDays(2));
//
//		// Print products:
//		System.out.println(p1);
//		System.out.println(p2);
//		System.out.println(p3);
//		System.out.println(p4);
//
//		// Test equals and hashCode methods:
//		System.out.println();
//		System.out.println(" Are these products equal?=" + p5_d.equals(p5_d2));
//		System.out.println(" hashcode=" + p5_d.hashCode() + p5_d);
//		System.out.println(" hashcode=" + p5_d2.hashCode() + p5_d2);
//		System.out.println(" Are these products equal?=" + p5_d.equals(p5_f));
//		System.out.println(" hashcode=" + p5_d.hashCode() + p5_d);
//		System.out.println(" hashcode=" + p5_f.hashCode() + p5_f);
//
//		// Apply product rating, creates a new Product instance because it is immutable:
//		System.out.println();
//		System.out.println(" Rating applied copies:");
//		System.out.println(p1.applyRating(Rating.ONE_STAR));
//		System.out.println(p1);
//		System.out.println(p3.applyRating(Rating.TWO_STARS));
//		System.out.println(p3);

	}

}