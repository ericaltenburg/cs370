 /**
  * Authors     : 	Eric Altenburg, Hamzah Nizami, and Constance Xu
  * File        : 	hashit.c
  * Description : 	SPOJ - hashit
  * Pledge      :		I pledge my honor that I have abided by the Stevens Honor System.
  * Date        : 	3 March 2020
  */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

#define TABLE_SIZE 101
#define MAX_INPUT_LENGTH 15 // SPOJ Spec

//Dr. B provided this struct 
typedef struct {
	char * keys[TABLE_SIZE];
	int num_keys;
} hash_set;

void clear_table(hash_set *set){ //ew valgrind.
    //Iterate through the entire table and free up everything in the table and set it to NULL
    //Once you are done with iterating through the table and freeing everything up, set num_keys to 0.
    for (int i = 0; i < 101; ++i) { 
        if (set->keys[i]) { 
            free(set->keys[i]);
            set->keys[i] = NULL;
        }
    }
    set->num_keys = 0;
}

// Generates hash value for a given key by doing the following:
// Hash(key) = h(key) % TABLE_SIZE
// where h(key) = 19 * (ASCII(key[i]) * i+1) + ... + (ASCII(key[i]) * i+1)
int hash (char *key) {
    int length = strnlen(key, MAX_INPUT_LENGTH);
    unsigned int ascii_mults = 0;
    for (int i = 0; i < length; ++i) {
        ascii_mults += key[i] * (i+1);
    }
    unsigned int hash_value = (19 * ascii_mults) % TABLE_SIZE;
    return hash_value;
}

//We repeatedly calculate this: (hash(key) * j * j + 23 * j) % 101
//That's really annoying to type, so I made this function.
int find_formula_value(int j, char *key){ 
    return (hash(key) + j * j + 23 * j) % TABLE_SIZE;
}

/*Iterates through a to see if a there is something at a specific index and returns a bool 
indicating if there is something at that index. We iterate until 20 since that's the max
amount of collisions we can possibly get. 
*/
bool key_exists(hash_set *set, char *key){ 
    for (int i = 0; i < 20; ++i) { 
        int index = find_formula_value(i, key);
        if (set->keys[index] && strcmp(set->keys[index], key) == 0) {    //is there something at that index? does the element at that index match the key?
            return true;
        }
    }
    return false;
}

/* 
Same as key_exists function, but instead of returing true, we return the index instead and -1 if nothing found.
Is this redundant? Yeah. Are we too lazy to get rid of it? Yeah. Do we feel bad about it? No.
*/
int search(hash_set *set, char *key){ 
    for (int i = 0; i < 20; ++i) { 
        int index = find_formula_value(i, key);
        if (set->keys[index] && strcmp(set->keys[index], key) == 0) { 
            return index; 
        }
    }
    return -1;
}

/* 
insert the key. We first search to see if the key exists in the set. If it does, return the index. 
If it doesn't, we then iterate through until 20 (max collisions), find the next place that is free in the hash_set
and then insert the key in there and increment the num_Keys amount and return index. Return -1 if there is an error, or -2 if it was already inserted.
*/
int insert_key(hash_set *set, char *key){ 
    int index = search(set, key);
    if (index != -1) { 
        return -2;
    }
    for (int i = 0; i < 20; ++i) {
        index = find_formula_value(i, key);
        if (!set->keys[index]) {
                set->keys[index] = key;
                ++(set->num_keys);
                return index;
        }
    }
    return -1;
}
/* 
Delete the key. First checks if the key exists in the set. If it does exist, we search for that key and then free that index, set that index to NULL
and decrement num_keys. Return -1 if there is an error.
*/
int delete_key(hash_set *set, char *key){ 
    bool does_key_exist = key_exists(set, key);
    if (does_key_exist) { 
        int index = search(set, key);
        free(set->keys[index]);
        set->keys[index] = NULL;
        --(set->num_keys);
        return index;
    }
    return -1;
}

/* 
Iterate through the entire table and print a key if there is one. Fairly straightforward.
*/
void display_keys (hash_set * set) {
    for (int i = 0; i < 101; ++i) {
        if (set->keys[i]) {
            printf("%i:%s\n", i, set->keys[i]);
        }
    }
}

int main(){ 
    int test_cases, operations; //test_cases has the amt of test cases and operations is amount of operations per test case. Just adhering to SPOJ requirements. 
    char buff [MAX_INPUT_LENGTH + 5];
    char *comp; // Used for strncpy

    hash_set htable;
    memset(&htable, 0, sizeof(hash_set)); //Create a hash_set using memset
    
    scanf("%d", &test_cases); //grab amount of test cases

    for (int i = 0; i < test_cases; ++i) { //iterate through test cases
        scanf("%d", &operations); //get amt of operations 

        for (int j = 0; j < operations; ++j) { //iterate through operations 
            scanf("%s", buff); //get the operation (ADD OR DEL)

            if (buff[0] == 'A') {    //we are adding 
                comp = (char*)malloc(MAX_INPUT_LENGTH + 5); 
            	strncpy(comp, buff+4, MAX_INPUT_LENGTH+5); //Copy over the buff to comp. We can now use this as a parameter for insert and delete

            	int did_it_work = insert_key(&htable, comp);
            	if (did_it_work < 0) { // did not insert, free up
            		free(comp);
            	}
            } else { //deleting
                delete_key(&htable, buff+4);
            }
        }

        // Display stuff
        printf("%d\n", htable.num_keys);
        display_keys(&htable);         
        clear_table(&htable);
    }

    return 0;
}

