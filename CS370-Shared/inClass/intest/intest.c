/*******************************************************************************
 * Name        : intest.c
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : February 4, 2016
 * Description : Solution to SPOJ INTEST - Enormous Input Test
 *               http://www.spoj.com/problems/INTEST/
 ******************************************************************************/
#include <stdio.h>

#define BUF_SIZE 65536

int main() {
    char buffer[BUF_SIZE];
    int num = 0, count_divisible = 0, num_read, i, k, n, c;
    /* Use scanf to read n and k. It's good enough for just a few inputs. */
    scanf("%d %d\n", &n, &k);
    /* Use fread to read BUF_SIZE elements, each of 1 byte, from stdin into the
       character buffer. */
    while ((num_read = fread(buffer, 1, BUF_SIZE, stdin)) > 0) {
        for (i = 0; i < num_read; ++i) {
            c = buffer[i];
            if (c != '\n') {
                /* Shift the number one unit to the left and put the new ones
                   digit in place. */
                num = (num << 3) + (num << 1) + c - '0';
            } else {
                if (num % k == 0) {
                    ++count_divisible;
                }
                num = 0;
            }
        }
    }
    printf("%d\n", count_divisible);
    return 0;
}
