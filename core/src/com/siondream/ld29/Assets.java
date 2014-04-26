package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

	public static Skin skin;
	public static Texture title;
	
	public Assets() {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		title = new Texture(Gdx.files.internal("ui/title.png"));
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		title.dispose();
	}
}
