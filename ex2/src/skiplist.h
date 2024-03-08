#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct SkipList{
  struct Node **heads; //sono i puntatori di inizio della SkipList per ogni livello fino a max_height (** array di puntatori)
  size_t max_level; //è il massimo numero di puntatori che al momento ci sono in un singolo nodo
  size_t max_height;  //è una costante che definisce il massimo numero di puntatori che possono esserci in un singolo nodo 
  int (*compare)(const void*, const void*);  //è il criterio secondo cui ordinare i dati 
}SkipList;

typedef struct Node {
  struct Node **next; //è l'array di puntatori in un dato nodo della SkipList
  size_t size;  // è il numero di puntatori in un dato nodo della SkipList
  void *item;  //è il dato memorizzato in un dato nodo della SkipList
}Node;


void new_skiplist(SkipList **list, size_t max_height, int (*compar)(const void*, const void*));

void clear_skiplist(SkipList **list);

void insert_skiplist(SkipList *list, void *item);

const void* search_skiplist(SkipList *list, void *item);

size_t randomLevel(size_t max_height);

Node *new_node(size_t size, void *item);

