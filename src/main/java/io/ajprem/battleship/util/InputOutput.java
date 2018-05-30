package io.ajprem.battleship.util;

import io.ajprem.battleship.interfaces.ValidationConsumer;

import java.io.PrintStream;
import java.util.Scanner;

/***
 * Basic io class for BattleShipGame
 *

 *
 */
public class InputOutput {

	private Scanner in = null;
	private PrintStream out;

	public InputOutput(final Scanner scanner) {
		this.in = scanner;
		out = System.out;
	}

	/***
	 * Reads data from Console/File
	 *
	 * @param resourceKey
	 *            Shows value from the messages.properties before asking for
	 *            input
	 * @param args
	 *            Args to construct output message
	 * @return returns user input string
	 */
	public String nextLine(final String resourceKey, final Object... args) {

		initScanner();

		String tempMessage = getValueFromMessageProperties(resourceKey).replace("{}", "%s");

		String inputMessage = tempMessage;
		if (AppUtil.isNeitherNullNorEmpty(args) && args.length > 0) {
			inputMessage = String.format(tempMessage, args);
		}
		out.print(inputMessage);
		String op = in.nextLine().trim();
		out.println();
		return op;
	}

	/***
	 * Display method on the console
	 *
	 * @param resourceKey
	 *            resource key for messages.properties
	 * @param args
	 *            Args to construct output message
	 */
	public void print(final String resourceKey, final Object... args) {

		String tempMessage = getValueFromMessageProperties(resourceKey).replace("{}", "%s");

		String inputMessage = tempMessage;
		if (AppUtil.isNeitherNullNorEmpty(args) && args.length > 0) {
			inputMessage = String.format(tempMessage, args);
		}
		out.print(inputMessage);
	}

	/***
	 * Display method on the console with next line
	 *
	 * @param resourceKey
	 *            resource key for messages.properties
	 * @param args
	 *            Args to construct output message
	 */
	public void println(final String resourceKey, final Object... args) {
		String tempMessage = getValueFromMessageProperties(resourceKey).replace("{}", "%s");

		String inputMessage = tempMessage;
		if (AppUtil.isNeitherNullNorEmpty(args) && args.length > 0) {
			inputMessage = String.format(tempMessage, args);
		}
		out.println(inputMessage);

	}

	private void initScanner() {
		if (in == null) {
			in = new Scanner(System.in);
		}
	}

	public void setIn(final Scanner input) {
		this.in = input;
	}

	public void setOut(final PrintStream out) {
		this.out = out;
	}

	private String getValueFromMessageProperties(final String resourceKey) {
		String message = MessagesProperties.get(resourceKey);
		if (AppUtil.isNullOrEmpty(message)) {
			return resourceKey;
		}

		return message;

	}

	/**
	 * Keep asking for input recursively until valid data is provided
	 *
	 * @param initialMessageKey
	 *            message with which user should be prompted with
	 * @param errorMessageKey
	 *            error message if invalid data is provided
	 * @param condition
	 *            validity consumer ValidationConsumer
	 * @param args
	 *            to construct message for the user
	 * @return valid data as string
	 */
	public String fetchUntil(final String initialMessageKey, final String errorMessageKey,
                             final ValidationConsumer condition, final Object... args) {
		String value;
		boolean isValid = false;
		do {
			value = nextLine(initialMessageKey, args);

			try {
				isValid = condition.valid(value);
			} catch (Exception e) {
				isValid = false;
			}
			if (!isValid) {
				println(errorMessageKey);
			}

		} while (!isValid);

		return value;
	}

}
