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
 * {@code Rating} enum represents Product ratings from 1 star to 5. Zero for not
 * rated. <br>
 * Possible enumeration values represent star rating cases using unicode
 * symbols.
 * 
 * @version 0.5.2 Add Custom Constructors to the Product Class.
 * @author oracle GNU GPL / fevvelasquez
 */
public enum Rating {
	/**
	 * Five empty white stars unicode symbol sequence.
	 */
	NOT_RATED("\u2606\u2606\u2606\u2606\u2606"),
	/**
	 * One black filled stars plus four empty white stars unicode symbol sequence.
	 */
	ONE_STAR("\u2605\u2606\u2606\u2606\u2606"),
	/**
	 * Two black filled stars plus three empty white stars unicode symbol sequence.
	 */
	TWO_STARS("\u2605\u2605\u2606\u2606\u2606"),
	/**
	 * Three black filled stars plus two empty white stars unicode symbol sequence.
	 */
	THREE_STARS("\u2605\u2605\u2605\u2606\u2606"),
	/**
	 * Four black filled stars plus one empty white star unicode symbol sequence.
	 */
	FOUR_STARS("\u2605\u2605\u2605\u2605\u2606"),
	/**
	 * Five black filled stars unicode symbol sequence.
	 */
	FIVE_STARS("\u2605\u2605\u2605\u2605\u2605");

	private String stars;

	/**
	 * @param unicode representation of stars Rating.
	 */
	private Rating(String stars) {
		this.stars = stars;
	}

	/**
	 * @return the unicode stars String representation of rating.
	 */
	public String getStars() {
		return stars;
	};

}