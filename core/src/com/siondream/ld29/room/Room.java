package com.siondream.ld29.room;

import com.badlogic.gdx.utils.Array;

public class Room {
	private String name;
	private String description;
	private Array<Action> actions;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Array<Action> getActions() {
		return actions;
	}
}
