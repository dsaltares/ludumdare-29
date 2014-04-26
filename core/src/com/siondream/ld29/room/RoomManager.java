package com.siondream.ld29.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.siondream.ld29.LudumDare;

public class RoomManager {
	private Array<Room> rooms;
	private Array<String> facts;
	
	private Room currentRoom;
	
	public RoomManager() {
		rooms = new Array<Room>();
		facts = new Array<String>();
		currentRoom = null;
	}
	
	public void reset() {
		facts.clear();
		currentRoom = rooms.size > 0 ? rooms.get(0) : null;
	}
	
	public void runAction(String verb, String object) {
		if (currentRoom == null) {
			return;
		}
		
		Action matchingAction = null;
		
		for (Action action : currentRoom.getActions()) {
			if (action.getVerb().equals(verb) && action.getObject().equals(object)) {
				matchingAction = action;
				break;
			}
		}
		
		ActionResult result = null;
		
		if (matchingAction != null) {
			result = matchingAction.run();
		}
		else {
			result = new ActionResult(false, "you cannot do that");
		}
		
		Gdx.app.log(LudumDare.TAG, result.message);
	}
	
	public void addFact(String name) {
		if (facts.contains(name, false)) {
			facts.add(name);
		}
	}
	
	public void removeFact(String name) {
		facts.removeValue(name, false);
	}
	
	public boolean hasFact(String name) {
		return facts.contains(name, false);
	}
	
	public Room getRoom(String name) {
		for (Room room : rooms) {
			if (room.getName().equals(name)) {
				return room;
			}
		}
		
		return null;
	}
	
	public void setRoom(Room room) {
		currentRoom = room;
	}	
}
