package io.ajprem.battleship.util;

/***
 * Helper class to imitate range functionality
 *

 *
 */
public final class Range<T extends Comparable<T>> {

	private final T minimum;
	private final T maximum;

	public static <T extends Comparable<T>> Range<T> between(final T min, final T max) {
		return new Range<>(min, max);

	}

	private Range(final T min, final T max) {

		if (min.compareTo(max) <= 0) {
			this.minimum = min;
			this.maximum = max;
		} else {
			this.minimum = max;
			this.maximum = min;
		}
	}

	public boolean contains(final T val) {
		if (val == null) {
			return false;
		}
		return val.compareTo(minimum) > -1 && val.compareTo(maximum) < 1;
	}

	public T getMinimum() {
		return minimum;
	}

	public T getMaximum() {
		return maximum;
	}

}
