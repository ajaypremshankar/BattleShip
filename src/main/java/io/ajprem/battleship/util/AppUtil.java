package io.ajprem.battleship.util;

import io.ajprem.battleship.model.Cell;

import java.util.Random;

/***
 * Project level Util class used for very basic operations
 *

 *
 */
public final class AppUtil {

	private static final Random random = new Random();

	private static final Range<Character> heightRange = Range.between('A', 'Z');
	private static final Range<Integer> widthRange = Range.between(1, 9);

	/***
	 *
	 * Constructor for AppUtil
	 */
	private AppUtil() {
	}

	/***
	 * Method to check if given point is boundary or not [A-Z][1-9]
	 *
	 * @param point
	 *            point to be checked upon
	 * @return true if valid
	 */
	public static boolean isValidBoundary(final String point) {
		if (AppUtil.isNullOrEmpty(point) || point.trim().length() != 2) {
			return false;
		}
		int width = AppUtil.getIntAt(0, point);
		char height = AppUtil.getCharAt(1, point);

		return heightRange.contains(height) && widthRange.contains(width);
	}

	/***
	 * Check object null or empty
	 *
	 * @param obj
	 *            target object
	 * @return true if null/empty
	 */
	public static boolean isNullOrEmpty(final Object obj) {
		if (obj == null || String.valueOf(obj).trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/***
	 * Check object is not null or non-empty
	 *
	 * @param obj
	 *            target object
	 * @return true if not null and non-empty
	 */
	public static boolean isNeitherNullNorEmpty(final Object obj) {
		if (obj != null && !String.valueOf(obj).trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/***
	 * Get Int at given index in the string
	 *
	 * @param index
	 *            at which int to be found
	 * @param from
	 *            target string
	 * @return int value found at index in string
	 */
	public static int getIntAt(final int index, final String from) {
		return Integer.valueOf(from.substring(index, index + 1));
	}

	/***
	 * Get Char at given index in the string
	 *
	 * @param index
	 *            at which int to be found
	 * @param from
	 *            target string
	 * @return Char value found at index in string
	 */
	public static char getCharAt(final int index, final String from) {
		return from.charAt(index);
	}

	/**
	 * Get random int for id creation
	 *
	 * @return string representation of random number
	 */
	public static String getRandomId() {
		return Integer.toString(random.nextInt(100));
	}

	/**
	 * Get random String (number) for id creation
	 *
	 * @param target
	 *            which type of id to be created. For "Ship" it'll created
	 *            "S"RandomValue
	 * @return string representation of random number as for given string
	 */
	public static String getRandomId(final String target) {
		if (target == null || target.length() <= 0) {
			return getRandomId();
		}
		return "" + target.charAt(0) + random.nextInt(100);
	}

	/***
	 * Create Cell object for given point
	 *
	 * @param point
	 *            target point string
	 * @return Cell object
	 */
	public static Cell getCell(final String point) {

		int column = AppUtil.getIntAt(1, point);
		char row = AppUtil.getCharAt(0, point);

		Cell cell = new Cell(row, column);
		return cell;
	}
}
