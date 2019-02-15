package day03;

import common.InputReader;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day03 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 3]");
		parts1And2();
	}
	
	private static void parts1And2() throws FileNotFoundException {
		final Stream<String> input = InputReader.readInput("day03.txt");
		
		final String claimRegex = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)";
		final Pattern claimPattern = Pattern.compile(claimRegex);
		
		final List<Claim> claims = input.map(claimString -> {
			final Matcher claimMatcher = claimPattern.matcher(claimString);
			if (!claimMatcher.find()) throw new InputMismatchException();
			return new Claim(
					Integer.parseInt(claimMatcher.group(1)),
					Integer.parseInt(claimMatcher.group(2)),
					Integer.parseInt(claimMatcher.group(3)),
					Integer.parseInt(claimMatcher.group(4)),
					Integer.parseInt(claimMatcher.group(5)));
		}).collect(Collectors.toList());
		
		final Map<SquareInch, List<Claim>> fabric = new HashMap<>();
		for (final Claim claim : claims) {
			final int claimX = claim.getX(),
					claimY = claim.getY(),
					claimWidth = claim.getWidth(),
					claimHeight = claim.getHeight();
			// Let's add this claim to all square inches it spans
			for (int x = claimX; x < claimX + claimWidth; ++x) {
				for (int y = claimY; y < claimY + claimHeight; ++y) {
					final SquareInch claimSqInch = new SquareInch(x, y);
					fabric.putIfAbsent(claimSqInch, new ArrayList<>());
					
					final List<Claim> claimsOnThisSqInch = fabric.get(claimSqInch);
					if (claimsOnThisSqInch.size() == 1) {
						// If there was only one claim on this square inch, then that one now overlaps
						claimsOnThisSqInch.get(0).setOverlaps(true);
					}
					claimsOnThisSqInch.add(claim);
					if (claimsOnThisSqInch.size() > 1) {
						// If there is more than one claim on this square inch, then this one overlaps too
						claim.setOverlaps(true);
					}
				}
			}
		}
		
		final long overlaps = fabric.values().stream().filter(claimList -> claimList.size() >= 2).count();
		System.out.println("Part 1 > The number of overlapped square inches is: " + overlaps);
		
		final Claim nonOverlappingClaim = claims.stream().filter(claim -> !claim.getOverlaps()).findAny().orElseThrow();
		System.out.println("Part 2 > The ID of the claim that doesn't overlap is: " + nonOverlappingClaim.getId());
	}
}
