package AlexSnake;

public class Brick {
	private boolean isObstacle = false;
	private boolean isVisited = false;
	
	public Brick() {
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
