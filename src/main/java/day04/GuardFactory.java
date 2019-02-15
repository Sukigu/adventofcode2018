package day04;

import java.util.HashMap;
import java.util.Map;

final class GuardFactory {
	private final static Map<Integer, Guard> guards = new HashMap<>();
	
	private GuardFactory() {}
	
	static Guard get(final int id) {
		Guard guard = guards.get(id);
		if (guard == null) {
			guard = new Guard(id);
			guards.put(id, guard);
		}
		return guard;
	}
}
