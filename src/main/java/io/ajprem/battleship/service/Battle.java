package io.ajprem.battleship.service;

import io.ajprem.battleship.model.Player;
import io.ajprem.battleship.model.PlayerBattleAssoc;
import io.ajprem.battleship.model.Ship;
import io.ajprem.battleship.model.ShipType;
import io.ajprem.battleship.util.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/***
 * Instance of Battle represents a game. Every new game will have it's own
 * instance of battle
 *
 *
 *
 */
public class Battle {

	/***
	 * BattleHelper is utility class to provide supporting methods to game to
	 * run.
	 */
	private BattleHelper battleHelper = new BattleHelper();

	private int shipsPerPlayer = 2; // Default 2
	private final String gameId;
	private String currentPlayingPlayerId = null;

	/***
	 * PlayerBattleAreaMap keeps map of an Player.id with PlayerBattleAssoc
	 */
	private TreeMap<String, PlayerBattleAssoc> playerBattleMap;

	/***
	 *
	 * @param shipPerPlayer:
	 *            represents the number of ships each player can have. Current
	 *            default number is 2
	 * @param players
	 *            : varargs to take player ids for this game. Ideally two
	 *            players will play a game but even multiplayer is supported
	 *            (Beta)
	 */
	public Battle(final Integer shipPerPlayer, final String... players) {
		this.gameId = AppUtil.getRandomId("Game");
		this.shipsPerPlayer = shipPerPlayer;
		initPlayerBattleAssoc(players);
	}

	/***
	 *
	 * shipPerPlayer: Current default number is 2
	 *
	 * @param players
	 *            : varargs to take player ids for this game. Ideally two
	 *            players will play a game but even multiplayer is supported
	 *            (Beta)
	 */
	public Battle(final String... players) {
		this.gameId = AppUtil.getRandomId("Game");
		initPlayerBattleAssoc(players);
	}

	/***
	 * On game creation we also initialize Player to battle Association map
	 *
	 * @param players
	 *            : For each player one PlayerBattleAssoc will be maintained in
	 *            the map
	 */
	private void initPlayerBattleAssoc(final String[] players) {

		playerBattleMap = new TreeMap<>();

		for (int i = 1; i < players.length; i++) {

			Player current = ObjectPool.getPlayer(players[i - 1]);
			Player next = ObjectPool.getPlayer(players[i]);

			PlayerBattleAssoc currentAssoc = AppUtil.isNeitherNullNorEmpty(playerBattleMap.get(players[i - 1]))
					? playerBattleMap.get(players[i - 1]) : new PlayerBattleAssoc(current);

					PlayerBattleAssoc nextAssoc = AppUtil.isNeitherNullNorEmpty(playerBattleMap.get(players[i]))
							? playerBattleMap.get(players[i]) : new PlayerBattleAssoc(next);
							if (currentAssoc.getNext() == null) {
								currentAssoc.setNext(nextAssoc);
							}

							playerBattleMap.put(players[i - 1], currentAssoc);
							playerBattleMap.put(players[i], nextAssoc);
		}

		// Creating cyclic assocs last.next = first
		playerBattleMap.get(players[players.length - 1]).setNext(playerBattleMap.get(players[0]));

	}

	/***
	 * This is where we take battle area dimension from user and create one
	 * battle area per player We maintain this data in PlayerBattleAssoc
	 */
	private void initBattleArea() {

		String boundary = battleHelper.getIo().fetchUntil("combat.area.boundaries", "combat.area.boundaries.invalid",
				on -> {
					if (AppUtil.isNeitherNullNorEmpty(on)) {
						on = on.replace(" ", "");
						return AppUtil.isValidBoundary(on);
					}
					return false;
				});
		boundary = boundary.replace(" ", "");
		for (String playerId : playerBattleMap.keySet()) {
			PlayerBattleAssoc playerBattleAssoc = playerBattleMap.get(playerId);
			playerBattleAssoc.setBattleArea(new BattleArea(boundary));
		}
	}

	/***
	 * Single point execution command to start the game. This method enables
	 * user input mechanism to start and then starts battle
	 */
	public void play() {

		battleHelper.getIo().println("combat.start", gameId);

		initBattleArea();
		addShipToBattleArea();
		getTargetsForPlayers();

		fight();
	}

