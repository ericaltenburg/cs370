"""
Name          : lcs_dp.py
Author        : Brian S. Borowski
Version       : 1.1
Date          : March 1, 2018
Last modified : March 7, 2018
Description   : Longest common subsequence implemented with dynamic programming.
"""
import sys

def display_table(T):
    """Displays matrix T on the screen formatted as a table."""
    m = len(T[0])
    n = len(T)
    max_val = 0
    for row in range(n):
        for col in range(m):
            if not T[row][col] is None and T[row][col] > max_val:
                    max_val = T[row][col]
    max_cell_width = len(str(max(m, n, max_val)))
    sys.stdout.write(' ' * len(str(n)))
    for col in range(m):
        sys.stdout.write(' ' + str(col).rjust(max_cell_width))
    sys.stdout.write('\n')
    for row in range(n):
        sys.stdout.write(str(row).rjust(max_cell_width))
        for col in range(m):
            if T[row][col] is None:
                sys.stdout.write(' -')
            else:
                sys.stdout.write(' ' + str(T[row][col]).rjust(max_cell_width))
        sys.stdout.write('\n')

def lcs_dp(s1, s2, show_table=False):
    """Returns a tuple of values. Index 0 contains the length of the longest
    common subsequence, while index 1 contains the string. Uses bottom-up
    dynamic programming to improve performance."""
    m = len(s1)
    n = len(s2)
    c = [[0] * (n + 1) for _ in range(m + 1)]

    for i in range(1, m + 1):
        for j in range(1, n + 1):
            if s1[i - 1] == s2[j - 1]:
                c[i][j] = c[i - 1][j - 1] + 1
            else:
                c[i][j] = max(c[i][j - 1], c[i - 1][j])

    if show_table:
        display_table(c)

    s = ''
    while m > 0 and n > 0:
        if s1[m - 1] == s2[n - 1]:
            s = s1[m - 1] + s
            m -= 1
            n -= 1
        elif c[m][n - 1] >= c[m - 1][n]:
            n -= 1
        else:
            m -= 1

    return len(s), s

print(lcs_dp('aba', 'aaa'))
print(lcs_dp('abaca', 'aacab', True))
print(lcs_dp('daksfgsakjdgfkahdgfkjahriefdsaf',
             'asdjfhglkwehgfrkjasdhgfkjadhkad', True))
