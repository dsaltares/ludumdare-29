package com.siondream.ld29.room;

import com.siondream.ld29.Env;
import com.siondream.ld29.GameScreen;

public class AddFactPostAction extends PostAction {

	private String name;
	
	@Override
	public void run() {
		GameScreen screen = (GameScreen)Env.game.getScreen(GameScreen.class);
		screen.getRoomManager().addFact(name);		
	}
}
