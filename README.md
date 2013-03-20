rivers
======

Compute river basins based on OSM data.

This tool is intended to create maps of river basins like [Flussgebiete Deutschlands](http://www.kompf.de/gps/rivermap.html).

prerequisites
-------------

* Linux 64 bit (eg. Ubuntu 12.04)

* Java JDK 1.6

* Osmium fork from [skaringa](https://github.com/skaringa/osmium)

* Geotools 8.4. [Download libraries](http://sourceforge.net/projects/geotools/files/GeoTools%208%20Releases/) 

* [Tilemill](http://mapbox.com/tilemill/)

workflow
--------

1. Compile Osmium/osmjs. See [OSM wiki](http://wiki.openstreetmap.org/wiki/Osmium/Quick_Start) to get started.

2. Compile rivers:

        mkdir bin
        cp=bin
        GEOTOOLS=$GEOTOOLS_JAR_DIR
        for file in $GEOTOOLS/*.jar; do
            cp="$cp:$file"
        done
        javac -d bin -sourcepath src -cp $cp src/com/skaringa/riversystem/RiverSystems.java
        javac -d bin -sourcepath src -cp $cp src/com/skaringa/shapefile/JoinCsv.java
    
3. Download germany.osm.pbf file from [Geofabrik](http://download.geofabrik.de/). 
   Generally the tool should work with other regions as well, but in that case you probably need to add 
   more names and IDs to [WellknownRivers.java](https://github.com/skaringa/rivers/blob/master/src/com/skaringa/riversystem/WellknownRivers.java).

4. Run *osmjs* to create shapefiles and JSON files with config_rivermap: 

        osmjs -2 -m -l sparsetable -i osm2shape.js -j js/config_rivermap.js germany.osm.pbf
    
5. Run *rivers* with the JSON files to build the river basins: 

        java -Xmx3000m -cp $cp com.skaringa.riversystem.RiverSystems wways.json wtr.json rsystems.csv 
    
   This creates a CSV file that contains the mapping of the OSM way ID to the name of the river basin.
  
6. Merge the CSV file into the waterways shape file:

        java -Xmx3000m -cp $cp com.skaringa.shapefile.JoinCsv waterways.shp rsystems.csv waterways-r.shp
    
   Alternatively you may use other tools, like [Quantum GIS](http://www.qgis.org/) to merge CSV and shape file.

7. Use *tilemill* to render, customize, and export the map.

