package com.siondream.ld29;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TypeWriterLabel extends Label {

	public interface CompletionListener {
		public void onFinished(TypeWriterLabel label);
	}
	
	private CharSequence fullText;
	private float lettersPerSecond;
	private float totalTime;
	private float timer;
	private boolean playing;
	private CompletionListener listener;
	
	public TypeWriterLabel(CharSequence text, Skin skin) {
		this(text, skin, 30.0f);
	}
	
	public TypeWriterLabel(CharSequence text, Skin skin, float lettersPerSecond) {
		super(text, skin);
		
		this.lettersPerSecond = lettersPerSecond;
		this.timer = 0.0f;
		this.fullText = text;
		this.totalTime = fullText.length() / lettersPerSecond;
		this.playing = true;
	}
	
	public void setCompletionListener(CompletionListener listener) {
		this.listener = listener;
	}

	public void reset() {
		timer = 0.0f;
		playing = true;
	}
	
	public void stop() {
		playing = false;
		timer = 0.0f;
	}
	
	public void play() {
		playing = true;
	}
	
	public void pause() {
		playing = false;
	}
	
	public boolean isFinished() {
		return timer >= totalTime;
	}
	
	@Override
	public void act(float delta) {
		boolean wasFinished = isFinished();
		
		if (playing) {
			timer = Math.min(timer + delta, totalTime);
		}
		
		float percentage = timer / totalTime;		
		int numberOfLetters = Math.min((int) (fullText.length() * percentage), fullText.length());
		super.setText(fullText.subSequence(0, numberOfLetters));
		
		if (!wasFinished && isFinished() && listener != null) {
			listener.onFinished(this);
		}
	}
	
	@Override
	public void setText(CharSequence newText) {
		if (!newText.equals(fullText)) {
			timer = 0.0f;
			fullText = newText;
			totalTime = fullText.length() / lettersPerSecond;
		}
	}
	
	@Override
	public CharSequence getText() {
		return fullText;
	}
}
