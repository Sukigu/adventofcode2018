package day02;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartTwo {
	public static void main(final String[] args) throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day02");
		final List<String> inputList = input.collect(Collectors.toUnmodifiableList());
		
		/*
		Create a list with combinations of all input strings, where for each string,
		each character is ommitted one at a time. 'currentCombinations' holds all
		such combinations for the current string, whereas those are then stored
		in 'pastCombinations' in hash form to save space
		 */
		List<Integer> pastCombinations = new ArrayList<>();
		for (int i = 0; i < inputList.size(); ++i) {
			final List<String> currentCombinations = new ArrayList<>();
			
			final String id = inputList.get(i);
			for (int j = 0; j + 1 < id.length(); ++j) {
				final String partialString = id.substring(0, j) + id.substring(j + 1);
				currentCombinations.add(partialString);
			}
			
			if (i != 0) {
				final String match = currentCombinations.stream().filter(s->pastCombinations.contains(s.hashCode())).findAny().orElse(null);
				if (match != null) {
					System.out.println("The letters in common between the correct box IDs are: '" + match + "'");
					return;
				}
			}
			
			for (final String combination : currentCombinations) {
				pastCombinations.add(combination.hashCode());
			}
		}
	}
}
