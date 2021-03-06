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
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * {@code Product Manager} class represents a factory which creates instances of
 * Product subclasses. <br>
 * 
 * @version 0.14.2. Ensure Product Manager Memory Safety.
 * @author oracle GNU GPL / fevvelasquez
 */
public class ProductManager {

	// STATIC CONTEXT
	private static final Logger logger;
	private static final Map<String, ResourceFormatter> rformatters;
	private static final ProductManager PM_INSTANCE;
	// ----------------------------------------------------------------------
	static {
		logger = Logger.getLogger(ProductManager.class.getName());

		rformatters = Map.of("en-GB", new ResourceFormatter(Locale.UK), "en-US", new ResourceFormatter(Locale.US),
				"fr-FR", new ResourceFormatter(Locale.FRANCE), "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
				"zh-CN", new ResourceFormatter(Locale.CHINA), "es-MX", new ResourceFormatter(new Locale("es", "MX")));

		PM_INSTANCE = new ProductManager();
	}
	// ----------------------------------------------------------------------

	// STATIC METHODS
	public static ProductManager getInstance() {
		return PM_INSTANCE;
	}

	public static Set<String> getSupportedLocales() {
		return rformatters.keySet();
	}
	// ----------------------------------------------------------------------

	// INSTANCE CONTEXT
	private Map<Product, List<Review>> products;
	private final ReentrantReadWriteLock reeLock;
	private final Lock writeLock;
	private final Lock readLock;

	private final ResourceBundle config;
	private final MessageFormat reviewFormat;
	private final MessageFormat productFormat;

	private final Path reportsFolder;
	private final Path dataFolder;
	private final Path tempFolder;
	// ----------------------------------------------------------------------
	{
		products = new HashMap<>();
		reeLock = new ReentrantReadWriteLock();
		writeLock = reeLock.writeLock();
		readLock = reeLock.readLock();

		config = ResourceBundle.getBundle("me.fevvelasquez.pm.data.config");
		reviewFormat = new MessageFormat(config.getString("review.data.format"));
		productFormat = new MessageFormat(config.getString("product.data.format"));

		reportsFolder = Path.of(config.getString("reports.folder"));
		dataFolder = Path.of(config.getString("data.folder"));
		tempFolder = Path.of(config.getString("temp.folder"));
	}
	// ----------------------------------------------------------------------

	// CONSTRUCTORS
	private ProductManager() {
		loadAllData();
	}
	// ----------------------------------------------------------------------

	//
	public ResourceFormatter getLocale(String languageTag) {
		return rformatters.getOrDefault(languageTag, rformatters.get("en-GB"));
	}
	// ----------------------------------------------------------------------

	//
	public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
		Product product = null;

