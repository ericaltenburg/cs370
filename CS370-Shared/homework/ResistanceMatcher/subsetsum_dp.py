'''
Author: Brian Borowski
Description: A dynamic programming solution for the subset sum problem.
'''
import sys

OUTPUT_BOOLEAN = 0
OUTPUT_LETTERS = 1
OUTPUT_NUMBERS = 2

def display_table(T, output_type):
    '''Displays matrix T on the screen formatted as a table.

       Args:
           T: the matrix to display
           output_type: whether or not to display elements as booleans,
		       letters, or integers.
    '''
    m = len(T[0])
    n = len(T)
    max_val = 0
    for row in range(0, n):
        for col in range(0, m):
            if not T[row][col] is None and T[row][col] > max_val:
                    max_val = T[row][col]
    max_cell_width = len(str(max(m, n, max_val)))
    sys.stdout.write(' ' * len(str(n)))
    for col in range(0, m):
        sys.stdout.write(' ' + str(col).rjust(max_cell_width))
    sys.stdout.write('\n')
    for row in range(0, n):
        sys.stdout.write(str(row))
        for col in range(0, m):
            if T[row][col] is None:
                sys.stdout.write(' -')
            elif output_type == OUTPUT_BOOLEAN:
                if T[row][col]:
                    sys.stdout.write(' ' + str('T').rjust(max_cell_width))
                else:
                    sys.stdout.write(' ' + str('F').rjust(max_cell_width))
            elif output_type == OUTPUT_LETTERS:
                sys.stdout.write(' ' + chr(65 + T[row][col]))
            else:
                sys.stdout.write(' ' + str(T[row][col]).rjust(max_cell_width))
        sys.stdout.write('\n')

def backtrack(values, lookup, row, col):
    '''Returns a list of values add up to the target target_sum.
    '''
    solution = []
    if lookup[row][col]:
        while row > 0 and col > 0:
            row -= 1
            if not lookup[row][col]:
                curr = values[row]
                solution.append(curr)
                col -= curr
    solution.sort()
    return solution

def subset_sum(values, target_sum):
    '''Uses dynamic programming to determine if it's possible to create the
       target sum out of values in the 'values' list.
    '''
    n = len(values)

    # Make a 2D lookup table.
    lookup = [[False for col in range(target_sum + 1)] for row in range(n + 1)]

    # If sum is 0, then the answer is True.
    for row in range(n + 1):
        lookup[row][0] = True

    # If sum is not 0 and values is empty, then the answer is False.
    for col in range(1, target_sum + 1):
         lookup[0][col] = False

    # Fill in the lookup table, using a bottom-up approach.
    for row in range(1, n + 1):
        for col in range(1, target_sum + 1):
            curr = values[row - 1]
            lookup[row][col] = lookup[row - 1][col]
            if col >= curr:
                lookup[row][col] = lookup[row][col] or \
				                   lookup[row - 1][col - curr]

    # Display table, if deemed useful.
    display_table(lookup, OUTPUT_BOOLEAN)

    # Display solution.
    if lookup[n][target_sum]:
        print('Solution found.')
        print(backtrack(values, lookup, n, target_sum))
    else:
        print('No solution found.')

if __name__ == '__main__':
    values = [3, 3, 11, 11, 2, 1, 8, 20]
    target_sum = 20
    subset_sum(values, target_sum)
