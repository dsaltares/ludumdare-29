package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

	public static Skin skin;
	public static Texture title;
	public static Texture background;
	public static Texture descriptionPanel;
	public static Texture smallPanel;
	public static ShaderProgram shader;
	public static Sound success;
	public static Sound failure;
	
	public Assets() {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		title = new Texture(Gdx.files.internal("ui/title.png"));
		background = new Texture(Gdx.files.internal("ui/background.png"));
		descriptionPanel = new Texture(Gdx.files.internal("ui/descriptionPanel.png"));
		smallPanel = new Texture(Gdx.files.internal("ui/smallPanel.png"));
		shader = new ShaderProgram(Gdx.files.internal("shaders/postprocess.vert"),
								   Gdx.files.internal("shaders/postprocess.frag"));
		
		if (!shader.isCompiled()) {
			Gdx.app.error(LudumDare.TAG, shader.getLog());
		}
		
		success = Gdx.audio.newSound(Gdx.files.internal("audio/success.wav"));
		failure = Gdx.audio.newSound(Gdx.files.internal("audio/failure.wav"));
		
		title.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		background.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		descriptionPanel.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		smallPanel.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		title.dispose();
		shader.dispose();
		background.dispose();
		descriptionPanel.dispose();
		smallPanel.dispose();
		failure.dispose();
		success.dispose();
	}
}
