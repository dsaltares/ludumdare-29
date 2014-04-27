package com.siondream.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.siondream.ld29.room.Action;
import com.siondream.ld29.room.ActionResult;
import com.siondream.ld29.room.AddFactPostAction;
import com.siondream.ld29.room.ChangeRoomPostAction;
import com.siondream.ld29.room.FactCondition;
import com.siondream.ld29.room.Room;
import com.siondream.ld29.room.RoomManager;


public class GameScreen extends ScreenAdapter implements InputProcessor {

	private RoomManager roomManager;
	private VignetteController vignetteController;
	
	// UI Stuff
	private Stage stage;
	private TextField actionField;
	private Image titleImage;
	private Image backgroundImage;
	private TypeWriterLabel descriptionLabel;
	private TypeWriterLabel resultLabel;
	private Label actionLabel;
	
	private float resolution[];
	
	FrameBuffer fbo;
	TextureRegion fboRegion;
	
	public GameScreen() {
		loadRooms();
		
		vignetteController = new VignetteController();
		
		Viewport viewport = Env.game.getViewport();
		stage = new Stage(viewport);
		
		resolution = new float[2];
		
		createUI();
	}
	
	@Override
	public void render(float delta) {
		// Update UI stuff
		descriptionLabel.setText(roomManager.getRoom().getDescription());		
		
		vignetteController.update(delta);
		
		// Update stage
		stage.act(delta);
		
		Viewport viewport = Env.game.getViewport();
		resolution[0] = Gdx.graphics.getWidth();
		resolution[1] = Gdx.graphics.getHeight();
		
		// Render
		stage.getSpriteBatch().setShader(null);
		fbo.begin();
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		fbo.end();
		
		
		Batch batch = stage.getSpriteBatch();
		batch.setShader(Assets.shader);
		
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.begin();
		Assets.shader.setUniform2fv("resolution", resolution , 0, 2);
		Assets.shader.setUniformf("radius", vignetteController.getRadius());
		batch.draw(fboRegion, 0.0f, 0.0f);
		batch.end();
		
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
		stage.setKeyboardFocus(actionField);
	}
	
	@Override
	public void resize(int width, int height) {		
		positionUI();
		Viewport viewport = Env.game.getViewport();
		fbo = new FrameBuffer(Format.RGB888, viewport.getViewportWidth(), viewport.getViewportHeight(), false);
		fboRegion = new TextureRegion(fbo.getColorBufferTexture());
		fboRegion.flip(false, true);
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
			json.addClassTag("changeRoom", ChangeRoomPostAction.class);
			
			roomManager = json.fromJson(RoomManager.class, Gdx.files.internal("rooms.json"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createUI() {
		actionLabel = new Label("What do you do?", Assets.skin);
		actionLabel.setColor(Color.WHITE);
		actionLabel.setVisible(false);
		
		actionField = new TextField("", Assets.skin);
		actionField.setVisible(false);

		titleImage = new Image(Assets.title);
		
		backgroundImage = new Image(Assets.background);
		
		descriptionLabel = new TypeWriterLabel("This is supposed to be a super long description", Assets.skin);
		descriptionLabel.setSize(800.0f, 300.0f);
		descriptionLabel.setWrap(true);
		descriptionLabel.setCompletionListener(new TypeWriterListener());
		
		resultLabel = new TypeWriterLabel("", Assets.skin);
		resultLabel.setCompletionListener(new TypeWriterListener());
		
		stage.addActor(backgroundImage);
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
						
						ActionResult result = roomManager.runAction(verb, object);
						
						actionLabel.setVisible(false);
						actionField.setTouchable(Touchable.disabled);
						
						resultLabel.setText(result.message);
						
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
	
	private class TypeWriterListener implements TypeWriterLabel.CompletionListener {

		@Override
		public void onFinished(TypeWriterLabel label) {
			actionField.setTouchable(Touchable.enabled);
			
			actionField.setVisible(true);
			actionLabel.setVisible(true);
			
			Color labelColor = actionLabel.getColor();
			actionLabel.setColor(labelColor.r, labelColor.g, labelColor.b, 0.0f);
			actionLabel.addAction(Actions.alpha(1.0f, 2.5f, Interpolation.pow4Out));
			
			Color fieldColor = actionField.getColor();
			actionField.setColor(fieldColor.r, fieldColor.g, fieldColor.b, 0.0f);
			actionField.addAction(Actions.alpha(1.0f, 2.5f, Interpolation.pow4Out));
		}
		
	}
}
