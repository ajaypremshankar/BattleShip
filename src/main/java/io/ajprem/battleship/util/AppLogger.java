package io.ajprem.battleship.util;

import java.util.logging.Logger;

/***
 * Generic implementation for Obtaining logger in the class
 *

 *
 */
public class AppLogger {

	/***
	 * Only method to get logger object on the basis of class in which it is
	 * being invoked
	 *
	 * @return Logger object
	 */
	public static Logger getLogger() {
		StackTraceElement stackElement = new Throwable().getStackTrace()[1];
		String className = stackElement.getClassName();
		return Logger.getLogger(className);
	}
}