package com.siondream.ld29.room;

import com.siondream.ld29.Env;
import com.siondream.ld29.GameScreen;

public class FactCondition {
	
	private String name;
	private String failMessage;
	private boolean negation;
	
	public boolean isMet() {
		GameScreen screen = (GameScreen)Env.game.getScreen(GameScreen.class);
		RoomManager manager = screen.getRoomManager();
		boolean hasFact = manager.hasFact(name);
		return (negation && !hasFact || !negation && hasFact); 
	}
	
	public String getFailMessage() {
		return failMessage;
	}
}
