'''
' Solution for Hackerrank Question 'Encryption'
' https://www.hackerrank.com/challenges/encryption/problem
' Author: Sam Kraus / Brian Borowski
' Last modified: April 27, 2020
'''
#!/bin/python3

import math
import os

#
# Complete the encryption function below.
#
def encryption(s):
    length = len(s)
    low = math.floor(math.sqrt(length))

    row = col = low
    if row * col < length:
        col += 1
    if row * col < length:
        row += 1
    # print(row, col)
    r_index = c_index = 0
    output = ''
    # mat = [[0 for c in range(col)] for r in range(row)]
    # print(mat)
    for _ in range(row * col):
        if r_index * col + c_index < length:
            output += s[r_index * col + c_index]
            # mat[r_index][c_index] = s[r_index * col + c_index]
        r_index += 1
        if r_index == row:
            r_index = 0
            c_index += 1
            output += ' '
    # print(mat)
    return output

if __name__ == '__main__':
    # print(encryption(input()))
    fptr = open(os.environ['OUTPUT_PATH'], 'w')
    fptr.write(encryption(input()) + '\n')
    fptr.close()
