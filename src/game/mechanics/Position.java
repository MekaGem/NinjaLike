package game.mechanics;

public class Position {
	private static final int [] dx = {0, 1, 0, -1};
	private static final int [] dy = {1, 0, -1, 0};

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position move(int direction) {
		return new Position(x + dx[direction], y + dy[direction]);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int distanceTo(Position p) {
		return Math.abs(x - p.x) + Math.abs(y - p.y);
	}
}
