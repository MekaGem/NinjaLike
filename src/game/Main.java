package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Drop";
		cfg.useGL20 = true;
		cfg.width = WIDTH;
		cfg.height = HEIGHT;
		cfg.resizable = false;
		new LwjglApplication(new NinjaLikeGame(), cfg);
	}
}