	/***
	 * After creating battle areas, we need to park the ships on the BattleArea.
	 * This method takes care of that
	 */
	private void addShipToBattleArea() {

		for (int shipNumber = 1; shipNumber <= shipsPerPlayer; shipNumber++) {

			ShipType shipType = battleHelper.getShipTypeFor(shipNumber);

			Integer[] dimensions = battleHelper.getShipDimensions(shipNumber);

			Integer rows = dimensions[0];
			Integer columns = dimensions[1];

			for (String playerId : playerBattleMap.keySet()) {

				String shipId = AppUtil.getRandomId("Ship");
				Ship shipForPlayer = new Ship(shipId, rows, columns, shipType);

				BattleArea battleArea = this.playerBattleMap.get(playerId).getBattleArea();
				String location = battleHelper.getValidShipLocationFor(battleArea, shipForPlayer, shipNumber, playerId);

				battleArea.parkShip(location, shipForPlayer);

			}

		}

	}

	/***
	 * Fetches valid targets (Array of locations as string) from the user and
	 * stores information under playerBattleMap
	 */
	private void getTargetsForPlayers() {
		for (String playerId : playerBattleMap.keySet()) {

			String[] targets = battleHelper.getTargetsFor(playerId);

			if (AppUtil.isNeitherNullNorEmpty(targets)) {
				playerBattleMap.get(playerId).setTargets(targets);
			}

		}

	}

	/***
	 * After user provided all the valid inputs for both the users. Firing on
	 * the battleAreas starts here. This method declares if there is any winner
	 * or it is a tie.
	 */
	private void fight() {

		PlayerBattleAssoc rightfulAssoc = findNextRightfullPlayerBattleAssoc();

		while (rightfulAssoc != null) {

			Player playerToPlay = rightfulAssoc.getPlayer();
			String target = rightfulAssoc.getNextTarget();

			if (playerToPlay == null || target == null) {
				break;
			}

			boolean isHit = false;
			for (PlayerBattleAssoc opponentAssoc : getOpponentsOf(rightfulAssoc)) {
				BattleArea battleArea = opponentAssoc.getBattleArea();
				Player opponent = opponentAssoc.getPlayer();
				isHit = battleArea.hit(target);

				battleHelper.getIo().println("combat.player.hit-miss", playerToPlay.getId(), opponent.getId(), target,
						isHit ? "hit" : "missed");

				if (declareWinner(playerToPlay, battleArea)) {
					return;
				}

			}

			if (!isHit) {
				rightfulAssoc = findNextRightfullPlayerBattleAssoc();
			}

		}
		battleHelper.getIo().println("combat.result.draw");

	}

	/***
	 * Method to find opponents of any Player in the game. As we are already
	 * considering this to be multiplayer (More than 2), this method returns
	 * list of opponents
	 *
	 * @param of
	 *            opponent of
	 * @return arraylist of opponents
	 */
	private List<PlayerBattleAssoc> getOpponentsOf(final PlayerBattleAssoc of) {
		List<PlayerBattleAssoc> opponents = new ArrayList<>();
		playerBattleMap.values().forEach((assoc) -> {
			if (!assoc.equals(of)) {
				opponents.add(assoc);
			}
		});
		return opponents;

	}

	/***
	 * If there is any winner just prints the winner id.
	 *
	 * @param player
	 *            currently playing user object
	 * @param opponentBattleArea
	 *            battle area of opponent to check all ships are destroyed or
	 *            not
	 * @return true if this player was a winner
	 */
	private boolean declareWinner(final Player player, final BattleArea opponentBattleArea) {

		if (AppUtil.isNeitherNullNorEmpty(opponentBattleArea) && opponentBattleArea.areAllShipsDestroyed()) {
			battleHelper.getIo().println("combat.result.winner", player.getId());
			return true;
		}

		return false;
	}

	/***
	 * Method to find who should play the next. In case of current player misses
	 * the target, we need to find next player
	 *
	 * @return PlayerBattleAssoc object for the player who'll be playing next
	 */
	private PlayerBattleAssoc findNextRightfullPlayerBattleAssoc() {

		PlayerBattleAssoc tempRightfulAssoc = null;
		int totalPlayersCount = playerBattleMap.keySet().size();
		boolean found = false;
		if (currentPlayingPlayerId == null) {
			tempRightfulAssoc = (PlayerBattleAssoc) playerBattleMap.values().toArray()[0];
		} else {
			tempRightfulAssoc = playerBattleMap.get(currentPlayingPlayerId).getNext();
		}

		for (int i = 0; i < totalPlayersCount; i++) {
			if (!tempRightfulAssoc.isNextTargetAvailable()) {
				battleHelper.getIo().println("combat.player.missiles.consumed", tempRightfulAssoc.getPlayer().getId());
				tempRightfulAssoc = tempRightfulAssoc.getNext();
			} else {
				found = true;
				break;
			}
		}

		if (found && AppUtil.isNeitherNullNorEmpty(tempRightfulAssoc)) {
			Player nextPlayer = tempRightfulAssoc.getPlayer();
			currentPlayingPlayerId = nextPlayer.getId();
		}

		return tempRightfulAssoc;

	}

}
