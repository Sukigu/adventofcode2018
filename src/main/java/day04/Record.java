package day04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

abstract class Record implements Comparable<Record> {
	private final LocalDateTime time;
	Integer guardId;
	
	Record(final LocalDateTime time) {
		this.time = time;
	}
	
	LocalDateTime getTime() {
		return time;
	}
	
	int getGuardId() {
		return guardId;
	}
	
	void setGuardId(final int guardId) {
		this.guardId = guardId;
	}
	
	@Override
	public int compareTo(final Record rec2) {
		return time.compareTo(rec2.time);
	}
	
	@Override
	public String toString() {
		return "[" + getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "] ";
	}
}
