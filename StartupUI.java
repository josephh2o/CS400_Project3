import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;

/**
 * Defines a window that pops up 
 */
class StartupUI extends Stage {
    private Button yesButton;
    private Button noButton;
    private boolean useTesterData = false;
    
    StartupUI() {
      // set title
      setTitle("AirportPathFinder -- Use Testing Data?");
      
      // prompt for user
      Text prompt = new Text("Use testing data for map?");
      prompt.setFont(new Font("Cambria", 23));
      HBox top = new HBox(prompt);
      
      // buttons for user
      yesButton = new Button("Yes");
      yesButton.setMinHeight(50);
      yesButton.setMinWidth(100);
      yesButton.setFont(new Font(20));
      
      noButton = new Button("No");
      noButton.setMinHeight(50);
      noButton.setMinWidth(100);
      noButton.setFont(new Font(20));
         
      HBox buttons = new HBox(yesButton, noButton);
      
      // add to root
      VBox root = new VBox(top, buttons);
      top.setAlignment(Pos.CENTER);
      buttons.setAlignment(Pos.BOTTOM_CENTER);
      root.setMargin(buttons, new Insets(20,50,20,50));
      root.setMargin(top, new Insets(20,0,20,0));
        
      
      initModality(Modality.APPLICATION_MODAL);
      setScene(new Scene(root,300, 150));
      setResizable(false);
      
      // set button actions
      noButton.setOnAction(noTestData -> {
        useTesterData = false;
        close();
       });
      yesButton.setOnAction(noTestData -> {
        useTesterData = true;
        close();
       });
    }
    
    boolean useTesterData() {
      return useTesterData;
    }
    
    Button getYesButton() { return yesButton; }
    
    Button getNoButton() { return noButton; }
    
  }