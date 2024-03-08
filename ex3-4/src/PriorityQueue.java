import java.util.*;
//Comparable: 
//PriorityQueue implementata come un heap. L'arraylist rappresenta il nostro heap
//L'heap usato qua è un heap minimo (se non erro)
public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {
    ArrayList<E> queue; //Elementi dell'Heap
    HashMap<E, Integer> elementsMap; //Coppia di Elemento e Intero. La chiave è l'elemento stesso mentre il valore INT è il suo indice. Int quindi tiene traccia della posizione dell'elemento nell'heap (permettendo operazioni rapide)
    Comparator<E> comparator;//Utilizzato per i confronti e per determinare la priorità degli elementi

    public PriorityQueue(Comparator<E> comparator) { //comparator: parametro per comparatore personalizzato per tipo specifico di dato
        this.comparator = comparator;
        this.queue = new ArrayList<>();
        this.elementsMap = new HashMap<>();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public boolean push(E e) {
        if (elementsMap.containsKey(e) || e == null) {
            return false; //l'elemento è già presente nella coda
        }
        queue.add(e);//aggiungo l'elemento nella coda
        elementsMap.put(e, queue.size() - 1);//aggiorno l'hashmap con il nuovo elemento
        heapUp(queue.size() - 1);//dopo aver aggiunto l'elemento nell'heap, devo riordinarlo partendo dall'indice dell'el appena aggiunto.
        //Sposto quindi l'elemento in alto fino a quando non trovo la posizione corretta 
        return true;
    }

    public boolean contains(E e) {
        return elementsMap.containsKey(e);
    }

    public E top() {
        if (empty()) {
            return null;
        }
        return queue.get(0);
    }

    public void pop() { //rimuove l'elemento con priorità più alta dopo aver preso quello di priorità (peso nel caso del grafo) minore dalla fine (swap)
        if (empty()) {
            return; //se la coda è vuota non fa nulla
        }
        int lastIndex = queue.size() - 1; //ultimo elemento della coda
        swap(0, lastIndex); //scambio primo e ultimo elemento della coda
        elementsMap.remove(queue.get(lastIndex)); //rimuovo l'ultimo el dalla hashmap
        queue.remove(lastIndex); //rimuovo l'ultimo el dalla coda
        if (!empty()) { //se dopo la rimozione la coda non è vuota riorganizzo l'heap con heapdown
            heapDown(0);//per mantenere le proprietà di heap minimo
        }
    }

    public boolean remove(E e) { //rimuovo un elemento dall'heap mantenendo le proprietà di heap minimo dopo la rimozione
        if (!elementsMap.containsKey(e)) { // Element does not exist in the queue
            return false;
        }
        int indexToRemove = elementsMap.get(e);
        int lastIndex = queue.size() - 1;
        if (indexToRemove != lastIndex) {
            swap(indexToRemove, lastIndex); //scambio l'el da rimuovere con l'ultimo elemento
        }
        queue.remove(lastIndex); //rimuovo l'ultimo elemento dall'heap
        elementsMap.remove(e);//aggiorno la hashmap rimuovendo l'elemento in questione
        if (indexToRemove != lastIndex) { //se l'el rimosso non era l'ultimo allora chiamo \/
            updatePriority(indexToRemove);//upPriority per riordinare l'heap
        }
        return true; //elemento rimosso con successo
    }

    private void heapUp(int index) {//mantiene le proprietà di heapMin quando un elemento viene aggiunto o quando la priorità di un el esistente viene aggiornata
        E element = queue.get(index); //nodo di partenza
        while (index > 0) { //proseguo fino a quando il nodo non è all'inizio dell'heap aka radice
            int parentIndex = (index - 1) / 2; //Indice del genitore
            E parent = queue.get(parentIndex); 
            if (comparator.compare(element, parent) >= 0) {
                break; //se l'elemento è >= del genitore interrompo il ciclo
            }
            //se invece l'el è < ,scambio element con parent usando swap
            swap(index, parentIndex);
            index = parentIndex; //aggiorno l'indice del genitore
        }
    }

    private void heapDown(int index) {//mantiene le proprietà di heapMin dopo la rimozione di un elemento (el + piccolo in cima)
        int size = queue.size(); //dimensione heap
        E element = queue.get(index);
        while (true) { //continua fino a quando l'elemento ha almeno un figlio
            int childIndex = 2 * index + 1; //indice del figlio sinistro
            if (childIndex >= size) { //l'elemento non ha figli
                break;
            }
            if (childIndex + 1 < size && comparator.compare(queue.get(childIndex + 1), queue.get(childIndex)) < 0) {
                //controllo se il figlio destro esiste e se è minore del sinistro
                childIndex++; //Incremento per puntare al figlio destro
            }
            if (comparator.compare(queue.get(childIndex), element) >= 0) {
                //se il figlio sinistro o destro è >= dell'elemento, il ciclo si interrompe
                break;
            }
            swap(index, childIndex); //scambio l'el con il figlio minore
            index = childIndex; //aggiorno l'indice a quello del figlio
        }
    }//Determina quale dei due figli è il minore. Se il figlio destro esiste e ha un valore inferiore al figlio sinistro, allora il figlio destro diventa il candidato per lo scambio.

    private void swap(int i, int j) {
        E temp = queue.get(i); //salvo l'el all'indice i
        queue.set(i, queue.get(j));//sostituisco l'el in i con l'elemento in j
        queue.set(j, temp); //metto l'el salvato in temp all'indice j
        elementsMap.put(queue.get(i), i);//aggiorno elMap con gli indici aggiornati
        elementsMap.put(queue.get(j), j);
    }

    private void updatePriority(int index) {
        //se l'el in index ha un valore minore rispetto al suo genitore, allora dovrei spostare l'elemento verso l'alto, quindi heapUP
        if (index > 0 && comparator.compare(queue.get(index), queue.get((index - 1) / 2)) < 0) {
            heapUp(index);
        } else {
            //se l'elemento è maggiore del genitore, allora dovrei spostarlo verso il basso, heapDOWN
            heapDown(index);
        }
    }

}