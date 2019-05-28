package day06;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 6]");
		
		final Stream<String> input = InputReader.readInput("day06.txt");
		
		final String coordinateRegex = "(\\d+), (\\d+)";
		final Pattern coordinatePattern = Pattern.compile(coordinateRegex);
		
		// Create list with all the input coordinates
		final List<Coordinate> coordinates = input.map(s -> {
			final Matcher coordinateMatcher = coordinatePattern.matcher(s);
			if (!coordinateMatcher.find()) return null;
			return new Coordinate(Integer.parseInt(coordinateMatcher.group(1)), Integer.parseInt(coordinateMatcher.group(2)));
		}).collect(Collectors.toList());
		
		// Determine the maximum values of X and Y to size the grid
		int maxX = 0, maxY = 0;
		for (final Coordinate c : coordinates) {
			if (c.getX() > maxX) maxX = c.getX();
			if (c.getY() > maxY) maxY = c.getY();
		}
		
		// Create the grid
		final Location[][] grid = new Location[maxX + 1][maxY + 1];
		
		// Put the coordinates in the grid
		for (final Coordinate c : coordinates) {
			grid[c.getX()][c.getY()] = c;
		}
		
		final int largestArea = getLargestArea(grid, maxX, maxY, coordinates);
		System.out.println("Part 1 > The size of the largest area that isn't infinite is: " + largestArea);
		
		final int sizeOfRegionWithTotalDistanceToCoordinatesUnder10000
				= getSizeOfRegionWithTotalDistanceToCoordinatesUnder10000(maxX, maxY, coordinates);
		System.out.println("Part 2 > The size of the region containing all locations which have a total distance to all given coordinates of less than 10000 is: "
				+ sizeOfRegionWithTotalDistanceToCoordinatesUnder10000);
	}
	
	private static int getDistanceToCoordinate(final int x, final int y, final Coordinate c) {
		// Manhattan distance
		return Math.abs(c.getX() - x) + Math.abs(c.getY() - y);
	}
	
	private static int getLargestArea(final Location[][] grid, final int maxX, final int maxY, final List<Coordinate> coordinates) {
		// Let's go for a brute force approach: for each location, calculate the shortest distance
		// to a coordinate and mark it as belonging to that coordinate's area. If more than one
		// coordinate is equally far away, leave the grid item as null
		for (int x = 0; x <= maxX; ++x) {
			for (int y = 0; y <= maxY; ++y) {
				// If this location is a coordinate, skip it
				if (grid[x][y] instanceof Coordinate) {
					continue;
				}
				// Create a map with all coordinates and their distance to this location
				final Map<Coordinate, Integer> coordinateDistanceMap = new HashMap<>();
				for (final Coordinate c : coordinates) {
					coordinateDistanceMap.put(c, getDistanceToCoordinate(x, y, c));
				}
				// Calculate the shortest distance to any coordinate
				final int minDistance = Collections.min(coordinateDistanceMap.values());
				// Get a list of all coordinates that are the shortest distance away
				final List<Coordinate> minDistanceCoordinates = coordinateDistanceMap.entrySet().stream()
						.filter(e -> e.getValue().equals(minDistance)).map(Map.Entry::getKey).collect(Collectors.toList());
				// Only one closest coordinate is expected. Otherwise, this location doesn't count as being closest to any coordinate
				if (minDistanceCoordinates.size() != 1) {
					continue;
				}
				// Set this location as belonging to the area of its closest coordinate
				grid[x][y] = CoordinateAreaLocation.getInstance(minDistanceCoordinates.get(0));
			}
		}
		
		// Finally, we'll iterate over the grid again, but this time calculating the coordinate area sizes
		final Map<Coordinate, Integer> coordinateAreaSizes = new HashMap<>();
		final Set<Coordinate> infiniteAreas = new HashSet<>();
		for (int x = 0; x <= maxX; ++x) {
			for (int y = 0; y <= maxY; ++y) {
				// Get the current location. If it's not part of a coordinate area or isn't a coordinate itself, skip it
				final Location currLocation = grid[x][y];
				final Coordinate thisAreaCoordinate;
				if (currLocation instanceof CoordinateAreaLocation) {
					thisAreaCoordinate = ((CoordinateAreaLocation) currLocation).getClosestCoordinate();
				} else if (currLocation instanceof Coordinate) {
					thisAreaCoordinate = (Coordinate) currLocation;
				} else {
					continue;
				}
				
				// If we've already established this area is infinite, skip it
				if (infiniteAreas.contains(thisAreaCoordinate)) {
					continue;
				}
				// If this location is at any edge of the grid, its area is infinite.
				// Remove it from the area sizes map and add it to the infinite areas set
				if (x == 0 || x == maxX || y == 0 || y == maxY) {
					coordinateAreaSizes.remove(thisAreaCoordinate);
					infiniteAreas.add(thisAreaCoordinate);
					continue;
				}
				// Increment this area's size by one
				coordinateAreaSizes.merge(thisAreaCoordinate, 1, Integer::sum);
			}
		}
		// The largest area size is the maximum value in the coordinate area sizes map
		return coordinateAreaSizes.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getValue();
	}
	
	private static int getSizeOfRegionWithTotalDistanceToCoordinatesUnder10000(final int maxX, final int maxY, final List<Coordinate> coordinates) {
		int regionSize = 0;
		for (int x = 0; x <= maxX; ++x) {
			for (int y = 0; y <= maxY; ++y) {
				int totalDistanceToAllCoordinates = 0;
				for (final Coordinate c : coordinates) {
					totalDistanceToAllCoordinates += getDistanceToCoordinate(x, y, c);
				}
				if (totalDistanceToAllCoordinates < 10000) {
					++regionSize;
				}
			}
		}
		return regionSize;
	}
}
