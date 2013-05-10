package game.geom;

public class Segment {
	private Vector2D a;
	private Vector2D b;

	public Segment(Vector2D a, Vector2D b) {
		this.a = a;
		this.b = b;
	}

	public Vector2D getA() {
		return a;
	}

	public Vector2D getB() {
		return b;
	}

	public Segment getBound() {
		double x1, y1;
		double x2, y2;

		x1 = a.getX();
		y1 = a.getY();
		x2 = b.getX();
		y2 = b.getY();

		Vector2D ra = new Vector2D(Math.min(x1, x2), Math.min(y1, y2));
		Vector2D rb = new Vector2D(Math.max(x1, x2), Math.max(y1, y2));
		return new Segment(ra, rb);
	}

	public boolean boundIntersect(Segment s) {
		Segment b1, b2;
		b1 = getBound();
		b2 = s.getBound();
		if (Math.min(b1.getB().getX(), b2.getB().getX()) < Math.max(b1.getA().getX(), b2.getA().getX())) {
			return false;
		}
		if (Math.min(b1.getB().getY(), b2.getB().getY()) < Math.max(b1.getA().getY(), b2.getA().getY())) {
			return false;
		}
		return true;
	}

	public boolean onBothSidesOf(Segment s) {
		int ca = s.getA().compare(a, s.getB());
		int cb = s.getA().compare(b, s.getB());
		return (ca * cb <= 0);
	}

	public boolean intersectsWith(Segment s) {
		return (onBothSidesOf(s) && s.onBothSidesOf(this) && boundIntersect(s));
	}
}
