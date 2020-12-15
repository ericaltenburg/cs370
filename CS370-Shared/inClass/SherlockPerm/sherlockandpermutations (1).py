"""
Created on Jan 29, 2020
@author: Sam Kraus and Brian Borowski
Solution to HackerRank -> Practice -> Mathematics ->
            Fundamentals -> Sherlock and Permutations
"""
#!/bin/python3

import os
import sys

DIVISOR = 1000000007

# Complete the solve function below.
def solve(n, m):
    # n is 0
    # m is 1
    facts = [1] * (n + m)
    for i in range(1, n + m):
        facts[i] = facts[i - 1] * i
    return (facts[n + m - 1] // (facts[n] * facts[m - 1])) % DIVISOR

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')
    t = int(input())
    for t_itr in range(t):
        nm = input().split()
        n = int(nm[0])
        m = int(nm[1])
        result = solve(n, m)
        fptr.write(str(result) + '\n')
    fptr.close()
