import java.util.ArrayList;
import java.util.List;
public class AirportDatabaseBD implements AirportDatabaseInterface{

    List<AirportInterface> airportList; // Contains a list of the nodes
    List<PathInterface> pathList; // Contains a list of the edges
                                  
    public AirportDatabaseBD() {
       airportList = new ArrayList<>(); 
       airportList.add(new AirportBD("a", "alice", 0, 0));
       airportList.add(new AirportBD("b", "bob", 0, 1));
       airportList.add(new AirportBD("c", "charlie", 0, 1));

       pathList = new ArrayList<>();
       pathList.add(new PathBD("a", "b", 1));
       pathList.add(new PathBD("a", "c", 5));
    }

    private static void dotReader() {// reads all nodes, stores it
        
    }

    public List<AirportInterface> getAirportList() {
        return airportList;
    }
    public List<PathInterface> getPathList() {
        return pathList;
    }

}
