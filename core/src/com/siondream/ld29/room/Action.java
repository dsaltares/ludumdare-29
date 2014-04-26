package com.siondream.ld29.room;

import com.badlogic.gdx.utils.Array;

public class Action {
	private String verb;
	private String object;
	private String success;
	private Array<FactCondition> conditions;
	private Array<PostAction> postActions;
	
	public String getVerb() {
		return verb;
	}
	
	public String getObject() {
		return object;
	}
	
	public ActionResult run() {
		for (FactCondition condition : conditions) {
			if (!condition.isMet()) {
				return new ActionResult(false, condition.getFailMessage());
			}
		}
		
		for (PostAction postAction : postActions) {
			postAction.run();
		}
		
		return new ActionResult(true, success);
	}
}
