public class Edge<V, L extends Comparable<L>> implements AbstractEdge<V, L>, Comparable<Edge<V, L>> {
//V:vertici; L:pesi. L comparable: permette ai pesi di essere confrontati tra loro.
//Comparable<Edge<V, L>>metodo per confrontare due oggetti Edge tra loro. Questo è particolarmente utile per ordinare gli archi o per utilizzarli in strutture dati che dipendono dal confronto.
//Edge: ARCO
    V start; //nodo di partenza
    V end;   //nodo di destinazione
    L label; //peso

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
        // Serve per confrontare l'oggetto corrente this(.edge) con un altro arco
        if (o == null) {
            return 1;
        }
        return label.compareTo(o.getLabel());
    }
    
    public String toString(){
        return start + "," + end + "," + label;
    }
}
