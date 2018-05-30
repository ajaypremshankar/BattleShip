package io.ajprem.battleship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/***
 * Ship class holds details for Ship. Like distributed parts on the battlearea
 *

 *
 */
public class Ship implements Comparable<Ship> {

	private final Map<Integer, ShipPart> parts;
	private final String id;
	private final int height;
	private final int width;
	private final ShipType type;
	private boolean isActive;

	public Ship(final String id, final int height, final int width, final ShipType type) {
		this.id = id;
		this.height = height;
		this.width = width;
		this.type = type;
		this.parts = new TreeMap<>();
		this.isActive = true;

		int partId = 1;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				parts.put(partId, new ShipPart(id, partId++));
			}
		}
	}

	public Map<Integer, ShipPart> getParts() {
		return parts;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public ShipType getType() {
		return type;
	}

	public List<ShipPart> getDestroyedParts() {
		List<ShipPart> damaged = new ArrayList<>();
		this.parts.forEach((partId, part) -> {
			if (!part.isActive()) {
				damaged.add(part);
			}
		});

		return damaged;
	}

	public List<ShipPart> getActiveParts() {
		List<ShipPart> active = new ArrayList<>();
		this.parts.forEach((partId, part) -> {
			if (part.isActive()) {
				active.add(part);
			}
		});

		return active;
	}

	public boolean hit(final int partId) {
		ShipPart part = parts.get(partId);
		boolean hit = false;
		if (part != null && part.isActive()) {
			part.incrementHitCount();
			if (type.getTotalHits() <= part.getCurrentHitCount()) {
				part.setActive(false);
			}
			hit = true;
		}

		if (getActiveParts().size() == 0) {
			setActive(false);
		}

		return hit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		result = prime * result + width;
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
		Ship other = (Ship) obj;
		if (height != other.height) {
			return false;
		}
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		return true;
	}

	public String getId() {
		return id;
	}

	@Override
	public int compareTo(final Ship o) {
		return id.compareTo(o.getId());
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean isActive) {
		this.isActive = isActive;
	}

}
