package com.siondream.ld29.room;

import com.badlogic.gdx.utils.Array;

public class Action {
	String verb;
	String object;
	Array<Condition> conditions;
	Array<PostAction> postActions;
	
	public ActionResult run() {
		for (Condition condition : conditions) {
			if (!condition.isMet()) {
				return new ActionResult(false, condition.getFailMessage());
			}
		}
		
		for (PostAction postAction : postActions) {
			postAction.run();
		}
		
		return new ActionResult(true, "");
	}
}
