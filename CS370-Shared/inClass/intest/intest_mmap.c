/*******************************************************************************
 * Name        : intest_mmap.c
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : January 20, 2020
 * Description : Solution to SPOJ INTEST - Enormous Input Test
 *               http://www.spoj.com/problems/INTEST/
 * Notes       : To run with memory mapped STDIN, use this syntax to place
 *               the input, found in input.txt, in file descriptor 0.
 *               <input.txt ./intest_mmap
 ******************************************************************************/
#include <stdio.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/stat.h>

int read_int(char **p);
char* open_file();

inline int read_int(char **p) {
    int r = 0;
    do {
        r = r * 10 + **p - '0';
        (*p)++;
    } while (**p >= '0');
    (*p)++;
    return r;
}

inline char* open_file() {
    struct stat sb;
    fstat(STDIN_FILENO, &sb);
    char *f = (char*)mmap(
        (void*)0, sb.st_size, PROT_READ, MAP_SHARED, STDIN_FILENO, 0);
    return f;
}

int main() {
    char *f = open_file();
    read_int(&f);
    int k = read_int(&f);
    int ans = 0;

    while (*f) {
        if (read_int(&f) % k == 0) {
            ++ans;
        }
    }
    printf("%d\n", ans);
    return 0;
}
