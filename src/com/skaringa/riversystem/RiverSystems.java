package com.skaringa.riversystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONWriter;

public class RiverSystems {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException, IOException {
		File wwaysFile = new File("../../river/output/wways.json");
		File rsystemsFile = new File("../../river/js/_riversystems.js");
		
		Waterways waterways = Waterways.loadFromJson(wwaysFile);
		waterways.explore();
		writeResult(rsystemsFile, waterways.getId2Basin());
		System.out.println("Finished. Result file: " + rsystemsFile);
	}

	private static void writeResult(File rsystemsFile,
			Map<Long, String> id2Basin) throws IOException, JSONException {
		FileWriter writer = new FileWriter(rsystemsFile);
		try {
			writer.write("var riversystems = ");
			JSONWriter jsonWriter = new JSONWriter(writer);
			jsonWriter.object();
			for (Map.Entry<Long, String> entry : id2Basin.entrySet()) {
				jsonWriter.key(entry.getKey().toString());
				jsonWriter.value(entry.getValue());
			}
			jsonWriter.endObject();
			writer.write(";\n");
		} finally {
			writer.close();
		}
	}

}
