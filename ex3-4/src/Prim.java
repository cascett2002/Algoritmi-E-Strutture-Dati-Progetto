import java.io.*;
import java.util.*;
//Spiegazione Prim: La classe Prim implementa l'algoritmo di Prim per trovare la foresta di copertura minima in un grafo. 
//L'algoritmo inizia selezionando un nodo casuale e aggiungendo i suoi archi adiacenti in una coda di priorità. 
//Poi, fino a quando la coda non è vuota e non sono stati visitati tutti i nodi, estrae l'arco con il peso minimo 
//che collega un nodo visitato con uno non visitato, aggiungendolo al risultato. I nodi collegati da questo arco vengono marcato come visitati.

//Metodo minimumSpanningForest sotto riportato (spiegazione): prende un grafo come input e restituisce una collezione di archi che rappresentano 
//la foresta di copertura minima. Questo metodo utilizza un PriorityQueue per selezionare gli archi con il peso minimo e un HashSet per tenere 
//traccia dei nodi visitati.

public class Prim {
    //<V, L extends Number & Comparable<L>>: V è il tipo dei nodi del grafo. L è il tipo delle etichette degli archi, 
    //che deve estendere la classe Number e implementare l'interfaccia Comparable<L>. Ciò garantisce che le etichette possano essere sia numeriche sia confrontabili.
    //Collection<AbstractEdge<V, L>>: Il tipo di ritorno del metodo. Restituisce una collezione di oggetti 
    //AbstractEdge che rappresentano gli archi della foresta di copertura minima.
    //Parametro: prende in input un grafo

