"""
Created on Feb 11, 2020
@author: Sam Kraus and Brian Borowski
Solution to HackerRank -> Practice -> Algorithms ->
            Graph Theory -> Journey to the Moon
"""
#!/bin/python3

from collections import Counter

def find(countries, index):
    country = index
    while country != countries[country]:
        country = countries[country]
    countries[index] = country
    return country

def union(countries, i, j):
    root = find(countries, i)
    countries[find(countries, j)] = root
    countries[j] = root

def journey_to_moon(n, astronaut_pairs):
    countries = list(range(0, n))
    for alice, bob in astronaut_pairs:
        union(countries, alice, bob)
    # Counter is a dictionary, with key = argument and value =
    # how many times that argument is seen.
    countrySize = Counter(find(countries, i) for i in range(n))

    total = 0
    size = n
    for i in range(n):
        size -= countrySize[i]
        total += countrySize[i] * size
    return total

if __name__ == '__main__':
    np = input().split()
    n = int(np[0])
    p = int(np[1])
    astronaut_pairs = []
    for _ in range(p):
        pair = input().rstrip().split()
        astronaut_pairs.append([int(pair[0]), int(pair[1])])
    print(journey_to_moon(n, astronaut_pairs))
