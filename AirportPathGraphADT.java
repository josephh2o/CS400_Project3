import java.util.List;
import java.util.NoSuchElementException;

public interface AirportPathGraphADT<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType> {
    
    public List<NodeType> shortestTrioPathData(NodeType airport1, NodeType airport2, NodeType airport3);
    
    public double shortestTrioPathCost(NodeType airport1, NodeType airport2, NodeType airport3);

}
