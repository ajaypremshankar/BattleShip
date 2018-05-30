package io.ajprem.battleship.service;

import io.ajprem.battleship.model.Cell;
import io.ajprem.battleship.model.Ship;
import io.ajprem.battleship.model.ShipPart;
import io.ajprem.battleship.util.AppUtil;
import io.ajprem.battleship.util.Range;

import java.util.TreeMap;

/***
 * In every game, each player will have it's battle area where he/she hides
 * his/her ships. Cell with the ship part are stored in treeMap (cells)
 *

 *
 */
public class BattleArea {

	/***
	 * Cells having a ShipPart will be stored in the cells map
	 */
	private final TreeMap<Cell, ShipPart> cells;

	/***
	 * All the ships existing in this battle area
	 */
	private TreeMap<String, Ship> ships;
	/***
	 * Initially ranges are for BattleArea. Once battleArea is created ranges
	 * will be for cells for this battleArea
	 */
	private Range<Character> heightRange = Range.between('A', 'Z');
	private Range<Integer> widthRange = Range.between(1, 9);

	/***
	 * Only constructor to create battle-area for a player
	 *
	 * @param boundaries
	 *            takes as 1E or 5D as input
	 */
	public BattleArea(final String boundaries) {

		if (!AppUtil.isValidBoundary(boundaries)) {
			String message = "Not a valid cell on the BattleArea of : " + widthRange.getMaximum() + " "
					+ heightRange.getMaximum();
			throw new IllegalArgumentException(message);
		}

		int width = AppUtil.getIntAt(0, boundaries);
		char height = AppUtil.getCharAt(1, boundaries);

		createRangesForCells(width, height);

		this.cells = new TreeMap<>();
	}

	/***
	 * Once battle is created. Ranges for battle area boundary will be updated
	 * as user-input
	 *
	 * @param width
	 *            width of the battle area [A-Z]
	 * @param height
	 *            height of the battle area [1-9]
	 */
	private void createRangesForCells(final int width, final char height) {
		heightRange = Range.between('A', height);
		widthRange = Range.between(1, width);
	}

	/***
	 * Validate if battle area boundary for given input is valid or not
	 *
	 * @param width
	 *            width of the component
	 * @param height
	 *            height of the component
	 * @return true if given input is in the boundary
	 */
	public boolean validateBoundaries(final int width, final char height) {

		return heightRange.contains(height) && widthRange.contains(width);
	}

	/***
	 * To park a ship we validate if the ship can be contained in the battle
	 * area or not.
	 *
	 * @param startPoint
	 *            from this point we keep moving right-down to find if battle
	 *            area is wide enough to contain this ship or not
	 * @param ship
	 *            ship to parked
	 * @return true if be contained
	 */
	public boolean isValidShipLocation(final String startPoint, final Ship ship) {
		if (!isValidCell(startPoint)) {
			return false;
		}

		Cell cell = AppUtil.getCell(startPoint);

		char rightLowerHeight = (char) (cell.getRow() + (char) ship.getWidth() - 1);
		int rightLowerWidth = cell.getColumn() + ship.getHeight() - 1;

		if (!validateBoundaries(rightLowerWidth, rightLowerHeight)) {
			return false;
		}

		return true;
	}

	/***
	 * If ship can be parked. We store the ship on the battle area
	 *
	 * @param startPoint
	 *            start point of ship parking
	 * @param ship
	 *            ship to be parked
	 * @return true if ship was parked successfully
	 */
	public boolean parkShip(final String startPoint, final Ship ship) {
		Cell cell = AppUtil.getCell(startPoint);

		if (isValidShipLocation(startPoint, ship)) {
			plantAt(cell, ship);
		} else {
			return false;
		}

		return true;
	}

	/***
	 * Utility method to park the ship at given location
	 *
	 * @param startCell
	 *            Start point as Cell object
	 * @param ship
	 *            Ship which needs to be planted
	 */
	private void plantAt(final Cell startCell, final Ship ship) {
		int endHeight = ship.getWidth() + startCell.getRow() - 1;
		int endWidth = ship.getHeight() + startCell.getColumn() - 1;
		int shipPartId = 1;
		for (int row = startCell.getRow(); row <= endHeight; row++) {
			for (int column = startCell.getColumn(); column <= endWidth; column++) {
				ShipPart part = ship.getParts().get(shipPartId++);
				Cell cell = new Cell((char) row, column, part);

				this.getCells().put(cell, part);

			}
		}

		if (AppUtil.isNullOrEmpty(ships)) {
			this.ships = new TreeMap<>();
		}

		this.ships.put(ship.getId(), ship);
	}

	/***
	 * Checks if Point is valid or not
	 *
	 * @param point
	 *            param in form of [A-Z][1-9]
	 * @return true if this is a valid point on battle area
	 */
	public boolean isValidCell(final String point) {
		if (AppUtil.isNullOrEmpty(point) || point.trim().length() != 2) {
			return false;
		}

		int width = AppUtil.getIntAt(1, point);
		char height = AppUtil.getCharAt(0, point);

		return validateBoundaries(width, height);
	}

	/***
	 * Checks if Boundary is valid or not
	 *
	 * @param point
	 *            param in form of [1-9][A-Z] e.g., 5D
	 * @return true if this is a valid boundary in battle area
	 */
	public boolean isValidBoundary(final String point) {
		if (AppUtil.isNullOrEmpty(point) || point.trim().length() != 2) {
			return false;
		}

		int width = AppUtil.getIntAt(0, point);
		char height = AppUtil.getCharAt(1, point);

		return validateBoundaries(width, height);
	}

	/***
	 * Hit on the target if target is valid or not
	 *
	 * @param point
	 *            target provided by the user
	 * @return true if hit was successful. False if it was missed
	 */
	public boolean hit(final String point) {
		if (!isValidCell(point)) {
			return false;
		}

		Cell cell = AppUtil.getCell(point);
		ShipPart part = this.getCells().get(cell);
		boolean hit = false;
		if (AppUtil.isNeitherNullNorEmpty(part)) {
			String shipId = part.getShipId();
			Ship ship = this.ships.get(shipId);
			hit = ship.hit(part.getPartId());
		}

		return hit;
	}

	/***
	 * Get cells which has Part of ship parked on this battle area
	 *
	 * @return TreeMap of cells
	 */
	public TreeMap<Cell, ShipPart> getCells() {
		return cells;
	}

	/***
	 * Checks if all ships are inactive.
	 *
	 * @return true if all ships are destroyed
	 */
	public boolean areAllShipsDestroyed() {
		boolean allDestroyed = true;
		for (Ship ship : this.ships.values()) {
			if (ship.isActive()) {
				allDestroyed = false;
				break;
			}
		}
		return allDestroyed;
	}

}
