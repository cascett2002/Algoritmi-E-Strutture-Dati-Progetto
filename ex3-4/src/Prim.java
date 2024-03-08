import java.io.*;
import java.util.*;

public class Prim {

    public static <V, L extends Number & Comparable<L>> Collection<AbstractEdge<V, L>> minimumSpanningForest(
            Graph<V, L> graph) {

        // Comparator<Edge<V, L>> edgeComparator = Comparator.comparing(Edge::getLabel);
        Comparator<Edge<V, L>> edgeComparator = new Comparator<Edge<V, L>>() {
            public int compare(Edge<V, L> ob1, Edge<V, L> ob2) {
                return ob1.getLabel().compareTo(ob2.getLabel());
            }
        };

        Set<V> visitedNodes = new HashSet<>();
        Set<AbstractEdge<V, L>> forestEdges = new HashSet<>();
        PriorityQueue<Edge<V, L>> edgePriorityQueue = new PriorityQueue<>(edgeComparator);

        for (V currentNode : graph.getNodes()) {
            if (!visitedNodes.contains(currentNode)) {
                visitedNodes.add(currentNode);
                // addEdgesToQueue(graph, edgePriorityQueue, currentNode, visitedNodes);

                for (V neighbour : graph.getNeighbours(currentNode)) { // ERRORE QUA
                    L label = graph.getLabel(currentNode, neighbour);
                    edgePriorityQueue.push(new Edge<>(currentNode, neighbour, label));
                }

                while (!edgePriorityQueue.empty() && visitedNodes.size() < graph.numNodes()) {
                    Edge<V, L> minEdge = edgePriorityQueue.top();
                    edgePriorityQueue.pop();

                    V endNode = minEdge.getEnd();

                    if (!visitedNodes.contains(endNode)) {
                        visitedNodes.add(endNode);
                        forestEdges.add(minEdge);
                        // addEdgesToQueue(graph, edgePriorityQueue, endNode, visitedNodes);
                        for (V neighbour : graph.getNeighbours(endNode)) {
                            L label = graph.getLabel(endNode, neighbour);
                            if (!visitedNodes.contains(neighbour)) {
                                edgePriorityQueue.push(new Edge<>(endNode, neighbour, label));
                            }
                        }
                    }
                }
            }
        }

        return forestEdges;
    }

    /*
     * private static <V, L extends Number & Comparable<L>> void
     * processNode(Graph<V, L> graph, Set<V> visitedNodes,
     * Set<AbstractEdge<V, L>> forestEdges, PriorityQueue<Edge<V, L>>
     * edgePriorityQueue, V currentNode) {
     * if (!visitedNodes.contains(currentNode)) {
     * visitedNodes.add(currentNode);
     * addEdgesToQueue(graph, edgePriorityQueue, currentNode, visitedNodes);
     * 
     * while (!edgePriorityQueue.empty() && visitedNodes.size() < graph.numNodes())
     * {
     * Edge<V, L> minEdge = edgePriorityQueue.top();
     * edgePriorityQueue.pop();
     * 
     * V endNode = minEdge.getEnd();
     * 
     * if (!visitedNodes.contains(endNode)) {
     * visitedNodes.add(endNode);
     * forestEdges.add(minEdge);
     * addEdgesToQueue(graph, edgePriorityQueue, endNode, visitedNodes);
     * }
     * }
     * }
     * }
     * 
     * private static <V, L extends Number & Comparable<L>> void
     * addEdgesToQueue(Graph<V, L> graph,
     * PriorityQueue<Edge<V, L>> edgePriorityQueue, V node, Set<V> visitedNodes) {
     * for (V neighbor : graph.getNeighbours(node)) {
     * L label = graph.getLabel(node, neighbor);
     * if (!visitedNodes.contains(neighbor)) {
     * edgePriorityQueue.push(new Edge<>(node, neighbor, label));
     * }
     * }
     * }
     */
    // private static final String OUTPUT_FILE = "PrimOutput.csv";
    // private static final String INPUT_FILE = "italian_dist_graph.csv";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Errore: file input mancante");
            System.exit(1);
        }

        String inputFile = args[0];
        // String inputFile = "italian_dist_graph.csv";
        String outputFile = "PrimOutput.csv";
        Graph<String, Double> graph = new Graph<>(false, true);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                // costruzione grafo ponderato
                String[] split = line.split(",");
                graph.addNode(split[0]);
                graph.addNode(split[1]);
                graph.addEdge(split[0], split[1], Double.parseDouble(split[2]));
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Errore lettura file input " + e.getMessage());
            System.exit(1);
        }
        long start = System.currentTimeMillis();
        Collection<AbstractEdge<String, Double>> primForest = minimumSpanningForest(graph);
        long end = System.currentTimeMillis();
        System.out.println("");
        System.out.println("Tempo impiegato: " + (end - start) / 1000.0 + "s");
        System.out.println("");
        try {
            BufferedWriter bufferwriter = new BufferedWriter(new FileWriter(outputFile));
            for (AbstractEdge<String, Double> edge : primForest) {
                bufferwriter.write(edge.toString());
                bufferwriter.newLine();
            }
            bufferwriter.close();
        } catch (IOException e) {
            System.out.println("Errore di scrittura outputFile" + e.getMessage());
            System.exit(1);
        }

        System.out.println("Numero di nodi: " + graph.numNodes());
        System.out.println("Numero di archi: " + graph.numEdges());
        System.out.println("");

        HashSet<String> Numforest = new HashSet<>();
        for (AbstractEdge<String, Double> edge : primForest) {
            Numforest.add(edge.getStart());
            Numforest.add(edge.getEnd());
        }

        int qNumforest = Numforest.size();
        int numEdges = primForest.size();

        double weight = 0;
        Set<AbstractEdge<String, Double>> visitedEdges = new HashSet<>();

        for (AbstractEdge<String, Double> edge : primForest) {
            visitedEdges.add(edge);
            weight += edge.getLabel();
        }

        System.out.println("Foresta minima ricoprente:");
        System.out.println("N° di nodi : " + qNumforest);
        System.out.println("N° di archi: " + numEdges);
        System.out.println("Peso della minima foresta ricoprente: " + weight / 1000 + " km");
        System.out.println("");
    }
}
