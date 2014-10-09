package anssgeocoder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.GeoTools;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.geometry.BoundingBox;

/**
 *
 * @author Khepry Quixote <khepry.quixote@gmail.com>
 */
public class AnssGeoCoder {
    
    private static String csvSource = "/home/fracking/Data/ANSS/catsearch.17110.txt";
    private static String csvTarget = "/home/fracking/Data/ANSS/geocoded.17110.txt";
    
    private static String statesShapePath = "/home/fracking/shapefiles/statesp020/statesp020.shp";
    private static String countyShapePath = "/home/fracking/shapefiles/countyp020/countyp020.shp";
    private static String countryShapePath = "/home/fracking/shapefiles/world_borders/world_borders.shp";
    
    private static int ignoreLines = 1;
    
    private static Earthquakes earthquakes = new Earthquakes();
    private static Earthquakes countryQuakes = new Earthquakes();
    private static Earthquakes stateQuakes = new Earthquakes();
    
    private static String countryNameFilter = "United States";
    private static String stateNameFilter = "";
    private static String stateCodeFilter = "";
    
    private static GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(GeoTools.getDefaultHints());
    
    private static Envelope lower48States = new Envelope(-124.848974, -66.885444, 24.396308, 49.384358);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (String arg : args) {
                if (arg.startsWith("-csvSource")) {
                    csvSource = arg.substring(10);
                    continue;
                }
                if (arg.startsWith("-csvTarget")) {
                    csvTarget = arg.substring(10);
                    continue;
                }
                if (arg.startsWith("-ignoreLines")) {
                    ignoreLines = Integer.parseInt(arg.substring(12));
                    continue;
                }
                if (arg.startsWith("-countryNameFilter")) {
                    countryNameFilter = arg.substring(18);
                    continue;
                }
                if (arg.startsWith("-stateNameFilter")) {
                    stateNameFilter = arg.substring(16);
                    continue;
                }
                if (arg.startsWith("-stateCodeFilter")) {
                    stateCodeFilter = arg.substring(16);
                    continue;
                }
                if (arg.startsWith("-statesShapePath")) {
                    statesShapePath = arg.substring(16);
                    continue;
                }
                if (arg.startsWith("-countyShapePath")) {
                    countyShapePath = arg.substring(16);
                    continue;
                }
                if (arg.startsWith("-countryShapePath")) {
                    countryShapePath = arg.substring(17);
                    continue;
                }
            }
            
            loadEarthquakes(csvSource, ignoreLines, lower48States);

            assignCountryNameToEarthquakes();
            
            for (Earthquake earthquake : earthquakes) {
                if (countryNameFilter.equals("") || earthquake.getCountryName().equalsIgnoreCase(countryNameFilter)) {
                    countryQuakes.add(earthquake);
                }
             }
            earthquakes.clear();
            
            assignStateNameToEarthquakes(countryQuakes);
            
            for (Earthquake earthquake : countryQuakes) {
                if (stateNameFilter.equals("") || earthquake.getStateName().equalsIgnoreCase(stateNameFilter)) {
                    stateQuakes.add(earthquake);
                }
            }
            countryQuakes.clear();
            
            assignCountyAndStateToEarthquakes(stateQuakes);
            
