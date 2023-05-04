import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Airport, Path, and AirportDatabase classes created by the Data Wrangler.
 */
public class DataWranglerTests {

    /**
     * Tests the Airport class. Specifically ensures that the constructor and getters work as
     * intended.
     */
    @Test
    public void test1() {

        // Check for valid airport
        AirportInterface airport = new Airport("DTW", "Detroit Metropolitan Airport",
                42.2162F, 83.3554F);
        assertEquals("DTW", airport.getAirportCode());
        assertEquals("Detroit Metropolitan Airport", airport.getAirportName());
        assertEquals(42.2162F, airport.getLatitude());
        assertEquals(83.3554F, airport.getLongitude());
    }

    /**
     * Tests the Path class. Specifically ensures that the constructor and getters work
     * as intended.
     */
    @Test
    public void test2() {

        // Check for valid path
        PathInterface path = new Path("DEN", "DFW", 1032);
        assertEquals("DEN", path.getStart());
        assertEquals("DFW", path.getEnd());
        assertEquals(1032, path.getDistance());
    }

    /**
     * Tests the AirportDatabase class when a valid file is passed in.
     */
    @Test
    public void test3() {
        AirportDatabaseInterface database = new AirportDatabase();
        try {
            database.dotReader("data/flightData.dot");
        } catch (Exception e) {
            fail("Exception thrown");
        }
        // Check if size of lists are correct
        assertEquals(10, database.getAirportList().size());
        assertEquals(90, database.getPathList().size());

        // Check if airport data is correct
        assertEquals("DEN", database.getAirportList().get(0).getAirportCode());
        assertEquals("Denver International Airport", database.getAirportList().
                get(0).getAirportName());
        assertEquals(39.849312F, database.getAirportList().get(0).getLatitude());
        assertEquals(104.673828F, database.getAirportList().get(0).getLongitude());

        // Check if path data is correct
        assertEquals("DEN", database.getPathList().get(0).getStart());
        assertEquals("DFW", database.getPathList().get(0).getEnd());
        assertEquals(1032, database.getPathList().get(0).getDistance());
    }

    /**
     * Tests the AirportDatabase class when an invalid file is passed in.
     */
    @Test
    public void test4() {
        AirportDatabaseInterface database = new AirportDatabase();
        try {
            database.dotReader("data/blank.dot");
        } catch (Exception e) {
            fail("Exception thrown");
        }
        assertEquals(0, database.getAirportList().size());
        assertEquals(0, database.getPathList().size());
    }

    /**
     * Tests the AirportDatabase class when a file with errors is passed in.
     */
    @Test
    public void test5() {
        AirportDatabaseInterface database = new AirportDatabase();
        try {
            database.dotReader("data/fake.dot");
        } catch (Exception e) {
            fail("Exception thrown");
        }

        // Check if size of lists are correct
        assertEquals(1, database.getAirportList().size());
        assertEquals(1, database.getPathList().size());

        // Check if airport data is correct
        assertEquals("IAD", database.getAirportList().get(0).getAirportCode());
        assertEquals("Washington (Dulles) International Airport",
                database.getAirportList().get(0).getAirportName());
        assertEquals(38.9531F, database.getAirportList().get(0).getLatitude());
        assertEquals(77.4565F, database.getAirportList().get(0).getLongitude());

        // Check if path data is correct
        assertEquals("IAH", database.getPathList().get(0).getStart());
        assertEquals("DFW", database.getPathList().get(0).getEnd());
        assertEquals(361, database.getPathList().get(0).getDistance());
    }

