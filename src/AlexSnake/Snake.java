package AlexSnake;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Snake finds a way through a two dimensional array
 * 
 * @author Alexander Johansson, Ludwig Ninn
 *
 */
public class Snake {
	private Brick[][] brickArray;
	
	/**
	 * Coords stores x, y coordinates along with Coords-parent/children
	 * 
	 * @author Alexander Johansson, Ludwig Ninn
	 *
	 */
	private class Coords {
		private int x;
		private int y;
		private Coords parent;
		private Coords child;
		
		/**
		 * Constructor for Coords - inserts x and y values
		 * @param x Integer
		 * @param y Integer
		 */
		public Coords(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * toString prints the objects coord data (x,y)
		 */
		public String toString() {
			return x + "," + y;
		}
	}
	
	/**
	 * Method checks the brickArray using a DFS search, in conjunction with using a Stack for pushing Coords-objects
	 * This method will find a way for the snake through and past the obstacles specified
	 */
	public void checkDFS() {
		Stack<Coords> stack = new Stack<Coords>();
		// push first node
		Coords root = new Coords(0, 0);
		stack.push(root);
		
		// boolean to check if coord has neighbor in matrix
		boolean hasNeighbor;
		while(!stack.isEmpty()) {
			hasNeighbor = false;
			// pop coord from stack
			Coords coords = stack.pop();
			if(coords != null) {
				// get neighbor coords
				ArrayList<Coords> neighborCoords = getNeighbors(coords.x, coords.y);
				// if there are neighbors
				if(!neighborCoords.isEmpty()) {
					// get current brick
					Brick currentBrick = brickArray[coords.x][coords.y];
					// iterate for each neighbor
					for(Coords neighbor : neighborCoords) {
						// get neighbor brick
						Brick nextBrick = brickArray[neighbor.x][neighbor.y];
						// if neighbor brick is not obstacle or visited
						if(!nextBrick.isObstacle() && !nextBrick.isVisited()) {
							// set current brick to visited
							currentBrick.setVisited(true);
							
							// instantiate new coord
							Coords nextCoord = new Coords(neighbor.x, neighbor.y);
							// set child and parent
							coords.child = nextCoord;
							nextCoord.parent = coords;
							// push new coord to stack
							stack.push(nextCoord);
							
							// current brick has valid neighbor, break iteration
							hasNeighbor = true;
							break;
						}
					}
					
					// if coord has no neighbors
					if(!hasNeighbor) {
						// coord has a parent
						if(coords.parent != null) {
							// set current brick to visited
							currentBrick.setVisited(true);
							
							// push parent coord into stack
							stack.push(coords.parent);
						}
					}
				}
			}
		}
		
		// create list
		ArrayList<Coords> coordList = new ArrayList<Coords>();
		// set current coord to first coord
		Coords currCoords = root;
		while (currCoords != null) {
			// add children to list
			coordList.add(currCoords.child);
			currCoords = currCoords.child;
		}
		
		// print result
		printMatrix(coordList);
	}
	
	/**
	 * Prints the current snake path and snake coordinates
	 * @param coordList ArrayList<Coords>
	 */
	private void printMatrix(ArrayList<Coords> coordList) {
		// print snake path size
		System.out.println(coordList.size());
		// print all coordinates
		for(Coords coord : coordList) {
			if(coord != null) {
				System.out.println(coord.x + "," + coord.y);
			}
		}
		
		System.out.println();
		
		// print a visual representation of the matrix - S is where the snake has travelled, X is obstacles, 0 is non-visited bricks
		for(int x = 0; x < brickArray.length; x++) {
			for(int y = 0; y < brickArray[x].length; y++) {
				Brick brick = brickArray[x][y];
				if(checkIfVisited(coordList, x, y)){
					System.out.print("S ");
				} else if(brick.isObstacle()) {
					System.out.print("X ");
				} else {
					System.out.print("0 ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Checks if snake has travelled on x, y depending if it exists in given ArrayList
	 * @param coords ArrayList<Coords>
	 * @param x Integer
	 * @param y Integer
	 * @return boolean true if found, false if not
	 */
	private boolean checkIfVisited(ArrayList<Coords> coords, int x, int y) {
		for(Coords coord : coords) {
			if(coord != null) {
				if(coord.x == x && coord.y == y) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Finds valid neighbour coordinates from given x, y
	 * @param x Integer
	 * @param y Integer
	 * @return ArrayList<Coords>
	 */
	private ArrayList<Coords> getNeighbors(int x, int y) {
		ArrayList<Coords> neighbors = new ArrayList<Coords>();

		// up
		if (isValidPoint(x, y - 1)) {
			neighbors.add(new Coords(x, y - 1));
		} 
		// left
		if (isValidPoint(x - 1, y)) {
			neighbors.add(new Coords(x - 1, y));
 		}
		// down
		if (isValidPoint(x, y + 1)) {
			neighbors.add(new Coords(x, y + 1));
		}
		// right
		if (isValidPoint(x + 1, y)) {
			neighbors.add(new Coords(x + 1, y));
		}

		return neighbors;
	}

	/**
	 * Checks if point is valid and in boundaries
	 * @param x Integer
	 * @param y Integer
	 * @return boolean true if valid, false if not
	 */
	private boolean isValidPoint(int x, int y) {
		// check bounds
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
