/*******************************************************************************
 * Name        : intest.cpp
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : January 23, 2017
 * Description : Solution to SPOJ INTEST - Enormous Input Test
 *               http://www.spoj.com/problems/INTEST/
 ******************************************************************************/
#include <iostream>

using namespace std;

int main() {
    // Quick and dirty solution using some IO tricks.
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    int n, k, val, count = 0;
    cin >> n >> k;
    while (n--) {
        cin >> val;
        if (val % k == 0) {
            ++count;
        }
    }
    cout << count << "\n";
}
