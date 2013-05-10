package game.mechanics;

public abstract class Unit {
	private Position position;
	private int health;
	private int maxHealth;

	public Unit(int x, int y, int health) {
		position = new Position(x, y);
		this.health = health;
		this.maxHealth = health;
	}

	public void move(int direction) {
		position = position.move(direction);
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void changeHealth(int change) {
		this.health += change;
	}

	public double getHealthPercentage() {
		return (double)health / maxHealth;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}

