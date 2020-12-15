#!/bin/python3

import math
import os
import random
import re
import sys

# Complete the encryption function below.
def encryption(s):
    s = s.strip()
    column = math.ceil(math.sqrt(len(s)))
    output = ""
    for i in range(column):
        for j in range(i,len(s),column):
            output+=s[j]
        output+=" "
    return output


if __name__ == '__main__':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')

    s = input()

    result = encryption(s)

    fptr.write(result + '\n')

    fptr.close()