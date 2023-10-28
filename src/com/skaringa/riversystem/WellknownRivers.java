package com.skaringa.riversystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

public class WellknownRivers {

  public static final String RHONE = "Rhone";
  public static final String TAGLIAMENTO = "Tagliamento";
  public static final String PIAVE = "Piave";
  public static final String ISONZO = "Isonzo";
  public static final String ETSCH = "Etsch";
  public static final String PO = "Po";
  public static final String WEICHSEL = "Weichsel";
  public static final String MAAS = "Maas";
  public static final String EIDER = "Eider";
  public static final String SCHLEI = "Schlei";
  public static final String TRAVE = "Trave";
  public static final String PEENE = "Peene";
  public static final String WARNOW = "Warnow";
  public static final String ODER = "Oder";
  public static final String ELBE = "Elbe";
  public static final String DONAU = "Donau";
  public static final String EMS = "Ems";
  public static final String WESER = "Weser";
  public static final String RHEIN = "Rhein";
  
  public static final Set<String> ADRIA = new HashSet<String>(Arrays.asList(TAGLIAMENTO, PIAVE, ISONZO, ETSCH, PO));
  public static final Set<String> WARNOWPEENE = new HashSet<String>(Arrays.asList(WARNOW, PEENE));
  public static final Set<String> TRAVESCHLEI = new HashSet<String>(Arrays.asList(TRAVE, SCHLEI));

  // Map IDs of wellknown rivers to their basin name
  static TLongObjectMap<String> id2Basin;
  static {
    id2Basin = new TLongObjectHashMap<String>();
    id2Basin.put(102135980L, RHEIN);
    id2Basin.put(26970429L, RHEIN); // Berkel
    id2Basin.put(32965156L, RHEIN); // Issel
    id2Basin.put(52528188L, RHEIN); // Dinkel
    id2Basin.put(23452335L, RHEIN); // Vechte
    id2Basin.put(33655748L, RHEIN); // Bocholter Aa
    id2Basin.put(128043705L, WESER);
    id2Basin.put(4308645L, EMS);
    id2Basin.put(5011091L, DONAU);
    id2Basin.put(30613639L, ELBE);
    id2Basin.put(118848751L, ELBE); // Eger
    id2Basin.put(89253786L, ODER);
    id2Basin.put(4488193L, WARNOW);
    id2Basin.put(4532243L, PEENE);
    id2Basin.put(1211198775L, TRAVE);
    id2Basin.put(23859221L, SCHLEI); // Füsinger Au
    id2Basin.put(5011995L, EIDER);
    id2Basin.put(29432442L, ELBE); // Obere Eider
                                   // (http://de.wikipedia.org/wiki/Eider)
    id2Basin.put(36054759L, MAAS); // Rur
    id2Basin.put(9332131L, MAAS); // Niers
    id2Basin.put(25680743L, MAAS); // Schwalm
    id2Basin.put(28889257L, MAAS); // La Meuse
    id2Basin.put(48872639L, MAAS); // La Chiers

    id2Basin.put(64690374L, DONAU); // Drau
    id2Basin.put(23074512L, DONAU); // Sava

    id2Basin.put(34614840L, WEICHSEL);
    id2Basin.put(179582873L, WEICHSEL);

    id2Basin.put(68293532L, PO); // Maira
    id2Basin.put(105715697, PO); // Oglio
    id2Basin.put(182920858L, ETSCH); // Rambach - Ram
    id2Basin.put(52106448L, ISONZO);
    id2Basin.put(162189624L, TAGLIAMENTO);

    id2Basin.put(123033116L, RHONE);
    id2Basin.put(122130057L, RHONE); // Le Doubs
  }

