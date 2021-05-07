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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.9.2. Implement Review Sort and Product Search Features.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	private Locale locale;
	private ResourceBundle resources;
	private DateTimeFormatter dtFormatter;
	private NumberFormat currencyFormat;

	private Map<Product, List<Review>> products;

	{
		products = new HashMap<Product, List<Review>>();
	}

	public ProductManager(Locale locale) {
		this.locale = locale;
		this.resources = ResourceBundle.getBundle("me.fevvelasquez.pm.data.resources", this.locale);
		this.dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(this.locale);
		this.currencyFormat = NumberFormat.getCurrencyInstance(this.locale);
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
		int sum = 0;
		for (var review : reviews) {
			sum += review.getRating().ordinal();
		}
		product = product.applyRating(Math.round((float) sum / reviews.size()));

		products.put(product, reviews);
		return product;
	}

	public void printProductReport(int id) {
		printProductReport(findProduct(id));
	}

	public void printProductReport(Product product) {
		List<Review> reviews = products.get(product);

		StringBuilder mssg = new StringBuilder();
		mssg.append(MessageFormat.format(resources.getString("product"), product.getName(),
				currencyFormat.format(product.getPrice()), product.getRating().getStars(),
				dtFormatter.format(product.getBestBefore())));
		mssg.append("\n");

		Collections.sort(reviews);
		for (var review : reviews) {
			mssg.append(MessageFormat.format(resources.getString("review"), review.getRating().getStars(),
					review.getComments()));
			mssg.append("\n");
		}

		if (reviews.isEmpty()) {
			mssg.append(resources.getString("no.reviews"));
			mssg.append("\n");
		}
		System.out.println(mssg);
	}

	public Product findProduct(int id) {
		Product result = null;
		for (var product : products.keySet()) {
			if (product.getId() == id) {
				result = product;
				break;
			}
		}

		return result;
	}
}
