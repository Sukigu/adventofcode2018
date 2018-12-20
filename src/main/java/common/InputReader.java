package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.stream.Stream;

public class InputReader {
	public static Stream<String> readInput(final String filename) throws FileNotFoundException {
		return new BufferedReader(new FileReader(Path.of("src", "main", "res", filename).toString())).lines();
	}
}
