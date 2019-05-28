package day06;

import java.util.HashMap;
import java.util.Map;

class CoordinateAreaLocation extends Location {
	private Coordinate closestCoordinate;
	private static Map<Coordinate, CoordinateAreaLocation> instances = new HashMap<>();
	
	private CoordinateAreaLocation(final Coordinate closestCoordinate) {
		this.closestCoordinate = closestCoordinate;
	}
	
	static CoordinateAreaLocation getInstance(final Coordinate closestCoordinate) {
		instances.computeIfAbsent(closestCoordinate, c -> new CoordinateAreaLocation(closestCoordinate));
		return instances.get(closestCoordinate);
	}
	
	Coordinate getClosestCoordinate() {
		return closestCoordinate;
	}
}
