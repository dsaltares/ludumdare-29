package com.siondream.ld29.room;

import com.siondream.ld29.Env;
import com.siondream.ld29.GameScreen;

public class AddFactPostAction implements PostAction {

	private String name;
	
	@Override
	public void run() {
		Env.game.getScreen(GameScreen.class).getRoomManager().addFact(name);		
	}
}
