package game.mechanics;

import java.util.Random;

public class RoomGenerator {
	private static final Random random = new Random(100);

	private RoomGenerator() {
	}

	private static void putNinja(Room room) {
		int emptyCells = room.getEmptyCells();
		int cell = random.nextInt(emptyCells);

		for (int x = 0; x < room.getWidth(); ++x) {
			for (int y = 0; y < room.getHeight(); ++y) {
				if (!room.getWall()[x][y]) {
					if (cell == 0) {
						Ninja ninja = new Ninja(x, y);
						room.setNinja(ninja);
					}
					cell--;
				}
			}
		}
	}

	private static void buildWalls(Room room, int wallsNumber) {
		for (int index = 0; index < wallsNumber; ++index) {
			int w, h;
			int sx, sy;

			if (random.nextBoolean()) {
				w = random.nextInt(room.getWidth() / 3) + 1;
				h = 1;
				sx = random.nextInt(room.getWidth() - w + 1);
				sy = random.nextInt(room.getHeight());
			} else {
				w = 1;
				h = random.nextInt(room.getHeight() / 3) + 1;
				sx = random.nextInt(room.getWidth());
				sy = random.nextInt(room.getHeight() - h + 1);
			}

			for (int x = 0; x < w; ++x) {
				for (int y = 0; y < h; ++y) {
					room.getWall()[sx + x][sy + y] = true;
				}
			}
		}
	}

	public static Room generateRoom(int width, int height) {
		Room room;

		while (true) {
			room = new Room(width, height);
			int wallsNumber = (int)(1.5 * (width + height));//random.nextInt(width + height)
			buildWalls(room, wallsNumber);
			putNinja(room);

			if (room.checkPassability()) {
				break;
			}
		}

		return room;
	}
}
