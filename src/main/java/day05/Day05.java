package day05;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public class Day05 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 5]");
		
		final Stream<String> input = InputReader.readInput("day05.txt");
		final String polymer = input.findAny().orElseThrow();
		
		final String reactedPolymer = reactPolymer(polymer);
		System.out.println("Part 1 > After fully reacting the polymer, the number of remaining units is: " + reactedPolymer.length());
		
		final String shortestPolymer = getShortestReactedPolymerWithUnitRemoved(reactedPolymer);
		System.out.println("Part 2 > The length of the shortest possible reacted polymer with all units of a type removed is: " +
				shortestPolymer.length());
	}
	
	private static String reactPolymer(String polymer) {
		int previousLength;
		do {
			previousLength = polymer.length();
			polymer = polymer.replaceAll("([a-zA-Z])(?!\\1)(?i:\\1)", "");
		} while (polymer.length() != previousLength);
		return polymer;
	}
	
	private static String getShortestReactedPolymerWithUnitRemoved(final String polymer) {
		final String alphabet = "qwertyuiopasdfghjklzxcvbnm";
		final String regexPrefix = "(?i)";
		String shortestPolymer = null;
		for (int i = 0; i < alphabet.length(); ++i) {
			final String unitRemovedPolymer = polymer.replaceAll(regexPrefix + alphabet.charAt(i), "");
			final String reactedPolymer = reactPolymer(unitRemovedPolymer);
			if (shortestPolymer == null || reactedPolymer.length() < shortestPolymer.length()) {
				shortestPolymer = reactedPolymer;
			}
		}
		return shortestPolymer;
	}
}
