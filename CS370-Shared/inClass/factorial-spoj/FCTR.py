# your code goes here

def calc_trailing_zero(num): 
	if num < 5: 
		return 0
	acc = 0 
	start = 5
	while start < num: 
		acc += num // start 
		start *= 5
	return acc

x = int(input()) 
for i in range(x): 
	y = int(input())
	print(calc_trailing_zero(y))

