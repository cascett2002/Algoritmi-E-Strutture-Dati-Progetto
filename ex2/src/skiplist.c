#include "skiplist.h"

Node *new_node(size_t size,void  *item){

    Node *newNode = (Node *)malloc(sizeof(Node));
    newNode->next = (Node **)malloc(sizeof(Node *) * (size +1 ));
    newNode->size = size;
    newNode->item = item;

    for (int i = newNode->size; i >= 0; i--)
    {
        newNode->next[i] = NULL;
    }

    return newNode;
    
}

void new_skiplist(SkipList **list, size_t max_height, int (*compar)(const void*, const void*))
{
    *list = malloc(sizeof(SkipList));
    (*list)->heads = (Node **)malloc(sizeof(Node *) * (max_height));

    for(int i=0; i <= max_height; i++)
    {
        (*list)->heads[i] = new_node(max_height,NULL);
    }

    (*list)->max_level = 0;
    (*list)->max_height = max_height;
    (*list)->compare = compar;
}

void insert_skiplist(SkipList *list, void *item){

   int randomL = randomLevel(list->max_height);
   Node *new = new_node(randomL, item);

   if(new->size > list->max_level){

        list->max_level = new->size;

   }

    Node *x = *(list->heads);
    for(int k = list->max_level; k >= 0; k--)
    {
         if (x->next[k] == NULL || list->compare(item, x->next[k]->item) < 0) 
        {
            if(k <= new->size){

                new->next[k] = x->next[k];
                x->next[k] = new;
            }
        }
        else{

                x = x->next[k];
                k++;
         }
    }
}


void clear_skiplist(SkipList **list)
{
    if (*list == NULL)
    {
        return;
    }

    Node *testa, *next;
    for (int i = 0; i <= (*list)->max_level; ++i)
    {
        testa = (*list)->heads[i];
        while (testa != NULL)
        {
            next = testa->next[i];
            free(testa->next);
            testa = next;
        }
    }
    free((*list)->heads);
    free(*list);
    *list = NULL;
}
    
    


size_t randomLevel(size_t max_height){

    size_t lvl=0;

    while (((double)rand() / RAND_MAX) < 0.5 && lvl < max_height)
    {
        lvl = lvl+1;
    }

    return lvl;  

}

const void *search_skiplist(SkipList *list, void *item){

    Node *x = *(list->heads);

    for (int i = list -> max_level; i >= 0; i--){

        while (x->next[i] != NULL && list->compare(x->next[i]->item, item) < 0)
        {
            x = x->next[i];
        }

    }

    x = x->next[0];

    if(x != NULL && list->compare(x->item, item) == 0)
        return x->item;
    else
        return NULL;

}