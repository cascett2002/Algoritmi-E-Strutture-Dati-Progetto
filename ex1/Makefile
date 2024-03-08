CC = gcc -Ofast
K ?= 10
FIELD ?= 1

run:
	mkdir -p bin
	$(CC) ./src/main.c ./src/sort.c -o bin/main_ex1.out 
	./bin/main_ex1.out ./src/data/records.csv src/data/sorted.csv $(K) $(FIELD)

delete:
	rm -rf bin
	rm -rf tests
	clear

tests:
	mkdir -p tests
	$(CC) ./src/unit_test.c ./src/sort.c -o tests/tests.out
	./tests/tests.out 