#include "skiplist.h"

Node *new_node(size_t size,void  *item){//crea un nuovo nodo nella skiplist 

    Node *newNode = (Node *)malloc(sizeof(Node)); //alloco memoria per un nuovo nodo
    newNode->next = (Node **)malloc(sizeof(Node *) * (size +1 ));//Alloca memoria per l'array next all'interno del nodo. Questo array contiene 
    //puntatori ai nodi successivi in ciascun livello della skip list.
    newNode->size = size; //indica a quanti livelli il nodo partecipa.
    newNode->item = item; //dato effettivamente contenuto nel nodo

    for (int i = newNode->size; i >= 0; i--) //Inizializzazione dei Puntatori next
    {
        newNode->next[i] = NULL;
    }

    return newNode; //Restituisco puntatore al nodo creato
    
}

void new_skiplist(SkipList **list, size_t max_height, int (*compar)(const void*, const void*))
{
    *list = malloc(sizeof(SkipList));//alloco memoria per una nuova struttura skiplist. **list è un puntatore a un puntatore a skiplist
    //quindi *list si riferisce al puntatore a skiplist che verrà modificato per puntare alla memoria allocata
    (*list)->heads = (Node **)malloc(sizeof(Node *) * (max_height)); // Alloca memoria per l'array heads all'interno della SkipList. 
    //Questo array conterrà puntatori ai "nodi di testa" di ciascun livello della skip list. Il numero di questi nodi di testa è determinato da max_height.

    for(int i=0; i <= max_height; i++)//Un ciclo for che itera per ogni livello della skip list
    {
        (*list)->heads[i] = new_node(max_height,NULL); //Per ogni livello, inizializza il "nodo di testa" chiamando new_node. 
        //Questa funzione crea un nuovo nodo con un array di puntatori next che può raggiungere fino al massimo livello (max_height). Qui, il nodo viene inizializzato senza dati (NULL).
    }

    (*list)->max_level = 0; //Imposta il max_level della skip list a 0. All'inizio, il livello più alto con un nodo inserito è 0 (cioè, la skip list è vuota).
    (*list)->max_height = max_height;//(*list)->max_height = max_height: Imposta il max_height della skip list al valore di max_height passato come argomento. 
    //Questo valore definisce il numero totale di livelli possibili nella skip list.
    (*list)->compare = compar;//Imposta il campo compare della skip list con il puntatore alla funzione di confronto passata come argomento. 
}
//In conclusione, new_skiplist inizializza e alloca una nuova skip list, impostando i nodi di testa per ogni livello, definendo l'altezza massima della lista e assegnando la funzione di confronto per gestire gli elementi all'interno della lista.

void insert_skiplist(SkipList *list, void *item){

   int randomL = randomLevel(list->max_height); //genera un numero casuale che rappresenta il livello del nuovo nodo. Questo determina a quanti livelli della skip list il nuovo nodo apparterrà.
   Node *new = new_node(randomL, item); //crea un nuovo nodo. randomL è il numero di livelli a cui il nodo parteciperà, e item è il valore da inserire nel nodo.

   if(new->size > list->max_level){ //Se il livello del nuovo nodo è più alto del livello massimo attuale (max_level) della lista, max_level viene aggiornato. Ciò accade perché il nuovo nodo aggiunge un livello alla lista.

        list->max_level = new->size;

   }
    //Inserimento ordinato nella SkipList
    Node *x = *(list->heads); //Inizio dalla testa della SkipList
    for(int k = list->max_level; k >= 0; k--)//Il ciclo for scorre i livelli dalla cima verso il basso. Cerca il punto giusto per inserire il nuovo nodo a ogni livello.
    {
        //Se x->next[k] è NULL (fine del livello) o se item è minore del prossimo elemento nel livello, inserisce il nodo new in quel punto.
         if (x->next[k] == NULL || list->compare(item, x->next[k]->item) < 0) 
        {
            if(k <= new->size){ //Questa istruzione controlla se il livello corrente k in cui si sta tentando di inserire il nuovo nodo (new) è un livello a cui il nodo new appartiene.
            //new->size indica il numero di livelli a cui il nodo new partecipa (determinato casualmente quando il nodo viene creato).
            //Il controllo k <= new->size verifica se il livello corrente k è uno di quei livelli. Se vero, significa che il nodo new deve essere inserito in questo livello della skip list.

                new->next[k] = x->next[k];
                x->next[k] = new;
            }
        }
        else{ //Altrimenti, continua a spostarsi lungo il livello corrente.

                x = x->next[k];
                k++;
         }
    }
}


