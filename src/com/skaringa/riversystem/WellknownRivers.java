package com.skaringa.riversystem;

import java.util.HashMap;
import java.util.Map;

public class WellknownRivers {
	
	/* Funktioniert nicht, weil Namen mehrfach vorkommen (z.B: "Elbe" bei Fritzlar)!
	public static Set<String> basinNames = new HashSet<String>(Arrays.asList(
			"Rhein", "Weser", "Ems", "Donau", "Elbe", "Oder", "Warnow", "Schlei", "Eider", "Maas"));
	*/
	static Map<Long, String> id2Basin;
	static {
		id2Basin = new HashMap<Long, String>();
		id2Basin.put(102135980L, "Rhein");
		id2Basin.put(128043705L, "Weser");
	}

	public static String getBasin(long riverId) {
		return id2Basin.get(riverId);
	}
}
