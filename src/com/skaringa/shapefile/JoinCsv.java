package com.skaringa.shapefile;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.MultiLineString;

public class JoinCsv {

	private SimpleFeatureBuilder featureBuilder;
	private SimpleFeatureSource featureSource;
	private SimpleFeatureType outputType;
	private TLongObjectMap<String> id2Basin;

	/**
	 * @param args
	 * @throws IOException
	 * @throws SchemaException
	 */
	public static void main(String[] args) throws IOException, SchemaException {
		if (args.length != 3) {
			System.out.printf("Usage: java %s <input_shp_file> <input_csv_file> <output_shp_file%n", 
					JoinCsv.class.getName());
			System.exit(1);
		}

		JoinCsv joinCsv = new JoinCsv();

		joinCsv.openInputShapefile(args[0]);
		joinCsv.readInputCsv(args[1]);
		joinCsv.createFeatureBuilder();
		List<SimpleFeature> featuresOut = joinCsv.process();
		joinCsv.writeOutputShapefile(featuresOut, args[2]);
	}

	private List<SimpleFeature> process() throws IOException {
		SimpleFeatureCollection featuresIn = featureSource.getFeatures();
		List<SimpleFeature> featuresOut = new ArrayList<SimpleFeature>();

		FeatureIterator<SimpleFeature> iterator = featuresIn.features();
		try {
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				featureBuilder.add(feature.getAttribute("the_geom"));
				long id = Long.parseLong((String) feature.getAttribute("id"));
				featureBuilder.add(String.valueOf(id));
				featureBuilder.add(feature.getAttribute("type"));
				featureBuilder.add(feature.getAttribute("name"));
				featureBuilder.add(id2Basin.get(id));

				featuresOut.add(featureBuilder.buildFeature(null));
			}
		} finally {
			iterator.close();
		}
		
		return featuresOut;
	}

	private void openInputShapefile(String inputShapefile) throws IOException {
		File file = new File(inputShapefile);
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		featureSource = store.getFeatureSource();

		SimpleFeatureType schema = featureSource.getSchema();
		System.out.println(schema.getTypeName() + ": "
				+ DataUtilities.encodeType(schema));
	}
	
	private void readInputCsv(String inputCsvfile) throws IOException {
		File file = new File(inputCsvfile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		id2Basin = new TLongObjectHashMap<String>();
        try {
        	/* First line of the data file is the header */
            String line = reader.readLine();
            System.out.println("Csv Header: " + line);

            for (line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.trim().length() > 0) { // skip blank lines
                    String tokens[] = line.split("\\,");
                    long id = Long.parseLong(tokens[0]);
                    String basin = tokens[1].trim();
                    id2Basin.put(id, basin);
                }
            }
        }
        finally {
        	reader.close();
        }
	}

	private void createFeatureBuilder() throws SchemaException {
		/*outputType = DataUtilities
				.createType(
						"waterways",
						"the_geom:MultiLineString,id:String,type:String,name:String,rsystem:String");*/
						
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName("waterways");
		builder.setCRS(DefaultGeographicCRS.WGS84);
		builder.add("the_geom", MultiLineString.class);
		builder.length(12).add("id", String.class);
		builder.length(32).add("type", String.class);
		builder.length(32).add("name", String.class);
		builder.length(16).add("rsystem", String.class);
		builder.setDefaultGeometry("the_geom");

		outputType = builder.buildFeatureType();

		featureBuilder = new SimpleFeatureBuilder(outputType);
	}

	private void writeOutputShapefile(List<SimpleFeature> features, String outputShapefile)
			throws IOException {
		File file = new File(outputShapefile);

		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

		Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", Boolean.TRUE);

		ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory
				.createNewDataStore(params);
		newDataStore.createSchema(outputType);

		/*
		 * You can comment out this line if you are using the createFeatureType
		 * method (at end of class file) rather than DataUtilities.createType
		 */
		//newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

		/*
		 * Write the features to the shapefile
		 */
		Transaction transaction = new DefaultTransaction("create");

		String typeName = newDataStore.getTypeNames()[0];
		SimpleFeatureSource featureSource = newDataStore
				.getFeatureSource(typeName);

		SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

		/*
		 * SimpleFeatureStore has a method to add features from a
		 * SimpleFeatureCollection object, so we use the ListFeatureCollection
		 * class to wrap our list of features.
		 */
		SimpleFeatureCollection collection = new ListFeatureCollection(
				outputType, features);
		featureStore.setTransaction(transaction);
		try {
			featureStore.addFeatures(collection);
			transaction.commit();
			System.out.println("Sucessfully wrote shapefile " + file.getAbsolutePath());
		} catch (Exception problem) {
			problem.printStackTrace();
			transaction.rollback();

		} finally {
			transaction.close();
		}
	}
}