		try {
			writeLock.lock();
			product = new Food(id, name, price, rating, bestBefore);
			products.putIfAbsent(product, new ArrayList<Review>());
		} catch (Exception e) {
			product = null;
			logger.info("Error adding product. " + e.getMessage());
		} finally {
			writeLock.unlock();
		}
		return product;
	}

	public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
		Product product = null;

		try {
			writeLock.lock();
			product = new Drink(id, name, price, rating);
			products.putIfAbsent(product, new ArrayList<Review>());
		} catch (Exception e) {
			product = null;
			logger.info("Error adding product. " + e.getMessage());
		} finally {
			writeLock.unlock();
		}
		return product;
	}
	// ----------------------------------------------------------------------

	//
	public Product reviewProduct(int id, Rating rating, String comments) {
		try {
			writeLock.lock();
			return reviewProduct(findProduct(id), rating, comments);
		} catch (ProductManagerException e) {
			logger.info(e.getMessage());
		} finally {
			writeLock.unlock();
		}
		return null;
	}

	/**
	 * warning: private does not guaranty thread safe operations. Call this method
	 * always from {@link public Product reviewProduct(int id, Rating rating, String
	 * comments) or else add proper lock operations.}
	 */
	private Product reviewProduct(Product product, Rating rating, String comments) {
		List<Review> reviews = products.get(product);
		products.remove(product, reviews);

		reviews.add(new Review(rating, comments));
		product = product.applyRating(
				(int) Math.round(reviews.stream().mapToInt(r -> r.getRating().ordinal()).average().orElse(0)));

		products.put(product, reviews);
		return product;
	}
	// ----------------------------------------------------------------------

	//
	private List<Review> loadReviews(Product product) {
		List<Review> reviews = null;
		Path file = dataFolder
				.resolve(MessageFormat.format(config.getString("reviews.data.file"), String.valueOf(product.getId())));

		if (Files.notExists(file)) {
			reviews = new ArrayList<>();
		} else {
			try {
				reviews = Files.lines(file, Charset.forName("UTF-8")).map(line -> parseReview(line))
						.filter(review -> review != null).collect(Collectors.toList());
			} catch (IOException e) {
				logger.warning("Error loading reviews with id:" + product.getId() + ". " + e.getMessage());
			}
		}

		return reviews;
	}

	private Product loadProduct(Path file) {
		Product product = null;
		try {
			product = parseProduct(Files.lines(file, Charset.forName("UTF-8")).findFirst().orElseThrow());
		} catch (IOException e) {
			logger.warning("Error loading product with path: \"" + file + "\". " + e.getMessage());
		}
		return product;
	}

	private void loadAllData() {
		try {
			products = Files.list(dataFolder).filter(file -> file.getFileName().toString().startsWith("product"))
					.map(file -> loadProduct(file)).filter(product -> product != null)
					.collect(Collectors.toMap(product -> product, product -> loadReviews(product)));
		} catch (IOException e) {
			logger.severe("Error loading All Data within path: \"" + dataFolder + "\". " + e.getMessage());
		}
	}
	// ----------------------------------------------------------------------

	// MEMORY SWAP MECHANISM
	@SuppressWarnings("unused")
	private void dumpData() {
		try {
			if (Files.notExists(tempFolder)) {
				Files.createDirectory(tempFolder);
			}
			Path tempFile = tempFolder.resolve(MessageFormat.format(config.getString("temp.file"), Instant.now()));

			try (ObjectOutputStream out = new ObjectOutputStream(
					Files.newOutputStream(tempFile, StandardOpenOption.CREATE))) {
				out.writeObject(products);
			}
		} catch (IOException e) {
			logger.severe("Error Dumping data " + e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void restoreData() {
		try {
			Path tempFile = Files.list(tempFolder).filter(file -> file.getFileName().toString().endsWith("tmp"))
					.findFirst().orElseThrow();
			try (ObjectInputStream in = new ObjectInputStream(
					Files.newInputStream(tempFile, StandardOpenOption.DELETE_ON_CLOSE))) {
				products = (Map<Product, List<Review>>) in.readObject();
			}

		} catch (Exception e) {
			logger.severe("Error Restoring data " + e.getMessage());
		}

	}
	// ----------------------------------------------------------------------

	//
	private Review parseReview(String text) {
		Review review = null;
		try {
			Object[] values = reviewFormat.parse(text);

			review = new Review(Rateable.ratingValueOf(Integer.parseInt((String) values[0])), (String) values[1]);
		} catch (ParseException | NumberFormatException e) {
			logger.warning("Error Parsing Review \"" + text + "\"");
		}
		return review;
	}

	private Product parseProduct(String text) {
		Product product = null;
		try {
			Object[] values = productFormat.parse(text);

			int id = Integer.parseInt((String) values[1]);
			String name = (String) values[2];
			BigDecimal price = new BigDecimal((String) values[3]);
			Rating rating = Rateable.ratingValueOf(Integer.parseInt((String) values[4]));

			switch ((String) values[0]) {
			case "D":
				product = new Drink(id, name, price, rating);
				break;
			case "F":
				LocalDate bestBefore = LocalDate.parse((String) values[5]);
				product = new Food(id, name, price, rating, bestBefore);
				break;
			}

		} catch (ParseException | NumberFormatException | DateTimeParseException e) {
			logger.warning("Error Parsing \"" + text + "\"");
		}
		return product;
	}
	// ----------------------------------------------------------------------

	//
	public void printProductReport(int id, String languageTag, String client) {
		try {
			readLock.lock();
			printProductReport(findProduct(id), languageTag, client);
		} catch (ProductManagerException e) {
			logger.log(Level.INFO, "Could not print Product Report with id:" + id + ". " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.config(
					"UnsupportedEncoding, error writing Product Report for product id:" + id + ". " + e.getMessage());
		} catch (IOException e) {
			logger.log(Level.SEVERE,
					"IOException, error writing Product Report for product id:" + id + ". " + e.getMessage());
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * warning: private does not guaranty thread safe operations. call this method
	 * always from {@link public void printProductReport(int id, String languageTag)
	 * or else add proper lock operations.}
	 * 
	 * @param client added to ensure different file names from different concurrent
	 *               clients
	 */
	private void printProductReport(Product product, String languageTag, String client)
			throws UnsupportedEncodingException, IOException {
		ResourceFormatter rformatter = getLocale(languageTag);
		List<Review> reviews = products.get(product);
		Path productFile = reportsFolder.resolve(
				MessageFormat.format(config.getString("report.file"), String.valueOf(product.getId()), client));

		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
				Files.newOutputStream(productFile, StandardOpenOption.CREATE), Charset.forName("UTF-8")))) {

			out.append(rformatter.formatProduct(product) + System.lineSeparator());
			if (reviews.isEmpty()) {
				out.append(rformatter.getString("no.reviews") + System.lineSeparator());
			} else {
				out.append(reviews.stream().sorted().map(r -> rformatter.formatReview(r) + System.lineSeparator())
						.collect(Collectors.joining()));
			}
		}
		System.out.println("See: \"" + productFile + "\"");
	}
	// ----------------------------------------------------------------------

	//
	public void printProducts(Predicate<Product> filter, Comparator<Product> sorter, String languageTag) {
		try {
			readLock.lock();
			ResourceFormatter rformatter = getLocale(languageTag);
			StringBuilder mssg = new StringBuilder();
			mssg.append(products.keySet().stream().filter(filter).sorted(sorter)
					.map(p -> rformatter.formatProduct(p) + "\n").collect(Collectors.joining()));
			System.out.println(mssg);
		} finally {
			readLock.unlock();
		}
	}

	public Product findProduct(int id) throws ProductManagerException {
		try {
			readLock.lock();
			return products.keySet().stream().filter(p -> p.getId() == id).findFirst()
					.orElseThrow(() -> new ProductManagerException("Product id:" + id + ", not found."));
		} finally {
			readLock.unlock();
		}
	}
	// ----------------------------------------------------------------------

	/**
	 * Using Streams to implement calculation, formatting and data regrouping logic
	 * may improve performance by merging a number of data manipulations into a
	 * single pass on data and potentially benefiting from parallel stream
	 * processing capabilities in case you may have to handle a very large
	 * collection of products.
	 * 
	 * @return a total of all discount values for each group of products that have
	 *         the same rating.
	 */
	public Map<String, String> getDiscounts(String languageTag) {
		try {
			readLock.lock();
			ResourceFormatter rformatter = getLocale(languageTag);
			return products.keySet().stream()
					.collect(Collectors.groupingBy(p -> p.getRating().getStars(),
							Collectors.collectingAndThen(Collectors.summingDouble(p -> p.getDiscount().doubleValue()),
									discount -> rformatter.currencyFormat.format(discount))));
		} finally {
			readLock.unlock();
		}

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
