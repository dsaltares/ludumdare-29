package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

	public static Skin skin;
	public static Texture title;
	public static Texture background;
	public static ShaderProgram shader;
	
	public Assets() {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		title = new Texture(Gdx.files.internal("ui/title.png"));
		background = new Texture(Gdx.files.internal("ui/background.png"));
		shader = new ShaderProgram(Gdx.files.internal("shaders/postprocess.vert"),
								   Gdx.files.internal("shaders/postprocess.frag"));
		
		if (!shader.isCompiled()) {
			Gdx.app.error(LudumDare.TAG, shader.getLog());
		}
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		title.dispose();
		shader.dispose();
		background.dispose();
	}
}
