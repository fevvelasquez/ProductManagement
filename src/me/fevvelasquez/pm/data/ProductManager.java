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
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.8.1. Allow Multiple Reviews for a Product.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	private Locale locale;
	private ResourceBundle resources;
	private DateTimeFormatter dtFormatter;
	private NumberFormat currencyFormat;

	private Product product;
	private Review[] reviews;
	{
		// Instance initialization example:
		reviews = new Review[5];
	}

	public ProductManager(Locale locale) {
		this.locale = locale;
		this.resources = ResourceBundle.getBundle("me.fevvelasquez.pm.data.resources", this.locale);
		this.dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(this.locale);
		this.currencyFormat = NumberFormat.getCurrencyInstance(this.locale);
	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		product = new Food(id, name, price, rating, bestBefore);
		return product;

	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
		product = new Drink(id, name, price, rating);
		return product;

	}

	public Product reviewProduct(Product product, Rating rating, String comments) {
		if (reviews[reviews.length - 1] != null) {
			reviews = Arrays.copyOf(reviews, reviews.length + 5);
		}

		int sum = 0, i = 0;
		boolean reviewed = false;

		while (i < reviews.length && !reviewed) {
			if (reviews[i] == null) {
				reviews[i] = new Review(rating, comments);
				reviewed = true;
			}
			sum += reviews[i].getRating().ordinal();
			i++;
		}

		this.product = product.applyRating(Math.round((float) sum / i));
		return this.product;
	}

	public void printProductReport() {
		StringBuilder mssg = new StringBuilder();
		mssg.append(MessageFormat.format(resources.getString("product"), product.getName(),
				currencyFormat.format(product.getPrice()), product.getRating().getStars(),
				dtFormatter.format(product.getBestBefore())));
		mssg.append("\n");

		end_of_reviews: for (var review : reviews) {
			if (review == null) {
				break end_of_reviews;
			}
			mssg.append(MessageFormat.format(resources.getString("review"), review.getRating().getStars(),
					review.getComments()));
			mssg.append("\n");
		}

		if (reviews[0] == null) {
			mssg.append(resources.getString("no.reviews"));
			mssg.append("\n");
		}
		System.out.println(mssg);
	}
}
