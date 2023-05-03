import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads in the data from the .dot file and stores it in a list of airports and a list of paths.
 */

public class AirportDatabase implements AirportDatabaseInterface {

    private List<AirportInterface> airportList = new ArrayList<>(); // List of airports
    private List<PathInterface> pathList = new ArrayList<>(); // List of paths

    @Override
    public void dotReader(String filename) throws FileNotFoundException {
        Scanner dot = new Scanner(new File(filename));

        // Check if file is empty
        if (!dot.hasNextLine()) {
            dot.close();
            return;
        }

        // Skip digraph line
        dot.nextLine();

        // Traverse file by line
        while (dot.hasNextLine()) {
            String line = dot.nextLine();

            // Find airport data
            if (line.contains("label") && line.contains("pos")) {

                // Preprocess data, remove unnecessary characters
                String data = line.replaceAll( " \\[label=\"", "%");
                data = data.replaceAll("\",pos=\"", "%");
                data = data.replaceAll(",", "%");
                data = data.replaceAll("\"];", "");
                data = data.trim();

                // Split data
                String[] splitData = data.split("%");

                // If data is not in correct format, skip
                if (splitData.length == 4) {
                    String airportCode = splitData[0].trim();
                    String airportName = splitData[1].trim();
                    float latitude = Float.parseFloat(splitData[2].trim());
                    float longitude = Float.parseFloat(splitData[3].trim());

                    // Create airport object
                    Airport airport = new Airport(airportCode, airportName, latitude, longitude);
                    airportList.add(airport);
                }
            }

            // Find path data
            else if (line.contains("->") && line.contains("weight")) {

                // Preprocess data, remove unnecessary characters
                String data = line.replaceAll(" -> ", "%");
                data = data.replaceAll(" \\[weight=", "%");
                data = data.replaceAll("];", "");
                data = data.trim();

                // Split data
                String[] splitData = data.split("%");
                if (splitData.length == 3) { // If data is not in correct format, skip
                    String start = splitData[0].trim();
                    String end = splitData[1].trim();
                    int distance = Integer.parseInt(splitData[2].trim());

                    // Create path object
                    Path path = new Path(start, end, distance);
                    pathList.add(path);
                }
            }
        }
    }

    /**
     * Getter method for list of airports
     * @return list of airports
     */
    @Override
    public List<AirportInterface> getAirportList() {
        return airportList;
    }

    /**
     * Getter method for list of paths
     * @return list of paths
     */
    @Override
    public List<PathInterface> getPathList() {
        return pathList;
    }
}
