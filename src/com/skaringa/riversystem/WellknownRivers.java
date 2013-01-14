package com.skaringa.riversystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WellknownRivers {
	
	/* Funktioniert nicht, weil Namen mehrfach vorkommen (z.B: "Elbe" bei Fritzlar)!
	public static Set<String> basinNames = new HashSet<String>(Arrays.asList(
			"Rhein", "Weser", "Ems", "Donau", "Elbe", "Oder", "Warnow", "Schlei", "Eider", "Maas"));
	*/
	static Map<Long, String> id2Basin;
	static {
		id2Basin = new HashMap<Long, String>();
		id2Basin.put(102135980L, "Rhein");
		id2Basin.put(157538529L, "Rhein"); // Bodensee
		id2Basin.put(128043705L, "Weser");
		id2Basin.put(4308645L, "Ems");
		id2Basin.put(5011091L, "Donau");
		id2Basin.put(30613639L, "Elbe");
		id2Basin.put(118848751L, "Elbe"); // Eger
		id2Basin.put(89253786L, "Oder");
		id2Basin.put(156293030L, "Warnow");
		id2Basin.put(5005413L, "Trave");
		id2Basin.put(4267187L, "Schlei");
		id2Basin.put(5011995L, "Eider");
		id2Basin.put(36054759L, "Maas"); // Rur
	}

	// Waterways that are drainage divides
	static Set<Long> divides;
	static {
		divides = new HashSet<Long>();
		divides.add(29915851L); // Main-Donau-Kanal
		divides.add(30417811L); // Nord-Ostsee-Kanal
		divides.add(103423760L); // Elbe-Lübeck-Kanal
		divides.add(24205659L);
		divides.add(30378663L); // Mittellandkanal
		divides.add(169698060L); // Elbe-Seitenkanal
		divides.add(39743374L); // Ludwig-Donau-Main-Kanal
		divides.add(43861716L); // Oder-Spree-Kanal
		divides.add(27036866L); // Oste-Hamme-Kanal
		divides.add(30568943L); // Burgsittenser Bach 
		divides.add(166599679L); // Finowkanal
		divides.add(35898720L); // Oder-Havel-Kanal
		divides.add(28631513L); // Geeste Kanal - Schifffahrtsweg Elbe/Weser 
		divides.add(25410328L); // Else-Hase-Bifurkation
		divides.add(49213928L); // Dortmund-Ems-Kanal
		divides.add(70681602L); // Küstenkanal
	}
	
	public static String getBasin(Long riverId) {
		return id2Basin.get(riverId);
	}
	
	public static boolean isDivide(Long riverId) {
		return divides.contains(riverId);
	}
}
