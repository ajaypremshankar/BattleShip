package io.ajprem.battleship.interfaces;

/***
 * Functional approach to iterate over scanner till user enters valid input
 *
 *
 */
@FunctionalInterface
public interface ValidationConsumer {

	/***
	 * Only method to be implemented (See example in Battle)
	 *
	 * @param on
	 *            : target for operations
	 * @return : returns if provided input is valid or not
	 */
	public boolean valid(String on);

}
