package io.ajprem.battleship.model;

import io.ajprem.battleship.service.BattleArea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerBattleAssocTest {

	@Mock
	private Player player;

	@Mock
	private BattleArea battleArea;

	@Test
	public void testGetNextTarget() {
		String[] targets = { "B1", "B2", "B2", "B3" };
		PlayerBattleAssoc playerBattleAssoc = new PlayerBattleAssoc(player, battleArea, targets);

		assertEquals("B1", playerBattleAssoc.getNextTarget());
		assertEquals("B2", playerBattleAssoc.getNextTarget());
	}

	@Test
	public void testIsNextTargetAvailable() {
		String[] targets = { "B1", "B2" };
		PlayerBattleAssoc playerBattleAssoc = new PlayerBattleAssoc(player, battleArea, targets);

		assertEquals(true, playerBattleAssoc.isNextTargetAvailable());
		String target = playerBattleAssoc.getNextTarget();
		assertEquals(true, playerBattleAssoc.isNextTargetAvailable());
		target = playerBattleAssoc.getNextTarget();
		assertEquals(false, playerBattleAssoc.isNextTargetAvailable());

		assertEquals(1, playerBattleAssoc.getCurrTargetIndex());
	}

}
