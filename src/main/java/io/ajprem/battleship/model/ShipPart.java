package io.ajprem.battleship.model;

/***
 * One ship m*n will have m*n ship parts placed one on each cell of a battle area
 *

 *
 */
public class ShipPart {

	private boolean isActive;
	private final int partId;
	private int currentHitCount;
	private final String shipId;

	public ShipPart(final String shipId, final int partId) {
		this.shipId = shipId;
		this.partId = partId;
		this.setActive(true);
		this.currentHitCount = 0;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}

	public int getPartId() {
		return partId;
	}

	public int getCurrentHitCount() {
		return currentHitCount;
	}

	public void incrementHitCount() {
		this.currentHitCount = currentHitCount + 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + partId;
		result = prime * result + (getShipId() == null ? 0 : getShipId().hashCode());
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
		ShipPart other = (ShipPart) obj;
		if (partId != other.partId) {
			return false;
		}
		if (getShipId() == null) {
			if (other.getShipId() != null) {
				return false;
			}
		} else if (!getShipId().equals(other.getShipId())) {
			return false;
		}
		return true;
	}

	public String getShipId() {
		return shipId;
	}
}
