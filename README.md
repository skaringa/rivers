rivers
======

Compute river basins based on OSM data.

This tool is intended to create maps of river basins like [Flussgebiete Mitteleuropas](https://www.kompf.de/gps/rivermap.html).

prerequisites
-------------

* Linux 64 bit with >= 32 GB memory

* Java JDK 11+, Ant

* [libosmium](https://github.com/osmcode/libosmium) 

* [osmium-tool](https://github.com/osmcode/osmium-tool)

* [rivermap-osmium-tools](https://github.com/skaringa/rivermap-osmium-tools)


workflow
--------

1. Build libosmium, osmium-tool, and rivermap-osmium-tools.

2. Compile rivers:

        ant -lib /usr/share/java
    
3. Download europe-latest.osm.pbf file from [Geofabrik](https://download.geofabrik.de/). 
   Generally the tool should work with other regions as well, but in that case you probably need to add 
   more names and IDs to `WellknownRivers.java`.

4. Run the osmium tools to extract waterways from the OSM file and to create CSV files with te IDs of waterways and their nodes (you may need to prefix the executables with an additional path): 

        osmium tags-filter europe-latest.osm.pbf -e tags-filter.txt -o water.pbf
        osmium extract -b 5.0,44.5,20.0,56.0 water.pbf -o water-clipped.pbf
        osmium_waterway_ids water-clipped.pbf tags-filter.txt wways.csv wtr.csv
    
5. Run *rivers* with the CSV files generated above to build the river basins: 

        ant -lib /usr/share/java -Dargs="wways.csv wtr.csv rsystems.csv" RunRiverSystems
    
   This creates the CSV file *rsystems.csv* that contains the mapping of the OSM way ID to the name of the river basin.
  
6. Convert the OSM PBF water file into Spatialite and merge *rsystems.csv* into it (you may need to prefix the executable with an additional path):

        osmium_rivermap -r rsystems.csv water-clipped.pbf waterways.sqlite

7. Use a toolset of your choice (tilemill, mapnik, QGIS, etc.) to render and customize the map.

