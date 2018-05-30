package io.ajprem.battleship.service;

import io.ajprem.battleship.model.ShipType;
import io.ajprem.battleship.util.InputOutput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BattleHelperTest {

	@Mock
	private InputOutput io;

	@Mock
	private BattleArea battleArea;

	@Test
	public void testGetShipTypeFor() {

		BattleHelper battleHelper = new BattleHelper();
		battleHelper.setIo(io);
		when(io.fetchUntil(anyString(), anyString(), any(), anyVararg())).thenReturn("Q");

		ShipType shipType = battleHelper.getShipTypeFor(1);
		assertEquals(ShipType.Q, shipType);
	}

	@Test
	public void testShipDimensions() {

		BattleHelper battleHelper = new BattleHelper();
		battleHelper.setIo(io);
		when(io.fetchUntil(anyString(), anyString(), any(), anyVararg())).thenReturn("1 2");

		Integer[] dimensions = battleHelper.getShipDimensions(1);
		assertArrayEquals(new Integer[] { 1, 2 }, dimensions);
	}

}
