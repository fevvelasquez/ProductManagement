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
import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.ProductManager;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.12.1. Use Exception Handling to Fix Logical Errors.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {

		// Supported locales:
		// System.out.println("Supported locales:");
		// for (var locale : ProductManager.getSupportedLocales()) {
		// System.out.println(locale);
		// }
		// System.out.println();
		// ----------------------------------------------------------------------

		// Create products factory using LOCALE
		var pm = new ProductManager("en-GB");
		// ----------------------------------------------------------------------

		// Test id 11 which does not exists!, now throws an exception. =======
		pm.printProductReport(11);
		pm.reviewProduct(11, Rating.FOUR_STARS, "test comments");;
		// ----------------------------------------------------------------------
		
		
		// Test id 101, NOT RATED case
		pm.createProduct(101, "Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		pm.printProductReport(101);
		// ----------------------------------------------------------------------

		// Test id 101, Multiple reviews
		pm.reviewProduct(101, Rating.FOUR_STARS, "Nice hot cup of tea");
		pm.reviewProduct(101, Rating.TWO_STARS, "Rather weak tea");
		pm.reviewProduct(101, Rating.FOUR_STARS, "Fine");
		pm.reviewProduct(101, Rating.FOUR_STARS, "Good tea!");
		pm.reviewProduct(101, Rating.FIVE_STARS, "Perfect.");
		pm.reviewProduct(101, Rating.THREE_STARS, "Just add some lemon");
//		pm.printProductReport(101);
		// ----------------------------------------------------------------------

		// change locale:
//		pm.changeLocale("es-MX");
		// Test id 102, Multiple reviews
		pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		pm.reviewProduct(102, Rating.THREE_STARS, "Coffe was ok");
		pm.reviewProduct(102, Rating.ONE_STAR, "Where is the milk?");
		pm.reviewProduct(102, Rating.FIVE_STARS, "It's perfect with ten spoons of sugar");
//		pm.printProductReport(102);
		// ----------------------------------------------------------------------

		// change locale:
//		pm.changeLocale("ru-RU");
		// Test id 103, Multiple reviews
		pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.NOT_RATED, LocalDate.now().plusDays(2));
		pm.reviewProduct(103, Rating.FIVE_STARS, "Very nice cake");
		pm.reviewProduct(103, Rating.FOUR_STARS, "It's good, but I've expected more chocolate");
		pm.reviewProduct(103, Rating.FIVE_STARS, "This cake it's perfect!");
//		pm.printProductReport(103);
		// ----------------------------------------------------------------------

		// change locale:
//		pm.changeLocale("fr-FR");
		// Test id 104, Multiple reviews
		pm.createProduct(104, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED, LocalDate.now());
		pm.reviewProduct(104, Rating.THREE_STARS, "Just another cookie");
		pm.reviewProduct(104, Rating.THREE_STARS, "Meh");
//		pm.printProductReport(104);
		// ----------------------------------------------------------------------

		// Test p5, Multiple reviews
		Product p5 = pm.createProduct(105, "Hot Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED);
		p5 = pm.reviewProduct(p5, Rating.FOUR_STARS, "Tasty!");
		p5 = pm.reviewProduct(p5, Rating.FOUR_STARS, "Not bad at all");
//		pm.printProductReport(p5);
		// ----------------------------------------------------------------------

		// change locale:
//		pm.changeLocale("zh-CN");
		// Test id 106, Multiple reviews
		pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED, LocalDate.now().plusDays(3));
		pm.reviewProduct(106, Rating.TWO_STARS, "Too sweet");
		pm.reviewProduct(106, Rating.THREE_STARS, "Better than cookie :s");
		pm.reviewProduct(106, Rating.TWO_STARS, "Too bitter");
		pm.reviewProduct(106, Rating.ONE_STAR, "I don't get it!");
//		pm.printProductReport(106);
		// ----------------------------------------------------------------------

//		// STREAM EXAMPLES: =======================================================
//
//		// Sort by name length, implementing Comparator interface inline:
//		System.out.println("--- Sort by name length, ALL:");
//		pm.printProducts(p -> true, new Comparator<>() {
//			@Override
//			public int compare(Product p1, Product p2) {
//				return p1.getName().length() - p2.getName().length();
//			}
//		});
//		// ----------------------------------------------------------------------
//
//		// Sort by price example, using lambda expression directly as a parameter:
//		System.out.println("--- Sort by price, start with 'C' ONLY:");
//		pm.printProducts(p -> p.getName().startsWith("C"), (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
//		// ----------------------------------------------------------------------
//
//		// Sort by rating example, creating Comparator object.:
//		System.out.println("--- Sort by rating, then by name length. Reversed, price < 2 ONLY:");
//		Comparator<Product> ratingAsc = (p1, p2) -> p1.getRating().ordinal() - p2.getRating().ordinal();
//		Comparator<Product> nameLengthAsc = (p1, p2) -> p1.getName().length() - p2.getName().length();
//		pm.printProducts(p -> p.getPrice().floatValue() < 2, nameLengthAsc.thenComparing(ratingAsc).reversed());
		// ----------------------------------------------------------------------

		// test:
		pm.getDiscounts().forEach((rating, discount) -> System.out.println(rating + "\t" + discount));
		// ----------------------------------------------------------------------
	}

}