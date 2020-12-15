/*******************************************************************************
 * Name        : in_test.cpp
 * Author      : Hamzah Nizami
 * Version     : 1.0
 * Date        : 1/21/2020
 * Description : https://www.spoj.com/problems/INTEST/
 * Pledge      : I pledge my honor that I have abided by the Stevens Honor System.
 ******************************************************************************/

#include <iostream>
#include <fstream>
#include <string>
#include <stdlib.h>
using namespace std;


int main(int argc, char *argv[]){ 
    int counter;
    ifstream file(argv[1]);
    string line;
    int file_line_num;
    while(getline(file, line)){ 
        file_line_num++;
        if(file_line_num == 1){ 
            continue;
        }
        int num = atoi(line);
        if(num % 3 == 0){ 
            counter++;
        }
    }
    return counter;
}
    