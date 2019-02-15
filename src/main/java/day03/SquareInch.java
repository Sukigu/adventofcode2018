package day03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class SquareInch {
	private final int x;
	private final int y;
	private final List<Claim> claims;
	
	SquareInch(final int x, final int y) {
		this.x = x;
		this.y = y;
		this.claims = new ArrayList<>();
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	List<Claim> getClaims() {
		return claims;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof SquareInch)) return false;
		final SquareInch si2 = (SquareInch) obj;
		return x == si2.x && y == si2.y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
