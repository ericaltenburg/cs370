"""
Name          : swapnodes.py
Author        : Brian S. Borowski
Version       : 1.1
Date          : February 12, 2018
Last modified : February 19, 2018
Description   : Solution to HackerRank -> Data Structures -> Trees ->
                Swap Nodes [Algo]
"""
import sys
from queue import Queue

class BSTNode(object):
    def __init__(self, data):
        """Constructor."""
        self.data = data
        self.left = None
        self.right = None

    def inorder(self):
        """Performs an inorder traversal rooted at the current
        BSTNode and returns the nodes' data values in a list."""
        traversal = []
        if self.left:
            traversal.extend(self.left.inorder())
        traversal.append(self.data)
        if self.right:
            traversal.extend(self.right.inorder())
        return traversal

    def swap(self, current_depth, swap_depth):
        """Swaps the left and right subtrees of all nodes at multiples
        of swap_depth."""
        if current_depth % swap_depth == 0:
            temp = self.left
            self.left = self.right
            self.right = temp
        if self.left:
            self.left.swap(current_depth + 1, swap_depth)
        if self.right:
            self.right.swap(current_depth + 1, swap_depth)

def build_tree(queue):
    """Builds the tree from top to bottom, left to right, with the help
    of a queue. This breadth-first approach is necessary to process the
    input in the order it is supplied."""
    tree = None
    while not queue.empty():
        node = queue.get()
        if not tree:
            tree = node
        left_data, right_data = [int(x) for x in input().split()]
        if left_data > 0:
            node.left = BSTNode(left_data)
            queue.put(node.left)
        if right_data > 0:
            node.right = BSTNode(right_data)
            queue.put(node.right)
    return tree

def read_input():
    num_nodes = int(input())
    queue = Queue()
    queue.put(BSTNode(1))
    return num_nodes, build_tree(queue)

if __name__ == '__main__':
    sys.setrecursionlimit(10000)
    num_nodes, tree = read_input()
    for _ in range(int(input())):  # _ loops over number of swaps
        tree.swap(1, int(input())) # read the swap depth
        print(' '.join([str(x) for x in tree.inorder()]))
