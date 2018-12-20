package day01;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public class PartOne {
	public static void main(final String[] args) throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day01.txt");
		final int resultingFrequency = input.mapToInt(Integer::parseInt).sum();
		System.out.println("The resulting frequency is: " + resultingFrequency);
	}
}
