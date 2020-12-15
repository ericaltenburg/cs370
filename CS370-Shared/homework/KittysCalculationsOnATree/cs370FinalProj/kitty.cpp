/**
 *    Name:                kitty.cpp
 *    Authors:            Eric Altenburg, Hamzah Nizami, and Constance Xu
 *    Date:                May 5, 2020
 *    Description:        Solves Kitty's Calculation on a tree problem from hackerrank (https://www.hackerrank.com/challenges/kittys-calculations-on-a-tree/problem)
                        by using 
*     Pledge: I pledge my honor that I have abided by the Stevens Honor System.
 **/

#include <bits/stdc++.h> //test in lubuntu!
using namespace std;

#define MOD 1000000007ll
typedef long long subc; //subc means subset computation

// Start from the end of the vals, and go through each used node (including merged nodes) and compute their
// value then add it to the result and return it
subc kitty_solver (int n, subc setSum, vector<int> &ancestors, vector<subc> &children, vector<int> &vals) {
    subc result = 0;

    for (int i = n; i > 0; --i) {
        subc temp = children[i]; // Grab the value in children even if not used as it may be a merged node

        if (vals[i] == 1) {
            temp += i; // If node being used, add it to the temp
        }

        if (temp != 0) { // So long as temp has something in it, calculate the formula
            subc temp1 = temp % MOD;
            subc temp2 = (setSum - temp) % MOD;
            subc temp3 = (temp1 * temp2) % MOD; // M O D

            if (temp3 > MOD - result) {
                result -= MOD;
            }

            result += temp3;
        }

        children[ancestors[i]] += temp; // Update the child value under the parent of node
    }

    return result;
}

vector<int> generate_ancestors(int n){ 
    vector<int> ancestors(n+2); 

    for(int i = 0; i < n; i++){ 
        int first, second;
        scanf("%d %d", &first, &second);
        ancestors[max(first, second)] = min(first, second);
    }
    ancestors.at(1) = 0;
    return ancestors;
}

void find_subset_nodes(int sets, int nodes, vector<int> &ancestors){ 
    vector<subc> children(nodes+1);
    vector<int> vals(nodes+1);
    subc setSum;


    // Iterate through the sets q
    for (int i = 0; i < sets; ++i) {
           int k; // size of set
        scanf("%d", &k);

        fill(children.begin(), children.end(), 0);
        fill(vals.begin(), vals.end(), 0);

        setSum = 0;

        for (int j = 0; j < k; ++j) {
            int num;
            scanf("%d", &num);

            setSum += num;
            vals[num] = 1;
        }

        cout << kitty_solver(nodes, setSum, ancestors, children, vals) << '\n';
    }
}

int main() {
    // Take in n and q where n is number of notes in tree and q is the number of sets
    int n, q;
    scanf("%d %d", &n, &q);

	ios_base::sync_with_stdio(1); //check for memory leaks!
	cin.tie(0);

    vector<int> ancestors = generate_ancestors(n-1);
    find_subset_nodes(q, n, ancestors);

    return 0;
}
