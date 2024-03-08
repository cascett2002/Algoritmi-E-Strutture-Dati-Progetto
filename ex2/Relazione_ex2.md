# Skiplist (es2) 

# Definizione e caratteristiche di una Skiplist

La Skip List è una struttura dati innovativa, progettata per memorizzare elementi in un ordine specifico, facilitando così processi efficienti di inserimento e ricerca. Questa struttura si distingue da una normale lista concatenata grazie all'impiego di puntatori multipli, che permettono di attraversare rapidamente la lista durante le operazioni di ricerca.

Il segreto della sua efficacia risiede nell'uso di diversi livelli. Ogni elemento, o nodo, nella Skip List non solo mantiene un valore, ma possiede anche una serie di puntatori che lo collegano a nodi situati su livelli più elevati. La costruzione di questi livelli superiori avviene attraverso l'aggiunta di nodi basata su una probabilità calcolata. Questo metodo evita la necessità di passare in rassegna l'intera lista per le ricerche, velocizzando notevolmente il processo.

Quando si inserisce un nuovo elemento nella Skip List, si utilizza un meccanismo di randomizzazione per determinare a quale altezza posizionare il nodo. Questa randomizzazione assegna a caso un livello al nuovo nodo, assicurando che gli elementi siano distribuiti equamente all'interno della struttura. Come risultato, la Skip List si presenta come un'opzione più efficiente rispetto ad altre strutture dati come gli alberi binari di ricerca, specialmente in termini di gestione e ricerca di dati.


## Sviluppo e test

**Sistema Operativo**: Ubuntu

**Flag di compilazione gcc -Ofast**: Ottimizza  significativamente la velocità di esecuzione del codice  attivando una serie di ottimizzazioni avanzate.


## Tabella caricamento e ricerca

 | Caricamento | Ricerca  | max_height |
|-------------|----------|----------|
| 3.442839s   | 0.000312s| 10       |
| 0.439664s   | 0.000122s| 15       |
| 0.389255s   | 0.000119s| 25       |
| 0.515160s   | 0.000161s| 30       |
| 0.507304s   | 0.000169s| 45       |
| 0.215332s   | 0.000123s| 70       |
| 0.290618s   | 0.000094s| 90       |
| 0.211767s   | 0.000126s| 120      |
| 0.518746s   | 0.000158s| 150      |


# MAX_HEIGHT

Il valore **"max height"** in una **Skip List** rappresenta l'altezza massima che può raggiungere un nodo all'interno di questa struttura dati. Questo parametro è **cruciale** per influenzare l'efficienza delle operazioni di ricerca, inserimento e rimozione all'interno della **Skip List**. Avere un **"max height"** più alto può **diminuire** il numero medio di passaggi richiesti per trovare un nodo specifico, migliorando così l'efficienza della ricerca.

D'altra parte, impostare un **"max height"** eccessivamente alto può portare a un **incremento** nel consumo di memoria e a una diminuzione della velocità nelle operazioni di inserimento e rimozione. La scelta del **"max height"** ottimale dipende dalla **grandezza** del dataset che si intende gestire con la Skip List. Per un set di dati più grande è consigliabile un "max height" superiore per mantenere alte prestazioni. Tuttavia, è fondamentale **bilanciare** questo valore per prevenire un utilizzo eccessivo di risorse di memoria.
