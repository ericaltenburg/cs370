#!/bin/python3

import math
import os
import random
import re
import sys

#
# Complete the 'nonDivisibleSubset' function below.
#
# The function is expected to return an INTEGER.
# The function accepts following parameters:
#  1. INTEGER k
#  2. INTEGER_ARRAY s
#

def nonDivisibleSubset(k, s):
    # Write your code here
    answer = 0
    partial_mods = [0] * k 
    for i in range(len(s)): 
        partial_mods[s[i] % k] += 1

    if partial_mods[0] > 0: answer+=1

    for i in range(1, (len(partial_mods)//2) + 1): 
        if i == k-i : answer += 1  
        else:
            if partial_mods[i] >= partial_mods[k-i]: 
                answer += partial_mods[i]
            else: 
                answer += partial_mods[k-i]
    return answer

    

if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')

    first_multiple_input = input().rstrip().split()

    n = int(first_multiple_input[0])

    k = int(first_multiple_input[1])

    s = list(map(int, input().rstrip().split()))

    result = nonDivisibleSubset(k, s)

    fptr.write(str(result) + '\n')

    fptr.close()
