/*******************************************************************************
 * Name          : flattenlist.cpp
 * Author        : Brian S. Borowski
 * Version       : 1.0
 * Date          : February 17, 2020
 * Last modified : February 25, 2020
 * Description   : Solution to LeetCode 430
 *                 Flatten a Multilevel Doubly Linked List
 ******************************************************************************/

/*
// Definition for a Node.
class Node {
public:
    int val;
    Node* prev;
    Node* next;
    Node* child;
};
*/

class Solution {
public:
    Node* find_tail(Node *head) {
        Node *q = nullptr, *p = head;
        while (p != nullptr) {
            q = p;
            p = p->next;
        }
        return q;
    }
    
    Node* flatten(Node* head) {
        Node *q = nullptr, *p = head;
        while (p != nullptr) {
            if (p->child != nullptr) {
                Node *f = flatten(p->child);
                q = p;
                p = p->next;
                q->child = nullptr;
                q->next = f;
                f->prev = q;
                Node *tail = find_tail(f);
                tail->next = p;
                if (p != nullptr) {
                    p->prev = tail;
                }
            } else {
                q = p;
                p = p->next;
            }
        }
        return head;
    }
};
