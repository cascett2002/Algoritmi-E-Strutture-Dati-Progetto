import java.util.*;
public interface AbstractGraph<V, L> {
    public abstract boolean isDirected();   //This function returns a boolean value indicating whether the graph is directed (true) or undirected (false).
    public abstract boolean isLabelled();   //This function returns a boolean value indicating whether the graph is labelled (true) or unlabelled (false)
    public abstract boolean addNode(V a);  // This function adds a node with value a to the graph and returns true if the addition is successful
    public abstract boolean addEdge(V a, V b, L l); //This function adds an edge between nodes a and b, with label l, to the graph and returns true if the addition is successful. 
    public abstract boolean containsNode(V a);  //This function checks whether the graph contains a node with value a and returns true if it does, otherwise false.
    public abstract boolean containsEdge(V a, V b);// This function checks whether the graph contains an edge between nodes a and b and returns true if it does, otherwise false.
    public abstract boolean removeNode(V a);//This function removes the node with value 'a' from the graph.
    public abstract boolean removeEdge(V a, V b); //This function removes the edge between nodes a and b from the graph and returns true if the removal is successful.
    public abstract int numNodes();//This function returns the total number of nodes in the graph.
    public abstract int numEdges();// This function returns the total number of edges in the graph.
    public abstract AbstractCollection<V> getNodes(); // This function returns an AbstractCollection containing all Nodes present in the graph.
    public abstract AbstractCollection<AbstractEdge<V, L>> getEdges(); // This function returns an AbstractCollection containing all the edges present in the graph.
    public abstract AbstractCollection<V> getNeighbours(V a);//This function returns an AbstractCollection containing all the neighbor nodes of node 'a' in the graph.
    public abstract L getLabel(V a, V b); //This function returns the label associated with the edge between nodes a and b in the graph,
}