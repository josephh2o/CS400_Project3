import java.io.FileNotFoundException;
import java.util.List;
public interface AirportDatabaseInterface {
    public void dotReader(String filename) throws FileNotFoundException; // reads all nodes, stores it in a list
    public List<AirportInterface> getAirportList();
    public List<PathInterface> getPathList();
}