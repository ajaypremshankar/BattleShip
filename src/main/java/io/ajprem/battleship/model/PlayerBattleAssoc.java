package io.ajprem.battleship.model;

import io.ajprem.battleship.service.BattleArea;
import io.ajprem.battleship.util.AppUtil;

/***
 * Class to hold Player and BattleArea data for particular Game. This class is
 * specific to game. One player involved in multiple games can have multiple
 * Assoc. But one player in game will have only one assoc
 *

 *
 */

public class PlayerBattleAssoc {

	private BattleArea battleArea;
	private final Player player;

	private PlayerBattleAssoc next;

	private String[] targets;

	private int currenTargetIndex = -1;

	public PlayerBattleAssoc(final Player player) {
		this.player = player;
	}

	public PlayerBattleAssoc(final Player player, final BattleArea battleArea, final String[] targets) {
		this.player = player;
		this.battleArea = battleArea;
		this.targets = targets;
	}

	public int getCurrTargetIndex() {
		return currenTargetIndex;
	}

	public String getNextTarget() {
		String target = null;

		int nextTarget = currenTargetIndex + 1;
		if (nextTarget < targets.length) {
			target = targets[nextTarget];
			currenTargetIndex = nextTarget;
		}
		return target;
	}

	public boolean isNextTargetAvailable() {
		boolean available = false;

		int nextTarget = currenTargetIndex + 1;
		if (nextTarget < targets.length) {
			available = true;
		}
		return available;
	}

	public BattleArea getBattleArea() {
		return battleArea;
	}

	public Player getPlayer() {
		return player;
	}

	public String[] getTargets() {
		return targets;
	}

	public void setBattleArea(final BattleArea battleArea) {
		this.battleArea = battleArea;
	}

	public void setTargets(final String[] targets) {
		this.targets = targets;
	}

	public PlayerBattleAssoc getNext() {
		return next;
	}

	public void setNext(final PlayerBattleAssoc next) {
		this.next = next;
	}

	@Override
	public String toString() {

		if (AppUtil.isNullOrEmpty(player)) {
			return "<NullPlayer>";
		}
		return player.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (battleArea == null ? 0 : battleArea.hashCode());
		result = prime * result + (player == null ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PlayerBattleAssoc other = (PlayerBattleAssoc) obj;
		if (battleArea == null) {
			if (other.battleArea != null) {
				return false;
			}
		} else if (!battleArea.equals(other.battleArea)) {
			return false;
		}
		if (player == null) {
			if (other.player != null) {
				return false;
			}
		} else if (!player.equals(other.player)) {
			return false;
		}
		return true;
	}

}
