package day04;

import java.time.LocalDateTime;

class BeginShiftRecord extends Record {
	BeginShiftRecord(final LocalDateTime time, final int guardId) {
		super(time);
		this.guardId = guardId;
	}
	
	@Override
	public String toString() {
		return super.toString() + "Guard #" + guardId + " begins shift";
	}
}
