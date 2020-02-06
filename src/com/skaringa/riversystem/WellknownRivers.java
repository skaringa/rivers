package com.skaringa.riversystem;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

public class WellknownRivers {

  private static final String RHONE = "Rhone";
  private static final String TAGLIAMENTO = "Tagliamento";
  private static final String PIAVE = "Piave";
  private static final String ISONZO = "Isonzo";
  private static final String ETSCH = "Etsch";
  private static final String PO = "Po";
  private static final String WEICHSEL = "Weichsel";
  private static final String MAAS = "Maas";
  private static final String EIDER = "Eider";
  private static final String SCHLEI = "Schlei";
  private static final String TRAVE = "Trave";
  private static final String PEENE = "Peene";
  private static final String WARNOW = "Warnow";
  private static final String ODER = "Oder";
  private static final String ELBE = "Elbe";
  private static final String DONAU = "Donau";
  private static final String EMS = "Ems";
  private static final String WESER = "Weser";
  private static final String RHEIN = "Rhein";

  // Map IDs of wellknown rivers to their basin name
  static TLongObjectMap<String> id2Basin;
  static {
    id2Basin = new TLongObjectHashMap<String>();
    id2Basin.put(102135980L, RHEIN);
    id2Basin.put(157538529L, RHEIN); // Bodensee
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
    id2Basin.put(161734566L, WARNOW); // Recknitz
    id2Basin.put(4532243L, PEENE);
    id2Basin.put(5005413L, TRAVE);
    id2Basin.put(29249972L, TRAVE); // Schwentine
    id2Basin.put(104383228L, TRAVE); // Stepenitz
    id2Basin.put(219919816L, SCHLEI);
    id2Basin.put(98726563L, EIDER); // Arlau
    id2Basin.put(52997220L, EIDER); // Miele
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

    id2Basin.put(77604974L, PO); // Ticino
    id2Basin.put(68293532L, PO); // Maira
    id2Basin.put(105715697, PO); // Oglio
    id2Basin.put(182920858L, ETSCH); // Rambach - Ram
    id2Basin.put(52106448L, ISONZO);
    id2Basin.put(159833469L, PIAVE);
    id2Basin.put(162189624L, TAGLIAMENTO);

    id2Basin.put(123033116L, RHONE);
    id2Basin.put(122130057L, RHONE); // Le Doubs
    id2Basin.put(121412596L, RHONE); // La Saône
  }

  // Waterways that are drainage divides
  static TLongSet divides;
  static {
    divides = new TLongHashSet();
    divides.add(39743371L); // Main-Donau-Kanal
    divides.add(102832333L); // Der Strom
    divides.add(28070936L); // Nord-Ostsee-Kanal
    divides.add(131279449L); // alte Schleuse Kiel-Holtenau Nordkammer
    divides.add(73034121L); // alte Schleuse Kiel-Holtenau Nordkammer
    divides.add(131279454L); // alte Schleuse Kiel-Holtenau Südkammer
    divides.add(73034123L); // alte Schleuse Kiel-Holtenau Südkammer
    divides.add(131279451L); // neue Schleuse Kiel-Holtenau Nordkammer
    divides.add(73033120L); // neue Schleuse Kiel-Holtenau Nordkammer
    divides.add(131279453L); // neue Schleuse Kiel-Holtenau Südkammer
    divides.add(73033118L); // neue Schleuse Kiel-Holtenau Südkammer
    divides.add(179081443L); // alte Schleuse Eider Kanal Rendsburg
    divides.add(169231236L); // Schleuse Gieselau-Kanal
    divides.add(169231235L); // Schleuse Gieselau-Kanal
    divides.add(176010294L); // Schaalseekanal
    divides.add(162105959L); // Störkanal
    divides.add(103423760L); // Elbe-Lübeck-Kanal
    divides.add(30909396L); // Schweriner See, Wallensteingraben
    divides.add(24205659L);
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
    divides.add(43861716L); // Oder-Spree-Kanal
    divides.add(27036866L); // Oste-Hamme-Kanal
    divides.add(30568943L); // Burgsittenser Bach
    divides.add(166599679L); // Finowkanal
    divides.add(35898720L); // Oder-Havel-Kanal
    divides.add(28631513L); // Geeste Kanal - Schifffahrtsweg Elbe/Weser
    divides.add(25410328L); // Else-Hase-Bifurkation
    divides.add(8134358L);
    divides.add(49213928L); // Dortmund-Ems-Kanal
    divides.add(36272347L); // Dortmund-Ems-Kanal
    divides.add(8134358L); // Dortmund-Ems-Kanal
    divides.add(48749385L); // Ems-Vechte-Kanal
    divides.add(28071866L); // Süd-Nord Kanal
    divides.add(70681602L); // Küstenkanal
    divides.add(160029569L); // Luttertunnel Bielefeld
    divides.add(116711212L); // Haselbachstollen
    divides.add(210614669L); // Flößgraben
    divides.add(302373912L); // Großer Graben

    divides.add(82037779L); // Tříhrázný rybník (Donau/Elbe)
    divides.add(81931306L); // unknown (Donau/Elbe)
    divides.add(82080861L); // dibavod:id=207680006300
    divides.add(81453698L); // dibavod:id=131260002300
    divides.add(272086643L); // umělý kanál (Donau/Elbe)
    divides.add(27568398L); // Schwarzenbergischer Schwemmkanal
    divides.add(262751461L); // Schwarzenbergischer Schwemmkanal

    divides.add(60687941L); // Czerna (Donau/Weichsel)

    divides.add(195089417L); // Kanał Bydgoski (Bromberger Kanal, Oder/Weichsel)

    divides.add(80965690L); // Canal du Rhône au Rhin
    divides.add(80965316L);
    divides.add(245257513L);
    divides.add(171083336L); // Canal d'alimentation de la Largue
    divides.add(73811224L);
    divides.add(159656530L); // Canal des Vosges
    divides.add(82715306L); // Souterrainl de Foug (Canal de la Marne au Rhin)

    divides.add(208688655L); // Maas-Waal Kanaal
    divides.add(79301837L);
    divides.add(165188439L); // Heusdensch Kanaal
    divides.add(86033810L);
    divides.add(208687596L); // Kanaal van St. Andries
    divides.add(79718960L);
  }

  public static String getBasin(long riverId) {
    return id2Basin.get(riverId);
  }

  public static boolean isDivide(long riverId) {
    return divides.contains(riverId);
  }
}
