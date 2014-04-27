package com.siondream.ld29.room;

import com.badlogic.gdx.utils.Array;

public class Action {
	private Room room;
	private String verb;
	private String object;
	private String message = "";
	private Array<FactCondition> conditions;
	private Array<PostAction> postActions;
	private boolean success = true;
	private boolean valid = true;
	private boolean oneTime = false;
	
	public String getVerb() {
		return verb;
	}
	
	public String getObject() {
		return object;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public ActionResult run() {
		if (!valid) {
			return new ActionResult(false);
		}
		
		for (FactCondition condition : conditions) {
			if (!condition.isMet()) {
				return new ActionResult(false, condition.getFailMessage());
			}
		}
		
		for (PostAction postAction : postActions) {
			postAction.setAction(this);
			postAction.run();
		}
		
		if (oneTime) {
			valid = false;
		}
		
		return new ActionResult(true, message);
	}
}
