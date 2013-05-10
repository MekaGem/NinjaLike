package game.mechanics;

import com.badlogic.gdx.math.MathUtils;

public class Room {
	private final int width;
	private final int height;
	private final boolean [][] wall;
	private final boolean [][] passability;

	private Ninja ninja;

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		wall = new boolean [width][height];
		passability = new boolean [width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				wall[x][y] = false;
				passability[x][y] = false;
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean [][] getWall() {
		return wall;
	}

	public void setNinja(Ninja ninja) {
		this.ninja = ninja;
	}

	public Ninja getNinja() {
		return ninja;
	}

	public int getEmptyCells() {
		int emptyCells = 0;
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (!wall[x][y]) {
					emptyCells++;
				}
			}
		}
		return emptyCells;
	}

	public boolean isInto(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	private void dfs(int x, int y) {
		passability[x][y] = true;
		for (int direction = 0; direction < 4; ++direction) {
			Position p = new Position(x, y);
			p = p.move(direction);
			if (isInto(p.getX(), p.getY()) && !wall[p.getX()][p.getY()] && !passability[p.getX()][p.getY()]) {
				dfs(p.getX(), p.getY());
			}
		}
	}

	private int gcd(int a, int b) {
		int c;
		while (a > 0) {
			b %= a;
			c = a;
			a = b;
			b = c;
		}
		return b;
	}

	private boolean checkCell(int x, int y, int cx, int cy) {
		if (x == cx && y == cy) {
			return true;
		}
		if (!isInto(cx, cy)) {
			return false;
		}
		return !wall[cx][cy];
	}

	public boolean isVisible(int x, int y) {
		Position p = new Position(x, y);
		if (p.distanceTo(ninja.getPosition()) > 50) {
			return false;
		}

		if (x == ninja.getX() && y == ninja.getY()) {
			return true;
		}

		int nx = ninja.getX();
		int ny = ninja.getY();
		int w = x - ninja.getX();
		int h = y - ninja.getY();

		if (w == 0) {
			int hstep = h / Math.abs(h);
			for (int dh = hstep; dh != h; dh += hstep) {
				if (!checkCell(x, y, nx, ny + dh)) {
					return false;
				}
			}
			return true;
		} else {
			double k = (double)h / w;
			double wstep = (double)w / (4 * Math.abs(w));

			for (double dw = 0; Math.abs(dw) <= Math.abs(w); dw += wstep) {
				int cx = (int)(nx + 0.5 + dw);
				int cy = (int)(ny + 0.5 + dw * k);
				if (!checkCell(x, y, cx, cy)) {
					return false;
				}
			}

			if (true) return true;

			int gcd = gcd(w, h);
			for (int index = 1; index < gcd; ++index) {
				int cx = nx + (w * index) / gcd;
				int cy = ny + (h * index) / gcd;

				if (!checkCell(x, y, cx, cy) || !checkCell(x, y, cx - 1, cy)
						|| !checkCell(x, y, cx, cy - 1) || !checkCell(x, y, cx - 1, cy - 1)) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean checkPassability() {
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				passability[x][y] = false;
			}
		}

		boolean found = false;
		for (int x = 0; x < width && !found; ++x) {
			for (int y = 0; y < height && !found; ++y) {
				if (!wall[x][y]) {
					dfs(x, y);
					found = true;
				}
			}
		}

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (!wall[x][y] && !passability[x][y]) {
					return false;
				}
			}
		}
		return true;
	}
}
