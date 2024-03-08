CC = gcc -g -Ofast
max_height?=10

run:
	mkdir -p bin
	$(CC) src/main.c src/skiplist.c -o bin/main_ex2.out
	./bin/main_ex2.out src/data/dictionary.txt src/data/correctme.txt $(max_height)

delete:
	rm -rf bin
	rm -rf tests
	clear

tests:
	mkdir -p tests
	$(CC) ./src/unit_test.c ./src/skiplist.c -o tests/tests.out
	./tests/tests.out 