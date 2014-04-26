package com.siondream.ld29;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

public class VignetteController {
	private static final float maxRadius = 0.70f;
	private static final float minRadius = 0.60f;
	private static final float time = 1.0f;
	
	private enum State {
		Grow,
		Shrink,
	}
	
	private State state;
	private float timer;
	
	
	public VignetteController() {
		timer = 0.0f;
		state = State.Grow;
	}
	
	public void update(float delta) {
		timer = MathUtils.clamp(timer + delta, 0.0f, time);
		
		if (timer >= time) {
			timer = 0.0f;
			state = state == State.Grow ? State.Shrink : State.Grow;
		}
	}
	
	public float getRadius() {
		return state == State.Grow ? Interpolation.linear.apply(minRadius, maxRadius, timer / time) :
									 Interpolation.linear.apply(maxRadius, minRadius, timer / time);
	}
}
