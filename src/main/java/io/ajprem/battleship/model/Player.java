package io.ajprem.battleship.model;

/***
 * Player class
 *

 *
 */
public class Player {

	private final String id;
	private String name;
	private long rank;
	private long score;

	public Player(final String playerId) {

		this.id = playerId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public long getRank() {
		return rank;
	}

	public void addRank(final long rank) {
		this.rank += rank;
	}

	public long getScore() {
		return score;
	}

	public void addScore(final long score) {
		this.score += score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
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
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return id;
	}

}