            stateQuakes.toCsvFile(new File(csvTarget), ",", "\n");
        } catch (MalformedURLException ex) {
            Logger.getLogger(AnssGeoCoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CQLException | NumberFormatException ex) {
            Logger.getLogger(AnssGeoCoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnssGeoCoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnssGeoCoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void loadEarthquakes(String sourceCSV, int skipLines, Envelope envelope) throws FileNotFoundException, IOException {

        long recordCount = 0L;
        long subsetCount = 0L;
        
        earthquakes.clear();
        
        double lat;
        double lng;

        String line;
        String[] pieces;
        File sourceFile = new File(sourceCSV);
        if (sourceFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
                while ((line = br.readLine()) != null) {
                    recordCount++;
                    if (recordCount > skipLines) {
                        pieces = line.split(",");
                        if (pieces.length == 12) {
                            lat = Double.parseDouble(pieces[1]);
                            lng = Double.parseDouble(pieces[2]);
                            if (envelope.contains(lng, lat)) {
                                Earthquake earthquake = new Earthquake();
                                earthquake.setDateTime(pieces[0]);
                                earthquake.setLatitude(pieces[1]);
                                earthquake.setLongitude(pieces[2]);
                                earthquake.setDepth(pieces[3]);
                                earthquake.setMagnitude(pieces[4]);
                                earthquake.setMagType(pieces[5]);
                                earthquake.setNbrStations(pieces[6]);
                                earthquake.setGap(pieces[7]);
                                earthquake.setDistance(pieces[8]);
                                earthquake.setRms(pieces[9]);
                                earthquake.setSource(pieces[10]);
                                earthquake.setEventId(pieces[11]);
                                earthquakes.add(earthquake);
                                subsetCount++;
                            }
                        }
                    }
                    if (recordCount % 1000 == 0) {
                        System.out.println(subsetCount + " records of " + recordCount + " written.");
                    }
                }
                System.out.println(subsetCount + " records of " + recordCount + " written.");
            }
        } else {
            System.err.println("csvSource file does not exist: " + sourceCSV);
        }
    }
    
    private static void assignCountryNameToEarthquakes() throws MalformedURLException, IOException, CQLException {
        int featureCount = 0;
        long recordCount = 0L;
        File shapeFile = new File(countryShapePath);
        if (shapeFile.exists()) {
            SimpleFeature simpleFeature;
            Geometry geometry;
            Point point;
            ShapefileDataStore dataStore = new ShapefileDataStore(shapeFile.toURI().toURL());
            SimpleFeatureSource featureSource = dataStore.getFeatureSource();
            SimpleFeatureCollection featureCollection;
            Filter filter = CQL.toFilter("CNTRY_NAME='" + countryNameFilter + "'");
            if (!countryNameFilter.equals("")) {
                featureCollection = featureSource.getFeatures(filter);
            }
            else {
                featureCollection = featureSource.getFeatures();
            }
            int featureTotal = featureCollection.size();
            SimpleFeatureIterator featureIterator = featureCollection.features();
            try {
                while (featureIterator.hasNext()) {
                    featureCount++;
                    simpleFeature = featureIterator.next();
                    geometry = (Geometry)simpleFeature.getDefaultGeometry();
                    for (Earthquake earthquake : earthquakes) {
                        point = geometryFactory.createPoint(new Coordinate(earthquake.getDblLongitude(), earthquake.getDblLatitude())); 
                        if (geometry.contains(point)) {
                            recordCount++;
                            earthquake.setCountryName(simpleFeature.getProperty("CNTRY_NAME").getValue().toString());
                            //System.out.println(earthquake.getUniqueId() + "," + earthquake.getCountryName());
                            if (recordCount % 1000 == 0) {
                                System.out.println("CountryName: " + recordCount);
                            }
                        }
                    }
                    if (featureCount % 10 == 0) {
                        System.out.println("Feature: " + featureCount + " of " + featureTotal);
                    }
                }
                System.out.println("Feature: " + featureCount + " of " + featureTotal);
            } finally {
                featureIterator.close();
            }
        }
    }
    
    private static void assignCountyAndStateToEarthquakes(Earthquakes quakes) throws MalformedURLException, IOException, CQLException {
        long recordCount = 0L;
        
        File shapeFile = new File(countyShapePath);
        if (shapeFile.exists()) {
            SimpleFeature simpleFeature;
            Geometry geometry;
            Point point;
            ShapefileDataStore dataStore = new ShapefileDataStore(shapeFile.toURI().toURL());
            SimpleFeatureSource featureSource = dataStore.getFeatureSource();
            SimpleFeatureCollection featureCollection;
            Filter filter = CQL.toFilter("STATE='" + stateCodeFilter + "'");
            if (!stateCodeFilter.equals("")) {
                featureCollection = featureSource.getFeatures(filter);
            }
            else {
                featureCollection = featureSource.getFeatures();
            }
            SimpleFeatureIterator featureIterator = featureCollection.features();
            try {
                while (featureIterator.hasNext()) {
                    simpleFeature = featureIterator.next();
                    geometry = (Geometry)simpleFeature.getDefaultGeometry();
                    for (Earthquake earthquake : quakes) {
                        point = geometryFactory.createPoint(new Coordinate(earthquake.getDblLongitude(), earthquake.getDblLatitude())); 
                        if (geometry.contains(point)) {
                            recordCount++;
                            earthquake.setStateCode(simpleFeature.getProperty("STATE").getValue().toString());
                            earthquake.setCountyName(simpleFeature.getProperty("COUNTY").getValue().toString());
                            earthquake.setFips(simpleFeature.getProperty("FIPS").getValue().toString());
                            //System.out.println(earthquake.getUniqueId() + "," + earthquake.getStateCode() + "," + earthquake.getCountyName() + "," + earthquake.getFips());
                            if (recordCount % 1000 == 0) {
                                System.out.println("CountyName: " + recordCount);
                            }
                        }
                    }
                }
            } finally {
                featureIterator.close();
            }
        }
    }
    
    private static void assignStateNameToEarthquakes(Earthquakes quakes) throws MalformedURLException, IOException, CQLException {
        long recordCount = 0L;
        File shapeFile = new File(statesShapePath);
        if (shapeFile.exists()) {
            SimpleFeature simpleFeature;
            Geometry geometry;
            Point point;
            ShapefileDataStore dataStore = new ShapefileDataStore(shapeFile.toURI().toURL());
            SimpleFeatureSource featureSource = dataStore.getFeatureSource();
            SimpleFeatureCollection featureCollection;
            Filter filter = CQL.toFilter("STATE='" + stateNameFilter + "'");
            if (!stateNameFilter.equals("")) {
                featureCollection = featureSource.getFeatures(filter);
            }
            else {
                featureCollection = featureSource.getFeatures();
            }
            SimpleFeatureIterator featureIterator = featureCollection.features();
            try {
                while (featureIterator.hasNext()) {
                    simpleFeature = featureIterator.next();
                    geometry = (Geometry)simpleFeature.getDefaultGeometry();
                    for (Earthquake earthquake : quakes) {
                        point = geometryFactory.createPoint(new Coordinate(earthquake.getDblLongitude(), earthquake.getDblLatitude())); 
                        if (geometry.contains(point)) {
                            recordCount++;
                            earthquake.setStateName(simpleFeature.getProperty("STATE").getValue().toString());
                            //System.out.println(earthquake.getUniqueId() + "," + earthquake.getStateName());
                            if (recordCount % 1000 == 0) {
                                System.out.println("StateName: " + recordCount);
                            }
                        }
                    }
                }
            } finally {
                featureIterator.close();
            }
        }
    }
}
