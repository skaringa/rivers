package com.skaringa.riversystem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.TLongObjectMap;

public class RiverSystems {

  /**
   * @param args
   * @throws IOException
   * @throws JSONException
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.printf("Usage: java %s <input_file_1> [<input_file_2> ] <output_csv_file>%n",
          RiverSystems.class.getName());
      System.exit(1);
    }

    long ts = System.currentTimeMillis();
    long tm = Runtime.getRuntime().totalMemory();

    List<File> inputFileList = new ArrayList<File>();
    for (int i = 0; i < args.length - 1; ++i) {
      inputFileList.add(new File(args[i]));
    }
    File csvFile = new File(args[args.length - 1]);
    File csvtFile = new File(args[args.length - 1] + "t");

    Waterways waterways = new Waterways();
    for (int pass = 1; pass < inputFileList.size()+1; ++pass) {
      System.out.println("Pass " + pass);
      for (int i = 0; i < pass; ++i) {
        waterways.loadFromFile(inputFileList.get(i));
      }
      waterways.explore(pass);
    }
    
    waterways.reportCollisions(System.out);
    
    System.out.println("Writing result...");
    writeCsvt(csvtFile);
    writeCsv(csvFile, waterways.getId2Basin());

    System.out.printf("Finished. Time: %d s Memory: %d MB%n",
        (System.currentTimeMillis() - ts) / 1000,
        (Runtime.getRuntime().totalMemory() - tm) / 1024 / 1024);
  }

  private static void writeCsvt(File csvtFile) throws IOException {
    PrintWriter writer = new PrintWriter(csvtFile);
    try {
      writer.println("\"Integer(10)\",\"String(32)\"");
    } finally {
      writer.close();
    }
  }

  private static void writeCsv(File csvFile, TLongObjectMap<String> id2Basin) throws IOException {
    PrintWriter writer = new PrintWriter(csvFile);
    try {
      writer.println("id,rsystem");
      for (TLongObjectIterator<String> it = id2Basin.iterator(); it.hasNext();) {
        it.advance();
        writer.printf("%d,%s%n", it.key(), it.value());
      }
      // divides
      TLongIterator iterator = WellknownRivers.divides.iterator();
      while (iterator.hasNext()) {
        writer.printf("%d,divide%n", iterator.next());
      }
    } finally {
      writer.close();
    }
    System.out.println("Result file: " + csvFile);
  }
}
