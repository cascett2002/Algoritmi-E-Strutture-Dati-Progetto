import java.util.*;
//V,L parametri generici che rappresentano praticamente i vertici e i pesi. I pesi sono comparabili.
//Ricorda: l'abstractcollection è un'interfaccia di java che ci permette di creare collezioni di dati
//dove alcuni metodi sono già implementati, così da facilitarci la vita. Infatti questa interfaccia è
//usata per nodes e edges, e contiene già metodi come add(E e), iterator(), size(), isEmpty(), contains(Object o)...
public class Graph<V, L extends Comparable<L>> implements AbstractGraph<V, L> { //V ed L parametri di tipo generico
    boolean directed; //ogni arco ha una direzione definita da un nodo sorgente a un nodo destinazione
    boolean labelled; //gli archi sono pesati (hanno un valore CREDO numerico aka peso)
    AbstractCollection<V> nodes; //nodi
    AbstractCollection<AbstractEdge<V, L>> edges; //archi
    HashMap<V, Set<Edge<V, L>>> adjacencyList;//Ricordiamo l'hashmap dalla priorityQueue
    //In questo caso per ogni nodo V (presente nel grafo) la hashmap mantiene un insieme SET di archi EDGE<V,L>
    //Ogni arco rappresenta una connessione dal nodo V ad un altro nodo nel grafo
    //RICORDIAMO che Set è una collezione SENZA duplicati; annullando l'inserimento di archi duplicati si mantiene l'integrità del grafo
    public Graph(boolean directed, boolean labelled) {
        this.directed = directed;
        this.labelled = labelled;
        this.nodes = new HashSet<>(); //per i nodi e gli archi usiamo hashset perchè garantisce che ogni elemento sia unico
        this.edges = new HashSet<>();//,permette di fare operazioni O(1)
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

    //
    public boolean addEdge(V source, V destination, L label) {

        if (!containsNode(source) || !containsNode(destination)) //controllo che i nodi sorgente e destinazione esistano nel grafo (containsnode è un metodo di Graph)
            return false;

        Edge<V, L> edge = new Edge<>(source, destination, label); //creo l'arco con i dati forniti
        edges.add(edge);//aggiungo l'arco alla collezione di archi

        Set<Edge<V, L>> adjacentEdges = adjacencyList.get(source); //ottengo il set di archi al nodo sorgente

        /*
         * Set<Edge<V, L>> adjacentEdges = adjacencyList.computeIfAbsent(source, k ->
         * new HashSet<>());
         * adjacentEdges.add(edge);
         */

        if (adjacentEdges == null) {
            adjacentEdges = new HashSet<>(); //se non ci sono archi adiacenti creo l'hashset
            adjacencyList.put(source, adjacentEdges); //Il nuovo HashSet viene associato al nodo sorgente nella adjacencyList, che è la mappa che tiene traccia delle connessioni di tutti i nodi.
        }
        adjacentEdges.add(edge);//l'arco appena creato viene aggiunto al set di archi adiacenti.

        //Se il grafo è non diretto, crea un arco inverso e aggiunge sia alla collezione edges che alla lista di adiacenza del nodo destinazione.
        if (!directed) {
            Edge<V, L> reverseEdge = new Edge<>(destination, source, label);//Crea un arco inverso dal nodo destinazione al nodo sorgente.
            edges.add(reverseEdge);//Aggiunge l'arco inverso all'insieme globale degli archi.

            Set<Edge<V, L>> adjacentEdgesDestination = adjacencyList.get(destination);//Ottiene il set di archi adiacenti al nodo destinazione
            if (adjacentEdgesDestination == null) {//Se non esistono archi adiacenti per il nodo destinazione, crea un nuovo HashSet
                adjacentEdgesDestination = new HashSet<>();
                adjacencyList.put(destination, adjacentEdgesDestination);//Associa il nuovo set di archi adiacenti al nodo destinazione nella mappa di adiacenza.
            }
            adjacentEdgesDestination.add(reverseEdge);//Aggiunge l'arco inverso al set di archi adiacenti del nodo destinazione.
        }
        return true;
    }//Queste istruzioni garantiscono che in un grafo non diretto, ogni volta che viene aggiunto un arco, viene aggiunto anche l'arco inverso, 
    //mantenendo così le relazioni bidirezionali nel grafo.
    //Ogni volta che aggiungo un arco ad ogni nodo devo controllare che la lista di adiacenza esista. Se no la devo creare

    // Method to check if source node is present in the graph
    public boolean containsNode(V node) {
        return nodes.contains(node);
    }

    public boolean containsEdge(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination))
            return false; //Verifica se i nodi sorgente e destinazione esistono nel grafo. Se uno dei due non esiste, ritorna false.

