/* 
Authors: Eric Altenburg, Hamzah Nizami, Constance Xu
CS370 HW 3: Contacts
February 10th, 2020
I pledge my honor that I have abided by the Stevens Honor System.
*/
#include <assert.h>
#include <limits.h>
#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ALPHABET_LENGTH    26
#define OPERATION_BUF_SIZE  7 /* Large enough to cover the word 'search' and '\0' */
#define NAME_BUF_SIZE      22

/* Basic trie node -- also, keep track of number of nodes below this one. */
typedef struct node {
    int prefix_count;
    int children_amount; //tells us how many children a certain node may have.
    /* Allocate +1 for the the pointer to the end-of-string marker. Needed
       for the search feature. */
    struct node *children[ALPHABET_LENGTH + 1];
    bool end_word; //basically finds whether we have reached the end of the word. relevant for the search function for denoting whether a search term actually inputted
} trie_node;

//iteration is a lil nutjob in C aPpArentlY so we went recurssive with this assignment. 

trie_node* create(){    //create our delicious trie with an array of size 26 (size of alphabet) mmmmm toasty 
    trie_node* trie = (trie_node*) malloc(sizeof(trie_node));
    trie->prefix_count=0; //we don't use this, but Dr. B had it in his stub so we decided to be respectful and keep it (even though we don't use it)
    trie->end_word = false; //this will become very relevant for our search function
    for(int i = 0; i < 26; i++){ 
        trie->children[i] = NULL;
    }
    return trie;
}
void delete_trie(trie_node* root){ 
    //delete the trie at the end so we don't get some valgrind errors
    for(int i = 0; i < 26; i++){ 
        if(root->children[i] != NULL){ 
            delete_trie(root->children[i]);
        }
    }
    free(root);
}

int find(trie_node *root, char* query){ //first hackerrank problem! recursively go through our query until it's empty and then return the children amount!
    if(root == NULL){ 
        return 0;
    } if(*query != '\0'){ 
        return find(root->children[(*query) - 97], query+1);
    }
    return root->children_amount;
}

void add(trie_node *node, char* query) { //insert elements into the trie. basically, we update the child count and then check if the query reached the null terminator. 
    //if it hasn't reached the null terminator, then it checks whether the array is null at the character we are at. if it is, create it. if it isn't, keep recurssing baybeeeeeeeee
    node->children_amount += 1;
    if (*query != '\0'){ 
        if (node->children[*(query) - 'a'] == NULL) {
            node->children[(*query) - 'a'] = create();
        }
        add(node->children[(*query) - 'a'], query+1);
    }
    else{ 
        node->end_word = true;
    }
    return;
}


int search(trie_node* root, char* query){ 
    //this one made us consider biz-tech.
    //checks to see if the root's children are null depending on which the character we are on in the query string. if it is null, we then check if the root 
    //has it's end flag set to true. if so, then return a 1 if not return a 0.
    if (root->children[*(query)-'a'] == NULL) { 
        return (root->end_word == true ? 1 : 0);
    } else { 
        return search(root->children[*(query)-'a'], query+1);
    }
}
// main
int main()
{
    //based off the hackerrank main function 
    int number_operations; //that first line where you figure out how many operations you wanna do
    char *op, *cont;
    trie_node* trie = create(); // create that delicious trie mmm toasty
    if (scanf("%d", &number_operations) != 1){
        printf("please enter an int.\n");      //get all num of operations that you wanna do
        return 1;
    } else if(number_operations < 1){
        printf("please enter a number greater than 0.\n");
        return 1;
    }
    //loop over the next commands
    for(int i = 0; i < number_operations; i++){
        //scan in words
        op = (char *)malloc(1000 * sizeof(char));
        cont = (char * )malloc(1000 * sizeof(char));
        scanf("%s %s", op, cont);
        
        if (op != NULL && cont == NULL) { // Error checking
            printf("Invalid input.");
            return 1;
        }
        //check if input matches correctly
        if(op[0] == 'a') {
            add(trie, cont);
        } else if (op[0] == 'f') {
            printf("%d\n",find(trie, cont));
        } else if (op[0] == 's') {
            search(trie, cont) ? (printf("Yes\n")):(printf("No\n"));  //converting the 1 or the 0 we get to a Yes or a No with a ternary operation
        } else{
            printf("operations are find, add, search.\n");
            return 1;
        }
    }
    delete_trie(trie); // delete our delicious trie mmmm toasty
   return 0;
}