    /**
     * This JUnit 5 test checks the functionality when integrated with the Backend Developer
     * classes.
     */
    @Test
    public void testIntegration1() {
        AirportPath airportPath = new AirportPath();

        // Check if there are 10 airports and 6 paths
        assertEquals(10, airportPath.getAirports().size());
        assertEquals(6, airportPath.getPaths().size());

        // Check if the airport data is correct
        String[] airportCodes = {"DEN", "DFW", "MCO", "IAD", "IAH", "SLC", "ORD", "SFO", "JFK",
                "DTW"};
        String[] airportNames = {"Denver International Airport",
                "Dallas/Fort Worth International Airport",
                "Orlando International Airport",
                "Washington (Dulles) International Airport",
                "George Bush Intercontinental Airport",
                "Salt Lake City International Airport",
                "O'Hare International Airport",
                "San Francisco International Airport",
                "John F Kennedy Airport",
                "Detroit Metropolitan Airport"};
        float[] latitudes = {39.849312F, 32.7079F, 28.4240F, 38.9531F, 29.9902F, 40.7608F, 41.9803F,
                37.6213F, 40.6446F, 42.2162F};
        float[] longitudes = {104.673828F, 96.9209F, 81.3099F, 77.4565F, 95.3368F, 111.8910F,
                87.9090F, 122.3790F, 73.7858F, 83.3554F};

        for (int i = 0; i < airportCodes.length; i++) {
            assertEquals(airportCodes[i], airportPath.getAirports().get(i).getAirportCode());
            assertEquals(airportNames[i], airportPath.getAirports().get(i).getAirportName());
            assertEquals(latitudes[i], airportPath.getAirports().get(i).getLatitude());
            assertEquals(longitudes[i], airportPath.getAirports().get(i).getLongitude());
        }

        // Check if the path data is correct
        String[] startAirports = {"DEN", "DFW", "DFW", "IAD", "ORD", "JFK"};
        String[] endAirports = {"DFW", "ORD", "JFK", "DEN", "SFO", "IAH"};
        int[] distances = {1032, 1290, 2239, 2337, 2971, 2280};

        for (int i = 0; i < startAirports.length; i++) {
            assertEquals(startAirports[i], airportPath.getPaths().get(i).getStart());
            assertEquals(endAirports[i], airportPath.getPaths().get(i).getEnd());
            assertEquals(distances[i], airportPath.getPaths().get(i).getDistance());
        }
    }

    /**
     * This JUnit 5 test checks the functionality when integrated with the Backend Developer
     * classes
     */
    @Test
    public void testIntegration2() {
        AirportPath airportPath = new AirportPath();

        // Check if the shortest path is correct
        assertEquals("[DEN, DFW, ORD, SFO]",
                airportPath.getShortestPath("DEN", "SFO").toString());

        // Check if the shortest path is correct
        assertEquals("[DEN, DFW, JFK, IAH]",
                airportPath.getShortestPath("DEN", "IAH").toString());

        // Check if exception is thrown when there is no path
        assertThrows(NoSuchElementException.class, () -> airportPath.getShortestPath("SFO", "DEN"));

        // Check if exception is thrown when there is no path
        assertThrows(NoSuchElementException.class, () -> airportPath.getShortestPath("SLC", "DTW"));

        // Check if exception is thrown if the airports do not exist
        assertThrows(NoSuchElementException.class, () -> airportPath.getShortestPath("LAX", "MKE"));
    }

    /**
     * This JUnit 5 test checks the functionality of the AirportPathGraph class written by the
     * Algorithm Engineer.
     */
    @Test
    public void testCodeReviewOfAlgorithmEngineer1() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("DEN");
        graph.insertNode("DFW");
        graph.insertNode("MCO");
        graph.insertNode("IAD");
        graph.insertNode("IAH");
        graph.insertEdge("DEN", "DFW", 1.0);
        graph.insertEdge("DFW", "DEN", 2.0);
        graph.insertEdge("DFW", "MCO", 1.0);
        graph.insertEdge("MCO", "DEN", 2.0);
        graph.insertEdge("MCO", "IAD", 1.0);
        graph.insertEdge("IAD", "MCO", 2.0);
        graph.insertEdge("IAD", "IAH", 1.0);
        graph.insertEdge("IAH", "IAD", 2.0);
        graph.insertEdge("DEN", "IAD", 7.0);

        // Check if the shortest path of the graph is correct
        assertEquals("[DEN, DFW, MCO, IAD, IAH]",
                graph.shortestTrioPathData("DEN", "MCO", "IAH").toString());
    }

    /**
     * This JUnit 5 test checks the functionality of the AirportPathGraph class written by the
     * Algorithm Engineer.
     */
    @Test
    public void testCodeReviewOfAlgorithmEngineer2() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("DEN");
        graph.insertNode("DFW");
        graph.insertNode("MCO");
        graph.insertNode("IAD");
        graph.insertNode("IAH");
        graph.insertEdge("DEN", "DFW", 1.0);
        graph.insertEdge("MCO", "IAH", 2.0);

        // If the desired nodes of the graph are not connected, NoSuchElementException is thrown
        assertThrows(NoSuchElementException.class, () ->
                graph.shortestTrioPathData("DEN", "MCO", "IAH"));
    }
}