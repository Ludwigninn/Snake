package AlexSnake;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Exempel {
	private Brick[][] brickArray;

	public void checkDFS() {
		Stack<Brick> s = new Stack<Brick>();
		System.out.println("x: " + brickArray[0][0].x + " y: " + brickArray[0][0].y);
		s.push(brickArray[0][0]);

		while (!s.isEmpty()) {
			Brick brick = s.peek();

			List<Brick> neighbors = getNeighbors(brickArray, brick.x, brick.y);
			if (!neighbors.isEmpty()) {
				for (Brick b : neighbors) {
					if (!b.isVisited() && !b.isObstacle()) {
						System.out.println("x: " + b.x + " y: " + b.y + " ");
						brick.setVisited(true);
						s.push(b);
					}
				}
			} else {
				s.pop();
			}
		}
	}

	private List<Brick> getNeighbors(Brick[][] matrix, int x, int y) {
		List<Brick> neighbors = new ArrayList<Brick>();

		if (isValidPoint(matrix, x - 1, y)) {
			neighbors.add(brickArray[x - 1][y]);
		}
		if (isValidPoint(matrix, x + 1, y)) {
			neighbors.add(brickArray[x + 1][y]);
		}
		if (isValidPoint(matrix, x, y - 1)) {
			neighbors.add(brickArray[x][y - 1]);
		}
		if (isValidPoint(matrix, x, y + 1)) {
			neighbors.add(brickArray[x][y + 1]);
		}

		return neighbors;
	}

	private boolean isValidPoint(Brick[][] matrix, int x, int y) {
		return !(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && !matrix[x][y].isObstacle();
	}

	/**
	 * Metod som hämtar textfilen från datorn
	 * 
	 * @param textToParse
	 *            Sökvägen där textfilen ligger på datorn
	 */
	public void parseTextFile(String textToParse) {
		ArrayList<String> splitted = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(textToParse))) {
			String line;
			String[] cutLine;
			while ((line = br.readLine()) != null) {
				cutLine = line.split(",");
				if (cutLine.length > 2) {
					for (int i = 0; i < 3; i++)
						splitted.add(cutLine[i]);

				} else {
					splitted.add(cutLine[0]);
					splitted.add(cutLine[1]);
				}
			}
			initializeVariables(splitted);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metod som initierar alla variabler, hämtar rader
	 * 
	 * @param parsedText
	 *            ArrayList med kartan
	 */
	private void initializeVariables(ArrayList<String> parsedText) {
		int nbrColums = Integer.parseInt(parsedText.get(0));
		int nbrRows = Integer.parseInt(parsedText.get(1));
		int nbrOfObstacles = Integer.parseInt(parsedText.get(2));
		int obstacle_X;
		int obstacle_Y;

		// Array med storleken enligt rader & kolumner.
		brickArray = new Brick[nbrColums][nbrRows];

		// Initierar brickorna och hindren i spelet.
		initializeBricks();

		// En 3:a då första positionen på första hindret är [3] enligt input.txt
		int indexCounter = 3;

		// Loop som körs lika många ggr som det finns hinder.
		for (int i = 0; i < nbrOfObstacles; i++) {
			obstacle_X = Integer.parseInt(parsedText.get(indexCounter));
			obstacle_Y = Integer.parseInt(parsedText.get(indexCounter + 1));
			brickArray[obstacle_X][obstacle_Y].setIsObstacle(true);

			// Öka med 2 pga av varje hinder representeras av [x],[y]
			indexCounter += 2;
		}
	}

	/**
	 * Metod som skapar lika många brick-objekt som det finns rader & kolumner
	 * och lägger dom i brick-Arrayen.
	 */
	private void initializeBricks() {
		for (int x = 0; x < brickArray.length; x++) {
			for (int y = 0; y < brickArray[x].length; y++) {
				brickArray[x][y] = new Brick(x, y);
			}
		}
	}
}
