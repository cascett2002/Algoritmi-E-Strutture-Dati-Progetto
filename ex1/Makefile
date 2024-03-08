CC = gcc -Ofast #ottimizzazione Ofast
K ?= 20 
FIELD ?= 2
#mkdir: credo directory

#seconda riga run: compilo main e sort e creo l'eseguibile in bin
#terza riga run: avvio l'eseguibile creato dando come parametri il file di input (record) e il file di output(sorted), k e field.
run:
	mkdir -p bin
	$(CC) ./src/main.c ./src/sort.c -o bin/main_ex1.out  
	./bin/main_ex1.out ./src/data/records.csv src/data/sorted.csv $(K) $(FIELD)

#rm: rimozione, rf: comandi di rm. r:rimozione della directory e di quello che sta dentro, f: forza la rimozione senza chiedere conferma e ignorando directory o file inesistenti
#clear: vabbè pulisco il terminale
delete:
	rm -rf bin
	rm -rf tests
	clear

tests:
	mkdir -p tests
	$(CC) ./src/unit_test.c ./src/sort.c -o tests/tests.out
	./tests/tests.out 