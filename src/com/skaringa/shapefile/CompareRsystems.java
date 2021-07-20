package com.skaringa.shapefile;

import java.io.IOException;

import com.skaringa.riversystem.RiverSystems;
import com.skaringa.riversystem.WellknownRivers;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.procedure.TLongObjectProcedure;

/**
 * Compyre the results of two runs of {@link RiverSystems}
 *
 */
public class CompareRsystems {
  

  /**
   * 
   * @param args rsystems.csv rsystems.csv.bak
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.printf("Usage: java %s <new rsystems.csv> <old rsystems.csv>%n",
          CompareRsystems.class.getName());
      System.exit(1);
    }

    JoinCsv newRsystemsReader = new JoinCsv();
    newRsystemsReader.readInputCsv(args[0]);
    final TLongObjectMap<String> newId2Basin = newRsystemsReader.getId2Basin();
    
    JoinCsv oldRsystemsReader = new JoinCsv();
    oldRsystemsReader.readInputCsv(args[1]);
    final TLongObjectMap<String> oldId2Basin = oldRsystemsReader.getId2Basin();
    
    newId2Basin.forEachEntry(new TLongObjectProcedure<String>() {

      @Override
      public boolean execute(long id, String basin) {
        String oldBasin = oldId2Basin.get(id);
        if (basin != null && oldBasin != null && !basin.equals(oldBasin)) {
          if (! (WellknownRivers.ADRIA.contains(basin) && WellknownRivers.ADRIA.contains(oldBasin))) {
            System.out.printf("%d: %s -> %s%n", id, oldBasin, basin);
          }
        }
        return true;
      }
    });
    
  }

}
