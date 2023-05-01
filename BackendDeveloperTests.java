import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BackendDeveloperTests {

    /*
     * Tests the constructor of the AirportPath class to make sure that the
     * constructor loads the data into the graph and lists correctly
     */
    @Test
    public void testConstructor() {
        AirportPath ap = new AirportPath(true);
        // Test Airports loaded correctly
        assertEquals("[alice, bob, charlie]", ap.getAirports().toString());
        // Test Paths loaded correctly
        assertEquals("[a --1-> b, a --5-> c]", ap.getPaths().toString());
    }

    /*
     * Tests that the shortest path is gotten from the Algorithm Engineer when
     * called from the backend
     */
    @Test
    public void testShortestPath() {
        AirportPath ap = new AirportPath(true);
        // Test that the correct path is gotten from the AE
        assertEquals("[a, c]", ap.getShortestPath("a", "c").toString());
        // Test another path
        assertEquals("[a, b]", ap.getShortestPath("a", "b").toString());
        // Test node that cannot be traversed to
        assertThrows(NoSuchElementException.class, () -> ap.getShortestPath("b", "c").toString());
    }

    /*
     * Tests that the weight of the shortest path cost is gotten from the
     * Algorithm Engineer when called from the backend
     */
    @Test
    public void testShortestDistance() {
        AirportPath ap = new AirportPath(true);
        // Test the correct shortest path is gotten from AE
        assertEquals(5.0, ap.getShortestDistance("a", "c"));
        // Test with another start and end
        assertEquals(1.0, ap.getShortestDistance("a", "b"));
    }

    /*
     * Tests that the shortest trio path is gotten from the Algorithm Engineer
     * when called from the backend
     */
    @Test
    public void testShortestTrioPath() {
        AirportPath ap = new AirportPath(true);
        // Test that getShortestTrioPath is called correctly
        assertThrows(NoSuchElementException.class, () -> ap.getShortestTrioPath("a", "b", "c"));

    }

    /* Test that the key value pairs in the hashmap for the abbriviations
    * is correct
    */
    @Test
    public void testGetCode() {
        AirportPath ap = new AirportPath(true);
        assertEquals("alice", ap.getFullAirportName("a"));
        // Test another pair
        assertEquals("bob", ap.getFullAirportName("b"));
        // Test the last pair
        assertEquals("charlie", ap.getFullAirportName("c"));
    }
}
