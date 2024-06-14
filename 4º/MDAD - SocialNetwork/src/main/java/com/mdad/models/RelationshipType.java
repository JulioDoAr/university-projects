package com.mdad.models;

public enum RelationshipType {
	FRIENDS(1), FOLLOWS(2), BLOCKS(3), DENIED(4);

	private final int type;

	RelationshipType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
