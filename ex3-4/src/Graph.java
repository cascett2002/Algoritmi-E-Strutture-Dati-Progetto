import java.util.*;

public class Graph<V, L extends Comparable<L>> implements AbstractGraph<V, L> {
    boolean directed;
    boolean labelled;
    AbstractCollection<V> nodes;
    AbstractCollection<AbstractEdge<V, L>> edges;
    HashMap<V, Set<Edge<V, L>>> adjacencyList;

    public Graph(boolean directed, boolean labelled) {
        this.directed = directed;
        this.labelled = labelled;
        this.nodes = new HashSet<>();
        this.edges = new HashSet<>();
        this.adjacencyList = new HashMap<>();
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isLabelled() {
        return labelled;
    }

    public boolean addNode(V node) {
        return nodes.add(node);
    }

    public boolean addEdge(V source, V destination, L label) {
        if (!containsNode(source) || !containsNode(destination))
            return false;

        Edge<V, L> edge = new Edge<>(source, destination, label);
        edges.add(edge);

        Set<Edge<V, L>> adjacentEdges = adjacencyList.get(source);

        /*
         * Set<Edge<V, L>> adjacentEdges = adjacencyList.computeIfAbsent(source, k ->
         * new HashSet<>());
         * adjacentEdges.add(edge);
         */

        if (adjacentEdges == null) {
            adjacentEdges = new HashSet<>();
            adjacencyList.put(source, adjacentEdges);
        }
        adjacentEdges.add(edge);

        // If the graph is undirected, add the reverse edge
        if (!directed) {
            Edge<V, L> reverseEdge = new Edge<>(destination, source, label);
            edges.add(reverseEdge);

            Set<Edge<V, L>> adjacentEdgesDestination = adjacencyList.get(destination);
            if (adjacentEdgesDestination == null) {
                adjacentEdgesDestination = new HashSet<>();
                adjacencyList.put(destination, adjacentEdgesDestination);
            }
            adjacentEdgesDestination.add(reverseEdge);
        }
        return true;
    }

    // Method to check if source node is present in the graph
    public boolean containsNode(V node) {
        return nodes.contains(node);
    }

    public boolean containsEdge(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination))
            return false;

        for (Edge<V, L> edge : adjacencyList.get(source)) {
            if (edge.getEnd().equals(destination))
                return true;
        }
        return false;
    }

    public boolean removeNode(V node) {
        if (!containsNode(node)) {
            return false;
        }

        Set<Edge<V, L>> edgesToRemove = adjacencyList.remove(node);
        if (edgesToRemove != null) {
            for (Edge<V, L> edge : edgesToRemove) {
                V endNode = edge.getEnd();

                // Remove references to the removed node from other nodes' adjacency lists
                Set<Edge<V, L>> adjacentEdges = adjacencyList.get(endNode);
                if (adjacentEdges != null) {
                    adjacentEdges.removeIf(e -> e.getEnd().equals(node));
                }
                edges.remove(edge);
            }
        }
        nodes.remove(node);
        return true;
    }

    public boolean removeEdge(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination)) {
            return false;
        }

        // Find the edge to remove from the source node's adjacency list
        Set<Edge<V, L>> edgesOfSources = adjacencyList.get(source);
        Edge<V, L> edgeToRemove = null;
        for (Edge<V, L> edge : edgesOfSources) {
            if (edge.getEnd().equals(destination)) {
                edgeToRemove = edge;
                break;
            }
        }

        // If the edge is found, remove it and update the reverse edge (if undirected)
        if (edgeToRemove != null) {
            edgesOfSources.remove(edgeToRemove);
            edges.remove(edgeToRemove);

            // If the graph is undirected, update the reverse edge
            if (!isDirected()) {
                Set<Edge<V, L>> edgesOfB = adjacencyList.get(destination);
                edgesOfB.removeIf(edge -> edge.getEnd().equals(source));
            }
            return true;
        }
        return false;
    }

    public int numNodes() {
        return nodes.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public AbstractCollection<V> getNodes() {
        return nodes;
    }

    public AbstractCollection<AbstractEdge<V, L>> getEdges() {
        return edges;
    }

    public AbstractCollection<V> getNeighbours(V node) {
        if (!containsNode(node))
            return null;

        AbstractCollection<V> neighbours = new HashSet<>();
        // Iterate over edges associated with the given node
        for (Edge<V, L> edge : adjacencyList.get(node)) {
            neighbours.add(edge.getEnd());
        }
        return neighbours;
    }

    public L getLabel(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination))
            return null;

        // Iterate over edges associated with the source node
        for (Edge<V, L> edge : adjacencyList.get(source)) {
            if (edge.getEnd().equals(destination)) {
                return edge.getLabel();
            }
        }
        return null;
    }
}