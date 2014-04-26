package com.siondream.ld29.room;

import com.siondream.ld29.Env;
import com.siondream.ld29.GameScreen;

public class Condition {
	private String name;
	private String failMessage;
	
	public boolean isMet() {
		RoomManager manager = Env.game.getScreen(GameScreen.class).getRoomManager();
		return manager.hasFact(name);
	}
	
	public String getFailMessage() {
		return failMessage;
	}
}
