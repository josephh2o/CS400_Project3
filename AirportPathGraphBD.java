
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class AirportPathGraphBD<NodeType,EdgeType extends Number> extends DijkstraGraph<NodeType, EdgeType> implements AirportPathGraphADT <NodeType, EdgeType> { 
 
    public List<NodeType> shortestTrioPathData(NodeType airport1, NodeType airport2, NodeType airport3) {
        return new ArrayList<NodeType>();
    }

	//will call shortestPathData for airport 1&2, then 2&3 and then return a combined list of those two lists
    public double shortestTrioPathCost(NodeType airport1, NodeType airport2, NodeType airport3) {
		return 0;
    }
}
