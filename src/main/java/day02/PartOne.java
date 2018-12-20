package day02;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartOne {
	public static void main(final String[] args) throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day02");
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
	
	private static class ChecksumBuilder {
		private int occurrencesOfTwo = 0;
		private int occurrencesOfThree = 0;
		
		void incOccurrencesOfTwo() {
			++occurrencesOfTwo;
		}
		
		void incOccurrencesOfThree() {
			++occurrencesOfThree;
		}
		
		int calcChecksum() {
			return occurrencesOfTwo * occurrencesOfThree;
		}
	}
}
