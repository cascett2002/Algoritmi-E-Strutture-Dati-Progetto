#include "sort.h"
#include <time.h>
#define Elementi 20000000

typedef struct
{
    int id;
    char *field1; //String
    int field2;  //int
    float field3;  //float
}Record;

int compare_float(const void *p1, const void *p2){
  if(p1==NULL){
    printf("il primo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  if(p2==NULL){
    printf("il secondo parametro del compare non può essere NULL \n");
    exit(EXIT_FAILURE);
  }
  
  if(((Record*)p1)->field3 < ((Record*)p2)->field3)
  return -1;
  else if (((Record*)p1)->field3 > ((Record*)p2)->field3)
  return 1;
  else return 0;
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
  
  if(((Record*)p1)->field2 < ((Record *)p2)->field2)
  return -1;
  else if(((Record*)p1)->field2 > ((Record*)p2)->field2)
  return 1;
  else return 0;
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

int (*get_comparator(size_t field))(const void *, const void *){
  switch (field)
  {
  case 1:
  return compare_char;
  case 2:
  return compare_int;
  case 3:
  return compare_float;
  default:
  printf("Invalid field value: %ld\n", field);
  return NULL; //field not valid
  }
}

void load_record(Record *record, int id, char *str, int intero, float flt, int i){
  record[i].id = id;
  record[i].field1 = malloc((30*sizeof(char)));
  strncpy(record[i].field1, str,30);
  record[i].field2 = intero;
  record[i].field3 = flt;
}

void sort_records(FILE *infile, FILE *outfile, size_t k, size_t field)
{
  Record *record = malloc(Elementi * sizeof(Record));
  if (infile == NULL || outfile == NULL)
    {
        printf("File error...");
        free(record);
        return;
    }
  printf("i file esistono \n Lettura File in corso...\n");

  //mi serve trasportare le righe del file record in un array
  char linea[2000];
  int i=0;

  while (((fgets(linea, sizeof(linea), infile)) != NULL))
  {
    //gli elementi in ogni riga sono separati da virgole
    int id = atoi(strtok(linea,","));
    char *str = strdup(strtok(NULL,","));
    int intero = atoi(strtok(NULL,","));
    float flt = atof(strtok(NULL,","));

    load_record(record,id,str,intero,flt,i);
    free(str);
    i++;
  }


  printf("file letto!\n");
  int length=i;
  

  switch (field)
  {
  case 1:
  printf("|-Sorting String-|\n");
  break;
  case 2:
  printf("|-Sorting Int-|\n");
  break;
  case 3:
  printf("|-Sorting Float-|\n");
  break;

  default:
    printf("il field inserito non è valido, field supportati: 1-2-3");
    free(record);
    return;
  }

  if(field>=1 && field<=3){
    clock_t start, end;
    start = clock();
    merge_binary_insertion_sort(record, length, sizeof(Record),k, get_comparator(field));
    end = clock();
    printf("Durata: %fs\n", ((double)(end - start)) / CLOCKS_PER_SEC);

    for (int i = 0; i < length; i++)
        {
            fprintf(outfile, "%d,%s,%d,%f\n", record[i].id, record[i].field1, record[i].field2, record[i].field3);
        }

        printf("Fine creazione del file ordinato!!\nNumRighe:%d\n", i);
        printf("Libero la memoria...\n");
        for (int j = 0; j < length; j++)
        {
            free(record[j].field1);
        }
        free(record);
        printf("Fine!!\n");
  }
}

int main(int argc,char* argv[]){
    
  FILE *infile = fopen(argv[1], "r");
  FILE *outfile = fopen(argv[2], "w");
  int k = atoi(argv[3]);
  int field = atoi(argv[4]);
  
  sort_records(infile, outfile, k, field);
  fclose(outfile);
  fclose(infile);  

  return 0;
}