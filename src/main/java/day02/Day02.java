package day02;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day02 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 2]");
		part1();
		part2();
	}
	
	private static void part1() throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day02.txt");
		final ChecksumBuilder chksumBuilder = new ChecksumBuilder();
		input.forEach(id -> {
			final Map<Integer, Long> m = id.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			if (m.containsValue(2L)) {
				chksumBuilder.incOccurrencesOfTwo();
			}
			if (m.containsValue(3L)) {
				chksumBuilder.incOccurrencesOfThree();
			}
		});
		System.out.println("The computed checksum is: " + chksumBuilder.calcChecksum());
	}
	
	private static void part2() throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day02.txt");
		final List<String> inputList = input.collect(Collectors.toUnmodifiableList());
		
		/*
		Let's create many partial strings, each one omitting a character of each input
		string. Get its hash, then see if we had already found it in a previous
		input. If so, return. If no partial string of the current input matches,
		add all hashes of the current input to the list and move on to the next input
		 */
		final List<Integer> pastCombinationHashes = new ArrayList<>();
		for (final String id : inputList) {
			final List<Integer> currentCombinationHashes = new ArrayList<>();
			
			for (int i = 0; i + 1 < id.length(); ++i) {
				final String partialString = id.substring(0, i) + id.substring(i + 1);
				final int partialStringHash = partialString.hashCode();
				
				if (pastCombinationHashes.contains(partialStringHash)) {
					System.out.println("The letters in common between the correct box IDs are: '" + partialString + "'");
					return;
				}
				currentCombinationHashes.add(partialStringHash);
			}
			
			pastCombinationHashes.addAll(currentCombinationHashes);
		}
	}
}
