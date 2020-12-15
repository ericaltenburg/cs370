#!/bin/python3

'''
Authors: Eric Altenburg, Hamzah Nizami, Constance Xu
Description: SPOJ Problem: https://www.spoj.com/problems/TRIP/
Pledge: I pledge my honor that I have abided by the Stevens Honor System.
'''

from collections import OrderedDict 

def lcs_dp(a, b): 
	#This was taken straight from Dr. B's code that was provided for us. 
	dp = [[0] * (len(b) + 1) for _ in range(len(a) + 1)]  #generate table

	for i in range(1, len(a) + 1): 
		for j in range(1, len(b) + 1): 
			if a[i -1] == b[j-1]: 
				dp[i][j] = dp[i - 1][j-1] + 1
			else: 
				dp[i][j] = max(dp[i][j-1], dp[i-1][j])

	answers = []   #we are going to store all our answers here
	process_backtracking = set()  #memoization technique - taking it back to CS115
	traverse_all_sols(len(a), len(b), "", process_backtracking, answers, a, b, dp)
	answers = list(OrderedDict.fromkeys(answers)) #getting rid of duplicates. Apparently list -> set -> list is costly.
	return answers


def traverse_all_sols(len_alice, len_bob, ans_build, backtrack_process, results, alice, bob, lcs_dp): 
	if(ans_build, len_alice, len_bob) in backtrack_process: #check if already processed
		return
	if len_alice == 0 or len_bob == 0: #we've reached an answer! 
		results.append(ans_build)
		return
	if alice[len_alice - 1] == bob[len_bob -1]: #keep backtracking. if same character in the table cell, append to the answer_builder and keep going
		ans_build = alice[len_alice - 1] + ans_build 
		traverse_all_sols(len_alice-1, len_bob - 1, ans_build, backtrack_process, results, alice, bob, lcs_dp)
		return 
	if lcs_dp[len_alice - 1][len_bob] ==  lcs_dp[len_alice][len_bob]:   #take the longer of the two sequences we found
		traverse_all_sols(len_alice -1, len_bob, ans_build, backtrack_process, results, alice, bob, lcs_dp)
	if lcs_dp[len_alice][len_bob -1] == lcs_dp[len_alice][len_bob]: 
		traverse_all_sols(len_alice, len_bob - 1, ans_build, backtrack_process, results, alice, bob, lcs_dp)
	backtrack_process.add((ans_build, len_alice, len_bob)) #we have reached a processed element. append so that we don't redo


if __name__ == '__main__': 
	#parse the inputs 
	n = int(input())
	while(n): 
		first_city_string = input() 
		second_city_string = input() 
		n -= 1 
		print(*sorted(lcs_dp(first_city_string, second_city_string)), sep='\n') #evaluate the answer and print it out sorted.