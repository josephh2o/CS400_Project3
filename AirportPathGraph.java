import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AirportPathGraph<NodeType, EdgeType extends Number> extends DijkstraGraph<NodeType, EdgeType> implements AirportPathGraphADT<NodeType, EdgeType>{
    
    
    /*
    overrides the shortest path cost to not throw error and instead return very big number
     */
    @Override
    public double shortestPathCost(NodeType start, NodeType end) {
        try {
            return super.shortestPathCost(start, end);
        }
        catch (NoSuchElementException e) {
            return Double.MAX_VALUE;
        }
    }
    
    /*
    return search node path, can reorder airports to achieve this path
    could probably be more efficient :)
    but the more calls to the pathfinding algorithm the better right?
    pleaaase ignore the time complexity
     */
    public NodeType[] computeShortestTrioOrder(NodeType airport1, NodeType airport2, NodeType airport3) throws NoSuchElementException {
        
        double minCost = Double.MAX_VALUE;
        NodeType[] order = null;
        NodeType[][] permutations = (NodeType[][]) (new Object[][]
            {   {airport1, airport2, airport3},
                {airport3, airport2, airport1},
                {airport2, airport1, airport3},
                {airport3, airport1, airport2},
                {airport1, airport3, airport2},
                {airport2, airport3, airport1}
            });
        
        double cost;
        for (NodeType[] path: permutations) {
            cost = shortestPathCost(path[0], path[1]) + shortestPathCost(path[1], path[2]);
            if (cost < minCost) {
                minCost = cost;
                order = path;
            }
        }
        
        if (order == null) {
            throw new NoSuchElementException();
        }
        
        return order;
        
//        double path213 = shortestPathCost(airport2, airport1) + shortestPathCost(airport1, airport3);
//        double path123 = shortestPathCost(airport1, airport2) + shortestPathCost(airport2, airport3);
//        double path132 = shortestPathCost(airport1, airport3) + shortestPathCost(airport3, airport2);
//        System.out.println(path213);
//        System.out.println(path123);
//        System.out.println(path132);
//        if (path213 <= path123 && path213 <= path132) {
//            return (NodeType[]) (new Object[] {airport2, airport1, airport3});
//        }
//        else if (path123 <= path213 && path123 <= path132) {
//            return (NodeType[]) (new Object[] {airport1, airport2, airport3});
//        }
//        else {
//            return (NodeType[]) (new Object[] {airport1, airport3, airport2});
//        }
    }
    
    /*
    return the shortest path between any three airports, can reorder airports to achieve this path
     */
    public List shortestTrioPathData(NodeType airport1, NodeType airport2, NodeType airport3) {
        NodeType[] order = computeShortestTrioOrder(airport1, airport2, airport3);
        List path = shortestPathData(order[0], order[1]);
        path.remove(order[1]);
        path.addAll(shortestPathData(order[1], order[2]));
        return path;
    }
    
    /*
    return distance of the shortest path between any three airports, can reorder airports to achieve this path
     */
    @Override
    public double shortestTrioPathCost(NodeType airport1, NodeType airport2, NodeType airport3) {
        NodeType[] order = computeShortestTrioOrder(airport1, airport2, airport3);
        return shortestPathCost(order[0], order[1]) + shortestPathCost(order[1], order[2]);
    }
}
