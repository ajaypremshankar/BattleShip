package io.ajprem.battleship.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 * Obtaining properties from messages.properties
 *

 *
 */
public class MessagesProperties {

	private static Properties properties;
	private static InputStream input;

	static {
		try {
			input = new FileInputStream("messages.properties");
			properties = new Properties();

			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Gets property from application properties
	 *
	 * @param key
	 *            key in the properties file
	 * @return value in the properties file
	 */
	public synchronized static String get(final String key) {
		return properties.getProperty(key);
	}

}
