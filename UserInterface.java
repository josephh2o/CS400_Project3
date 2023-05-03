import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class that defines the UserInterface window for the application
 * @author Cameron
 *
 */
public class UserInterface extends VBox {

  private static final double buttonHeight = 50; // px
  private static final double buttonWidth = 70; // px
  private static double button_inset_X = 130; // px
  private static double button_inset_Y = 50; //px
  public static double baseViewOrder = 6;
  
  
  private ArrayList<Button> modeSelects; // select 2 airports or select 3 airports buttons
  private Button confirmButton;           // lets user move on to next step in selection mode
  private Button quitButton;              // exits the app
  private Text prompt;                    // text to gives instructions to the user
  private Pane airportGroup;              // deals with drawing to map
  private Button backButton;              // escapes user to main menu
  private Text userRouteText;                 // displays additional route information to user
  
  public UserInterface() {
    super();
    modeSelects = new ArrayList<Button>();
    airportGroup = new Pane();
    userRouteText = new Text();
    backButton = new Button("Reset map");
    quitButton = new Button("Quit");
    prompt = new Text("Please choose an action");


    // set up title node
    Text titleText = new Text("Airport Pathfinder");
    titleText.setFont(new Font("Cambria", 35));
    HBox title = new HBox(titleText);
    title.setAlignment(Pos.CENTER);

    // set up subtitle1
    backButton.setDisable(true);
    prompt.setBoundsType(null);
    prompt.setFont(new Font("Calibri", 20));
    HBox subtitle1 = new HBox(backButton, prompt);
    subtitle1.setAlignment(Pos.CENTER);
    HBox.setMargin(prompt, new Insets(0, 30, 0, 30));

    // set up subtitle2
    userRouteText.setVisible(true);
    userRouteText.setFont(Font.font("Calibri", FontWeight.BLACK, 24));
    HBox subtitle2 = new HBox(userRouteText);
    subtitle2.setAlignment(Pos.CENTER);
    subtitle2.setMinHeight(80);

    // add title, subtitles to top box
    VBox top = new VBox(title, subtitle1, subtitle2);
    title.setPadding(new Insets(20, 0, 20, 0));
    
    // set up map image and related nodes
    Image mapUS = new Image("/assets/blank-map-of-us.png");
    double map_width = mapUS.getWidth();
    double map_height = mapUS.getHeight();
    ImageView mapIV = new ImageView();
    mapIV.setImage(mapUS);
    mapIV.setViewOrder(baseViewOrder);
    airportGroup.getChildren().add(mapIV);
    

    confirmButton = new Button("Confirm");
    confirmButton.setLayoutX(map_width - button_inset_X);
    confirmButton.setLayoutY(map_height - button_inset_Y);
    confirmButton.setViewOrder(0);
    
    airportGroup.getChildren().add(confirmButton);
    airportGroup.setBackground(Background.fill(Paint.valueOf("lightblue")));
    airportGroup.setBorder(new Border(new BorderStroke(Paint.valueOf("black"),
        BorderStrokeStyle.SOLID, null, BorderStroke.MEDIUM)));
    airportGroup.setDisable(true);

    
    // bottom buttons
    quitButton.setOnAction(quit -> Platform.exit());
    quitButton.setMinHeight(buttonHeight);
    quitButton.setMinWidth(buttonWidth);

    Button route2Button = new Button("Create New Route (2 airports)");
    modeSelects.add(route2Button);
    route2Button.setMinHeight(buttonHeight);
    route2Button.setMinWidth(buttonWidth);

    Button route3Button = new Button("Create New Route (3 airports)");
    modeSelects.add(route3Button);
    route3Button.setMinHeight(buttonHeight);
    route3Button.setMinWidth(buttonWidth);

    // add bottom buttons to bottom box
    HBox functionSelect = new HBox(route2Button, route3Button);
    HBox bottomControls = new HBox(functionSelect, quitButton);
    bottomControls.setAlignment(Pos.CENTER_RIGHT);
 
    // add created boxes to the UserInterface
   getChildren().addAll(top,airportGroup, bottomControls);
   VBox.setMargin(airportGroup, new Insets(20,50,20,50));
   setBackground(Background.fill(Paint.valueOf("lightgray")));
  }
  
  
  
  
  // getters for various children
  
  public List<Button> getModeSelects() {
    return  modeSelects; // select 2 airports or select 3 airports buttons
  }
  
  public Button getConfirmButton() {
    return confirmButton;           // lets user move on to next step in selection mode
  }
  
  public Button getQuitButton() {
    return quitButton;              // exits the app
  }
  
  public Text getPromptText() {
    return prompt;                    // text to gives instructions to the user
  }
  
  public Pane getAirportGroup() {
    return airportGroup;              // deals with drawing to map
  }
  
  public Button getBackButton() {
    return backButton;              // escapes user to main menu
  }
  
  public Text getUserRouteText() {
    return userRouteText;                 // displays additional route information to user
  }
  
}
