import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportPath implements AirportPathInterface {
	private AirportPathGraphADT<String, Double> graph;
	private AirportDatabaseInterface airports;
	private Map<String, String> airportDecoder;

    public AirportPath() {
        // graph = new AirportPathGraphBD<String, Double>();
        graph = new AirportPathGraph<String, Double>();
        // airports = new AirportDatabaseBD();
        airports = new AirportDatabase("data/flightDataModified.dot");
        airportDecoder = new HashMap<String, String>();
        loadCodes();
        loadGraph();
    }
    private void loadCodes() {
        for(AirportInterface airport : airports.getAirportList())
            airportDecoder.put(airport.getAirportCode(), airport.getAirportName());
    }

    private void loadGraph() {
        for(AirportInterface airport : airports.getAirportList())
            insertNode(airport.getAirportCode());

        for(PathInterface path: airports.getPathList())
            insertEdge(path.getStart(), path.getEnd(), path.getDistance());
    }

	public boolean insertEdge(String predecessor, String successor, double weight) {
        return graph.insertEdge(predecessor, successor, weight);
    }
	public boolean insertNode(String data) {
       return graph.insertNode(data);
    }
	public List<String> getShortestPath(String start, String end) {
        return graph.shortestPathData(start, end);
    }
	public double getShortestDistance(String start, String end) {
       return graph.shortestPathCost(start, end);
    }
	public List<String> getShortestTrioPath(String uno, String dos, String tres) {
        return graph.shortestTrioPathData(uno, dos, tres);
    }
	public List<AirportInterface> getAirports() {
        return airports.getAirportList();
    }
    public List<PathInterface> getPaths() {
        return airports.getPathList();
    }
    // get airport code from frontend and find airport’s full name 
    public String getFullAirportName(String airportCode) {
        return airportDecoder.get(airportCode);
    }
}
