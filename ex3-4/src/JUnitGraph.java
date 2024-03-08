import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JUnitGraph {
    private Graph<String, Integer> directedGraph;
    private Graph<String, Double> undirectedGraph;

    @BeforeEach
    public void setUp() { // Inizializzazione del grafo diretto e non etichettato prima di ogni test
        directedGraph = new Graph<>(true, true);
        undirectedGraph = new Graph<>(false, true);
    }

    @Test
    public void testAddNode() {
        assertTrue(directedGraph.addNode("A"));
        assertTrue(undirectedGraph.addNode("B"));

        assertFalse(directedGraph.addNode("A")); // Prova ad aggiungere un nodo già esistente (deve restituire false)
    }

    @Test
    public void testAddEdge() {
        directedGraph.addNode("A");
        directedGraph.addNode("B");

        assertTrue(directedGraph.addEdge("A", "B", 10)); // Aggiunge un arco da A a B con etichetta 10
        assertFalse(directedGraph.addEdge("A", "C", 5)); // Prova ad aggiungere un arco da A a C (C non esiste)

        undirectedGraph.addNode("X");
        undirectedGraph.addNode("Y");

        assertTrue(undirectedGraph.addEdge("X", "Y", 3.5)); // Aggiunge un arco da X a Y con etichetta 3.5
        assertFalse(undirectedGraph.addEdge("X", "Z", 2.0)); // Prova ad aggiungere un arco da X a Z (Z non esiste)
    }

    @Test
    public void testRemoveNode() {
        directedGraph.addNode("A");
        directedGraph.addNode("B");
        directedGraph.addEdge("A", "B", 5);

        assertTrue(directedGraph.removeNode("A"));
        assertFalse(directedGraph.containsNode("A"));
        assertFalse(directedGraph.containsEdge("A", "B"));
    }

    @Test
    public void testRemoveEdge() {
        directedGraph.addNode("A");
        directedGraph.addNode("B");
        directedGraph.addEdge("A", "B", 8);

        assertTrue(directedGraph.removeEdge("A", "B"));
        assertFalse(directedGraph.containsEdge("A", "B"));
    }

    @Test
    public void testGetNeighbours() {
        undirectedGraph.addNode("X");
        undirectedGraph.addNode("Y");
        undirectedGraph.addEdge("X", "Y", 4.2);

        Set<String> neighboursX = new HashSet<>(Arrays.asList("Y"));
        assertEquals(neighboursX, undirectedGraph.getNeighbours("X"));

        Set<String> neighboursY = new HashSet<>(Arrays.asList("X"));
        assertEquals(neighboursY, undirectedGraph.getNeighbours("Y"));
    }

    @Test
    public void testGetLabel() {
        directedGraph.addNode("A");
        directedGraph.addNode("B");
        directedGraph.addEdge("A", "B", 6);

        assertEquals(Integer.valueOf(6), directedGraph.getLabel("A", "B"));
        // assertNull(directedGraph.getLabel("B", "A")); // Verifica che non ci sia un
        // arco da "B" a "A" in un grafo diretto
        assertNull(directedGraph.getLabel("A", "C")); // Verifica che non ci sia un arco tra "A" e "C" se "C" non esiste
                                                      // nel grafo
    }
}