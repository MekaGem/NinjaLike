package game.mechanics;

import game.geom.Segment;
import game.geom.Vector2D;

public class Room {
	private final int width;
	private final int height;
	private final boolean [][] wall;
	private final boolean [][] passability;
	private final boolean [][] visited;

	private Ninja ninja;

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		wall = new boolean [width][height];
		passability = new boolean [width][height];
		visited = new boolean [width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				wall[x][y] = false;
				passability[x][y] = false;
				visited[x][y] = false;
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

	public void setVisited(int x, int y, boolean visited) {
		this.visited[x][y] = visited;
	}

	public boolean isVisited(int x, int y) {
		return visited[x][y];
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

	private boolean isClearCell(int x, int y, int cx, int cy) {
		if (x == cx && y == cy) {
			return true;
		}
		if (!isInto(cx, cy)) {
			return false;
		}
		return !wall[cx][cy];
	}

	public boolean inSight(Segment sight, int cx, int cy) {
		if (sight.intersectsWith(new Segment(new Vector2D(cx, cy), new Vector2D(cx + 1, cy)))) {
			return true;
		}
		if (sight.intersectsWith(new Segment(new Vector2D(cx, cy), new Vector2D(cx, cy + 1)))) {
			return true;
		}
		if (sight.intersectsWith(new Segment(new Vector2D(cx + 1, cy), new Vector2D(cx + 1, cy + 1)))) {
			return true;
		}
		if (sight.intersectsWith(new Segment(new Vector2D(cx, cy + 1), new Vector2D(cx + 1, cy + 1)))) {
			return true;
		}
		return false;
	}

	public boolean isVisible(int x, int y) {
		Position p = new Position(x, y);
		if (p.distanceTo(ninja.getPosition()) > 5) {
			return false;
		}

		if (x == ninja.getX() && y == ninja.getY()) {
			return true;
		}

		Vector2D a = new Vector2D(ninja.getX() + 0.5, ninja.getY() + 0.5);

		final double [] dx = {0, 0.5, 0, -0.5};
		final double [] dy = {0.5, 0, -0.5, 0};

		int visibleSides = 4;

		for (int index = 0; index < 4; ++index) {
			Vector2D b = new Vector2D(x + 0.5 + dx[index], y + 0.5 + dy[index]);
			Segment sight = new Segment(a, b);

			int sx, sy;
			int fx, fy;

			sx = Math.min(x, ninja.getX());
			sy = Math.min(y, ninja.getY());
			fx = Math.max(x, ninja.getX());
			fy = Math.max(y, ninja.getY());

			boolean found = false;
			for (int cx = sx; cx <= fx && !found; ++cx) {
				for (int cy = sy; cy <= fy && !found; ++cy) {
					if (inSight(sight, cx, cy) && !isClearCell(x, y, cx, cy)) {
						found = true;
						visibleSides--;
					}
				}
			}
		}

		return visibleSides > 0;
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
