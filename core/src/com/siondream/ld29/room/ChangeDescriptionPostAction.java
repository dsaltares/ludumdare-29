package com.siondream.ld29.room;

public class ChangeDescriptionPostAction extends PostAction {

	private String description;
	
	@Override
	public void run() {
		action.getRoom().setDescription(description);
	}

}
