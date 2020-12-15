#!/bin/python3
'''
Authors: Eric Altenburg, Hamzah Nizami, Constance Xu
Description: HackerRank Problem Sam and Substrings: https://www.hackerrank.com/challenges/sam-and-substrings/problem
Pledge: I pledge my honor that I have abided by the Stevens Honor System.
'''

'''Note: We did a fair bit of research into the best ways to solve this problem as well as cool python tips and tricks to make
the code more "pythonic". Solution should be O(n).'''

import os
import itertools

def substrings(n):
    num_to_digits = list(map(int, n))  #Given a number n, turn it into a list of it's digits. EX: 123 -> [1, 2, 3]
    result = 0  #our answer will be stored here
    mod = 1000000007 

    nw = [pow(10, item, mod) for item in range(0, len(n))]   
    '''
    nw, or new weights, generates a list of how many numbers are in a certain place. So, for the number 123 we would get: 
    [1, 10, 100] indicating that the highest we go is to the hundreths. However, if the number exceeds 9 digits, we have
    to keep in mind that the bottleneck for this problem is 1000000007. That's why we always mod in case we get a particularly
    large number. So, if we have 29485247852384597324853425 as our n, this line of code would give us: 
    [1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 999999937, 999999307, 999993007, 999930007, 999300007, 993000007, 930000007, 300000007, 49, 490, 4900, 49000, 490000, 4900000, 49000000, 490000000]
    '''
    new_weight_sums = list(itertools.accumulate(nw))  #partial sums formula,
    '''With some research, we found that the general formula we have to follow for the entire problem is: 
    si * sum of (10 ** i) * (i + 1) where i starts at 0 and we go till n-1. In this line , we are just 
    adding 1's to the numbers generated in nw. So, if we got [1, 10, 100, 1000] from nw, we would add 1 to the first element,
    11 to the second element, and 111 to the third element to get [1, 11, 111, 1111]. With this, we are getting the weights of the digit.
    '''

    for i in range(len(n)): 
        result += (num_to_digits[i] * new_weight_sums[len(n) - i - 1] * (i + 1)) % mod #essentially the formula we talked about in previous comments.
        result %= mod  #making sure our solution is in the bounds that they want.
    return result


#Same main as the HackerRank essentailly. 
if __name__ == '__main__':
    n = input()
    result = substrings(n)
    print (result)

