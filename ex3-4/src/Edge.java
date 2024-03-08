public class Edge<V, L extends Comparable<L>> implements AbstractEdge<V, L>, Comparable<Edge<V, L>> {

    V start;
    V end; 
    L label; 

    public Edge(V a, V b, L l) {
        start = a;
        end = b;
        label = l;
    }

    public V getStart() {
        return start;
    }

    public V getEnd() {
        return end;
    }

    public L getLabel() {
        return label;
    }

    public int compareTo(Edge<V, L> o) {
        // Compares this edge with another edge based on their labels
        if (o == null) {
            return 1;
        }
        return label.compareTo(o.getLabel());
    }
    
    public String toString(){
        return start + "," + end + "," + label;
    }
}
