#include "skiplist.h"
#include <time.h>

int compare_char(const void *p1,const void *p2){
  
  return strcmp((char *)p1, (char *)p2);
}

void find_errors(FILE *dictfile, FILE *textfile, size_t max_height){

  if (dictfile == NULL)
    {
        printf("Apertura dictfile non riuscita!\n");
        return;
    }
    if (textfile == NULL)
    {
        printf("Apertura textfile non riuscita\n");
        return;
    }
    
    SkipList *lista = NULL;
    new_skiplist(&lista, max_height, compare_char);
    
     time_t start, end;
     char linea[2000];
     char *string;
    start=clock();
    while ((fgets(linea,sizeof(linea),dictfile)) != NULL)
    {
      linea[strcspn(linea, "\r\n")] = '\0';
      string = strdup(linea);
 //   printf("%s", string);
      insert_skiplist(lista, (void*)string);
    }
    end=clock();
   printf("\n");
   printf("Dizionario aggiunto alla lista!\n");
   printf("\n");
   printf("Durata: %fs\n", ((double)(end - start)) / CLOCKS_PER_SEC);
   printf("\n");

   printf("Alla ricerca di errori...\n");
   printf("\n");

   char word[2000]; //sistemare
   char character;
   int i=0;
   start=clock();
   while (((character = fgetc(textfile)) != EOF))
    {
       if(isspace(character) || ispunct(character))//spazi e punteggiature fanno terminare la struttura della parola
       {  
         if(strlen(word) > 0)
         {

         // printf("%s", word);
          const void *search = search_skiplist(lista, (void *)word);
         
          //printf("%s", (const char *)search);

          if (search == NULL){

            printf("errore n°%d -> %s\n", i, word);
            i++;

          }

          memset(word, 0, 2000);
         
         }
       }
       else
       {
        character = tolower(character); //nel dizionario tutte le parole sono scritte in minuscolo
        strncat(word, &character, sizeof(char));
       }
    }
    end=clock();
    printf("\n");
    printf("Durata: %fs\n", ((double)(end - start)) / CLOCKS_PER_SEC);
    printf("\n");
    printf("-----------|FINE!|------------\n");
    clear_skiplist(&lista);

}

int main(int argc, char *argv[])
{
    FILE *dictfile = fopen(argv[1], "r");
    FILE *textfile = fopen(argv[2], "r");
    int max_height = atoi(argv[3]);

     if(argc!=4){
      printf("Errore: argomenti non validi");
      exit(EXIT_FAILURE);
      }

    find_errors(dictfile, textfile, max_height);

    fclose(dictfile);
    fclose(textfile);
    return 0;
}