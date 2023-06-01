import java.sql.Time;
import java.util.Scanner;

public class lifegame {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		// set the interface width & height 
		int frameWidth = 25;
		int frameHight = 25;
		int startNum;
		int[] startX;
		int[] startY;
		int gameMod;
		int cycle = 0;

		// Manually select initial position or choose randomly
		System.out.println("Random inital position: 1");
		System.out.println("Manual set inital position: 2");
		System.out.println("Exit: 3");
		gameMod = input.nextInt();

		if (gameMod == 1) {
			// How many cells do you want for initial state
			System.out.println("Please enter the munber of initial cells");
			startNum = input.nextInt();
			startX = new int[startNum];
			startY = new int[startNum];

			for (int i = 0; i < startNum; i++) {
				startX[i] = (int) (Math.random() * (frameWidth - 1) + 1);
				startY[i] = (int) (Math.random() * (frameHight - 1) + 1);
			}
		} else if (gameMod == 2) {
			System.out.println("Please enter the munber of initial cells");
			startNum = input.nextInt();
			startX = new int[startNum];
			startY = new int[startNum];

			// Coordinates
			printGame(frameWidth, frameHight, startNum, drawAxes(frameWidth, frameHight), cycle);

			for (int i = 1; i <= startNum; i++) {
				System.out.println("Please enter the " + i + "cell's X-coordinate (between 0 and " + frameWidth);
				startX[i - 1] = input.nextInt();
				System.out.println("Please enter the " + i + "cell's Y-coordinate (between 0 and " + frameHight);
				startY[i - 1] = input.nextInt();
			}
		} else {
			startX = new int[0];
			startY = new int[0];
			startNum = 0;
			System.out.println("Game shutdown");
			System.exit(0);
		}

		String[][] coordinate = buildCell(frameWidth, frameHight, startNum, startX, startY);
		printGame(frameWidth, frameHight, startNum, coordinate, cycle);

		// Frame rate (how long a cycle would last)
		while (true) {
			long lastTime = System.currentTimeMillis();
			while (true) {
				long nowTime = System.currentTimeMillis();
				if (nowTime - lastTime >= 600) {
					cycle++;
					cellLogic(frameWidth, frameHight, coordinate);
					printGame(frameWidth, frameHight, startNum, coordinate, cycle);
					break;
				}
			}
		}

	}

	/*************************** methods ***************************/

	// Drawing coordinate
	public static String[][] drawScence(int frameWidth, int frameHight) {
		String[][] coordinate = new String[frameWidth + 1][frameHight + 1];
		int x = 0;
		int y = 0;

		// Generate interface
		for (y = 0; y <= frameHight; y++) {
			for (x = 0; x <= frameWidth; x++) {
				if (x == 0 && y == 0) {
					coordinate[x][y] = " ┌─";
				} else if (x == frameWidth && y == 0) {
					coordinate[x][y] = "─┐";
				} else if (x == 0 && y == frameHight) {
					coordinate[x][y] = " └─";
				} else if (x == frameWidth && y == frameHight) {
					coordinate[x][y] = "─┘";
				} else if (x > 0 && x < frameWidth && (y == 0 || y == frameHight)) {
					coordinate[x][y] = "──";
				} else if ((x == 0 || x == frameWidth) && y > 0 && y < frameHight) {
					coordinate[x][y] = " │ ";
				} else {
					coordinate[x][y] = "  ";
				}
			}
		}
		return coordinate;
	}

	// Generate coordinate
	public static String[][] drawAxes(int frameWidth, int frameHight) {
		String[][] coordinate = new String[frameWidth + 1][frameHight + 1];
		int x = 0;
		int y = 0;

		// Generate interface
		for (y = 0; y <= frameHight; y++) {
			for (x = 0; x <= frameWidth; x++) {
				if (x == 0 && y == 0) {
					coordinate[x][y] = "┌\t";
				} else if (x == frameWidth && y == 0) {
					coordinate[x][y] = "┐\t";
				} else if (x == 0 && y == frameHight) {
					coordinate[x][y] = "└\t";
				} else if (x == frameWidth && y == frameHight) {
					coordinate[x][y] = "┘\t";
				} else if (x > 0 && x < frameWidth && (y == 0 || y == frameHight)) {
					coordinate[x][y] = x + "\t";
				} else if ((x == 0 || x == frameWidth) && y > 0 && y < frameHight) {
					coordinate[x][y] = y + "\t";
				} else {
					coordinate[x][y] = "+\t";
				}
			}
		}
		return coordinate;
	}

	// Generate cells
	public static String[][] buildCell(int frameWidth, int frameHight, int startNum, int[] startX, int[] startY) {
		String[][] coordinate = drawScence(frameWidth, frameHight);
		for (int i = 1; i <= startNum; i++) {
			coordinate[startX[i - 1]][startY[i - 1]] = "o ";
		}
		return coordinate;
	}

	// Cell's logic
	public static void cellLogic(int frameWidth, int frameHight, String[][] coordinate) {
		String[][] saveChange = new String[frameWidth][frameHight];

		for (int y = 1; y < frameHight; y++) {
			for (int x = 1; x < frameWidth; x++) {

				int count;
				if (coordinate[x][y] == "o ") {
					count = -1;
				} else {
					count = 0;
				}

				for (int sideY = y - 1; sideY <= y + 1; sideY++) {
					for (int sideX = x - 1; sideX <= x + 1; sideX++) {
						if (coordinate[sideX][sideY] == "o ") {
							count++;
						}
					}
				}

				if (count < 2) {
					saveChange[x][y] = "  ";
				} else if (count == 2) {

				} else if (count == 3) {
					saveChange[x][y] = "o ";
				} else {
					saveChange[x][y] = "  ";
				}

			}
		}
		for (int y = 1; y < frameHight; y++) {
			for (int x = 1; x < frameWidth; x++) {
				if (saveChange[x][y] != null) {
					coordinate[x][y] = saveChange[x][y];
				}
			}
		}

	}

	// Print screen
	public static void printGame(int frameWidth, int frameHight, int startNum, String[][] coordinate, int cycle) {
		System.out.println(" The " + cycle + " cycle");
		for (int y = 0; y <= frameHight; y++) {
			for (int x = 0; x <= frameWidth; x++) {
				System.out.print(coordinate[x][y]);
				if ((x + 1) % (frameWidth + 1) == 0) {
					System.out.println();
				}
			}
		}
	}

}
