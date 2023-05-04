import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import edu.wisc.cs.cs400.JavaFXTester;

/**
 * Tester class to test out frontend/UI of the Airport Path
 * Finder app
 * 
 * @author Cameron
 */
public class FrontendDeveloperTests extends JavaFXTester {

    public FrontendDeveloperTests() {
        super(AirportPathFinderFrontendFD.class);
    }
    
    

    /**
     * Checks app state transitions: from MAIN to different modes, and back to MAIN
     */
    @Test
    public void test1() {
      
      Text prompt = lookup("#prompt").query();
        // make sure it starts in MAIN state
      assertEquals("Please choose an action", prompt.getText());
      
      clickOn("#choose2");
      
      // make sure it switched states
      assertEquals("Please select an airport", prompt.getText());
      
      clickOn("#back");
      
      // back to MAIN
      assertEquals("Please choose an action", prompt.getText());
      
      clickOn("#choose3");
      assertEquals("Please select an airport", prompt.getText());
      
      clickOn("#back");
      
      // back to MAIN
      assertEquals("Please choose an action", prompt.getText());
    }

    
    /**
     * Tests a user-created route between two adjacent airports (using the select 2 airports method)
     */
    @Test
    public void test2() {      
      Text userRouteText = lookup("#userRouteText").query();

      clickOn("#choose2");
      
      clickOn("#ORD");
      clickOn("#confirm");
      clickOn("#SFO");
      clickOn("#confirm");
         
      assertEquals("Route: ORD->SFO\nTotal Distance: 2971 km", userRouteText.getText()); 
      clickOn("#back");
    }
    
    
    /**
     * Tests a user-created route between four nodes (using the select 3 airports option),
     * making sure the prompt for the user is correct
     */
    @Test
    public void test3() {
      Text userRouteText = lookup("#userRouteText").query();
      Text prompt = lookup("#prompt").query();

      clickOn("#choose3");
      assertEquals("Please select an airport", prompt.getText());
      
      clickOn("#DEN");
      clickOn("#confirm");
      assertEquals("Please select another airport", prompt.getText());
      
      clickOn("#DFW");
      clickOn("#confirm");
      assertEquals("Please select a final airport", prompt.getText());
      
      clickOn("#JFK");
      clickOn("#confirm");
      
      assertEquals("Please choose an action", prompt.getText());   
      assertEquals("Route: DEN->DFW->JFK\nTotal Distance: 3271 km", userRouteText.getText());
      clickOn("#back");
    }
    
    /**
     * tests case when the user starts clicking through airports during the select
     * route process but returns to the main menu instead of creating the route
     */
    @Test
    public void test4() {
      Text userRouteText = lookup("#userRouteText").query();
      Text prompt = lookup("#prompt").query();

      clickOn("#choose3");
      assertEquals("Please select an airport", prompt.getText());
      
      clickOn("#ORD");
      clickOn("#confirm");
      assertEquals("Please select another airport", prompt.getText());
      
      clickOn("#MCO");
      clickOn("#confirm");
      assertEquals("Please select a final airport", prompt.getText());
      
      clickOn("#back");
      
      // then try to click on another airport -- should do nothing
      clickOn("#SLC");
      
      assertEquals("Please choose an action", prompt.getText());   
      assertEquals("", userRouteText.getText());
      clickOn("#back");
    }
    
    /**
     * tests a reset of the map after a route has already been drawn to it,
     * makes sure the map resets
     */
    @Test
    public void test5() {
      Text userRouteText = lookup("#userRouteText").query();

      clickOn("#choose2");
      
      clickOn("#DEN");
      clickOn("#confirm");
      clickOn("#DFW");
      clickOn("#confirm");
         
      assertEquals("Route: DEN->DFW\nTotal Distance: 1032 km", userRouteText.getText()); 
      // now clear map
      clickOn("#back");
      assertEquals("", userRouteText.getText());
      
    }
    
    
    /**
     * tests drawing a new route to the map after the user already went through the process,
     * make sure the other route clears and the new route correctly displays
     */
    @Test
    public void test6() {
      Text userRouteText = lookup("#userRouteText").query();
      clickOn("#choose2");
      
      clickOn("#DEN");
      clickOn("#confirm");
      clickOn("#DFW");
      clickOn("#confirm");
         
      assertEquals("Route: DEN->DFW\nTotal Distance: 1032 km", userRouteText.getText()); 
      
      // now do a new route
      clickOn("#choose3");
      clickOn("#SFO");
      clickOn("#confirm");
      clickOn("#DFW");
      clickOn("#confirm");
      clickOn("#ORD");
      clickOn("#confirm");
      assertEquals("Route: DFW->ORD->SFO\nTotal Distance: 4261 km", userRouteText.getText());
      clickOn("#back");
    }
    
    /**
     * Tests case if Djikstra's algorithm fails and throws NoSuchElementException;
     * Backend should propagate the exception
     */
    @Test
    public void test1CodeReviewOfBackendDeveloper() {
      AirportPath backend = new AirportPath();
      
      assertThrows(NoSuchElementException.class ,() -> backend.getShortestPath("ORD", "DFW") );
      
    }
    
    
    @Test
    public void test2CodeReviewOfBackendDeveloper() {
      AirportPath backend = new AirportPath();
      
    }
    
    
    /**
     * Tests case where there is no directed route from selected starting/ending points
     */
    @Test
    public void test1Integration() {
      Text userRouteText = lookup("#userRouteText").query();
      clickOn("#choose2");
      
      clickOn("#MCO");
      clickOn("#confirm");
      clickOn("#IAH");
      clickOn("#confirm");
         
      assertEquals("No route found.", userRouteText.getText()); 
      
      clickOn("#back");
    }
    
    
    /**
     * 
     */
    @Test
    public void test2Integration() {

    }
    
    

}
