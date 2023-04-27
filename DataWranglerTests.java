import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Airport, Path, and AirportDatabase classes created by the Data Wrangler.
 *
 */
public class DataWranglerTests {

    /**
     * Tests the Airport class. Specifically ensures that the constructor and getters work as intended.
     */
    @Test
    public void test1() {

        // Check for valid airport
        AirportInterface airport = new AirportDW("DTW", "Detroit Metropolitan Airport",
                42.2162F, 83.3554F);
        assertEquals("DTW", airport.getAirportCode());
        assertEquals("Detroit Metropolitan Airport", airport.getAirportName());
        assertEquals(42.2162F, airport.getLatitude());
        assertEquals(83.3554F, airport.getLongitude());
    }

    /**
     * Tests the Path class. Specifically ensures that the constructor and getters work as intended.
     */
    @Test
    public void test2() {

        // Check for valid path
        PathInterface path = new PathDW("DEN", "DFW", 1032);
        assertEquals("DEN", path.getStart());
        assertEquals("DFW", path.getEnd());
        assertEquals(1032, path.getDistance());
    }

    /**
     * Tests the AirportDatabase class when a valid file is passed in.
     */
    @Test
    public void test3() {
        AirportDatabaseInterface database = new AirportDatabaseDW();
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
        AirportDatabaseInterface database = new AirportDatabaseDW();
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
        AirportDatabaseInterface database = new AirportDatabaseDW();
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
        assertEquals("Washington (Dulles) International Airport", database.getAirportList().
                get(0).getAirportName());
        assertEquals(38.9531F, database.getAirportList().get(0).getLatitude());
        assertEquals(77.4565F, database.getAirportList().get(0).getLongitude());

        // Check if path data is correct
        assertEquals("IAH", database.getPathList().get(0).getStart());
        assertEquals("DFW", database.getPathList().get(0).getEnd());
        assertEquals(361, database.getPathList().get(0).getDistance());
    }
}
