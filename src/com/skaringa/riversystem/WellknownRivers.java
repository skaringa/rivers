package com.skaringa.riversystem;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

public class WellknownRivers {
	
	// Map IDs of wellknown rivers to their basin name
	static TLongObjectMap<String> id2Basin;
	static {
		id2Basin = new TLongObjectHashMap<String>();
		id2Basin.put(102135980L, "Rhein");
		id2Basin.put(157538529L, "Rhein"); // Bodensee
		id2Basin.put(26970429L, "Rhein"); // Berkel
		id2Basin.put(32965156L, "Rhein"); // Issel
		id2Basin.put(52528188L, "Rhein"); // Dinkel
		id2Basin.put(23452335L, "Rhein"); // Vechte
		id2Basin.put(33655748L, "Rhein"); // Bocholter Aa
		id2Basin.put(128043705L, "Weser");
		id2Basin.put(4308645L, "Ems");
		id2Basin.put(5011091L, "Donau");
		id2Basin.put(30613639L, "Elbe");
		id2Basin.put(118848751L, "Elbe"); // Eger
		id2Basin.put(89253786L, "Oder");
		id2Basin.put(156293030L, "Warnow");
		id2Basin.put(161734566L, "Warnow"); // Recknitz
		id2Basin.put(4532243L, "Peene");
		id2Basin.put(5005413L, "Trave");
		id2Basin.put(29249972L, "Trave"); // Schwentine
		id2Basin.put(104383228L, "Trave"); // Stepenitz 
		id2Basin.put(4267187L, "Schlei");
		id2Basin.put(98726563L, "Eider"); // Arlau
		id2Basin.put(52997220L, "Eider"); // Miele
		id2Basin.put(5011995L, "Eider");
		id2Basin.put(36054759L, "Maas"); // Rur
		id2Basin.put(9332131L, "Maas"); // Niers
		id2Basin.put(25680743L, "Maas"); // Schwalm
		
		id2Basin.put(64690374L, "Donau"); // Drau
		id2Basin.put(23074512L, "Donau"); // Sava
		
		id2Basin.put(34614840L, "Weichsel");
		id2Basin.put(179582873L, "Weichsel");
		
		id2Basin.put(77604974L, "Po"); // Ticino
		id2Basin.put(68293532L, "Po"); // Maira
		id2Basin.put(105715697, "Po"); // Oglio
		id2Basin.put(182920858L, "Etsch"); // Rambach - Ram 
		id2Basin.put(52106448L, "Isonzo");
		id2Basin.put(159833469L, "Piave");
		id2Basin.put(162189624L, "Tagliamento");
		
		id2Basin.put(123033116L, "Rhone");
		id2Basin.put(122130057L, "Rhone"); // Le Doubs
		id2Basin.put(121412596L, "Rhone"); // La Saône 
	}

	// Waterways that are drainage divides
	static TLongSet divides;
	static {
		divides = new TLongHashSet();
		divides.add(29915851L); // Main-Donau-Kanal
		divides.add(102832333L); // Der Strom
		divides.add(30417811L); // Nord-Ostsee-Kanal
		divides.add(176010294L); // Schaalseekanal
		divides.add(162105959L); // Störkanal
		divides.add(103423760L); // Elbe-Lübeck-Kanal
		divides.add(24205659L);
		divides.add(30378663L); // Mittellandkanal
		divides.add(24950057L); // Mittellandkanal
		divides.add(44671142L); // Mittellandkanal
		divides.add(166221286L); // Mittellandkanal
		divides.add(2360794L); // Mittellandkanal (relation)
		divides.add(44671140L); // Mittellandkanal (area)
		divides.add(112674007L); // Fanggraben
		divides.add(169698060L); // Elbe-Seitenkanal
		divides.add(30772058L); // Elbe-Seitenkanal (area)
		divides.add(39743374L); // Ludwig-Donau-Main-Kanal
		divides.add(5120607L); // Altmühlüberleiter
		divides.add(43861716L); // Oder-Spree-Kanal
		divides.add(27036866L); // Oste-Hamme-Kanal
		divides.add(30568943L); // Burgsittenser Bach 
		divides.add(166599679L); // Finowkanal
		divides.add(35898720L); // Oder-Havel-Kanal
		divides.add(28631513L); // Geeste Kanal - Schifffahrtsweg Elbe/Weser 
		divides.add(25410328L); // Else-Hase-Bifurkation
		divides.add(8134358L);
		divides.add(49213928L); // Dortmund-Ems-Kanal
		divides.add(48749385L); // Ems-Vechte-Kanal 
		divides.add(28071866L); // Süd-Nord Kanal
		divides.add(70681602L); // Küstenkanal
		divides.add(160029569L); // Luttertunnel Bielefeld
		divides.add(116711212L); // Haselbachstollen 
		divides.add(210614669L); // Flößgraben 
		
		divides.add(82037779L); // Tříhrázný rybník (Donau/Elbe)
		divides.add(81931306L); // unknown (Donau/Elbe)
		divides.add(82080861L); // dibavod:id=207680006300
		divides.add(81453698L); // dibavod:id=131260002300
		
		divides.add(60687941L); // Czerna (Donau/Weichsel)
		
		divides.add(195089417L); // Kanał Bydgoski (Bromberger Kanal, Oder/Weichsel)
		
		divides.add(80965690L); // Canal du Rhône au Rhin
		divides.add(73811224L);
		divides.add(159656530L); // Canal des Vosges
	}
	
	public static String getBasin(long riverId) {
		return id2Basin.get(riverId);
	}
	
	public static boolean isDivide(long riverId) {
		return divides.contains(riverId);
	}
}
