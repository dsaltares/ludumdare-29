package com.siondream.ld29.room;

public class ActionResult {
	public final boolean success;
	public final String message;
	
	public ActionResult(boolean success) {
		this.success = success;
		this.message = success ? "" : "You cannot do that!";
	}
	
	public ActionResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
}
