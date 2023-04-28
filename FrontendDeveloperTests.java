import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.text.Text;

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
      assertEquals("Please select a starting airport", prompt.getText());
      
      clickOn("#back");
      
      // back to MAIN
      assertEquals("Please choose an action", prompt.getText());
      
      clickOn("#choose3");
      assertEquals("Please select a starting airport", prompt.getText());
      
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
      
      clickOn("#LAX");
      clickOn("#confirm");
      clickOn("#MKE");
      clickOn("#confirm");
         
      assertEquals("LAX->MKE\nTotal: 10 miles", userRouteText.getText()); 
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
      assertEquals("Please select a starting airport", prompt.getText());
      
      clickOn("#LAX");
      clickOn("#confirm");
      assertEquals("Please select an intermediate airport", prompt.getText());
      
      clickOn("#MKE");
      clickOn("#confirm");
      assertEquals("Please select an ending airport", prompt.getText());
      
      clickOn("#EWR");
      clickOn("#confirm");
      
      assertEquals("Please choose an action", prompt.getText());   
      assertEquals("LAX->MKE->ORF->EWR\nTotal: 17 miles", userRouteText.getText());
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
      assertEquals("Please select a starting airport", prompt.getText());
      
      clickOn("#LAX");
      clickOn("#confirm");
      assertEquals("Please select an intermediate airport", prompt.getText());
      
      clickOn("#MKE");
      clickOn("#confirm");
      assertEquals("Please select an ending airport", prompt.getText());
      
      clickOn("#back");
      
      // then try to click on another airport -- should do nothing
      clickOn("#HLN");
      
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
      
      clickOn("#MKE");
      clickOn("#confirm");
      clickOn("#HLN");
      clickOn("#confirm");
         
      assertEquals("MKE->HLN\nTotal: 3 miles", userRouteText.getText()); 
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
      clickOn("#MKE");
      clickOn("#confirm");
      clickOn("#HLN");
      clickOn("#confirm");
      assertEquals("MKE->HLN\nTotal: 3 miles", userRouteText.getText());
      
      // now do a new route
      clickOn("#choose3");
      clickOn("#LAX");
      clickOn("#confirm");
      clickOn("#DFW");
      clickOn("#confirm");
      clickOn("#ORF");
      clickOn("#confirm");
      assertEquals("LAX->DFW->ORF\nTotal: 12 miles", userRouteText.getText());
      clickOn("#back");
    }

}
