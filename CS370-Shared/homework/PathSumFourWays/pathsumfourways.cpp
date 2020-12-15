/*******************************************************************************
 * Name        : pathsumfourways.cpp
 * Author      : Eric Altenburg, Hamzah Nizami, Constance Xu
 * Date        : April 4th, 2020
 * Description : Project 83 Euler using BFS - https://projecteuler.net/problem=83
 * Pledge      : I pledge my honor that I have abided by the Stevens Honor System.
 ******************************************************************************/
#include <iostream>
#include <fstream> 
#include <limits>
#include <stdbool.h> 
#include <sstream>
#include <string>
#include <queue>
#include <unordered_set>
#include <utility> 
#include <vector>

using namespace std;

typedef vector<vector<bool> > Graph_Visits; //making our data structures. also just playing around with typedefs
typedef vector<vector<int> > Matrix; //making our data structures. also just playing around with typedefs

struct Node{ 
    pair<int, int> cell_positions; // positions on the graph, represented as <x, y> coordinates.
    int edge_weight; //sum of the nodes
    vector<int> history; //what nodes were previous to this. Used to construct the "Values" line in output.

    bool operator<(const Node& node) const{ // prioirty gives largest element, so flip it around
      return edge_weight > node.edge_weight; 
    }
};

Node create_node(int x, int y, int weight){ // Wanted to use initialization list, but got some annoying problems that I didn't to deal with.
    Node n; 
    n.cell_positions = make_pair(x, y);
    n.edge_weight = weight;
    return n;
}

vector<int> split(const string &s, char delimiter){ //this is our punishment for using C++ - no easy string split. 
    vector<int> tokens;
    string token;
    istringstream tokenStream(s);
    while(getline(tokenStream, token, delimiter)){ 
        int tok = stoi(token);
        tokens.push_back(tok);
    }
    return tokens;
}

/*
 *	We might have to get rid of this because there can be duplicates
 */
void dup_remove(vector<int> &v){  //Remove duplicates and preserve insertion order from vector
	vector<int>::iterator itr = v.begin();
	unordered_set<int> s;

	for (vector<int>::iterator curr = v.begin(); curr != v.end(); ++curr) {
		if (s.insert(*curr).second)
			*itr++ = *curr;
	}
	v.erase(itr, v.end());
}


Node traverse(Matrix matrix){ //using dijkstra, but taking hints from waterjug bfs
    /* 
    It's not necessary that we have a perfect NxN matrix every time. To account
    for these discrepancies, we made specific column and row length indicators
    to accurately build the table of bools 
    */
	int column_length = matrix.at(0).size(); 
    int row_length = matrix.size();

    priority_queue<Node> q;
    q.push(create_node(0, 0, matrix.at(0).at(0))); //upper left

    Graph_Visits seen(matrix.size());
    /* 
    Taking a hint from waterjug by using the matrix of bools to determine what we have seen already. This speeds up our code.
    You could have a "seen" field in your Node constructor, but it isn't as speedy as this. Also, traditional bfs implementations seem to use a bool table
    to determine what has been discovered to avoid repeats. 
    */
    for (int i = 0; i < seen.size(); i++){ 
        seen.at(i).resize(column_length, false);
    }

    while(!q.empty()){ 
        Node n = q.top(); //get the next thing to process
        q.pop(); //remove

        if(seen[n.cell_positions.first][n.cell_positions.second]){  //seen before? skip 
            continue;
        }
        seen[n.cell_positions.first][n.cell_positions.second] = true;

        if(n.cell_positions.first == row_length - 1 && n.cell_positions.second == column_length - 1){ //are we done? did we reach the bottom right cell? 
            n.history.push_back(matrix.at(n.cell_positions.first).at(n.cell_positions.second)); //add last node to history
            return n;
        }
        //Look up 
        if(n.cell_positions.first > 0){ 
            Node new_n = create_node(n.cell_positions.first - 1, n.cell_positions.second, n.edge_weight + matrix.at(n.cell_positions.first - 1).at(n.cell_positions.second));
            new_n.history = n.history; //copy the previous nodes history to the new node
            new_n.history.push_back(matrix.at(n.cell_positions.first).at(n.cell_positions.second)); //update history with the newest node
            q.push(new_n); //put new node in our queue for further processing.
        }
        //Look left
        //The other if statements follow the same algorithm as the "Look up" one, just with the coordinates changed.
        if(n.cell_positions.second > 0){ 
            Node new_n = create_node(n.cell_positions.first, n.cell_positions.second - 1, n.edge_weight + matrix.at(n.cell_positions.first).at(n.cell_positions.second - 1));
            new_n.history = n.history;
            new_n.history.push_back(matrix.at(n.cell_positions.first).at(n.cell_positions.second));
            q.push(new_n);
        }
        //Look right 
        if(n.cell_positions.second + 1 < column_length){ 
            Node new_n = create_node(n.cell_positions.first, n.cell_positions.second + 1, n.edge_weight + matrix.at(n.cell_positions.first).at(n.cell_positions.second + 1));
            new_n.history = n.history;
            new_n.history.push_back(matrix.at(n.cell_positions.first).at(n.cell_positions.second));
            q.push(new_n);
        }
        //Look down
        if(n.cell_positions.first + 1 < row_length){ 
            Node new_n = create_node(n.cell_positions.first + 1, n.cell_positions.second, n.edge_weight + matrix.at(n.cell_positions.first + 1).at(n.cell_positions.second));
            new_n.history = n.history;
            new_n.history.push_back(matrix.at(n.cell_positions.first).at(n.cell_positions.second));
            q.push(new_n);
        }
    }
    Node invalid = create_node(-1, -1, -1); //if there is no answer, give an invalid node and return
    return invalid;
}

void display_answer(Node end_node){ 
    //Simply just printing out the answer in the format required
    if(end_node.history.empty()){ //if no answer recieved
        cout << "Min sum: " << -1 << endl;
        cout << "Values: N/A" << endl; 
        return;
    }
    //if answer recieved
    cout << "Min sum: " << end_node.edge_weight << endl;
    cout << "Values:  "; 
    cout << "[";
//    dup_remove(end_node.history); //remove duplicates
    for(int i = 0; i < end_node.history.size(); i++){ 
        cout << end_node.history.at(i);
        if(i != end_node.history.size() - 1){ //don't want an extra comma at the end
            cout << ", ";
        }
    }
    cout << "]" << endl;
}


int main(int argc, char * const argv[]){ 
    if(argc != 2){ //insufficent args
        cerr << "Usage: ./pathsumfourways <input file>";
        return 1;
    }
    ifstream file(argv[1]);
    if(file.fail()){  //file not found
        cerr << "Error: File '" << argv[1] << "' not found." << endl;
        return 1;
    }
    if(file.peek() == EOF){ //empty file?
        cerr << "Usage: ./pathsumfourways <input file>" << endl;
        return 1;
    }
    string line; 
    Matrix matrix;
    while(getline(file, line)){ 
        matrix.push_back(split(line, ',')); //populate our matrix one row at a time.
        /* 
        We get 1,2,3,4,5 from the file. 
        Convert that to a vector<int> by splitting along the "," and appending to vector.
        So a string 1,2,3,4,5 -> <1,2,3,4,5>
        */
    }
    display_answer(traverse(matrix)); //display the answer in the correct format.

    return 0;
} 
