package com.skaringa.riversystem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.json.JSONException;

public class RiverSystems {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException, IOException {
		long ts = System.currentTimeMillis();
		File wwaysFile = new File("../../river/output/wways.json");
		File csvFile = new File("../../river/output/rsystems.csv");
		File csvtFile = new File("../../river/output/rsystems.csvt");
		
		Waterways waterways = Waterways.loadFromJson(wwaysFile);
		waterways.explore();
		
		writeCsvt(csvtFile);
		writeCsv(csvFile, waterways.getId2Basin());

		System.out.printf("Finished. Time: %d s%n", (System.currentTimeMillis() - ts) / 1000);
	}

	private static void writeCsvt(File csvtFile) throws IOException {
		PrintWriter writer = new PrintWriter(csvtFile);
		try {
			writer.println("\"Integer(10)\",\"String(32)\"");
		} finally {
			writer.close();
		}
	}

	private static void writeCsv(File csvFile, Map<Long, String> id2Basin) throws IOException, JSONException {
		PrintWriter writer = new PrintWriter(csvFile);
		try {
			writer.println("id,rsystem");
			for (Map.Entry<Long, String> entry : id2Basin.entrySet()) {
				writer.printf("%d,%s%n", entry.getKey(), entry.getValue());
			}
			// divides
			for (Long id : WellknownRivers.divides) {
				writer.printf("%d,divide%n", id);
			}
		} finally {
			writer.close();
		}
		System.out.println("Result file: " + csvFile);
	}
}
