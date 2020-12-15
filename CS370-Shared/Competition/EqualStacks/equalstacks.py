'''
' Solution for Hackerrank Question 'Equal Stacks'
' https://www.hackerrank.com/challenges/equal-stacks/problem
' Author: Sam Kraus / Brian Borowski
' Last modified: April 27, 2020
'''
#!/bin/python3

import os

#
# Complete the equal_stacks function below.
#
def equal_stacks(h1, h2, h3):
    tot1 = sum(h1)
    tot2 = sum(h2)
    tot3 = sum(h3)
    min_total = min(tot1, tot2, tot3)
    while tot1 > min_total or tot2 > min_total or tot3 > min_total:
        while tot1 > min_total:
            tot1 -= h1.pop(0)
        while tot2 > min_total:
            tot2 -= h2.pop(0)
        while tot3 > min_total:
            tot3 -= h3.pop(0)
        min_total = min(tot1, tot2, tot3)
    return min_total

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')
    _ = input() # Unused in Python

    h1 = list(map(int, input().rstrip().split()))
    h2 = list(map(int, input().rstrip().split()))
    h3 = list(map(int, input().rstrip().split()))

    fptr.write(str(equal_stacks(h1, h2, h3)) + '\n')
    fptr.close()
