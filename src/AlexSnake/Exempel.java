package AlexSnake;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Exempel {
	private Brick[][] brickArray;
	
	private class Coords {
		private int x;
		private int y;
		
		public Coords(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return x + "," + y;
		}
	}

	public void checkOne() {
		brickArray[3][0].setVisited(true);
		brickArray[3][2].setVisited(true);
		Coords coords = getNeighbor(3, 1);
		System.out.println(coords.x + "," + coords.y);
	}
	
	public void checkDFS() {
		ArrayList<Coords> coordList = new ArrayList<Coords>();
		int count = 0;
		Stack<Coords> s = new Stack<Coords>();
		// push first node
		s.push(new Coords(0, 0));
		brickArray[0][0].setVisited(true);
		
		while(!s.isEmpty()) {
			Coords coords = s.pop();
			if(coords != null) {
				Coords neighborCoords = getNeighbor(coords.x, coords.y);
				Brick currentBrick = brickArray[coords.x][coords.y];
				if(neighborCoords != null) {
					Brick nextBrick = brickArray[neighborCoords.x][neighborCoords.y];
					
					if(!nextBrick.isObstacle() && !nextBrick.isVisited()) {
						Coords nextCoord = new Coords(neighborCoords.x, neighborCoords.y);
						coordList.add(nextCoord);
						count++;
						s.push(nextCoord);
						//brickArray[neighborCoords.x][neighborCoords.y].setVisited(true);
					}
				}
			}
		}
		
		printMatrix(coordList, count);
	}
	
	private void printMatrix(ArrayList<Coords> coordList, int count) {
		System.out.println(count);
		for(Coords coord : coordList) {
			System.out.println(coord.x + "," + coord.y);
		}
	}

	private Coords getNeighbor(int x, int y) {
		Coords neighbor = null;

		if (isValidPoint(x - 1, y)) {
			neighbor = new Coords(x - 1, y);
 		} 
		if (isValidPoint(x + 1, y)) {
			neighbor = new Coords(x + 1, y);
		} 
		if (isValidPoint(x, y - 1)) {
			neighbor = new Coords(x, y - 1);
		} 
		if (isValidPoint(x, y + 1)) {
			neighbor = new Coords(x, y + 1);
		}

		return neighbor;
	}

	private boolean isValidPoint(int x, int y) {
		return !(x < 0 || x >= brickArray.length || y < 0 || y >= brickArray.length);
	}

	/**
	 * Metod som h�mtar textfilen fr�n datorn
	 * 
	 * @param textToParse
	 *            S�kv�gen d�r textfilen ligger p� datorn
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
	 * Metod som initierar alla variabler, h�mtar rader
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

		// En 3:a d� f�rsta positionen p� f�rsta hindret �r [3] enligt input.txt
		int indexCounter = 3;

		// Loop som k�rs lika m�nga ggr som det finns hinder.
		for (int i = 0; i < nbrOfObstacles; i++) {
			obstacle_X = Integer.parseInt(parsedText.get(indexCounter));
			obstacle_Y = Integer.parseInt(parsedText.get(indexCounter + 1));
			brickArray[obstacle_X][obstacle_Y].setIsObstacle(true);

			// �ka med 2 pga av varje hinder representeras av [x],[y]
			indexCounter += 2;
		}
	}

	/**
	 * Metod som skapar lika m�nga brick-objekt som det finns rader & kolumner
	 * och l�gger dom i brick-Arrayen.
	 */
	private void initializeBricks() {
		for (int x = 0; x < brickArray.length; x++) {
			for (int y = 0; y < brickArray[x].length; y++) {
				brickArray[x][y] = new Brick();
			}
		}
	}
}
