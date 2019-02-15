package day04;

import java.time.LocalDateTime;

class WakeUpRecord extends Record {
	WakeUpRecord(final LocalDateTime time) {
		super(time);
	}
	
	@Override
	public String toString() {
		return super.toString() + "wakes up";
	}
}
