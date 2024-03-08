#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sort.h"
// ... (Definizione della struttura Record e delle funzioni di confronto)

typedef struct
{
    int id;
    char *field1; //String
    int field2;  //int
    float field3;  //float
}Record;

int compare_float(const void *p1, const void *p2) {
    if (p1 == NULL || p2 == NULL) {
        printf("Il parametro del confronto non può essere NULL\n");
        exit(EXIT_FAILURE);
    }

    if (((Record *)p1)->field3 < ((Record *)p2)->field3)
        return -1;
    else if (((Record *)p1)->field3 > ((Record *)p2)->field3)
        return 1;
    else
        return 0;
}

int compare_int(const void *p1, const void *p2){
  if(p1==NULL){
    printf("il primo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  if(p2==NULL){
    printf("il secondo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  }

  int compare_char(const void *p1, const void *p2){
  if(p1==NULL){
    printf("il primo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  if(p2==NULL){
    printf("il secondo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  return strcmp(((Record*)p1)->field1, ((Record*)p2)->field1);
}

void print_records(Record *arr, int size) {
    for (int i = 0; i < size; i++) {
        printf("%d, %s, %d, %f\n", arr[i].id, arr[i].field1, arr[i].field2, arr[i].field3);
    }
}

void test_merge() {
    printf("Test unitario: merge\n");

    Record left[] = {
        {1, "testA", 10, 1.5},
        {3, "testC", 30, 3.5}
    };
    int left_length = sizeof(left) / sizeof(left[0]);

    Record right[] = {
        {2, "testB", 20, 2.5},
        {4, "testD", 40, 4.5}
    };
    int right_length = sizeof(right) / sizeof(right[0]);

    printf("Array di input (sinistra):\n");
    print_records(left, left_length);

    printf("Array di input (destra):\n");
    print_records(right, right_length);

    Record *result = malloc((left_length + right_length) * sizeof(Record));

    merge(left, right, left_length, right_length, compare_float, sizeof(Record));

    printf("Array dopo la fusione:\n");
    print_records(left, left_length + right_length);

    free(result);

    printf("Test completato.\n\n");
}

// Funzione di confronto per float


// Test unitario per merge_binary_insertion_sort con array di interi
void test_merge_binary_insertion_sort_int() {
    printf("Test unitario: merge_binary_insertion_sort con array di interi\n");

    Record records[] = {
        {2, "testB", 20, 2.5},
        {1, "testA", 10, 1.5},
        {4, "testD", 40, 4.5},
        {3, "testC", 30, 3.5}
    };
    int length = sizeof(records) / sizeof(records[0]);

    printf("Array di input:\n");
    print_records(records, length);

    merge_binary_insertion_sort(records, length, sizeof(Record), 2, compare_int);

    printf("Array ordinato:\n");
    print_records(records, length);

    printf("Test completato.\n\n");
}

// Test unitario per merge_binary_insertion_sort con array di stringhe
void test_merge_binary_insertion_sort_string() {
    printf("Test unitario: merge_binary_insertion_sort con array di stringhe\n");

    Record records[] = {
        {2, "testB", 20, 2.5},
        {1, "testA", 10, 1.5},
        {4, "testD", 40, 4.5},
        {3, "testC", 30, 3.5}
    };
    int length = sizeof(records) / sizeof(records[0]);

    printf("Array di input:\n");
    print_records(records, length);

    merge_binary_insertion_sort(records, length, sizeof(Record), 1, compare_char);

    printf("Array ordinato:\n");
    print_records(records, length);

    printf("Test completato.\n\n");
}

// Test unitario per merge_binary_insertion_sort con array di float
void test_merge_binary_insertion_sort_float() {
    printf("Test unitario: merge_binary_insertion_sort con array di float\n");

    Record records[] = {
        {2, "testB", 20, 2.5},
        {1, "testA", 10, 1.5},
        {4, "testD", 40, 4.5},
        {3, "testC", 30, 3.5}
    };
    int length = sizeof(records) / sizeof(records[0]);

    printf("Array di input:\n");
    print_records(records, length);

    merge_binary_insertion_sort(records, length, sizeof(Record), 3, compare_float);

    printf("Array ordinato:\n");
    print_records(records, length);

    printf("Test completato.\n\n");
}

int main() {
    test_merge();
    test_merge_binary_insertion_sort_int();
    test_merge_binary_insertion_sort_string();
    test_merge_binary_insertion_sort_float();

    return 0;
}