package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Json;
import com.siondream.ld29.room.Action;
import com.siondream.ld29.room.Condition;
import com.siondream.ld29.room.PostAction;
import com.siondream.ld29.room.Room;
import com.siondream.ld29.room.RoomManager;


public class GameScreen extends ScreenAdapter {

	private RoomManager roomManager;
	
	public GameScreen() {
		loadRooms();
	}
	
	@Override
	public void render(float delta) {
			
	}

	@Override
	public void dispose() {
		
	}
	
	public RoomManager getRoomManager() {
		return roomManager;
	}
	
	private void loadRooms() {
		
		try {
			Json json = new Json();
			json.setElementType(Room.class, "actions", Action.class);
			json.setElementType(Action.class, "conditions", Condition.class);
			
			roomManager = json.fromJson(RoomManager.class, Gdx.files.internal("rooms.json"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
