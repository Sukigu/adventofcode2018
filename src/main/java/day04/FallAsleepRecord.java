package day04;

import java.time.LocalDateTime;

class FallAsleepRecord extends Record {
	FallAsleepRecord(final LocalDateTime time) {
		super(time);
	}
	
	@Override
	public String toString() {
		return super.toString() + "falls asleep";
	}
}
