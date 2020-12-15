/*******************************************************************************
 * Name          : FCTRL.c
 * Author        : Brian S. Borowski
 * Version       : 1.0
 * Date          : January 31, 2017
 * Last modified : January 28, 2020
 * Description   : Solution to SPOJ FCTRL - Factorial
 *                 http://www.spoj.com/problems/FCTRL/
 ******************************************************************************/
#include <stdio.h>

/* Function prototypes */
int trailing_zeros(int num);
int next_int();

/**
 * Counts all the trailing zeros in num!
 * Count all the times increasing powers of 5 are part of the product. For every
 * 5, you are guaranteed to find a matching 2, so there is no need to do
 * anything more complicated.
 */
inline int trailing_zeros(int num) {
    int zeros = 0;
    for (int i = 5; i <= num; i *= 5) {
        zeros += num / i;
    }
    return zeros;
}

/**
 * Quick means of reading ints from STDIN nicely encapsulated in a function that
 * can be reused in other projects.
 */
inline int next_int() {
    register int c, value;
    c = getchar_unlocked();
    value = 0;
    do {
        value = value * 10 + (c & 15);
        c = getchar_unlocked();
        /* 33 is the lowest non-whitespace printable ASCII character. */
    } while (c >= 33);
    return value;
}

int main(int argc, char *argv[]) {
    int num_inputs = next_int(), i;
    for (i = 0; i < num_inputs; ++i) {
        printf("%d\n", trailing_zeros(next_int()));
    }
    return 0;
}
