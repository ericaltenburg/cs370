"""
Name          : issearchtree.py
Author        : Brian S. Borowski
Version       : 1.0
Date          : February 10, 2018
Last modified : February 10, 2018
Description   : Solution to HackerRank -> Data Structures -> Trees ->
                Is This a Binary Search Tree?
"""

""" Node is defined as
class node:
  def __init__(self, data):
      self.data = data
      self.left = None
      self.right = None
"""

def is_bst(node, min_bound, max_bound):
    if not node:
        return True

    data = node.data
    if data <= min_bound or data >= max_bound:
        return False

    return is_bst(node.left, min_bound, data) and \
           is_bst(node.right, data, max_bound)

def check_binary_search_tree_(root):
    if not root:
        return True
    return is_bst(root.left, -1, root.data) and \
           is_bst(root.right, root.data, 10001)
