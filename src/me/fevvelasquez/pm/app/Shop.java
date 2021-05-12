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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.fevvelasquez.pm.data.Product;
import me.fevvelasquez.pm.data.ProductManager;
import me.fevvelasquez.pm.data.Rating;

/**
 * {@code Shop} class represents an application that manages Products.
 * 
 * @version 0.14.3. Simulate Concurrent Callers.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Shop {

	/**
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		//
		var pm = ProductManager.getInstance();
		AtomicInteger clientCount = new AtomicInteger(0);
		// ----------------------------------------------------------------------

		// client body
		Callable<String> client = () -> {
			int productId = ThreadLocalRandom.current().nextInt(9) + 1101;
			String clientId = "Client" + clientCount.incrementAndGet();
			String threadName = Thread.currentThread().getName();
			String languageTag = ProductManager.getSupportedLocales().stream()
					.skip(ThreadLocalRandom.current().nextInt(5)).findFirst().get();

			String ID = clientId + " " + threadName;
			StringBuilder log = new StringBuilder();

			log.append("\n");
			log.append(ID + "\n-\tstart of log\t-\n");
			log.append(pm.getDiscounts(languageTag).entrySet().stream()
					.map(entry -> entry.getKey() + "\t" + entry.getValue()).collect(Collectors.joining("\n")));
			Product product = pm.reviewProduct(productId, Rating.FOUR_STARS, "Yet Another Review.");
			log.append((product != null) ? "\nProduct" + productId + " reviewed\n"
					: "\nProduct" + productId + " not reviewed\n");
			pm.printProductReport(productId, languageTag, clientId);
			log.append(clientId + " generated report for" + productId + "product");
			log.append(ID + "\n-\tend of log\t-\n");

			return log.toString();
		};
		// ----------------------------------------------------------------------

		//
		List<Callable<String>> clientsList = Stream.generate(() -> client).limit(5).collect(Collectors.toList());
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		try {
			List<Future<String>> results = executorService.invokeAll(clientsList);
			executorService.shutdown();

			results.stream().forEach(result -> {
				try {
					System.out.println(result.get());
				} catch (InterruptedException | ExecutionException e) {
					Logger.getLogger(Shop.class.getName()).severe("Error retrieveing client log" + e.getMessage());
				}
			});
		} catch (InterruptedException e) {
			Logger.getLogger(Shop.class.getName()).severe("Error invoking clients" + e.getMessage());
		}
		// ----------------------------------------------------------------------

		// From files:
//		pm.printProductReport(1101);
//		pm.printProductReport(1102);
		// ----------------------------------------------------------------------

		// From Shop class:
		pm.createProduct(2201, "Cammomile Tea", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Nice hot cup of tea");
		pm.reviewProduct(2201, Rating.TWO_STARS, "Rather weak tea");
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Fine");
		pm.reviewProduct(2201, Rating.FOUR_STARS, "Good tea!");
		pm.reviewProduct(2201, Rating.FIVE_STARS, "Perfect.");
		pm.reviewProduct(2201, Rating.THREE_STARS, "Just add some lemon");
//		pm.printProductReport(2201);
		// ----------------------------------------------------------------------
		pm.createProduct(2202, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED, LocalDate.now());
		pm.reviewProduct(2202, Rating.THREE_STARS, "Just another cookie");
		pm.reviewProduct(2202, Rating.THREE_STARS, "Meh");
//		pm.printProductReport(2202);S
		// ----------------------------------------------------------------------

		// STREAM EXAMPLES: =======================================================
		System.out.println("=== Print Products:");
		// Comparator<Product> ratingAsc = (p1, p2) -> p1.getRating().ordinal() -
		// p2.getRating().ordinal();
		Comparator<Product> nameLengthAsc = (p1, p2) -> p1.getName().length() - p2.getName().length();
		pm.printProducts(p -> true, nameLengthAsc, "en-GB");
		// ----------------------------------------------------------------------

	}

}