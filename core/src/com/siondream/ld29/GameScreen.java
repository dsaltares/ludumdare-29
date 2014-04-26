package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.utils.Json;
import com.siondream.ld29.room.Action;
import com.siondream.ld29.room.AddFactPostAction;
import com.siondream.ld29.room.FactCondition;
import com.siondream.ld29.room.Room;
import com.siondream.ld29.room.RoomManager;


public class GameScreen extends ScreenAdapter implements InputProcessor {

	private RoomManager roomManager;
	
	// UI Stuff
	private Stage stage;
	private TextField actionField;
	private Image titleImage;
	private Label descriptionLabel;
	private Label resultLabel;
	private Label actionLabel;
	
	public GameScreen() {
		loadRooms();
		
		stage = new Stage(Env.game.getViewport());
		createUI();
	}
	
	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		roomManager.reset();
	}
	
	@Override
	public void resize(int width, int height) {
		positionUI();
	}
	
	public RoomManager getRoomManager() {
		return roomManager;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		stage.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		stage.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		stage.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		stage.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		stage.scrolled(amount);
		return false;
	}
	
	private void loadRooms() {
		
		try {
			Json json = new Json();
			json.setElementType(Room.class, "actions", Action.class);
			json.setElementType(Action.class, "conditions", FactCondition.class);
			json.addClassTag("addFact", AddFactPostAction.class);
			
			roomManager = json.fromJson(RoomManager.class, Gdx.files.internal("rooms.json"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createUI() {
		actionLabel = new Label("What do you do?", Assets.skin);
		actionLabel.setColor(Color.WHITE);
		
		actionField = new TextField("", Assets.skin);

		titleImage = new Image(Assets.title);
		
		descriptionLabel = new Label("This is supposed to be a super long description", Assets.skin);
		descriptionLabel.setSize(800.0f, 300.0f);
		descriptionLabel.setWrap(true);
		
		resultLabel = new Label("Result", Assets.skin);
		
		stage.addActor(actionLabel);
		stage.addActor(actionField);
		stage.addActor(titleImage);
		stage.addActor(descriptionLabel);
		stage.addActor(resultLabel);
		
		actionField.setTextFieldListener(new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char c) {
				if (c == '\r') {
					String text = textField.getText();
					String[] parts = text.toLowerCase().split(" ");
					
					if (parts.length >= 2) {
						String verb = parts[0];
						String object = parts[1];
						
						roomManager.runAction(verb, object);
						
						if (roomManager.isFinished()) {
							// GAME FINISHED!!
							
						}
					}
					
					textField.setText("");
				}
			}
			
		});
		
		positionUI();
	}
	
	private void positionUI() {
		titleImage.setX((stage.getWidth() - titleImage.getWidth()) * 0.5f);
		titleImage.setY(stage.getHeight() - titleImage.getHeight() - 40.0f);
		
		descriptionLabel.setX((stage.getWidth() - descriptionLabel.getWidth()) * 0.5f);
		descriptionLabel.setY(200.0f);
		
		actionLabel.setPosition(descriptionLabel.getX(), 40.0f);
		
		actionField.setPosition(actionLabel.getRight() + 20.0f, actionLabel.getY());
		
		resultLabel.setPosition(descriptionLabel.getX(), descriptionLabel.getY() - 50.0f);
	}
}