  // Waterways that are drainage divides
  static TLongSet divides;
  static {
    divides = new TLongHashSet();
    divides.add(29915851L); // Main-Donau-Kanal
    divides.add(35963164L); //dto.
    divides.add(284797683L); // dto.
    divides.add(88817869L); // Hausseebruchgraben
    divides.add(733422822L); // Küste Oder rechts
    divides.add(30421371L); // Küste Peene rechts
    divides.add(102832365L); // Der Strom
    divides.add(95799872L); // Küste Peene rechts
    divides.add(28406303L); // Küste Trave rechts
    divides.add(25976489L); // Küste Schlei links
    divides.add(347556100L); // Küste Eider rechts
    divides.add(4100668L); // Küste Elbe rechts
    divides.add(4096524L); // Küste Weser rechts
    divides.add(159679203L); // Küste Ems rechts
    divides.add(921082861L); // Küste Rhein rechts
    divides.add(427799833L); // Nord-Ostsee-Kanal Kiel-Holtenau Wehr
    divides.add(131279454L); // neue Schleuse Kiel-Holtenau Nordkammer
    divides.add(73033120L); // neue Schleuse Kiel-Holtenau Nordkammer
    divides.add(131279453L); // neue Schleuse Kiel-Holtenau Südkammer
    divides.add(73033118L); // neue Schleuse Kiel-Holtenau Südkammer
    divides.add(169231236L); // Schleuse Gieselau-Kanal
    divides.add(169231235L); // Schleuse Gieselau-Kanal
    divides.add(176010294L); // Schaalseekanal
    divides.add(103513817L); // Elbe-Lübeck-Kanal
    divides.add(172109819L); // Elbe-Lübeck-Kanal
    divides.add(30909396L); // Schweriner See, Wallensteingraben
    divides.add(24205659L);
    divides.add(727924508L); // Küste Peene/Oder
    divides.add(30378663L); // Mittellandkanal
    divides.add(24950057L); // Mittellandkanal
    divides.add(44671142L); // Mittellandkanal
    divides.add(166221286L); // Mittellandkanal
    divides.add(244017125L); // dto.
    divides.add(183432284L);
    divides.add(2360794L); // Mittellandkanal (relation)
    divides.add(44671140L); // Mittellandkanal (area)
    divides.add(30380278L); // dto.
    divides.add(49010852L);
    divides.add(112674007L); // Fanggraben
    divides.add(169698060L); // Elbe-Seitenkanal
    divides.add(254500615L);
    divides.add(30772058L); // Elbe-Seitenkanal (area)
    divides.add(265233238L);
    divides.add(39743374L); // Ludwig-Donau-Main-Kanal
    divides.add(5120607L); // Altmühlüberleiter
    divides.add(5120782L); // dto.
    divides.add(43861716L); // Oder-Spree-Kanal
    divides.add(27036866L); // Oste-Hamme-Kanal
    divides.add(142987714L);
    divides.add(685450543L); // Templiner Kanal
    divides.add(663624865L); // Godenstedt-Ostereistedter Moorgraben
    divides.add(30568943L); // Burgsittenser Bach
    divides.add(166599679L); // Finowkanal
    divides.add(35898720L); // Oder-Havel-Kanal
    divides.add(28631513L); // Geeste Kanal - Schifffahrtsweg Elbe/Weser
    divides.add(25410328L); // Else-Hase-Bifurkation
    divides.add(8134358L); // Dortmund-Ems-Kanal
    divides.add(49213928L); // dto
    divides.add(36272348L); // dto
    divides.add(48749385L); // Ems-Vechte-Kanal
    divides.add(248963297L); // dto.
    divides.add(28071866L); // Süd-Nord Kanal
    divides.add(204413032L); // Haren-Rütenbrock-Kanal
    divides.add(456165106L); // dto.
    divides.add(715658668L); // dto.
    divides.add(611321392L); // Prinses Margrietkanaal
    divides.add(668498070L); // dto.
    divides.add(203463567L); // Stads-Compascuumkanaal
    divides.add(70681602L); // Küstenkanal
    divides.add(614235529L); // Drentsche Hoofdvaart
    divides.add(712007994L); // dto.
    divides.add(160029569L); // Luttertunnel Bielefeld
    divides.add(116711212L); // Haselbachstollen
    divides.add(210614669L); // Flößgraben
    divides.add(302373912L); // Großer Graben
    divides.add(23481759L); // Fossa Eugeniana
    divides.add(26726898L); // Krollbachbifurkation https://www.hoevelhof.de/de/tourismus/sehenswuerdigkeiten/sehenswuerdigkeiten/krollbachbifurkation.php
    divides.add(753903784L); // Moerskanal
    
    divides.add(82037779L); // Tříhrázný rybník (Donau/Elbe)
    divides.add(81931306L); // unknown (Donau/Elbe)
    divides.add(82080861L); // dibavod:id=207680006300
    divides.add(81453698L); // dibavod:id=131260002300
    divides.add(272086643L); // umělý kanál (Donau/Elbe)
    divides.add(27568398L); // Schwarzenbergischer Schwemmkanal
    divides.add(262751461L); // Schwarzenbergischer Schwemmkanal
    divides.add(340821441L); // Überleitung Hájený potok-Jizera (Oder/Elbe)
    divides.add(81866380L);; // Kanal Bílá Nisa/Kamenice
    divides.add(367797655L); // Osterbachkanal (Donau/Elbe)
    divides.add(1110496745L); // Hochmoor Topielsko (Oder/Elbe)

    divides.add(60687941L); // Czerna (Donau/Weichsel)
    divides.add(1041523012L); // Zásihlianka (Donau/Weichsel)

    divides.add(195089417L); // Kanał Bydgoski (Bromberger Kanal, Oder/Weichsel)
    divides.add(751229797L); // Kanał Bachorze
    divides.add(914971971L); // Oder/Weichsel
    divides.add(931826321L); // Oder/Weichsel
    divides.add(240614730L); // Küste Oder/Weichsel

    divides.add(80965690L); // Canal du Rhône au Rhin
    divides.add(80965316L);
    divides.add(245257513L);
    divides.add(171083336L); // Canal d'alimentation de la Largue
    divides.add(73811224L);
    divides.add(159656530L); // Canal des Vosges
    divides.add(480052426L); // dto. sidestream
    divides.add(275827959L); // dto.
    divides.add(82715306L); // Souterrainl de Foug (Canal de la Marne au Rhin)
    divides.add(5509033L); // dto. relation !!!
    
    divides.add(435393183L); // Rhone/Po
    divides.add(405871635L); // Rhone/Po
    divides.add(398968003L); // Canale del Comio
    divides.add(51920117L); // Canale Marignane Basse

    divides.add(208688655L); // Maas-Waal Kanaal
    divides.add(79301837L);
    divides.add(165188439L); // Heusdensch Kanaal
    divides.add(86033810L);
    divides.add(208687596L); // Kanaal van St. Andries
    divides.add(79718960L);
    divides.add(80258525L); // Nordkanal
    
    divides.add(380269472L); // Tunnel Ticino - Vorderrhein
    divides.add(781351380L); // Überleitungsstollen Bieltalbach
    divides.add(420267291L); // Überleitungstunnel Rhein/Donau
    
    divides.add(900738718L); // Donauversinkung
    divides.add(900736088L); // dto.
    divides.add(900738333L); // dto.
    
    divides.add(741211480L); // Überleitung Kops-Vermunt
    divides.add(1183699176L); // Bachüberleitung Fasulbach und Rosanna
  }

  public static String getBasin(long riverId) {
    return id2Basin.get(riverId);
  }

  public static boolean isDivide(long riverId) {
    return divides.contains(riverId);
  }
}
