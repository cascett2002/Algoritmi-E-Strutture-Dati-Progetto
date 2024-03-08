#include <assert.h>
#include "skiplist.h"

int compare_char(const void *p1, const void *p2) {
    return strcmp((char *)p1, (char *)p2);
}


void test_new_skiplist() {
    // Test per la funzione new_skiplist
    printf("Test unitario: new_skiplist\n");

    size_t max_height = 5;
    SkipList *list = NULL;
    new_skiplist(&list, max_height, compare_char);

    // Verifica che la nuova SkipList sia stata creata correttamente
    assert(list != NULL);
    assert(list->heads != NULL);
    assert(list->max_level == 0);
    assert(list->max_height == max_height);
    assert(list->compare == compare_char);

    // Verifica che gli array di puntatori siano inizializzati correttamente

    printf("Test completato.\n\n");
}

void test_insert_skiplist() {
    // Test per la funzione insert_skiplist
    printf("Test unitario: insert_skiplist\n");

    SkipList *list = NULL;
    new_skiplist(&list, 5, compare_char);

    char *item1 = "testA";
    char *item2 = "testB";
    char *item3 = "testC";

    // Inserisce elementi nella SkipList
    insert_skiplist(list, &item1);
    insert_skiplist(list, &item2);
    insert_skiplist(list, &item3);

    // Verifica che gli elementi siano stati inseriti correttamente
    const void *search1 = search_skiplist(list, &item1);
    const void *search2 = search_skiplist(list, &item2);
    const void *search3 = search_skiplist(list, &item3);

    assert(search1 != NULL);
    assert(search2 != NULL);
    assert(search3 != NULL);

    printf("Test completato.\n\n");
}

void test_search_skiplist() {
    // Test per la funzione search_skiplist
    printf("Test unitario: search_skiplist\n");

    SkipList *list = NULL;
    new_skiplist(&list, 5, compare_char);

    char *item1 = "testA";
    char *item2 = "testB";
    char *item3 = "testC";

    // Inserisce elementi nella SkipList
    insert_skiplist(list, item1);
    insert_skiplist(list, item2);
    insert_skiplist(list, item3);

    // Verifica che la funzione di ricerca restituisca i risultati corretti
    const void *search1 = search_skiplist(list, item1);
    const void *search2 = search_skiplist(list, item2);
    const void *search3 = search_skiplist(list, item3);

    assert(search1 != NULL);
    assert(search2 != NULL);
    assert(search3 != NULL);

    // Verifica che la funzione restituisca NULL per un elemento non presente
    char *item_not_in_list = "not_in_list";
    const void *search_not_in_list = search_skiplist(list, item_not_in_list);
    assert(search_not_in_list == NULL);

    printf("Test completato.\n\n");
}

void test_clear_skiplist() {
    // Test per la funzione clear_skiplist
    printf("Test unitario: clear_skiplist\n");

    SkipList *list = NULL;
    new_skiplist(&list, 5, compare_char);

    char *item1 = "testA";
    char *item2 = "testB";
    char *item3 = "testC";

    // Inserisce elementi nella SkipList
    insert_skiplist(list, item1);
    insert_skiplist(list, item2);
    insert_skiplist(list, item3);

    // Libera la memoria della SkipList
    clear_skiplist(&list);

    // Verifica che la lista sia stata deallocata correttamente
    assert(list == NULL);

    printf("Test completato.\n\n");
}


int main() {
    // Esegui tutti gli Unit Test
    test_new_skiplist();
    test_insert_skiplist();
    test_search_skiplist();
    test_clear_skiplist();

    return 0;
}