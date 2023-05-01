import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * This defines an application that allows the user to
 * find the shortest route between a start and an end airport,
 * with additional ability to add another airport that must be included
 * on the generated route.
 * @author Cameron
 * US map source: https://www.burningcompass.com/countries/united-states/blank-map-of-us.html
 */
public class AirportPathFinderFrontendFD  extends Application 
                                        implements AirportPathFinderFrontendInterface {
  
    private AirportPathInterface backend;             // deals with generating routes for user
    private Scene scene;                    
    private UserInterface userInterface;                // graphical interface root
    private AppState state;      // either in mode-select or route-generating mode
    private int numAirports;    // number of airports the user has currently selected
    private int airportsInRoute;    // number of airports the user needs to choose for their route
    private ArrayList<String> userAirportList; // ordinal list of airport codes created for user
    private HashMap<String, AirportCircle> airportLookup; // hashtable to convert codes to airports
    private HashMap<String,EdgeArrow> edges;    // hashtable to convert strings to edges 
    
    
	public AirportPathFinderFrontendFD() {
       this.backend = new AirportPath();
       userInterface = new UserInterface();

       userAirportList = new ArrayList<String>();
       airportLookup = new HashMap<String, AirportCircle>();
       edges = new HashMap<String,EdgeArrow>();
       
       // set to defaults
       state = AppState.MAIN;
       numAirports = 0;
       airportsInRoute = 0;
    }
    
	
	
	// calls drawMap(). Lets user do the following:
			// 1) UI lets the user choose to find a route,
			// 2) Three airport mode
			// 3) quit program
			// 4) reset map (if path(s)/ anything else) have been drawn 
	
  /**
   * Entry point for the application; sets up the map and lets it run
   */
	@Override
	public void start(final Stage stage) {
	  // set up call-backs for buttons
	  userInterface.getBackButton().setOnAction(backToMenu -> backHelper() );      
      userInterface.getConfirmButton().setOnAction(btclick -> confirmHelper());
      userInterface.getQuitButton().setOnAction(quit -> Platform.exit());

      // mode select buttons ([0]- user selects 2 airports, [1]- user selects 3 airports)
      userInterface.getModeSelects().get(0).setOnAction(route2 -> createRoute() );
      userInterface.getModeSelects().get(1).setOnAction( route3 -> createRouteThreeAirports() );  
	  
      // draw airports and edges to map
	  drawMap();
	  
	  
	   // add IDs to nodes for testing
	  userInterface.getBackButton().setId("back");     
      userInterface.getConfirmButton().setId("confirm");
      userInterface.getQuitButton().setId("quit");
      userInterface.getModeSelects().get(0).setId("choose2");
      userInterface.getModeSelects().get(1).setId("choose3");
      userInterface.getPromptText().setId("prompt");
      userInterface.getUserRouteText().setId("userRouteText");
	      
	  // create scene
	  scene = new Scene(userInterface);
	  stage.setScene(scene);
	  stage.setTitle("AirportPathFinder");
	  stage.setResizable(false);
	  stage.show();
	} 
	
	
	/**
	 * gets airport and edge data from backend and draws them to the map
	 */
	@Override
    public void drawMap() {     
      // add airports to map
      List<AirportInterface> airportList = backend.getAirports();
      List<PathInterface> pathList = backend.getPaths();
      
      for (AirportInterface ap : airportList) {
        airportLookup.put(ap.getAirportCode(), new AirportCircle(ap));
      }
      
      // add edges to map
      for (PathInterface path : pathList) {
        edges.put(path.getStart()+path.getEnd(), new EdgeArrow(path));  
      }
      userInterface.getAirportGroup().getChildren().addAll(edges.values()); 
      userInterface.getAirportGroup().getChildren().addAll(airportLookup.values());  
	}

	
	/**
	 *  used to allow user to create a route from only using start and end airports
	 */
     
    @Override
    public void createRoute() {
      airportsInRoute = 2;
      updateState(AppState.AIRPORT_SELECTION);
    }
    
    
    /**
     * similar to createRoute(), but lets the user
     * select three airports instead  
     */
    @Override                
    public void createRouteThreeAirports() {
      airportsInRoute = 3;
      updateState(AppState.AIRPORT_SELECTION);
    }
    
    /**
     * resets the map to default state, clearing any user input data
     */
    @Override          
    public void resetMap() {
      // --------------------------------clear nodes-----------------------------------------------
      for (AirportCircle airport : airportLookup.values()) {
        airport.setHighlight(false);
        airport.setSelected(false); 
      }
      // ------------------------------clear edges------------------------------------------------
      for (EdgeArrow e : edges.values()) {
        e.setOnRoute(false);
      }
      // ------------------------clear internal data----------------------------------------------
      userAirportList.clear();
      numAirports=0;
      // --------------------------remove route information--------------------------------------
      userInterface.getUserRouteText().setText("");
    }
    
    /**
     * takes in user’s input when user clicks on airport on the map 
     * (gets the airport code e.g. "LAX" )
     */
//    @Override
//    public String getAirport() {
//      numAirports++;
//      return null;
//    }
    
    // ^ note that we ended up not needing this method
    
    /**
     * Callback helper method for the airport nodes when clicked on by
     * the user
     * @param airport - airport the user clicked on
     */
    private void getAirportHelper(AirportCircle airport) {
      
     if (numAirports == userAirportList.size()-1) {
       // remove last highlighted airport clicked on by user, if any
       airportLookup.get(userAirportList.get(numAirports)).setHighlight(false); 
       userAirportList.set(numAirports, airport.airport.getAirportCode());
     } else userAirportList.add(airport.airport.getAirportCode());
     
     // highlight new option
      airport.setHighlight(true);
    
    }
    
    /**
     * Callback helper method for when the user clicks on the 
     * "Confirm" button
     */
    private void confirmHelper() {
     
      if (numAirports == userAirportList.size() - 1) {
        airportLookup.get(userAirportList.get(numAirports)).setSelected(true); // user has confirmed
        numAirports++;                                                             // an airport
        
        
        if (numAirports == airportsInRoute) { // user is done selecting airports
          // call Djistkra's method using the list of strings
          // print result to map
          if (airportsInRoute == 2) {
            drawPath(backend.getShortestPath(userAirportList.get(0), userAirportList.get(1)));
          } else if (airportsInRoute == 3) {
            drawPath(backend.getShortestTrioPath(
                userAirportList.get(0), userAirportList.get(1), userAirportList.get(2)));
          }
          
          
          // go back to main state
          updateState(AppState.MAIN); 
         } else if (numAirports==airportsInRoute-1) {
           userInterface.getPromptText().setText("Please select an ending airport");
         } else {
           userInterface.getPromptText().setText("Please select an intermediate airport");
         }   
      } // else nothing
    }
    
    /**
     * takes a path generated by the BD and draws it to the map,
     * along with additional information (e.g. total distance covered)
     * (path is list of airport codes (e.g. "LAX") )
     * @param path - list of nodes in the route generated for the user (in order from
     * start to end)
     */
    @Override
    public void drawPath(List<String> path) {
      int totalWeight = 0;
      String userRouteString = "";
      
      // ----------------------------highlight nodes---------------------------------------
      for (int i=0; i<path.size(); i++) {
        airportLookup.get(path.get(i)).setOnRoute(i+1);
        userRouteString += path.get(i);
        if (i != path.size()-1) userRouteString+= "->";
      }
           
      // ---------------------------highlight edges----------------------------------------------
      EdgeArrow current;
      for (int i=0; i<path.size()-1; i++) {
        current = edges.get(path.get(i)+path.get(i+1));
        current.setOnRoute(true);
        totalWeight += current.getWeight();
      }
      
      // ------------------------display additional info-------------------------------------------
      userInterface.getUserRouteText().setText(userRouteString+"\nTotal: "+totalWeight+ " miles");
      
    }
    
    /**
     * manages the function of the backButton depending on state
     * (either will return to main or will solely reset the map)
     */
    private void backHelper() {
      if (state == AppState.AIRPORT_SELECTION) { 
        resetMap();
        updateState(AppState.MAIN);      
        userInterface.getBackButton().setDisable(true);
        
      } else if (state == AppState.MAIN){
         resetMap();
         userInterface.getBackButton().setDisable(true);
      }
    }
    
    
    /**
     * manages state transitions in the app; when in MAIN,
     * the user can only select modes or clear the map;
     * when in AIRPORT_SELECTION, the user can choose their route or 
     * return to main
     * @param newState - the new state to set the app into
     */
    private void updateState(AppState newState) {
      state = newState;
    
      if (state == AppState.AIRPORT_SELECTION) {
        // clear currently selected airports/route
        resetMap();
        // unlock airport circles
        userInterface.getAirportGroup().setDisable(false);
        // lock the mode select buttons
        for (Button b : userInterface.getModeSelects()) {
          b.setDisable(true);
        }
        // enable back button
        userInterface.getBackButton().setDisable(false);
        userInterface.getBackButton().setText("Back to menu");
        // change prompt text
        if (numAirports==0) {
            userInterface.getPromptText().setText("Please select a starting airport");    
        }
         
      }  
      else if (state == AppState.MAIN) {
        // lock the airport circles
        userInterface.getAirportGroup().setDisable(true);
        // unlock mode select buttons
        for (Button b : userInterface.getModeSelects()) {
          b.setDisable(false);
        }
              
        // change prompt text, back button text
        userInterface.getPromptText().setText("Please choose an action");
        userInterface.getBackButton().setText("Reset map");
      }
        
    }
    
    

	
    /**
     * helper method to convert longitude to a x coordinate
     * @param longitude
     * @return x coordinate
     */
	private double longToX(double longitude) {
      double toRet = 0; // uses linear fit (works with the Mercator projection)
      double longi1 = -109.059;
      double pix1 = 270;
      double longi2 = -84.815;
      double pix2 = 684;
      
      double slope = (pix2 - pix1)/ (longi2 - longi1);
      toRet = pix1 + slope*(-1 * longitude - longi1);
      return toRet;
    }
	
	
	/**
	 * helper method to convert latitude to a y coordinate
	 * @param latitude
	 * @return y coordinate
	 */
	private double latToY(double latitude) {
	  double toRet = 0;
	  double lat1 = 49.003;
	  double pix1 = 13;
	  double lat2 = 31.333;
	  double pix2 = 410;
	  double slope = (pix2 - pix1)/ (lat2 - lat1);
      
	  toRet = pix1 + slope*(latitude - lat1);
	  
	  return toRet;
	}
	
	
	/**
	 * Defines a graphical (visual) representation of an edge for use on the map 
	 * @author Cameron
	 *
	 */
	private class EdgeArrow extends Line {
	  PathInterface path;

	  boolean isOnRoute;
	  AirportCircle[] nodes; // [0] = from; [1] = to
	  
	  static double edgeStrokeWeight = 1;
	  
	  /**
	   * sets up formatted edge
	   * @param path - the path (from airport to airport) to create the edge from
	   */
	  EdgeArrow(PathInterface path) {
	    this.path = path;
	    this.isOnRoute = false;
	    nodes = new AirportCircle[2];
	    nodes[0] = airportLookup.get(path.getStart());
	    nodes[1] = airportLookup.get(path.getEnd());
	    setStartX(nodes[0].getLayoutX());
	    setStartY(nodes[0].getLayoutY());
	    
	    setEndX(nodes[1].getLayoutX());
	    setEndY(nodes[1].getLayoutY());
	    setStrokeWidth(edgeStrokeWeight);
	  }
	  
	  /**
	   * Sets whether or not the edge is part of the generated route for the user.
	   * If the edge is part of the route, the stroke will be gold
	   * @param setOnRoute
	   */
	  void setOnRoute(boolean setOnRoute) {
	    isOnRoute = setOnRoute;
	    if (isOnRoute) {
	      setViewOrder(1);
	      setStroke(Paint.valueOf("gold"));
	    }
	    else {
	      setViewOrder(2);
	      setStroke(Paint.valueOf("black"));
	    }
	  }
	  
	  /**
	   * gets the weight of the edge (distance in miles)
	   * @return
	   */
	  int getWeight() { return path.getDistance(); }
	  
	  
	}
	
	
	/**
	 * defines a graphical (visual) representation of an airport for use on the map;
	 * contains a circle for the location, as well as the airport's name
	 * @author Cameron
	 *
	 */
	private class AirportCircle extends Pane {
	  AirportInterface airport;
	  Circle mapDot;
	  Text name;
	  double xPos;
	  double yPos;
	  boolean isHighlighted;
	  boolean isSelected;
	  Text position;
	  HBox nameBox;
	  static double RADIUS = 13;
	  static Paint unHighlightedColor = Paint.valueOf("red");
	  static Paint highlightedColor  = Paint.valueOf("blue");
	  static Paint onRouteColor = Paint.valueOf("gold");
	  
	  /**
	   * sets up formatted airport circle
	   * @param airport - the airport used to create the circle
	   */
	  AirportCircle(AirportInterface airport) {
	    xPos = longToX(airport.getLongitude());
	    yPos = latToY(airport.getLatitude());
	    this.airport = airport;
	    
	    
	    isHighlighted =  false;
	    isSelected = false;
	    
	    setLayoutX(xPos);
	    setLayoutY(yPos);
	    position = new Text();
	    position.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    
	    position.setTranslateX(-3);
	    position.setTranslateY(5);
	     
	    
	    name = new Text(airport.getAirportName()+" ("+airport.getAirportCode()+")");
	    name.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
	    name.setFill(Paint.valueOf("black"));
	    nameBox = new HBox(name);
	    nameBox.setBackground(Background.fill(Paint.valueOf("white")));
	    nameBox.setBorder(Border.stroke(Paint.valueOf("black")));
	    nameBox.setLayoutX(RADIUS+2);
	    nameBox.setLayoutY(RADIUS-3);
	    //nameBox.setVisible(false); // TODO if airport names start cluttering, 
	                                // just display them on mouse hover (see bottom of constructor)
	    
	    mapDot = new Circle(0, 0, RADIUS, unHighlightedColor);
	    mapDot.setCursor(Cursor.HAND);
	    mapDot.setOnMouseClicked(colorChange -> getAirportHelper(this));
	    mapDot.setId(airport.getAirportCode()); // for testing
	    setViewOrder(0);
	     
	    getChildren().addAll(mapDot, nameBox, position);
	    
	    //mapDot.setOnMouseEntered(show -> nameBox.setVisible(true));
	    //mapDot.setOnMouseExited(hide -> nameBox.setVisible(false));
	  }
	  
	  
	  /**
       * When true, sets the airport as a user-selected airport that has not yet been confirmed
       * (fills red)
       * @param setHighlight- whether the user has highlighted the airport
       */
	  void setHighlight(boolean setHighlight) {
	    isHighlighted = setHighlight;
	    position.setText("");
	    if (isHighlighted) mapDot.setFill(highlightedColor);
        else mapDot.setFill(unHighlightedColor);
	  }
	  
	  /**
	   * When true, sets the airport as a user-selected airport that has been confirmed
	   * (fills blue)
	   * @param setSelected - whether the user has confirmed the airport as part of the 
	   *                          itinerary or not
	   */
	  void setSelected(boolean setSelected) {
	    isSelected = setSelected;
	    setDisable(isSelected);
	  }
	  
	  /**
	   * Sets the airport as the specified airport on the generated route for the user
	   * (fills gold)
	   * @param pos - the ordinal position of the airport on the generated list
	   */
	  void setOnRoute(int pos) {
	    if (!position.getText().equals("")) { // if a route goes through same port >1
	      position.setText(position.getText()+ ",");
	    }
	    position.setText(position.getText()+Integer.toString(pos));
	    mapDot.setFill(onRouteColor);
	  }
	  
	}
	
	
	/**
	 * Defines the current state of the app,
	 * either in the MAIN (or mode-select) state or
	 * the AIRPORT_SELECTION state (where the user is selecting airports for their route)
	 * @author Cameron
	 *
	 */
	private enum AppState {
	  MAIN, AIRPORT_SELECTION
	}
	
	
	
	
	
}
