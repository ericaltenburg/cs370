/*******************************************************************************
 * Name        : largest_prime_factor.cpp
 * Author      : Hamzah Nizami
 * Version     : 1.0
 * Date        : 11/8/19
 * Description : proj euler - https://projecteuler.net/problem=3
 * Pledge      : I pledge my honor that I have abided by the Stevens Honor System.
 ******************************************************************************/

#include <iostream> 
#include <cmath>

using namespace std;

int main(){ 
    long num = 600851475143;
    int p  = 2;
    do{ 
        if(num % p == 0){ 
            cout << p << endl;
            num = num / p;
        } else { 
            p++;
        }
    }while(num >= p * p);
    cout << num << endl;
    return 1;
}