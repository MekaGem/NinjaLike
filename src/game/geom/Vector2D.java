package game.geom;

import java.util.Comparator;

public class Vector2D implements Comparator<Vector2D> {
	private static final double eps = 1e-5;
	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	public Vector2D subtract(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

	public double dot(Vector2D v) {
		return x * v.x + y * v.y;
	}

	public double cross(Vector2D v) {
		return x * v.y - y * v.x;
	}

	@Override
	public int compare(Vector2D o1, Vector2D o2) {
		double result = o1.subtract(this).cross(o2.subtract(this));
		if (Math.abs(result) < eps) {
			return 0;
		} else if (result > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}
