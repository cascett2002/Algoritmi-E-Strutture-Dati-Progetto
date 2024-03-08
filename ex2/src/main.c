#include "skiplist.h"
#include <time.h>

int compare_char(const void *p1,const void *p2){
  
  return strcmp((char *)p1, (char *)p2);
}

void find_errors(FILE *dictfile, FILE *textfile, size_t max_height){
//corretta apertura dei file
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
    new_skiplist(&lista, max_height, compare_char); //inizializzo una nuova skiplist con altezzamassima indicata nel main (parametro makefile)
    
     time_t start, end; //misuro il tempo impiegato per leggere ogni riga dal dizionario dictfile e inserire le parole nella skiplist
     char linea[2000];
     char *string;
    start=clock();
    while ((fgets(linea,sizeof(linea),dictfile)) != NULL) //fgets leggere una riga alla volta dal file e la memorizza in linea
    {
      linea[strcspn(linea, "\r\n")] = '\0'; //trova il primo carattere di nuova riga o ritorno a capo nella stringa linea e lo sostituisce con il terminatore di stringa \0, rimuovendo così eventuali caratteri di nuova riga.
      string = strdup(linea); //crea una copia della stringa linea (necessaria perché la skip list conserverà i puntatori a queste stringhe)
 //   printf("%s", string);
      insert_skiplist(lista, (void*)string); //inserisco la nuova stringa nella skiplist
    }
    end=clock();
   printf("\n");
   printf("Dizionario aggiunto alla lista!\n");
   printf("\n");
   printf("Durata: %fs\n", ((double)(end - start)) / CLOCKS_PER_SEC);
   printf("\n");

   printf("Alla ricerca di errori...\n");
   printf("\n");

   char word[2000]; //qui convservo temporaneamente le parole lette dal file di testo
   char character; //usato per leggere ogni carattere dal file di testo
   int i=0; //tiene traccia del numero di errori
   start=clock();
   while (((character = fgetc(textfile)) != EOF)) //fino a quando non raggiungo la fine del file
    {
       if(isspace(character) || ispunct(character))//spazi e punteggiature fanno terminare la struttura della parola
       {//HO UN DUBBIO SUL COMMENTO DELL'IF QUA SOTTO MA CREDO SIA GIUSTO
         if(strlen(word) > 0) //se la lunghezza è >0 allora contiene una parola completa
         {

         // printf("%s", word);
          const void *search = search_skiplist(lista, (void *)word); //cerco la parola nella skiplist
         
          //printf("%s", (const char *)search);

          if (search == NULL){ //la parola non è presente, viene incrementato l'indice degli errori

            printf("errore n°%d -> %s\n", i, word);
            i++;

          }

          memset(word, 0, 2000); //azzero word per prepararlo alla ricerca della prossima parola
         
         }
       }
       else //se il carattere letto non è nè uno spazio nè un car di punteggiatura, viene convertito in minuscolo
       {
        character = tolower(character); //nel dizionario tutte le parole sono scritte in minuscolo
        strncat(word, &character, sizeof(char)); //aggiungo il carattere alla fine di word usando strncat
       }
    }
    //In sintesi, il ciclo while legge il testo carattere per carattere, costruisce le parole e verifica se ogni parola è 
    //presente nel dizionario rappresentato dalla skip list. Le parole non trovate nel dizionario vengono segnalate come errori.
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