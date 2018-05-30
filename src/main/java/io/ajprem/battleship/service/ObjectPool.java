package io.ajprem.battleship.service;

import io.ajprem.battleship.model.Player;
import io.ajprem.battleship.util.AppUtil;
import io.ajprem.battleship.util.InputOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * Pool to obtain various objects. Simulating singleton
 *

 *
 */
public class ObjectPool {

	private static Map<String, Player> players = new HashMap<>();

	private static InputOutput io;

	/***
	 * Gets a player from the pool. Or creates it and puts on the pool
	 *
	 * @param playerId
	 *            player id to create pool
	 * @return non null Player object
	 */
	public static Player getPlayer(final String playerId) {
		Player player = players.get(playerId);
		if (player == null) {
			player = new Player(playerId);
		}
		return player;
	}

	public static void setIo(final InputOutput io) {

		if (AppUtil.isNeitherNullNorEmpty(io)) {
			ObjectPool.io = io;
		}
	}

	public static InputOutput getIo() {

		if (AppUtil.isNullOrEmpty(io)) {
			setIo(new InputOutput(new Scanner(System.in)));
		}
		return io;
	}

}
