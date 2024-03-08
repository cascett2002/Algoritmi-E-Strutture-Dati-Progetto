#include "sort.h"

void copy(void *x, void *y, int size){
    unsigned char *a=x, *b=y;
    for(int i=0; i<size; i++)
       b[i]=a[i];
}

void *merge(void *v, void *w, int left, int right, int(*compar)(const void *, const void *), int t_size){
    int i=0, j=0, k=0;
    int length=left+right;
    void *arr=malloc(length*t_size);
    while(k<length){
        if (j == right)
        {
            copy(v + i * t_size, arr + k * t_size, t_size);
            if (i < left)
                i++;
        }
        else if (i == left)
        {
            copy(w + j * t_size, arr + k * t_size, t_size);
            if (j < right)
                j++;
        }
        else if (compar(v + i * t_size, w + j * t_size) < 0)
        {
            copy(v + i * t_size, arr + k * t_size, t_size);
            if (i < left)
                i++;
        }
        else if (compar(v + i * t_size, w + j * t_size) >= 0)
        {
            copy(w + j * t_size, arr + k * t_size, t_size);
            if (j < right)
                j++;
        }
        k++;
    }
    for(int i=0; i<length;i++)
    copy(arr+i*t_size,v+i*t_size,t_size);

    free(arr);
    return v;
}
int binary_search(void *arr, void *el, int l, int r, int t_size, int(*compar)(const void *, const void *)){
    if(r>=l){
        int m=(l+r)/2;
        if(compar(arr+t_size*m,el)==0)     
        return m;
        if(compar(arr+t_size*m,el)>0)
        return binary_search(arr,el,l,m-1,t_size,compar);
        return binary_search(arr,el,m+1,r,t_size,compar);
    }
    return l;
}
void switch_element(void *x, void *y, int t_size){
    unsigned char *a=x, *b=y, tmp;
    for(int i=0; i<t_size; i++){
        tmp=a[i];
        a[i]=b[i];
        b[i]=tmp;
    }
}

void *binary_insertion(void *arr, int size, int t_size, int(*compar)(const void*, const void*)){ //pseudocodice tradotto appr
    if(size==0) return arr;
    if(arr==NULL) return NULL;
    for(int i=1; i<size; i++){
        int start=0, end=i-1;
        int pos=binary_search(arr, arr+i*t_size, start, end, t_size, compar);
        for(int k=i; k>pos; k--)
        switch_element((arr + k*t_size),(arr+(k-1)*t_size),t_size);
    }
    return arr;
}


void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void *, const void*)) {
    if(base!=NULL && nitems!=0){
    void *mid=base+((nitems/2)*size);
    if(nitems>k){
        if(nitems>1){
        merge_binary_insertion_sort(base, nitems/2,size,k,compar);
        merge_binary_insertion_sort(mid,(nitems+1)/2,size,k,compar);
        merge(base, mid, nitems/2,(nitems+1)/2, compar, size);
        }
    }
    else{
        binary_insertion(base, nitems, size, compar);
      }
    }
}