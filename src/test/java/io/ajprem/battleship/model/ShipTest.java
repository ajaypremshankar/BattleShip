package io.ajprem.battleship.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ShipTest {

	@Test
	public void testShipCreation() {
		Ship ship = new Ship("A", 3, 5, ShipType.P);

		assertEquals(3, ship.getHeight());
		assertEquals(5, ship.getWidth());
		assertEquals(ShipType.P, ship.getType());

	}

	@Test
	public void testShipPartCreation() {
		Ship ship = new Ship("A", 3, 4, ShipType.P);

		assertEquals(12, ship.getParts().entrySet().size());
		assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]", ship.getParts().keySet().toString());
	}

	@Test
	public void testHit() {
		Ship ship = new Ship("A", 3, 4, ShipType.P);

		Map<Integer, ShipPart> parts = ship.getParts();
		ShipPart targetPart = parts.get(3);

		// Before hit
		assertEquals(0, targetPart.getCurrentHitCount());
		assertEquals(true, targetPart.isActive());

		// Hit
		assertEquals(true, ship.hit(3));

		// After hit
		assertEquals(1, targetPart.getCurrentHitCount());
		assertEquals(false, targetPart.isActive());

	}

	@Test
	public void testMiss() {
		Ship ship = new Ship("A", 3, 4, ShipType.P);

		Map<Integer, ShipPart> parts = ship.getParts();
		ShipPart targetPart = parts.get(15);
		assertEquals(null, targetPart);
		assertEquals(false, ship.hit(15));
	}
}
