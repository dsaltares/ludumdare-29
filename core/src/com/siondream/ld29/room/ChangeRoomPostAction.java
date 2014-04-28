package com.siondream.ld29.room;

import com.siondream.ld29.Env;
import com.siondream.ld29.GameScreen;

public class ChangeRoomPostAction extends PostAction {

	private String name;
	
	@Override
	public void run() {
		GameScreen screen = (GameScreen)Env.game.getScreen(GameScreen.class);
		screen.setRoom(name);
	}
}
