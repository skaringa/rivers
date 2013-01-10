package com.skaringa.riversystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Waterways {

	private Map<Long, String> id2Basin = Collections.synchronizedMap(new HashMap<Long, String>());
	
	private long[] index2Id;
	private boolean[] resolved;
	
	private Map<Long, Integer> id2Index = new HashMap<Long, Integer>();
	
	private long[][] nodes;
	
	public static Waterways loadFromJson(File jsonFile) throws JSONException, IOException {
		Waterways waterways = new Waterways();
		
		FileInputStream in = new FileInputStream(jsonFile);
		try {
			waterways.load(in);
		} finally {
			in.close();
		}
		return waterways;
	}
	
	public Map<Long, String> getId2Basin() {
		return id2Basin;
	}
	
	public void explore() {
		LinkedList<Thread> threadList = new LinkedList<Thread>();
		List<Long> rootIds = new LinkedList<Long>(id2Basin.keySet());
		for (final Long id : rootIds) {
			Thread t = new Thread() {
				@Override
				public void run() {
					System.out.printf("Thread %d start.%n", id);
					List<Long> newIds = exploreNodes(id);
					while (! newIds.isEmpty()) {
						System.out.printf("Found %d new ways%n", newIds.size());
						List<Long> testIds = newIds;
						newIds = new LinkedList<Long>();
						for (Long tid : testIds) {
							newIds.addAll(exploreNodes(tid));
						}
					}
					System.out.printf("Thread %d end.%n", id);
				}
			};
			threadList.add(t);
			t.start();
		}
		for (Thread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Interrupt.");
			}
		}
	}
	
	private List<Long> exploreNodes(Long wayid) {
		List<Long> newIds = new LinkedList<Long>();
		int refWayIndex = id2Index.get(wayid);
		String basin = id2Basin.get(wayid);
		long[] refway = nodes[refWayIndex];
		for (int i = 0; i < nodes.length; ++i) {
			if (resolved[i]) continue;
			for (long refNodeId : refway) {
				if (Arrays.binarySearch(nodes[i], refNodeId) >= 0) {
					// found
					long id = index2Id[i];
					id2Basin.put(id, basin);
					newIds.add(id);
					resolved[i] = true;
					break;
				}
			}
		}
		return newIds;
	}

	private void load(InputStream in) throws JSONException {
		JSONTokener tokener = new JSONTokener(in);
		JSONArray wwayArray = new JSONArray(tokener);
		int wwayCount = wwayArray.length();
		nodes = new long[wwayCount][];
		index2Id = new long[wwayCount];
		resolved = new boolean[wwayCount];
		for (int i = 0; i < wwayCount; ++i) {
			JSONObject wway = wwayArray.getJSONObject(i);
			long id = wway.getLong("id");
			index2Id[i] = id;
			id2Index.put(id, i);
			nodes[i] = toSortedNodeList(wway.getJSONObject("nodes"));
			String basin = WellknownRivers.getBasin(id);
			if (basin != null) {
				id2Basin.put(id, basin);
				resolved[i] = true;
			} else {
				resolved[i] = false;
			}
		}
		System.out.printf("Loaded %d ways.%n", wwayCount);
	}

	private long[] toSortedNodeList(JSONObject nodes) throws JSONException {
		int nodeCount = nodes.getInt("length");
		long[] nodeArray = new long[nodeCount];
		for (int i = 0; i < nodeCount; ++i) {
			nodeArray[i] = nodes.getLong(String.valueOf(i));
		}
		Arrays.sort(nodeArray);
		return nodeArray;
	}

}
