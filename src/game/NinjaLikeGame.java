package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.mechanics.*;

public class NinjaLikeGame implements ApplicationListener {
	private Texture stone;
	private Texture wall;
	private Texture ninja;

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;

	private Room room;

	@Override
	public void create() {
		stone = new Texture(Gdx.files.internal("data/images/stone.png"));
		wall = new Texture(Gdx.files.internal("data/images/wall.png"));
		ninja = new Texture(Gdx.files.internal("data/images/ninja.png"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		room = RoomGenerator.generateRoom(20, 20);
	}

	@Override
	public void resize(int i, int i2) {
	}

	private void moveNinja(int direction) {
		Position p = room.getNinja().getPosition();
		p = p.move(direction);
		if (room.isInto(p.getX(), p.getY()) && !room.getWall()[p.getX()][p.getY()]) {
			room.getNinja().setPosition(p);
		}
	}

	private void processMoveInput() {
		int direction = -1;

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (!upPressed) {
				direction = 0;
			}
			upPressed = true;
		} else {
			upPressed = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (!rightPressed) {
				direction = 1;
			}
			rightPressed = true;
		} else {
			rightPressed = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (!downPressed) {
				direction = 2;
			}
			downPressed = true;
		} else {
			downPressed = false;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (!leftPressed) {
				direction = 3;
			}
			leftPressed = true;
		} else {
			leftPressed = false;
		}

		if (direction != -1) {
			moveNinja(direction);
		}
	}

	private void renderUnitHealth(Unit u) {
		int nx = u.getX() * 32, ny = u.getY() * 32;
		shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 0.0f);
		shapeRenderer.rect(nx + 1, ny + 1, 30, 4);
		shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 0.0f);
		shapeRenderer.rect(nx + 2, ny + 2, (int) (28 * u.getHealthPercentage()), 2);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		batch.begin();
		for (int x = 0; x < room.getWidth(); ++x) {
			for (int y = 0; y < room.getHeight(); ++y) {
				if (room.isVisible(x, y)) {
					if (room.getWall()[x][y]) {
						batch.draw(wall, x * 32, y * 32);
					} else {
						batch.draw(stone, x * 32, y * 32);
					}
				}
			}
		}
		int nx = room.getNinja().getX() * 32, ny = room.getNinja().getY() * 32;
		batch.draw(ninja, nx, ny);
		batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		renderUnitHealth(room.getNinja());
		shapeRenderer.end();

		processMoveInput();
	}

	@Override
	public void pause() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void resume() {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void dispose() {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
