package day02;

class ChecksumBuilder {
	private int occurrencesOfTwo = 0;
	private int occurrencesOfThree = 0;
	
	void incOccurrencesOfTwo() {
		++occurrencesOfTwo;
	}
	
	void incOccurrencesOfThree() {
		++occurrencesOfThree;
	}
	
	int calcChecksum() {
		return occurrencesOfTwo * occurrencesOfThree;
	}
}
