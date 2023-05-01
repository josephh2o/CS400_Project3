import java.util.List;

public interface AirportPathInterface{
	// private GraphADT<String, Double> graph;
	// private AirportDatabase airports;
	// private List<Path> paths;
	// private Map<String, String> airportDecoder;
    // public AirportPathBD();
	public boolean insertEdge(String predecessor, String successor, double weight);
	public boolean insertNode(String data);
	public List<String> getShortestPath(String start, String end);
	public double getShortestDistance(String start, String end);
	public List<String> getShortestTrioPath(String uno, String dos, String tres);
	public List<AirportInterface> getAirports();
    public List<PathInterface> getPaths();
    // get airport code from frontend and find airport’s full name 
    public String getFullAirportName(String airportCode);
}

