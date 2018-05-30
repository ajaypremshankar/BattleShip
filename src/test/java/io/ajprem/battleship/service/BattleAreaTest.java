package io.ajprem.battleship.service;

import io.ajprem.battleship.model.Ship;
import io.ajprem.battleship.model.ShipPart;
import io.ajprem.battleship.model.ShipType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BattleAreaTest {

	@Mock
	private Ship mockHitShip;

	@Test
	public void testBattleAreaFailureWithStr() {

		String boundary = "1a";

		boolean failed = false;

		try {
			new BattleArea(boundary);
		} catch (IllegalArgumentException e) {
			failed = true;
		}

		assertEquals(true, failed);
	}

	@Test
	public void testValidateBoundaries() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);

		assertEquals(true, battleArea.validateBoundaries(8, 'S'));
		assertEquals(true, battleArea.validateBoundaries(9, 'T'));

		assertEquals(false, battleArea.validateBoundaries(0, 'A'));
		assertEquals(false, battleArea.validateBoundaries(18, 'U'));
	}

	@Test
	public void testCheckShipPlantingWithOutOfBoundaryStartPoint() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);
		boolean status = false;

		Ship ship = new Ship("A", 1, 1, ShipType.P);

		// Start Location out of boundary
		status = battleArea.parkShip("U3", ship);

		assertEquals(false, status);
	}

	@Test
	public void testCheckShipPlantingWithOutOfBoundaryEndPoint() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);
		boolean status = false;
		Ship ship = new Ship("A", 3, 3, ShipType.P);

		// Start Location out of boundary
		status = battleArea.parkShip("S3", ship);

		assertEquals(false, status);
	}

	@Test
	public void testCheckShipPlantingWithRightBoundary() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);
		boolean failed = false;
		try {
			Ship ship = new Ship("A", 3, 3, ShipType.P);

			// Start Location out of boundary
			battleArea.parkShip("B3", ship);
		} catch (IllegalArgumentException e) {
			failed = true;
		}

		assertEquals(false, failed);
		assertEquals(9, battleArea.getCells().keySet().size());
		assertEquals("[B3, B4, B5, C3, C4, C5, D3, D4, D5]", battleArea.getCells().keySet().toString());
	}

	@Test
	public void testIsValidCell() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);

		assertEquals(true, battleArea.isValidCell("A8"));
		assertEquals(false, battleArea.isValidCell("W9"));
	}

	@Test
	public void testIsValidBoundary() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);

		assertEquals(true, battleArea.isValidBoundary("8A"));
		assertEquals(false, battleArea.isValidBoundary("9W"));
	}

	@Test
	public void testHit() {
		String boundary = "9T";
		BattleArea battleArea = new BattleArea(boundary);
		boolean failed = false;
		when(mockHitShip.compareTo(any(Ship.class))).thenReturn(1);
		Map<Integer, ShipPart> parts = new HashMap<>();
		int partId = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				parts.put(partId, new ShipPart("1", partId++));
			}
		}

		when(mockHitShip.getId()).thenReturn("1");
		when(mockHitShip.getWidth()).thenReturn(3);
		when(mockHitShip.getHeight()).thenReturn(3);
		when(mockHitShip.getParts()).thenReturn(parts);
		when(mockHitShip.hit(any(Integer.class))).thenReturn(true);

		battleArea.parkShip("B3", mockHitShip);

		try {
			assertEquals(true, battleArea.hit("B5"));
			assertEquals(false, battleArea.hit("B9"));

		} catch (IllegalArgumentException e) {
			failed = true;
		}
		assertEquals(false, failed);

	}

}
