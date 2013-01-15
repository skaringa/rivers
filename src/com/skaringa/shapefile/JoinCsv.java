package com.skaringa.shapefile;

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
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class JoinCsv {

	private SimpleFeatureBuilder featureBuilder;
	private SimpleFeatureSource featureSource;
	private SimpleFeatureType outputType;
	private Map<Long, String> id2Basin;

	/**
	 * @param args
	 * @throws IOException
	 * @throws SchemaException
	 */
	public static void main(String[] args) throws IOException, SchemaException {
		JoinCsv joinCsv = new JoinCsv();

		joinCsv.openInputShapefile();
		joinCsv.readInputCsv();
		joinCsv.createFeatureBuilder();
		List<SimpleFeature> featuresOut = joinCsv.process();
		joinCsv.writeOutputShapefile(featuresOut);
	}

	private List<SimpleFeature> process() throws IOException {
		SimpleFeatureCollection featuresIn = featureSource.getFeatures();
		List<SimpleFeature> featuresOut = new ArrayList<SimpleFeature>();

		FeatureIterator<SimpleFeature> iterator = featuresIn.features();
		try {
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				featureBuilder.add(feature.getAttribute("the_geom"));
				Long id = (Long) feature.getAttribute("id");
				featureBuilder.add(id);
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

	private void openInputShapefile() throws IOException {
		File file = new File("../../river/output/waterways.shp");
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		featureSource = store.getFeatureSource();

		SimpleFeatureType schema = featureSource.getSchema();
		System.out.println(schema.getTypeName() + ": "
				+ DataUtilities.encodeType(schema));
	}
	
	private void readInputCsv() throws IOException {
		File file = new File("../../river/output/rsystems.csv");
		BufferedReader reader = new BufferedReader(new FileReader(file));
		id2Basin = new HashMap<Long, String>();
        try {
        	/* First line of the data file is the header */
            String line = reader.readLine();
            System.out.println("Csv Header: " + line);

            for (line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.trim().length() > 0) { // skip blank lines
                    String tokens[] = line.split("\\,");
                    Long id = Long.valueOf(tokens[0]);
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
		outputType = DataUtilities
				.createType(
						"waterways",
						"the_geom:MultiLineString,id:java.lang.Long,type:String,name:String,rsystem:String");

		featureBuilder = new SimpleFeatureBuilder(outputType);
	}

	private void writeOutputShapefile(List<SimpleFeature> features)
			throws IOException {
		File file = new File("../../river/output/waterways-r.shp");

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
		newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

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
