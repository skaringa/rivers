package com.skaringa.riversystem;

import gnu.trove.TCollections;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Waterways {
  
  static Set<String> TYPE_BLACKLIST = new HashSet<String>();
  static {
  }

  private TLongObjectMap<String> id2Basin = TCollections.synchronizedMap(new TLongObjectHashMap<String>());

  private TLongObjectMap<List<Way>> nodeId2WayList = new TLongObjectHashMap<List<Way>>();

  private static final int N_THREADS = 8;
  private LinkedBlockingQueue<Way> queue = new LinkedBlockingQueue<Way>();
  private int waiters;
  boolean running;

  private boolean debug = false;
  private TLongSet debugWayIds = new TLongHashSet(
      new long[] { 71224720L, 82725219L, 5509033L, 83841440L, 5367717L }
      );

  public void loadFromFileList(List<File> fileList) throws IOException {
    for (File file : fileList) {
      loadFromFile(file);
    }
  }

  public void loadFromFile(File file) throws IOException {
    System.out.println("Loading from " + file.getName());
    InputStream in = new BufferedInputStream(new FileInputStream(file));
    try {
      loadCsv(in);
    } finally {
      in.close();
    }
  }

  public TLongObjectMap<String> getId2Basin() {
    return id2Basin;
  }

  public void explore() {
    running = true;
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
    for (long refNodeId : refway.nodeList) {
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

  private void loadCsv(InputStream in) throws IOException {
    int wwayCount = 0;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        if (line.trim().length() > 0) { // skip blank lines
          String tokens[] = line.split("\\,");
          long id = Long.parseLong(tokens[0]);
          String type = tokens[1];
          if (! TYPE_BLACKLIST.contains(type)) {
            long[] nodes = new long[tokens.length - 2];
            for (int i = 2; i < tokens.length; ++i) {
              nodes[i - 2] = Long.parseLong(tokens[i]);
            }
            
            addWay(new Way(id, nodes));
            wwayCount++;
          }
        }
      }
    } finally {
      reader.close();
    }
    System.out.printf("Loaded %d ways.%n", wwayCount);
  }

  private void addWay(Way way) {
    String basin = id2Basin.get(way.id);
    if (basin != null) {
      // has basin from previous pass
      way.resolved = true;
      queue.offer(way);
    } else {
      basin = WellknownRivers.getBasin(way.id);
      if (basin != null) {
        // basin of a wellknown river
        id2Basin.put(way.id, basin);
        way.resolved = true;
        queue.offer(way);
      }
    }
    if (WellknownRivers.isDivide(way.id)) {
      way.resolved = true;
    }
    for (long nodeId : way.nodeList) {
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
    long id;
    long[] nodeList;
    boolean resolved;

    Way(long id, long[] nodes) {
      this.id = id;
      this.nodeList = nodes;
    }
  }

}
