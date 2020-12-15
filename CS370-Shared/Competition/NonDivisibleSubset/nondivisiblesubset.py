'''
' Solution for Hackerrank Question 'Non-Divisible Subset'
' https://www.hackerrank.com/challenges/non-divisible-subset/problem
' Author: Sam Kraus / Brian Borowski
' Last modified: April 27, 2020
'''
#!/bin/python3

import math
import os

# Complete the non_divisible_subset function below.
def non_divisible_subset(k, S):
    rem_count = [0] * k
    for num in S:
         rem_count[num % k] += 1
    # print(rem_count)
    total = min(rem_count[0], 1)
    kmod2 = k % 2
    kdiv2 = k // 2
    for i in range(1, kdiv2 + kmod2):
        total +=  max(rem_count[i], rem_count[k - i])
    if kmod2 == 0:
        total += min(rem_count[kdiv2], 1):
    return total

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')
    nk = input().split()
    _ = int(nk[0])
    k = int(nk[1])
    S = list(map(int, input().rstrip().split()))
    fptr.write(str(non_divisible_subset(k, S)) + '\n')
    fptr.close()
    #print(non_divisible_subset(k, S))
