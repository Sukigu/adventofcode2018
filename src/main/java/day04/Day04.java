package day04;

import common.InputReader;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {
	public static void main(final String[] args) throws FileNotFoundException {
		System.out.println("[Day 4]");
		
		final Stream<String> input = InputReader.readInput("day04.txt");
		final var guardsAndAggregatedSleepingMinutes = part1(input);
		part2(guardsAndAggregatedSleepingMinutes);
	}
	
	private static Map<Guard, Map<Integer, Long>> part1(final Stream<String> input) {
		final List<Record> sortedRecords = input.map(Day04::parseRecord).sorted().collect(Collectors.toUnmodifiableList());
		
		// Let's create a map where the key is each guard, and the value is a list of all minutes they spent sleeping
		final Map<Guard, List<Integer>> guardsAndSleepingMinutes = new HashMap<>();
		Guard currentGuard = null;
		Integer lastFallenAsleepMinute = null;
		for (final Record record : sortedRecords) {
			if (currentGuard == null || record instanceof BeginShiftRecord) {
				currentGuard = GuardFactory.get(record.getGuardId());
			}
			
			if (record instanceof FallAsleepRecord) {
				lastFallenAsleepMinute = record.getTime().getMinute();
			} else {
				if (lastFallenAsleepMinute != null) {
					final List<Integer> sleepingMinutes = guardsAndSleepingMinutes.computeIfAbsent(currentGuard, guard -> new ArrayList<>());
					// Let's add each minute since the guard fell asleep until they woke up to their list of sleeping minutes
					for (int min = lastFallenAsleepMinute; min < record.getTime().getMinute(); ++min) {
						sleepingMinutes.add(min);
					}
				}
				lastFallenAsleepMinute = null;
			}
		}
		
		// To find the guard who slept the most time, we search the map for the guard whose
		// list size (number of sleeping minutes) is the largest
		final Guard guardWhoSleptMost = guardsAndSleepingMinutes.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()))
				.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
		// To find the minute this guard was asleep most often, we first aggregate all of the guards' sleeping minutes into a map
		// (where the key is the minute number and the value is how many times that minute number was spent asleep)
		var guardsAndAggregatedSleepingMinutes = guardsAndSleepingMinutes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
				entry -> entry.getValue().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))));
		// Then, we get the guard we found previously and find the largest value in their map
		// (times asleep during that minute), then we get its key (minute number)
		final int minuteMostSleptByGuardWhoSleptMost = guardsAndAggregatedSleepingMinutes.get(guardWhoSleptMost)
				.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
		
		System.out.println("Part 1 > The ID of the guard multiplied by the minute is: " + guardWhoSleptMost.getId() * minuteMostSleptByGuardWhoSleptMost);
		return guardsAndAggregatedSleepingMinutes;
	}
	
	private static void part2(final Map<Guard, Map<Integer, Long>> guardsAndAggregatedSleepingMinutes) {
		// This loop will search all guards for the one who was most often asleep in a given minute.
		// It keeps track of the number of times that was, plus which minute and which guard that was
		Integer mostSleptMinute = null;
		Integer mostTimesSleptOnMostSleptMinute = null;
		Guard mostTimesSleptOnMinuteGuard = null;
		for (final var entry : guardsAndAggregatedSleepingMinutes.entrySet()) {
			var thisGuardMostOftenAsleepEntry = entry.getValue().entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow();
			int thisGuardMostTimesAsleepDuringMostOftenAsleepMinute = thisGuardMostOftenAsleepEntry.getValue().intValue();
			if (mostTimesSleptOnMostSleptMinute == null
					|| thisGuardMostTimesAsleepDuringMostOftenAsleepMinute >  mostTimesSleptOnMostSleptMinute) {
				mostSleptMinute = thisGuardMostOftenAsleepEntry.getKey();
				mostTimesSleptOnMostSleptMinute = thisGuardMostTimesAsleepDuringMostOftenAsleepMinute;
				mostTimesSleptOnMinuteGuard = entry.getKey();
			}
		}
		if (mostSleptMinute == null || mostTimesSleptOnMinuteGuard == null) throw new IllegalStateException();
		
		System.out.println("Part 2 > The ID of the guard multiplied by the minute is: " + mostTimesSleptOnMinuteGuard.getId() * mostSleptMinute);
	}
	
	private static Record parseRecord(final String recordString) {
		// General regex for the records
		final String recordRegex = "^\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2})] (.*)$";
		final Pattern recordPattern = Pattern.compile(recordRegex);
		final Matcher recordMatcher = recordPattern.matcher(recordString);
		if (!recordMatcher.find()) throw new InputMismatchException();
		
		final LocalDateTime recordTime = LocalDateTime.parse(recordMatcher.group(1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		final String recordDescription = recordMatcher.group(2);
		
		Record parsedRecord = null;
		for (final RecordType recordType : RecordType.values()) {
			// Let's try each regex and depending on what matches, we found our record
			final Matcher recordDescriptionMatcher = Pattern.compile(recordType.getDescriptionRegex()).matcher(recordDescription);
			if (!recordDescriptionMatcher.find()) continue;
			
			if (recordType == RecordType.BEGIN_SHIFT) {
				parsedRecord = new BeginShiftRecord(recordTime, Integer.parseInt(recordDescriptionMatcher.group(1)));
				break;
			} else if (recordType == RecordType.FALL_ASLEEP) {
				parsedRecord = new FallAsleepRecord(recordTime);
				break;
			} else if (recordType == RecordType.WAKE_UP) {
				parsedRecord = new WakeUpRecord(recordTime);
				break;
			}
		}
		return parsedRecord;
	}
}