    public static <V, L extends Number & Comparable<L>> Collection<AbstractEdge<V, L>> minimumSpanningForest(
            Graph<V, L> graph) {

        //Viene creato un oggetto Comparator per gli archi (Edge). Questo Comparator verrà utilizzato per definire l'ordine degli archi, ad esempio in una coda di priorità.
        Comparator<Edge<V, L>> edgeComparator = new Comparator<Edge<V, L>>() {
            //Viene definito il metodo compare, che è necessario implementare quando si crea un Comparator. Questo metodo prende due oggetti Edge come argomenti (ob1 e ob2).
            public int compare(Edge<V, L> ob1, Edge<V, L> ob2) {
                return ob1.getLabel().compareTo(ob2.getLabel());
            }
            //questo Comparator viene utilizzato per confrontare gli archi in base alle loro etichette,
        };

        Set<V> visitedNodes = new HashSet<>();//HashSet creato per tenere traccia dei nodi visitati
        Set<AbstractEdge<V, L>> forestEdges = new HashSet<>();//HashSet usato per salvare gli archi facenti parte della foresta di copertura minima
        PriorityQueue<Edge<V, L>> edgePriorityQueue = new PriorityQueue<>(edgeComparator);//Viene creata una coda di priorità per gli archi, 
        //dove gli archi vengono ordinati in base al loro peso.

        for (V currentNode : graph.getNodes()) { //Itero su tutti i nodi del grafo
            if (!visitedNodes.contains(currentNode)) {//controllo se è già stato visitato
                visitedNodes.add(currentNode);//se non visitato, lo aggiungo ai nodi visitati
                // addEdgesToQueue(graph, edgePriorityQueue, currentNode, visitedNodes);

                for (V neighbour : graph.getNeighbours(currentNode)) { //itero su tutti gli adiacenti del currentNode
                    L label = graph.getLabel(currentNode, neighbour); 
                    edgePriorityQueue.push(new Edge<>(currentNode, neighbour, label)); // Per ciascun adiacente, crea un nuovo arco 
                    //(new Edge<>(currentNode, neighbour, label)) con l'etichetta recuperata dal grafo e lo inserisce nella coda di priorità.
                }
                
                //Selezione dell'arco con peso minimo: Dentro il while, il codice estrae continuamente l'arco con il peso minimo 
                //dalla coda di priorità finché la coda non è vuota e finché ci sono nodi non visitati.
                while (!edgePriorityQueue.empty() && visitedNodes.size() < graph.numNodes()) {
                    Edge<V, L> minEdge = edgePriorityQueue.top();//Viene preso l'arco con la priorità più alta (o peso minimo) dalla coda di priorità senza rimuoverlo
                    edgePriorityQueue.pop();//rimuove l'arco appena esaminato (preso con top()) dalla coda di priorità. Dopo questa operazione, l'arco non è più nella coda, 
                    //e il prossimo arco con la priorità più alta diventa la nuova testa della coda.

                    V endNode = minEdge.getEnd();//recupero il nodo finale dell'arco con peso minimo

                    if (!visitedNodes.contains(endNode)) {//controllo verifica se il nodo finale dell'arco con il peso minimo non è già stato visitato
                        //se non è stato visitato \/
                        visitedNodes.add(endNode);//viene aggiunto all'insieme visitedNodes
                        forestEdges.add(minEdge);// L'arco minEdge viene aggiunto all'insieme forestEdges
                        // addEdgesToQueue(graph, edgePriorityQueue, endNode, visitedNodes);
                        for (V neighbour : graph.getNeighbours(endNode)) {//itera su tutti gli adiacenti del nodo endNode
                            L label = graph.getLabel(endNode, neighbour);//Recupera il peso dell'arco che collega endNode al suo vicino.
                            if (!visitedNodes.contains(neighbour)) {//Verifica se il vicino non è stato ancora visitato.
                                edgePriorityQueue.push(new Edge<>(endNode, neighbour, label));//Se l'adiacente non è stato visitato, l'arco che lo collega a endNode viene creato
                                //e aggiunto alla coda di priorità
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
            String line;//memorizzo ogni riga letta dal file

            while ((line = bufferedReader.readLine()) != null) {//fino a quando ci sono righe da leggere
                // costruzione grafo ponderato
                String[] split = line.split(",");
                //split[0] e split[1] sono i nodi collegati da un arco. Aggiungo i nodi al grafo
                graph.addNode(split[0]);
                graph.addNode(split[1]);
                //Aggiungo un arco tra i nodi con un peso specificato in split[2], convertendo questa stringa in un Double.
                graph.addEdge(split[0], split[1], Double.parseDouble(split[2]));
            }
            bufferedReader.close(); //Dopo che ho finito chiudo il file
        } catch (IOException e) { //se ci sono errori vabbè obv
            System.out.println("Errore lettura file input " + e.getMessage());
            System.exit(1);
        }
        //misuro il tempo impiegato per eseguire l'algoritmo di Prim
        long start = System.currentTimeMillis();
        Collection<AbstractEdge<String, Double>> primForest = minimumSpanningForest(graph);
        long end = System.currentTimeMillis();
        System.out.println("");
        System.out.println("Tempo impiegato: " + (end - start) / 1000.0 + "s");
        System.out.println("");
        try {//scrivo i risultati del Prim nell'outputFile
            BufferedWriter bufferwriter = new BufferedWriter(new FileWriter(outputFile));
            for (AbstractEdge<String, Double> edge : primForest) { //itero su ogni arco della foresta minima ricoprente
                bufferwriter.write(edge.toString());//converto ogni arco in stringa e lo scrivo nel file
                bufferwriter.newLine();
            }
            bufferwriter.close();
        } catch (IOException e) {//vabbè gls
            System.out.println("Errore di scrittura outputFile" + e.getMessage());
            System.exit(1);
        }

        System.out.println("Numero di nodi: " + graph.numNodes());
        System.out.println("Numero di archi: " + graph.numEdges());
        System.out.println("");

        //raccolgo tutti i nodi coinvolti nella primforest
        HashSet<String> Numforest = new HashSet<>();//in quest'hashset memorizzo i nodi
        for (AbstractEdge<String, Double> edge : primForest) {//Itero su ciascun arco della primForest
            Numforest.add(edge.getStart());//per ogni arco, aggiungo nodo di partenza e di arrivo
            Numforest.add(edge.getEnd());  //nella hashset numforest
        }

        int qNumforest = Numforest.size();//num nodi della foresta minima
        int numEdges = primForest.size();//num archi della foresta minima

        double weight = 0;
        Set<AbstractEdge<String, Double>> visitedEdges = new HashSet<>();//HashSet usato per memorizzare gli archi già visitati.

        for (AbstractEdge<String, Double> edge : primForest) {//itero per tutti gli archi della primForest
            visitedEdges.add(edge); //aggiungo gli archi visitati alla hashset visitedEdges
            weight += edge.getLabel();//incremento il peso con il peso dell'arco corrente
        }

        System.out.println("Foresta minima ricoprente:");
        System.out.println("N° di nodi : " + qNumforest);
        System.out.println("N° di archi: " + numEdges);
        System.out.println("Peso della minima foresta ricoprente: " + weight / 1000 + " km");
        System.out.println("");
    }
}
