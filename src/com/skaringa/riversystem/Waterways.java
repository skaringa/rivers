package com.skaringa.riversystem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Waterways {

	private Map<Long, String> id2Basin = Collections.synchronizedMap(new HashMap<Long, String>());
	
	private Map<Long, List<Way>>  nodeId2WayList = new HashMap<Long, List<Way>>();
	
	private static final int N_THREADS = 8;
	private LinkedBlockingQueue<Way> queue = new LinkedBlockingQueue<Way>();
	private int waiters;
	boolean running = true;
	
	private boolean debug = false;
	private Set<Long> debugWayIds = new HashSet<Long>(Arrays.asList(
			new Long[]{171051783L, 41298988L, 171051778L, 25022196L, 171051784L, 171051776L, 
					171051779L, 35956587L, 101237327L, 226998939L, 30772058L}
			));
	
	
	public void loadFromFileList(List<File> fileList) throws JSONException, IOException {
		for (File file : fileList) {
			loadFromFile(file);
		}
	}
	
	public void loadFromFile(File file) throws JSONException, IOException {
	  System.out.println("Loading from " + file.getName());
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		try {
		  if (file.getName().endsWith(".json")) {
		    loadJson(in);
		  } else if (file.getName().endsWith(".csv")) {
		    loadCsv(in);
		  } else {
		    System.out.println("Unknown filetype - skip");
		  }
		} finally {
			in.close();
		}
	}
	
  public Map<Long, String> getId2Basin() {
		return id2Basin;
	}
	
	public void explore() {
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
	
	private List<Way> exploreNodes(Way refway) {
		List<Way> newWays = new LinkedList<Way>();
		String basin = id2Basin.get(refway.id);
		for (Long refNodeId : refway.nodeList) {
			List<Way> wayList = nodeId2WayList.get(refNodeId);
			for (Way way : wayList) {
				if (way.resolved) {
					continue;
				}
				id2Basin.put(way.id, basin);
				if (debug && debugWayIds.contains(way.id)) {
					System.err.printf("way %d tagged from node %d of way %d%n", way.id, refNodeId, refway.id);
				}
				way.resolved = true;
				newWays.add(way);
			}
		}
		return newWays;
	}

	private void loadJson(InputStream in) throws JSONException {
		int wwayCount = 0;
		JSONTokener x = new JSONTokener(in);
        if (x.nextClean() != '[') {
            throw x.syntaxError("A JSONArray text must start with '['");
        }
        if (x.nextClean() != ']') {
	        x.back();
	        loop:
	        for (;;) {
	            if (x.nextClean() == ',') {
	            	// NULL
	                x.back();
	            } else {
	                x.back();
	                Object object = x.nextValue();
	                if (object instanceof JSONObject) {
	                	loadWaterway((JSONObject)object);
	                	wwayCount++;
	                } else {
	                	throw new JSONException(object +
	                			" is not a JSONObject.");
	                }
	                
	            }
	            switch (x.nextClean()) {
	            case ';':
	            case ',':
	                if (x.nextClean() == ']') {
	                    break loop;
	                }
	                x.back();
	                break;
	            case ']':
	            	break loop;
	            default:
	                throw x.syntaxError("Expected a ',' or ']'");
	            }
	        }
        }
        System.out.printf("Loaded %d ways.%n", wwayCount);
	}
	
	private void loadWaterway(JSONObject wway) throws JSONException {
		long id = wway.getLong("id");
		if ("relation".equals(wway.optString("from"))) {
			id /= 2; // restore original OSM id
		} else if ("way".equals(wway.optString("from"))) {
			id /= 2; // restore original OSM id
		}
		Long[] nodes = toNodeList(wway.getJSONObject("nodes"));
		addWay(new Way(id, nodes));
	}

	private Long[] toNodeList(JSONObject nodes) throws JSONException {
		int nodeCount = nodes.getInt("length");
		Long[] nodeArray = new Long[nodeCount];
		for (int i = 0; i < nodeCount; ++i) {
			nodeArray[i] = nodes.getLong(String.valueOf(i));
		}
		return nodeArray;
	}
	
  private void loadCsv(InputStream in) throws IOException {
    int wwayCount = 0;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        if (line.trim().length() > 0) { // skip blank lines
          String tokens[] = line.split("\\,");
          Long id = Long.valueOf(tokens[0]);
          Long[] nodes = new Long[tokens.length - 1];
          for (int i = 1; i < tokens.length; ++i) {
            nodes[i-1] = Long.valueOf(tokens[i]);
          }
          addWay(new Way(id, nodes));
          wwayCount++;
        }
      }
    } finally {
      reader.close();
    }
    System.out.printf("Loaded %d ways.%n", wwayCount);
  }

  private void addWay(Way way) {
    String basin = WellknownRivers.getBasin(way.id);
    if (basin != null) {
      id2Basin.put(way.id, basin);
      way.resolved = true;
      queue.offer(way);
    } 
    if (WellknownRivers.isDivide(way.id)) {
      way.resolved = true;
    }
    for (Long nodeId : way.nodeList) {
      List<Way> wayList = nodeId2WayList.get(nodeId);
      if (wayList == null) {
        wayList = new ArrayList<Way>();
        nodeId2WayList.put(nodeId, wayList);
      }
      wayList.add(way);
    }
  }

	class Explorer extends Thread {

		@Override
		public void run() {
			while (running) {
				Way way;
				synchronized (queue) {
					if (queue.isEmpty()) {
						try {
							waiters++;
							queue.wait();
							waiters--;
						} catch (InterruptedException e) {
						}
					}
					way = queue.poll();
				}
				if (way == null) {
					continue;
				}

				List<Way> newWay = exploreNodes(way);
				if (!newWay.isEmpty()) {
					synchronized (queue) {
						for (Way nw : newWay) {
							queue.offer(nw);
							queue.notify();
						}
					}
				}
			}
		}
	}
	
	static class Way {
		Long id;
		Long[] nodeList;
		boolean resolved;
		
		Way(Long id, Long[] nodes) {
			this.id = id;
			this.nodeList = nodes;
		}
	}

}
