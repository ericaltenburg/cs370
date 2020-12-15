/*******************************************************************************
 * Name        : prime1.cpp
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : February 23, 2016
 * Description : Segmented sieve of Eratosthenes
 ******************************************************************************/
#include <cmath>
#include <cstdio>
#include <iostream>

using namespace std;

/**
 * Displays primes in the range [a, b].
 */
void display_primes(int a, int b) {
    // Deal with some corner cases.
    if (a < 2) {
        a = 2;
    }
    int len = b - a + 1;
    if (len <= 0) {
        return;
    }

    int sqrt_b = (int)sqrt(b);
    bool low_primes[sqrt_b + 1], high_primes[len];

    // Standard sieve of Eratosthenes.
    low_primes[0] = low_primes[1] = false;
    for (int i = 2; i <= sqrt_b; i++) {
        low_primes[i] = true;
    }
    for (int i = 2, len = sqrt(sqrt_b); i <= len; i++) {
        if (low_primes[i]) {
            for (int j = i * i; j <= sqrt_b; j += i) {
                low_primes[j] = false;
            }
        }
    }

    // Segmented portion of algorithm.
    for (int i = 0; i < len; i++) {
        high_primes[i] = true;
    }
    for (int p = 2; p <= sqrt_b; p++) {
        if (low_primes[p]) {
            int i = ceil((double)a/p) * p - a;
            if (a <= p) {
                // This means high_primes also includes the low primes.
                // Add p so that we don't actually cross off the prime number
                // in high_primes.
                i += p;
            }
            for ( ; i < len; i += p) {
                high_primes[i] = false;
            }
        }
    }
    // All primes are now contained in high_primes, so display them.
    for (int i = 0; i < len; i++) {
        if (high_primes[i]) {
            cout << i + a << "\n";
        }
    }
}

/**
 * Fast way to read integers from standard in.
 */
inline int next_int() {
    register int c, value;
    c = getchar_unlocked();
    value = 0;
    do {
        value = value * 10 + (c & 15);
        c = getchar_unlocked();
    } while (c >= 33);
    // 33 is the ASCII value of the first non-whitespace printable character.
    return value;
}

int main() {
    cin.tie(NULL);
    ios::sync_with_stdio(false);
    int num_tests = next_int();
    for (int i = num_tests - 1; i >= 0; i--) {
        int a = next_int(), b = next_int();
        display_primes(a, b);
        if (i != 0) {
            cout << "\n";
        }
    }
    return 0;
}
