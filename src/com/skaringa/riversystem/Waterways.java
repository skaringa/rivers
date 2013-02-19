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
import java.util.concurrent.LinkedBlockingQueue;

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
	
	private static final int N_THREADS = 8;
	private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<Long>();
	private int waiters;
	boolean running = true;
	
	public static Waterways loadFromJson(List<File> jsonFileList) throws JSONException, IOException {
		Waterways allWaterways = null;
		
		for (File jsonFile : jsonFileList) {
			Waterways waterways = loadFromJson(jsonFile);
			if (allWaterways == null) {
				allWaterways = waterways;
			} else {
				allWaterways.merge(waterways);
			}
		}
		
		return allWaterways;
	}
	
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
		for (Long id : id2Basin.keySet()) {
			queue.offer(id);
		}
		
		LinkedList<Thread> threadList = new LinkedList<Thread>();
		for (int i = 0; i < N_THREADS; ++i) {
			Thread explorer = new Explorer();
			threadList.add(explorer);
			explorer.start();
		}
		
		synchronized (queue) {
			queue.notifyAll();
		}
		
		for (;;) {
			synchronized (queue) {
				System.out.printf("Ways found: %d, queue size: %d, waiters: %d%n",
						id2Basin.size(), queue.size(), waiters);
				if (waiters == N_THREADS) {
					running = false;
					queue.notifyAll();
					break;
				}
			}
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
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
			if ("relation".equals(wway.optString("from"))) {
				id /= 2; // restore original OSM id
			} else if ("way".equals(wway.optString("from"))) {
				id /= 2; // restore original OSM id
			}
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
			if (WellknownRivers.isDivide(id)) {
				resolved[i] = true;
			}
		}
		System.out.printf("Loaded %d ways.%n", wwayCount);
	}

	private void merge(Waterways waterways) {
		id2Basin.putAll(waterways.id2Basin);
		int oldLen = nodes.length;
		int addLen = waterways.nodes.length;
		int newLen = oldLen + addLen;
		
		long[][] nodes2 = new long[newLen][];
		System.arraycopy(nodes, 0, nodes2, 0, oldLen);
		System.arraycopy(waterways.nodes, 0, nodes2, oldLen, addLen);
		nodes = nodes2;
		
		long[] index2Id2 = new long[newLen];
		System.arraycopy(index2Id, 0, index2Id2, 0, oldLen);
		System.arraycopy(waterways.index2Id, 0, index2Id2, oldLen, addLen);
		index2Id = index2Id2;
		
		boolean[] resolved2 = new boolean[newLen];
		System.arraycopy(resolved, 0, resolved2, 0, oldLen);
		System.arraycopy(waterways.resolved, 0, resolved2, oldLen, addLen);
		resolved = resolved2;
		
		for (Map.Entry<Long, Integer> e : waterways.id2Index.entrySet()) {
			id2Index.put(e.getKey(), e.getValue() + oldLen);
		}
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

	class Explorer extends Thread {

		@Override
		public void run() {
			while (running) {
				Long id;
				synchronized (queue) {
					if (queue.isEmpty()) {
						try {
							waiters++;
							queue.wait();
							waiters--;
						} catch (InterruptedException e) {
						}
					}
					id = queue.poll();
				}
				if (id == null) {
					continue;
				}

				List<Long> newIds = exploreNodes(id);
				if (!newIds.isEmpty()) {
					synchronized (queue) {
						for (Long nid : newIds) {
							queue.offer(nid);
							queue.notify();
						}
					}
				}
			}
		}
	}
}
