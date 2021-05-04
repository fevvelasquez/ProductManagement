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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.7.2. Enable Products Review and Rating, Product Report.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	private Locale locale;
	private ResourceBundle resources;
	private DateTimeFormatter dtFormatter;
	private NumberFormat currencyFormat;

	private Product product;
	private Review review;

	public ProductManager(Locale locale) {
		this.locale = locale;
		this.resources = ResourceBundle.getBundle("me.fevvelasquez.pm.data.resources", locale);
		this.dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).localizedBy(locale);
		this.currencyFormat = NumberFormat.getCurrencyInstance(locale);
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
		this.review = new Review(rating, comments);
		this.product = product.applyRating(rating);
		return this.product;
	}

	public void printProductReport() {
		StringBuilder mssg = new StringBuilder();
		mssg.append(
				MessageFormat.format(
						resources.getString("product"),
						product.getName(),
						currencyFormat.format(product.getPrice()),
						product.getRating().getStars(),
						dtFormatter.format(product.getBestBefore())
						)
				);
		mssg.append("\n");
		if(review!=null) {
			mssg.append(
					MessageFormat.format(
							resources.getString("review"), 
							review.getRating().getStars(),
							review.getComments()
							)
					);
		}else {
			mssg.append(resources.getString("no.reviews"));
		}
		mssg.append("\n");
		System.out.println(mssg);
	}
}
