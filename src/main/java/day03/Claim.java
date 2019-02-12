package day03;

class Claim {
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean overlaps;
	
	Claim(final int id, final int x, final int y, final int width, final int height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.overlaps = false;
	}
	
	int getId() {
		return id;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getWidth() {
		return width;
	}
	
	int getHeight() {
		return height;
	}
	
	boolean getOverlaps() {
		return overlaps;
	}
	
	void setOverlaps(final boolean overlaps) {
		this.overlaps = overlaps;
	}
}
