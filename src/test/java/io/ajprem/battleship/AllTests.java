package io.ajprem.battleship;

import io.ajprem.battleship.model.PlayerBattleAssocTest;
import io.ajprem.battleship.model.ShipTest;
import io.ajprem.battleship.service.BattleAreaTest;
import io.ajprem.battleship.service.BattleHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BattleAreaTest.class, ShipTest.class, PlayerBattleAssocTest.class, BattleHelperTest.class })
public class AllTests {
}
