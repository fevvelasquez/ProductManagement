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

/**
 * {@code Rateable} functional interface represents an ability to associate
 * different classes with ‘Rating’ objects, in the Product Management System.
 * <br>
 * 
 * @version 0.7.1. Design the Rateable Interface.
 * @author oracle GNU GPL / fevvelasquez
 */
@FunctionalInterface
public interface Rateable<T> {
	public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

	/**
	 * Creates a new instance from current, applying the new rating value received
	 * as parameter.
	 * 
	 * @param newRating Product new rating as {@code Rating} enum.
	 * 
	 */
	public abstract T applyRating(Rating rating);

	public default T applyRating(int stars) {
		return applyRating(ratingValueOf(stars));
	}

	public default Rating getRating() {
		return DEFAULT_RATING;
	}

	public static Rating ratingValueOf(int stars) {
		return (stars >= 0 && stars <= 5) ? Rating.values()[stars] : DEFAULT_RATING;
	}
}
