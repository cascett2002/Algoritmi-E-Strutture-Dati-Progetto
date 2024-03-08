#include "sort.h"

void copy(void *x, void *y, int size){
    unsigned char *a=x, *b=y;
    for(int i=0; i<size; i++)
       b[i]=a[i];
}
//v,w:puntatori ai sottoarray da fondere; left,right:dimensioni dei due array; compar:puntatore a funzione per confronti; t_size:dimensione in byte di ogni el dell'array
void *merge(void *v, void *w, int left, int right, int(*compar)(const void *, const void *), int t_size){
    int i=0, j=0, k=0;
    int length=left+right;
    void *arr=malloc(length*t_size); //alloco dinamicamente un array temporaneo per contenere gli elementi fusi
    while(k<length){ //fino a quando tutti gli elementi dei sottoarray non sono stati processati
        if (j == right) //sottoarray w esaurito
        {
            copy(v + i * t_size, arr + k * t_size, t_size);
            if (i < left)
                i++;
        }
        else if (i == left) //sottoarray v esaurito
        {
            copy(w + j * t_size, arr + k * t_size, t_size);
            if (j < right)
                j++;
        }
        else if (compar(v + i * t_size, w + j * t_size) < 0) //se l'elemento in v è minore di quello in w
        {
            copy(v + i * t_size, arr + k * t_size, t_size); //copio v in arr e incremento i
            if (i < left)
                i++;
        }
        else if (compar(v + i * t_size, w + j * t_size) >= 0) //v maggioreuguale di w
        {
            copy(w + j * t_size, arr + k * t_size, t_size); //copio w in arr e incremento j
            if (j < right)
                j++;
        }
        k++; //incremento k dopo ogni iterazione 
    }
    for(int i=0; i<length;i++) //length: lunghezza totale dei sottoarray fusi
    copy(arr+i*t_size,v+i*t_size,t_size); //copio l'array temporaneo arr in v elemento per elemento

    free(arr);
    return v;
}
//In sintesi: se uno dei sottoarray è esaurito, si copiano gli elementi del rimanente
//Altrimenti, si confrontano gli elementi dei due sottoarray e si copia il minore nel temporaneo

//el:puntatore el da cercare; l,r: limiti della porzione di array da visitare;
int binary_search(void *arr, void *el, int l, int r, int t_size, int(*compar)(const void *, const void *)){
    if(r>=l){ //ci sono ancora elementi da esaminare
        int m=(l+r)/2; //indice medio
        if(compar(arr+t_size*m,el)==0) //elemento trovato, ritorno m;     
        return m;
        if(compar(arr+t_size*m,el)>0) //non trovato, cerco nella metà inferiore e superiore:(l,m-1)e(m+1,r) 
        return binary_search(arr,el,l,m-1,t_size,compar);
        return binary_search(arr,el,m+1,r,t_size,compar);
    }
    return l; //se l'el non viene trovato, ritorno l: indice di inserimento che mantiene l'array ordinato
}//In sintesi: cerco l'elemento a partire dal punto medio. Se l'elemento da cercare è minore del corrente
//allora cerco nella metà inferiore, altrimenti cerco nella metà superiore.


void switch_element(void *x, void *y, int t_size){
    unsigned char *a=x, *b=y, tmp;
    for(int i=0; i<t_size; i++){
        tmp=a[i];
        a[i]=b[i];
        b[i]=tmp;
    }
}

void *binary_insertion(void *arr, int size, int t_size, int(*compar)(const void*, const void*)){
    if(size==0) return arr;
    if(arr==NULL) return NULL;
    for(int i=1; i<size; i++){ //dal secondo indice
        int start=0, end=i-1;
        int pos=binary_search(arr, arr+i*t_size, start, end, t_size, compar); //Determina la posizione corretta (pos) per l'elemento corrente
        for(int k=i; k>pos; k--) //sposta gli elementi per fare spazio e inserire l'elemento corrente nella posizione corretta.
        switch_element((arr + k*t_size),(arr+(k-1)*t_size),t_size); // scambia gli elementi per spostarli.
    }
    return arr; //ritorno array ordinato
}
//In sintesi: parto dal secondo indice. Determino la posizione dell'elemento tramite binary search e scambio l'elemento con quello nella pos corretta

//base:PuntatoreArray, nItems:NumElementi, compar: puntatore a funzione usato per confrontare due elementi generici, permette alla funzione di lavorare su diversi tipi di dati
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*)) {
    if(base!=NULL && nitems!=0){
    void *mid=base+((nitems/2)*size); //calcolo del PuntoMedio oer la divisione in due nel mergesort
    if(nitems>k){ //si esegue il merge b i sort
        if(nitems>1){ //divide ricorsivamente l'array in due metà: la funzione chiama se stessa per ordinare ciascuna metà
        merge_binary_insertion_sort(base, nitems/2,size,k,compar);
        merge_binary_insertion_sort(mid,(nitems+1)/2,size,k,compar);
        merge(base, mid, nitems/2,(nitems+1)/2, compar, size); //dopo che entrambe le metà sono ordinate, merge le unisce in un unico array ordinato
        }
    }
    else{ //se il numero di elementi è piccolo si usa il BI
        binary_insertion(base, nitems, size, compar); 
      }
    }
}