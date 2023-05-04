import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import edu.wisc.cs.cs400.JavaFXTester;

public class BackendDeveloperTests extends JavaFXTester{
    
    public BackendDeveloperTests() {
        super(AirportPathFinderFrontendFD.class);
        // AirportPath ap = new AirportPath();
        // AirportPathFinderFrontendFD.setBackend(ap);
    }
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

    /* Test that the key value pairs in the hashmap for the abbreviations 
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

    /*
     * Integration test to check that airports are being loaded from the Data
     * Wranglers correctly.
     */
    @Test
    public void testloadDataIntegration() {
        AirportPath ap = new AirportPath();
        assertEquals(10, ap.getAirports().size());
        assertEquals(6, ap.getPaths().size());
        // Array of expected airports
        String[] airports = new String[] {"DEN", "DFW", "MCO", "IAD", "IAH", "SLC", "ORD", "SFO", "JFK", "DTW"};
        for(int i = 0; i < ap.getAirports().size(); i++) {
            assertEquals(airports[i], ap.getAirports().get(i).getAirportCode());
        }
    }

    /*
     * Integration test to check that the shortest path is gotten from the
     * Algorithm Engineer between two Airports.
     */
    @Test
    public void testShortestPathIntegration() {
        AirportPath ap = new AirportPath();
        // Test existing Path
        assertEquals("[DEN, DFW, ORD, SFO]", ap.getShortestPath("DEN", "SFO").toString());
        // Test non-existent Path
        assertThrows(NoSuchElementException.class, () -> ap.getShortestPath("SFO", "JFK"));
    }


    /**
     * Code review test to check that the user is not able to choose the same
     * point twice when choosing endpoints for the traversal.
     */
    @Test
    public void testPickSameCodeReviewOfFrontendDeveloper() {
        Text userRouteText = lookup("#userRouteText").query();
        clickOn("#choose2");
        clickOn("#DEN");
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        // Select the same airport again
        clickOn("#DEN");
        assertEquals("", userRouteText.getText());
        clickOn("#back");
    }

    /**
     * Test that the correct Route is printed when a path between two airports
     * exists and prints no route found when no path exists.
     */
    @Test
    public void testChoose2ReviewOfFrontendDeveloper() {
        // Test that shortest path is gotten when one exists
        Text userRouteText = lookup("#userRouteText").query();
        clickOn("#choose2");
        clickOn("#DEN");
        // Move to the confirm button
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        clickOn("#SFO");
        // Do not needto move up again because confirm button already highlighted
        type(KeyCode.ENTER);
        assertEquals("Route: DEN->DFW->ORD->SFO\nTotal Distance: 5293 km", userRouteText.getText());
        // Test that No route found is printed when no path exists
        clickOn("#choose2");
        clickOn("#SFO");
        // Move to the confirm button
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        clickOn("#DEN");
        // Do not need to move up again because confirm button already highlighted
        type(KeyCode.ENTER);
        assertEquals("No route found.", userRouteText.getText());
    }
}
