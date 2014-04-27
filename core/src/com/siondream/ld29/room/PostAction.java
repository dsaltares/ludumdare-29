package com.siondream.ld29.room;

public abstract class PostAction {
	
	protected Action action;
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public abstract void run();
}
