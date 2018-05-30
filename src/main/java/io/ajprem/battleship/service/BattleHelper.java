package io.ajprem.battleship.service;

import io.ajprem.battleship.model.Ship;
import io.ajprem.battleship.model.ShipType;
import io.ajprem.battleship.util.AppUtil;
import io.ajprem.battleship.util.InputOutput;

public class BattleHelper {

	/***
	 * Obtaining InputOutput from ObjectPool
	 */
	private InputOutput io = ObjectPool.getIo();

	/***
	 * Obtain ShipType from until he provides valid ShipType (P or Q)
	 *
	 * @param shipNumber
	 *            for the shipNumber in the battle (1 to totalShipsPerPlayer)
	 * @return non-null ShipType object
	 */
	public ShipType getShipTypeFor(final int shipNumber) {
		String shipTypeStr = getIo().fetchUntil("combat.ship.type", "combat.ship.type.invalid", on -> {
			try {
				if (AppUtil.isNeitherNullNorEmpty(on)) {
					ShipType.getTypeOf(on.charAt(0));
					return true;
				}
				return false;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}, shipNumber);

		return ShipType.getTypeOf(shipTypeStr.charAt(0));
	}

	/***
	 * Obtain ship dimensions from User
	 *
	 * @param shipNumber
	 *            shipNumber for which these dimensions are
	 * @return Non empty dimension
	 */
	public Integer[] getShipDimensions(final int shipNumber) {
		String dimensionStr = null;

		dimensionStr = getIo().fetchUntil("combat.ship.dimension", "combat.ship.dimension.invalid", on -> {
			if (AppUtil.isNeitherNullNorEmpty(on)) {
				on = on.replace(" ", "");
				return true;
			}
			return false;
		}, shipNumber);

		dimensionStr = dimensionStr.replace(" ", "");
		Integer rows = AppUtil.getIntAt(0, dimensionStr);
		Integer columns = AppUtil.getIntAt(1, dimensionStr);

		return new Integer[] { rows, columns };
	}

	/***
	 * On every battle area ships can be placed on valid locations only (within
	 * its boundaries).
	 *
	 * @param battleArea
	 *            object against which boundaries will be validated
	 * @param ship
	 *            Ship which needs to be parked
	 * @param shipNumber
	 *            For display purpose
	 * @param playerId
	 *            Playerid for which this ship is being parked
	 * @return returns valid ship location as [A-Z][1-9]
	 */
	public String getValidShipLocationFor(final BattleArea battleArea, final Ship ship, final int shipNumber,
			final String playerId) {

		boolean isValidLocation = false;
		String location = null;
		do {
			location = getIo().fetchUntil("combat.ship.location", "combat.ship.location.invalid", on -> {
				if (AppUtil.isNeitherNullNorEmpty(on)) {
					on = on.replace(" ", "");
					return true;
				}
				return false;
			}, shipNumber, playerId);

			isValidLocation = battleArea.isValidShipLocation(location, ship);
			if (!isValidLocation) {
				getIo().println("combat.ship.location.invalid");
			}
		} while (!isValidLocation);
		return location;
	}

	/***
	 * Obtain targets for firing
	 *
	 * @param playerId
	 *            targets for PlayerId
	 * @return returns araay of points in form of [A-Z][1-9]
	 */
	public String[] getTargetsFor(final String playerId) {
		String targets = getIo().fetchUntil("combat.player.targets", "combat.player.targets.invalid", on -> {
			if (AppUtil.isNeitherNullNorEmpty(on) && on.contains(" ")) {
				return true;
			}
			return false;
		}, playerId);
		return targets.split(" ");
	}

	public InputOutput getIo() {
		return io;
	}

	public void setIo(final InputOutput io) {
		this.io = io;
	}
}
