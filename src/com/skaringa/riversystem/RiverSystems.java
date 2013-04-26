package com.skaringa.riversystem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

public class RiverSystems {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException, IOException {
		if (args.length < 2) {
			System.out.printf("Usage: java %s <input_json_file_1> [<input_json_file_2> ...] <output_csv_file>%n", RiverSystems.class.getName());
			System.exit(1);
		}
		
		long ts = System.currentTimeMillis();
		
		List<File> inputFileList = new LinkedList<File>();
		for (int i = 0; i < args.length - 1; ++i) {
			inputFileList.add(new File(args[i]));
		}
		File csvFile = new File(args[args.length - 1]);
		File csvtFile = new File(args[args.length - 1] + "t");
		
		Waterways waterways = new Waterways();
		waterways.loadFromFileList(inputFileList);
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
