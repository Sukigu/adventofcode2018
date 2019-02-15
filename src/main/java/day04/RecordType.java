package day04;

enum RecordType {
	BEGIN_SHIFT("^Guard #(\\d+) begins shift$"),
	FALL_ASLEEP("^falls asleep$"),
	WAKE_UP("^wakes up$");
	
	private final String descriptionRegex;
	
	RecordType(final String descriptionRegex) {
		this.descriptionRegex = descriptionRegex;
	}
	
	String getDescriptionRegex() {
		return descriptionRegex;
	}
}
