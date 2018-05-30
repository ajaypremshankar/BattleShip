package io.ajprem.battleship;

import io.ajprem.battleship.service.Battle;
import io.ajprem.battleship.service.ObjectPool;
import io.ajprem.battleship.util.AppUtil;
import io.ajprem.battleship.util.InputOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/***
 * Main class to run the application
 *
 *
 *
 */
public class Main {

	/***
	 * @param args
	 *            : Takes input as file path to your input file Example : java
	 *            -jar "C:\input.txt"
	 * @throws FileNotFoundException
	 *             if file does not exist
	 */
	public static void main(final String[] args) throws FileNotFoundException {

		Scanner in;
		if (AppUtil.isNeitherNullNorEmpty(args) && args.length > 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
		}

		boolean wantToPlayAnother = false;

		InputOutput io = new InputOutput(in);
		ObjectPool.setIo(io);

		do {
			Battle game = new Battle(2, "1", "2");

			game.play();
			String reply = io.nextLine("combat.play.another");
			wantToPlayAnother = "Y".equalsIgnoreCase(reply) ? true : false;

		} while (wantToPlayAnother);
	}
}
