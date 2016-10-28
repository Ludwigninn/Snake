package AlexSnake;

public class Brick {
	private boolean isObstacle = false;
	private boolean isVisited = false;
	public int x;
	public int y;
	
	public Brick(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isObstacle() {
		return isObstacle;
	}
	
	public void setIsObstacle(boolean obstacle) {
		this.isObstacle = obstacle;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
}
