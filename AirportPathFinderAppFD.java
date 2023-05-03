import javafx.application.Application;

public class AirportPathFinderAppFD {

	public static void main( String[] args) {
	    AirportPathFinderFrontendFD.setBackend(new AirportPath());
		Application.launch(AirportPathFinderFrontendFD.class);
	}

}