void clear_skiplist(SkipList **list)
{
    if (*list == NULL) //Se il puntatore alla skip list è NULL, non c'è nulla da liberare, quindi la funzione termina immediatamente.
    {
        return;
    }

    Node *testa, *next; //Liberazione dei Nodi
    for (int i = 0; i <= (*list)->max_level; ++i) //La funzione scorre ogni livello della skip list, da 0 fino a max_level.
    {
        testa = (*list)->heads[i];
        while (testa != NULL) //Per ogni livello, attraversa la lista collegata e libera la memoria di ogni nodo.
        {                     //testa tiene traccia del nodo corrente da liberare, mentre next memorizza il riferimento al prossimo nodo prima che testa venga liberato.
            next = testa->next[i]; 
            free(testa->next);//libera l'array di puntatori next all'interno del nodo testa.
            testa = next;
        }
    }
    free((*list)->heads); //Dopo aver liberato tutti i nodi, la funzione libera l'array heads che contiene i puntatori ai nodi di testa di ogni livello.
    free(*list); //Poi, libera la memoria allocata per la struttura SkipList stessa.
    *list = NULL; //Infine, imposta il puntatore alla skip list a NULL per indicare che la memoria è stata completamente liberata e la skip list non esiste più.
}//In sintesi, clear_skiplist attraversa ogni livello della skip list, libera la memoria allocata per ogni nodo, libera l'array heads, 
 //la struttura SkipList stessa e infine imposta il puntatore alla skip list a NULL. 
 //Questo assicura che tutta la memoria utilizzata dalla skip list sia restituita al sistema, prevenendo perdite di memoria.
    
    

//Ricorda:  i nodi che partecipano a più livelli permettono di attraversare la skip list più rapidamente durante le ricerche
size_t randomLevel(size_t max_height){ // determina a quanti livelli un nuovo nodo apparterrà.
    size_t lvl=0; //Inizializzazione del Livello: 0 è il lvl più basso

    while (((double)rand() / RAND_MAX) < 0.5 && lvl < max_height)//rand() genera un numero casuale tra 0 e RAND_MAX
    {//(double)rand()/RAND_MAX calcola un numero a virgola mobile tra 0.0 e 1.0.
        lvl = lvl+1; //Se questo numero è minore di 0.5 e lvl è ancora minore di max_height, il livello viene incrementato.
    }//Questo ciclo continua finché non viene generato un numero maggiore o uguale a 0.5, o finché lvl non raggiunge max_height.

    return lvl;  
    //probabilità del 50% di aumentare di un livello ogni volta. Questo assicura che i nodi più alti
    //(che partecipano a più livelli) sono meno frequenti, il che è essenziale per mantenere l'efficienza della skip list.

}

const void *search_skiplist(SkipList *list, void *item){ // sfrutta l'efficienza della skip list per trovare un elemento in modo rapido

    Node *x = *(list->heads); //Impostazione del Nodo di Partenza: La ricerca inizia dal nodo di testa più alto della skip list

    for (int i = list -> max_level; i >= 0; i--){ //scorro i livelli della SL dal più alto, procedendo verso il basso

        while (x->next[i] != NULL && list->compare(x->next[i]->item, item) < 0)
        {//scorro gli elementi finché non trovo un nodo il cui valore successivo è maggiore o uguale all'elemento cercato (item).
            x = x->next[i]; //Se il risultato è minore di 0, significa che l'elemento da cercare è 
                            //maggiore dell'elemento corrente, e quindi la ricerca continua nel livello corrente.
        }

    }//Questo processo permette di "saltare" molti nodi che non sarebbero stati saltati in una lista collegata standard.
    //Verifica dell'Elemento Trovato \/
    x = x->next[0]; //Dopo aver attraversato tutti i livelli, la funzione si sposta al livello più basso (0) per controllare il nodo immediatamente successivo.

    if(x != NULL && list->compare(x->item, item) == 0) //Se questo nodo non è NULL e il suo valore è uguale all'elemento cercato (come determinato dalla funzione compare), 
        //allora l'elemento è stato trovato e la funzione restituisce il valore.
        return x->item;
    else
        return NULL; //Se l'elemento non viene trovato, restituisce NULL

}
//In sintesi, search_skiplist utilizza l'efficienza dei livelli multipli della skip list per trovare rapidamente un elemento. 
//La funzione scorre i livelli partendo dal più alto, utilizzando i collegamenti per saltare rapidamente attraverso la lista, 
//e poi esegue la ricerca fine nel livello più basso. Questo approccio riduce significativamente il numero di confronti necessari 
//rispetto a una ricerca in una lista collegata standard.