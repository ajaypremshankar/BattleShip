package io.ajprem.battleship.model;

import io.ajprem.battleship.util.AppUtil;

/***
 * Ship type enum
 *

 *
 */
public enum ShipType {

	P('P', 1), Q('Q', 2);

	char shipType;
	private final int totalHits;

	private ShipType(final char shipType, final int totalHits) {
		this.shipType = shipType;
		this.totalHits = totalHits;
	}

	public static ShipType getTypeOf(final char type) {
		ShipType resultType = null;
		for (ShipType shipType : ShipType.values()) {
			if (shipType.shipType == type) {
				resultType = shipType;
				break;
			}
		}
		if (AppUtil.isNullOrEmpty(resultType)) {
			throw new IllegalArgumentException("Unsupported ShipType (Only P and Q are valid)");
		}
		return resultType;
	}

	public int getTotalHits() {
		return totalHits;
	}

}
