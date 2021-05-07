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
 * {@code Review} class represents product reviews. Each review is associated
 * with a rating and comments, in the Product Management System. <br>
 * 
 * @version 0.9.2. Implement Review Sort and Product Search Features.
 * @author oracle GNU GPL / fevvelasquez
 */
public class Review implements Comparable<Review> {
	private Rating rating;
	private String comments;

	/**
	 * @param rating
	 * @param comments
	 */
	public Review(Rating rating, String comments) {
		this.rating = rating;
		this.comments = comments;
	}

	/**
	 * @return the rating
	 */
	public Rating getRating() {
		return rating;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	@Override
	public String toString() {
		return "Review [rating=" + rating + ", comments=" + comments + "]";
	}

	@Override
	public int compareTo(Review other) {
		return other.getRating().ordinal() - this.getRating().ordinal();
	}

}
