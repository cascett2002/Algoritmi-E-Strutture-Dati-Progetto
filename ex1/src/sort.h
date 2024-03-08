#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void switch_element(void *x, void *y, int t_size);
void copy(void *x, void *y, int size);
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));
int binary_search(void *arr, void *el, int l, int r, int t_size, int (*compar)(const void *, const void *));
void *binary_insertion(void *arr, int size, int t_size, int (*compar)(const void *, const void *));
void *merge(void *v, void *w, int left, int right, int (*compar)(const void *, const void *), int t_size);
