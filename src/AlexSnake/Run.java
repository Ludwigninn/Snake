package AlexSnake;
public class Run {
	public static void main(String[] args){		
		Exempel ex = new Exempel();
		String path = "src/Map2.txt";
		ex.parseTextFile(path);
		ex.checkDFS();
//		ex.checkOne();
	}
}
