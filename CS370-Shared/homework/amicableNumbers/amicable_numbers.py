"""
Authors: Eric Altenburg, Hamzah Nizami, Connie Xu
Pledge: I pledge my honor that I have abided by the Stevens Honor System.
Description: Amicable Numbers -- Project Euler 21 Modified
"""
import time

def pre_div_sums():
    '''
    We take a hint from the Sieve of Erastosthenes algorithm here (sorta). We initialize
    a list to all ones and then fill it in by getting the sum of proper divisors. We go up
    to 110,000 instead of 100,000 to make sure we find all amicable numbers. If our limit was at
    2700, we would be able to get 2620 but not 2924. So, we extend our limit by a bit
    to capture all numbers within bounds. We took this approach because we found a 
    corresponding Hackerrank to this question: https://www.hackerrank.com/contests/projecteuler/challenges/euler021/problem
    and to be fast enough we had to change our original method of sheer brute force. We did a ton
    of reading and found that we could implement a "sieve-like" algorithm with the divisors
    to greatly improve the speed.'''
    divs = [1 for i in range(110000)] #list of the divisor sums for each number
    for i in range(2, int(110000**0.5)): #only need to go to the square root of 110,000
        for j in range(i+1, 110000//i):
            divs[i*j]+= i+j
    return divs  #list of the sum of proper divisors


def calc_sum(divs):
    calc_ans = [] #add up the answer we get from pre_div_sums()
    for i in range(2, 110000):
        if divs[i] < i: # b != a
            if divs[divs[i]] == i: # d(b) == a?
                calc_ans += [divs[i], i]
    return calc_ans


def print_values(calc_ans): 
    #since we calculate for divisors above 100,000, we need to filter out numbers > 100,000
    filter_ans = list(filter(lambda x: x < 100000, calc_ans))
    it = iter(filter_ans)
    res = sorted(list(zip(it, it))) #this makes our list into a list of tuples. So [1, 2, 3, 4] would be [(1,2), (3, 4)]. Makes for easier printing
    print(*res, sep='\n') #print all tuples
    print("Sum:", sum(filter_ans)) #print the sum

if __name__ == '__main__':
    #run the program and print out everything with the right format
    start = time.time()
    div = pre_div_sums()
    print_values(calc_sum(div))
    print("Elapsed time: {0:0.2f} ms".format((time.time() - start)*1000))
 
