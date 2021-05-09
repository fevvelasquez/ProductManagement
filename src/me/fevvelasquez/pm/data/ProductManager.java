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
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.11.2. Add Discount per Rating Calculation.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	private ResourceFormatter rformatter;
	private Map<Product, List<Review>> products = new HashMap<>();

	private static Map<String, ResourceFormatter> rformatters;
	static {
		rformatters = Map.of("en-GB", new ResourceFormatter(Locale.UK), "en-US", new ResourceFormatter(Locale.US),
				"fr-FR", new ResourceFormatter(Locale.FRANCE), "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
				"zh-CN", new ResourceFormatter(Locale.CHINA), "es-MX", new ResourceFormatter(new Locale("es", "MX")));
	}

	public ProductManager(Locale locale) {
		this(locale.toLanguageTag());
	}

	public ProductManager(String languageTag) {
		changeLocale(languageTag);

	}

	public void changeLocale(String languageTag) {
		rformatter = rformatters.getOrDefault(languageTag, rformatters.get("en-GB"));

	}

	public static Set<String> getSupportedLocales() {
		return rformatters.keySet();
	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		Product product = new Food(id, name, price, rating, bestBefore);
		products.putIfAbsent(product, new ArrayList<Review>());
		return product;

	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
		Product product = new Drink(id, name, price, rating);
		products.putIfAbsent(product, new ArrayList<Review>());
		return product;

	}

	public Product reviewProduct(int id, Rating rating, String comments) {
		return reviewProduct(findProduct(id), rating, comments);
	}

	public Product reviewProduct(Product product, Rating rating, String comments) {
		List<Review> reviews = products.get(product);
		products.remove(product, reviews);

		reviews.add(new Review(rating, comments));
		product = product.applyRating(
				(int) Math.round(reviews.stream().mapToInt(r -> r.getRating().ordinal()).average().orElse(0)));

		products.put(product, reviews);
		return product;
	}

	public void printProductReport(int id) {
		printProductReport(findProduct(id));
	}

	public void printProductReport(Product product) {
		List<Review> reviews = products.get(product);

		StringBuilder mssg = new StringBuilder();
		mssg.append(rformatter.formatProduct(product));
		mssg.append("\n");

		if (reviews.isEmpty()) {
			mssg.append(rformatter.getString("no.reviews") + "\n");
		} else {
			// Collections.sort(reviews);
			mssg.append(reviews.stream().sorted().map(r -> rformatter.formatReview(r) + "\n")
					.collect(Collectors.joining()));
		}
		System.out.println(mssg);
	}

	public Product findProduct(int id) {
		return products.keySet().stream().filter(p -> p.getId() == id).findFirst().orElseGet(() -> null);
	}

	public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
		StringBuilder mssg = new StringBuilder();
		mssg.append(products.keySet().stream().filter(filter).sorted(sorter)
				.map(p -> rformatter.formatProduct(p) + "\n").collect(Collectors.joining()));

		System.out.println(mssg);
	}

	/**
	 * Using Streams to implement calculation, formatting and data regrouping logic
	 * may improve performance by merging a number of data manipulations into a
	 * single pass on data and potentially benefiting from parallel stream
	 * processing capabilities in case you may have to handle a very large collection
	 * of products.
	 * 
	 * @return a total of all discount values for each group of products that have
	 *         the same rating.
	 */
	public Map<String, String> getDiscounts() {
		return products.keySet().stream()
				.collect(Collectors.groupingBy(p -> p.getRating().getStars(),
						Collectors.collectingAndThen(Collectors.summingDouble(p -> p.getDiscount().doubleValue()),
								discount -> rformatter.currencyFormat.format(discount))));

	}

	/**
	 * static nested helper class to encapsulate management of text resources and
	 * localization. <br>
	 * This design helps to separate presentation and business logic application.
	 */
	private static class ResourceFormatter {
		private Locale locale;
		private ResourceBundle resources;
		private DateTimeFormatter dtFormatter;
		private NumberFormat currencyFormat;

		private ResourceFormatter(Locale locale) {
			this.locale = locale;
			this.resources = ResourceBundle.getBundle("me.fevvelasquez.pm.data.resources", this.locale);
			this.dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(this.locale);
			this.currencyFormat = NumberFormat.getCurrencyInstance(this.locale);
		}

		private String formatProduct(Product product) {
			return MessageFormat.format(resources.getString("product"), product.getName(),
					currencyFormat.format(product.getPrice()), product.getRating().getStars(),
					dtFormatter.format(product.getBestBefore()));
		}

		private String formatReview(Review review) {
			return MessageFormat.format(resources.getString("review"), review.getRating().getStars(),
					review.getComments());
		}

		private String getString(String key) {
			return resources.getString(key);
		}

	}
}
