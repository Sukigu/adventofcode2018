package day06;

class Coordinate extends Location {
	private int x;
	private int y;
	
	Coordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
}