        for (Edge<V, L> edge : adjacencyList.get(source)) { //Itera sugli archi adiacenti al nodo sorgente
            if (edge.getEnd().equals(destination))
                return true;//ontrolla se uno degli archi adiacenti ha come destinazione il nodo destinazione. Se trova un arco corrispondente, ritorna true.
        }
        return false;
    }

    public boolean removeNode(V node) {
        if (!containsNode(node)) {
            return false; //verifica se i nodi sorgente e destinazione esistono nel grafo. Se uno dei due non esiste, ritorna false.
        }

        Set<Edge<V, L>> edgesToRemove = adjacencyList.remove(node); //Rimuove il nodo dalla lista di adiacenza e recupera tutti gli archi adiacenti a quel nodo.
        if (edgesToRemove != null) {//controlla se ci sono archi adiacenti al nodo
            for (Edge<V, L> edge : edgesToRemove) {//Itera sugli archi adiacenti al nodo.
                V endNode = edge.getEnd(); //Ottiene il nodo di destinazione di ogni arco

                Set<Edge<V, L>> adjacentEdges = adjacencyList.get(endNode);//Recupera l'insieme di archi adiacenti al nodo di destinazione.
                if (adjacentEdges != null) { //Se il nodo di destinazione ha archi adiacenti, esegue il blocco di codice seguente.
                    adjacentEdges.removeIf(e -> e.getEnd().equals(node));//Rimuove gli archi che puntano al nodo rimosso dall'insieme di archi adiacenti al nodo di destinazione.
                }
                edges.remove(edge);//Rimuove l'arco dall'insieme globale degli archi
            }
        }
        nodes.remove(node); //rimuove il nodo specificato dall'insieme nodes, che rappresenta tutti i nodi nel grafo.
        return true;//Nodo rimosso con successo
    }

    public boolean removeEdge(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination)) {//Verifica se i nodi sorgente e destinazione esistono nel grafo 
            return false;
        }

        //Trova l'arco specifico da rimuovere dall'insieme di archi adiacenti al nodo sorgente. Se l'arco è trovato, lo memorizza nella variabile edgeToRemove.
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
            //: Rimuove l'arco dall'insieme degli archi adiacenti al nodo sorgente e dall'insieme globale degli archi.
            edgesOfSources.remove(edgeToRemove);
            edges.remove(edgeToRemove);

            // If the graph is undirected, update the reverse edge
            if (!isDirected()) {
                //Se il grafo è non diretto, rimuove anche l'arco inverso dall'insieme degli archi adiacenti al nodo destinazione.
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
        if (!containsNode(node)) //Verifica se il nodo dato esiste nel grafo.
            return null;

        AbstractCollection<V> neighbours = new HashSet<>(); //Crea un nuovo HashSet per memorizzare i nodi vicini.
        // Iterate over edges associated with the given node
        for (Edge<V, L> edge : adjacencyList.get(node)) { //Itera su tutti gli archi adiacenti al nodo dato.
            neighbours.add(edge.getEnd());//Aggiunge il nodo di destinazione di ciascun arco all'insieme dei vicini.
        }
        return neighbours; //Ritorna l'insieme dei nodi vicini.
    }

    public L getLabel(V source, V destination) {
        if (!containsNode(source) || !containsNode(destination)) // Verifica se i nodi sorgente e destinazione esistono
            return null;

        // Iterate over edges associated with the source node
        for (Edge<V, L> edge : adjacencyList.get(source)) {//Itera sugli archi adiacenti al nodo sorgente.
            if (edge.getEnd().equals(destination)) {
                return edge.getLabel(); //Se un arco adiacente ha come destinazione il nodo destinazione, ritorna l'etichetta di quell'arco.
            }
        }
        return null;
    }
}