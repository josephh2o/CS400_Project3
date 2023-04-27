// --== CS400 File Header Information ==--
// Name: Rachelle Lang
// Email: rklang@wisc.edu
// Group and Team: BL, Red
// Group TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: I hope you have a good time grading and that this is fun for you

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra""s shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType,EdgeType>
    implements GraphADT<NodeType, EdgeType> {
    
    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in it""s node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referenced by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it""s node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }
    
    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it""s cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) throws NoSuchElementException {
        
        // throw null exception if start and end are not in graph
        if (!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("invalid start and/or end node");
        }
        
        // special case: start == end
        if (start.equals(end)) {
            return new SearchNode(nodes.get(start), 0, null);
        }
        
        // set up list of nodes to visit
        PriorityQueue<SearchNode> toVisit = new PriorityQueue<>();
        toVisit.add(new SearchNode(nodes.get(start), 0, null));
        
        // set up list of visited nodes
        Hashtable<Node, Boolean> visited = new Hashtable<>();
        
        // main loop
        SearchNode visiting, next;
        while (! toVisit.isEmpty()) {
            
            visiting = toVisit.poll();
            if (visited.containsKey(visiting.node)) {
                continue;
            }
            
            if (visiting.node.data.equals(end)) {
                return visiting;
            }
            
            visited.put(visiting.node, true);
            
            // loop through all edges of current node
            for (Edge edge : visiting.node.edgesLeaving) {
                
                // if already visited continue
                if (visited.containsKey(edge.successor)) {
                    continue;
                }
    
                next = new SearchNode(edge.successor, visiting.cost + edge.data.doubleValue(), visiting);
    
                // add the edge to the queue
                toVisit.add(next);
                
            }
            
        }
    
        throw new NoSuchElementException("nodes do not connect");
    }
    
    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra""s shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) throws NoSuchElementException {
        
        SearchNode path = computeShortestPath(start, end);
        LinkedList<NodeType> data = new LinkedList<>();
        while (path != null) {
            data.addFirst(path.node.data);
            path = path.predecessor;
        }
        
        return data;
    }
    
    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra""s shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode path = computeShortestPath(start, end);
        return path.cost;
    }
    
}