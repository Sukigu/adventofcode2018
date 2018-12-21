package day01;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day01 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 1]");
		part1();
		part2();
	}
	
	private static void part1() throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day01.txt");
		final int resultingFrequency = input.mapToInt(Integer::parseInt).sum();
		System.out.println("Part 1 > The resulting frequency is: " + resultingFrequency);
	}
	
	private static void part2() throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day01.txt");
		
		// Get the frequency changes into a list of integers
		final List<Integer> freqList = input.mapToInt(Integer::parseInt).boxed().collect(Collectors.toUnmodifiableList());
		
		// Create a set to store the frequencies we reach as we iterate over the list
		final Set<Integer> reachedFreqs = new HashSet<>();
		
		boolean reachedTwice = false;
		int currentFreq = 0;
		
		reachedFreqs.add(currentFreq);
		do {
			for (final int i : freqList) {
				currentFreq += i;
				if (reachedFreqs.contains(currentFreq)) {
					reachedTwice = true;
					break;
				}
				reachedFreqs.add(currentFreq);
			}
		} while (!reachedTwice);
		
		System.out.println("Part 2 > The first frequency reached twice is: " + currentFreq);
	}
}
