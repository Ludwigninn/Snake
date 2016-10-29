package AlexSnake;

public class Run {
	public static void main(String[] args) {		
		Snake ex = new Snake();
		String path = "src/Map2.txt";
		ex.parseTextFile(path);
		ex.checkDFS();
	}
}
