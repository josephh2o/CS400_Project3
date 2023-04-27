
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmEngineerTests {

    /*
    normal test
     */
    @Test
    public void test1() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("San Francisco");
        graph.insertNode("Denver");
        graph.insertNode("Houston");
        graph.insertNode("Atlanta");
        graph.insertNode("Boston");
        graph.insertEdge("San Francisco", "Denver", 1.0);
        graph.insertEdge("Denver", "San Francisco", 2.0);
        graph.insertEdge("Denver", "Houston", 1.0);
        graph.insertEdge("Houston", "Denver", 2.0);
        graph.insertEdge("Houston", "Atlanta", 1.0);
        graph.insertEdge("Atlanta", "Houston", 2.0);
        graph.insertEdge("Atlanta", "Boston", 1.0);
        graph.insertEdge("Boston", "Atlanta", 2.0);
        
        assertEquals("[San Francisco, Denver, Houston, Atlanta, Boston]", graph.shortestTrioPathData("Houston", "San Francisco", "Boston").toString());
        assertEquals(4.0, graph.shortestTrioPathCost("Houston", "San Francisco", "Boston"));
        
    }
    
    /*
    test when not every path is valid but there is at least one valid path
     */
    @Test
    public void test2() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("San Francisco");
        graph.insertNode("Denver");
        graph.insertNode("Houston");
        graph.insertNode("Atlanta");
        graph.insertNode("Boston");
        graph.insertEdge("San Francisco", "Denver", 1.0);
        graph.insertEdge("Denver", "Houston", 1.0);
        graph.insertEdge("Houston", "Atlanta", 1.0);
        graph.insertEdge("Atlanta", "Boston", 1.0);
    
        assertEquals("[San Francisco, Denver, Houston, Atlanta, Boston]", graph.shortestTrioPathData("Houston", "San Francisco", "Boston").toString());
        assertEquals(4, graph.shortestTrioPathCost("Houston", "San Francisco", "Boston"));
        
    }
    
    /*
    should throw no such element exception when the path does not exist
     */
    @Test
    public void testDisconnected() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("San Francisco");
        graph.insertNode("Denver");
        graph.insertNode("Houston");
        graph.insertNode("Atlanta");
        graph.insertNode("Boston");
        graph.insertEdge("San Francisco", "Denver", 1.0);
        graph.insertEdge("Denver", "San Francisco", 1.0);
        graph.insertEdge("Denver", "Houston", 1.0);
        graph.insertEdge("Houston", "Denver", 1.0);
        graph.insertEdge("Houston", "Atlanta", 1.0);
        graph.insertEdge("Boston", "Atlanta", 1.0);
    
        assertThrows(NoSuchElementException.class, () -> {graph.shortestTrioPathData("Houston", "San Francisco", "Boston");});;
        assertThrows(NoSuchElementException.class, () -> {graph.shortestTrioPathCost("Houston", "San Francisco", "Boston");});
        
    }
    
    /*
    should throw no such element exception when the airport is not in the graph
     */
    @Test
    public void testFake() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("San Francisco");
        graph.insertNode("Denver");
        graph.insertNode("Houston");
        graph.insertNode("Atlanta");
        graph.insertNode("Boston");
        graph.insertEdge("San Francisco", "Denver", 1.0);
        graph.insertEdge("Denver", "San Francisco", 1.0);
        graph.insertEdge("Denver", "Houston", 1.0);
        graph.insertEdge("Houston", "Denver", 1.0);
        graph.insertEdge("Houston", "Atlanta", 1.0);
        graph.insertEdge("Atlanta", "Houston", 1.0);
        graph.insertEdge("Atlanta", "Boston", 1.0);
        graph.insertEdge("Boston", "Atlanta", 1.0);
        
        assertThrows(NoSuchElementException.class, () -> {graph.shortestTrioPathData("Austin", "San Francisco", "Boston");});;
        assertThrows(NoSuchElementException.class, () -> {graph.shortestTrioPathCost("Austin", "San Francisco", "Boston");});
        
    }
    
    /*
    when there are duplicates, should treat as just one
     */
    @Test
    public void testDuplicates() {
        AirportPathGraph<String, Double> graph = new AirportPathGraph<>();
        graph.insertNode("San Francisco");
        graph.insertNode("Denver");
        graph.insertNode("Houston");
        graph.insertNode("Atlanta");
        graph.insertNode("Boston");
        graph.insertEdge("San Francisco", "Denver", 1.0);
        graph.insertEdge("Denver", "San Francisco", 2.0);
        graph.insertEdge("Denver", "Houston", 1.0);
        graph.insertEdge("Houston", "Denver", 2.0);
        graph.insertEdge("Houston", "Atlanta", 1.0);
        graph.insertEdge("Atlanta", "Houston", 2.0);
        graph.insertEdge("Atlanta", "Boston", 1.0);
        graph.insertEdge("Boston", "Atlanta", 2.0);
        
        assertEquals("[San Francisco, Denver, Houston]", graph.shortestTrioPathData("Houston", "San Francisco", "Houston").toString());
        assertEquals(2.0, graph.shortestTrioPathCost("Houston", "San Francisco", "Houston"));
        
    }
    
    


}
