/*******************************************************************************
 * Name        : resistancematcher.cpp
 * Author      : Eric Altenburg, Hamzah Nizami, Constance Xu
 * Date        : April 7th, 2020
 * Description : Program to match the resistance that the user seeks
 * Pledge      : I pledge my honor that I have abided by the Stevens Honor System.
 ******************************************************************************/
#include <algorithm>
#include <iostream>
#include <fstream> 
#include <math.h>
#include <stdlib.h>
#include <stdbool.h> 
#include <string>
#include <utility> 
#include <vector>
#include <iomanip>
#include <map>
#include <functional>
#include <iterator>

using namespace std;

double min_fit = 100;

struct sols{  //if you go with this, you'll need to change output function and you'll need to make a create_solution function
    double best_fit; 
    double sol_error;
    int num_resistors;
    vector<float> resistors;

    sols(double _best_fit, double _sol_error, int _num_resistors) : best_fit(_best_fit), sol_error(_sol_error), num_resistors(_num_resistors) { }

    bool operator<(const sols& sol) const{ 
        if(abs(sol_error - sol.sol_error) < 0.001){ 
            return num_resistors < sol.num_resistors;
        }
		// cout << "Comparing: " << sol_error << " with: " << sol.sol_error << " RESULT: " << isless(sol_error, sol.sol_error) << endl;
        return isless(sol_error, sol.sol_error);      
    }

    friend ostream& operator <<(ostream &out, const sols &s);
};


ostream& operator<< (ostream &out, const sols &s){   //you can now just do cout << <sol object> 
    out << "Solution{BEST-FIT: " << s.best_fit << "; ERROR: " << s.sol_error << "; NUMBER_OF_RESISTORS: " << s.num_resistors << ";}";
    return out;
}

double percent_error(float target, double best_fit){ 
    return fabs((best_fit - target) / target) * 100;  //returns it as a raw percent. Need to do / 100 for computation
}


pair<double, double> calculate_bounds(double error, float target){ 
    if(error == 0.0) return make_pair((double)target, (double)target);
    pair<double, double> bounds = make_pair(target - (target * (error / 100)), target + (target * (error / 100))); //first is lower, second is higher
    return bounds;
}


vector<float> process_file(string file_name, double lower_bound){ 
    ifstream file(file_name);
    if(file.fail()){ //does the file exist? 
        cerr << "Error: Input file '" << file_name << "' not found." << endl;
        exit(EXIT_FAILURE);
    }
    if(file.peek() == EOF){ //Empty? 
        cerr << "Error: File '" << file_name << "' is empty." << endl;
        exit(EXIT_FAILURE);
    }
    vector<float> inputs; //put all of our inputs from the file here
    inputs.push_back(-1);
    int line_count = 1;
    string line;
    while(getline(file, line)){ 
        float val;  //same structure as the error checking we do in the main file.
        try{ 
            val = stof(line);
            if(val <= 0){ 
                cerr << "Error: Invalid value '" << line << "' on line " << line_count << "." << endl;
                exit(EXIT_FAILURE);
            }
        } catch(invalid_argument){ 
            cerr << "Error: Invalid value '" << line << "' on line " << line_count << "." << endl;
            exit(EXIT_FAILURE);
        }
        line_count++;
        if(val < lower_bound) continue;
        inputs.push_back(val);
    }
    return inputs;
}


// Calculates the ohms
double parallel_formula (vector<float> resistors) {
	double sum = 0;

	for (int i = 0; i < resistors.size(); ++i) {
		sum += 1 / resistors.at(i);
	}
	return 1 / sum;
}


void inline_vector_print(vector<float> v){ //inline printing of a vector. Just to pass the test cases.
    if(v.empty()){ 
        cout << "not possible.";
        return;
    }

    cout << "possible with ";

    cout << "[";
    for(int i = 0; i < v.size(); i++){ 
        cout << v.at(i);
        if(i != v.size() - 1){ 
            cout << ", ";
        }
    }
    cout << (v.size() != 1 ? "] ohm resistors." : "] ohm resistor."); //Dr. B likes grammar
}

void output(int max_r, float tolerance, vector<float> resistors, float target, double best_fit, string print_target){ 
    //outputting everything to exactly how the test script wants it.
    cout << "Max resistors in parallel: " << max_r << endl;
    cout.precision(1);
    cout << "Tolerance: " << fixed << tolerance << " %" << endl;
    cout << "Target resistance of " << print_target << " ohms is ";
    inline_vector_print(resistors);
    cout << endl;
    if(resistors.empty()) exit(EXIT_SUCCESS);
    cout.precision(4);
    cout << "Best fit: " << fixed << best_fit << " ohms" << endl;
    cout.precision(2);
    cout << "Percent error: " << percent_error(target, best_fit) << setprecision(2) << " %" << endl;
    exit(EXIT_SUCCESS);
}

void findDuplicates(vector<float> &vecOfElements, map<float, int> & countMap) {
	// Iterate over the vector and store the frequency of each element in map
	for (auto & elem : vecOfElements)
	{
		auto result = countMap.insert(pair<float, int>(elem, 1));
		if (result.second == false)
			result.first->second++;
	}
 
	// Remove the elements from Map which has 1 frequency count
	for (auto it = countMap.begin() ; it != countMap.end() ;)
	{
		if (it->second == 1)
			it = countMap.erase(it);
		else
			it++;
 
	}
}

vector<sols> backtracking (vector < vector < bool > > &mat, vector<float> &resistor_vals, long last_row_index, long upperBound, long lowerBound, int num_r, float target, float tolerance){ 
    vector<float> temp_solution;
    vector<sols> solution;

    long i = resistor_vals.size()-1;
    long j = last_row_index;


	while (i > 0 && j > 0) {

		if (mat.at(i-1).at(j)) { // Cell above is true, then move up one

			i = i-1;
		} else { // Cell above is false, move to the left by the amount of the resistor and add it to the temp solutions
			j = j-((1/resistor_vals.at(i))*1000000);

			temp_solution.push_back(resistor_vals.at(i));
		}
	}

	// // Find duplicates and see if they can be replaced with doubled resistors
	// vector<float> inv_resistor_vals;
	// for (int a = 0; a < resistor_vals.size(); a++) {
	// 	inv_resistor_vals.push_back(1/resistor_vals.at(i));
	// }

	// map<float, int> countMap;

	// findDuplicates(temp_solution, countMap);

	// for (auto & elem : countMap) {
	// 	for (int i = elem.second; i > 1; --i) {
	// 		float temp_x = (1/elem.first)*(float)i;

	// 		// float temp = elem.first * i;
	// 		vector<float>::iterator it = find(inv_resistor_vals.begin(), inv_resistor_vals.end(), temp_x);

	// 		if (it != resistor_vals.end()) {
	// 			sort(temp_solution.begin(), temp_solution.end()); // sort items
	// 			vector<float>::iterator it2 = find(temp_solution.begin(), temp_solution.end(), elem.first);
	// 			temp_solution.erase(temp_solution.begin()+distance(temp_solution.begin(), it2), temp_solution.begin()+distance(temp_solution.begin()+i, it2));
	// 			temp_solution.push_back(1/temp_x);
	// 			break;
	// 		}
	// 	}
	// }
	
	if (temp_solution.size() <= num_r) { // temp solution does not use too many resistors
		sort(temp_solution.begin(), temp_solution.end());
		double best_fit = parallel_formula(temp_solution);
		double perror = percent_error(target, best_fit);
		if (perror <= tolerance) {
			sols psol(best_fit, perror, temp_solution.size());
        	psol.resistors = temp_solution;
			solution.push_back(psol);

			return solution;
		}
	}

	return solution;
}

sols populate (vector<float> &resistor_vals, float tolerance, int num_r, float target) {
	// Upper and lower bound for the target inverses and move decimal by 8, then place into a vector for easy mapping values between matrix
	double temp_UB = (1/(target-(target*(tolerance/100)))); // 3007
	long upperBound = temp_UB * 1000000;
	double temp_LB = (1/(target+(target*(tolerance/100)))); // 2721
	long lowerBound = temp_LB * 1000000;

	vector <long> map_inverse_resistances;
	map_inverse_resistances.push_back(-1);

	for (long i = upperBound; i>=0; --i) { // MIGHT NEED TO PUT THE WHOLE INDIVIDUAL INCREMENTS IN THE MAT TO MAKE SURE THE TABLE IS FILLED OUT PROPERLY
		map_inverse_resistances.push_back(i);
	}

	// Make the matrix used for dp and fill up top row with false and the left with true excluding index 0
	vector < vector < bool > > mat;
	vector <bool> temp_vect;
    temp_vect.resize(upperBound+1, false);
	for (long i = 0; i < resistor_vals.size(); ++i) { // Push all arrays to the matrix
		mat.push_back(temp_vect);
	}

    mat.at(0).resize(upperBound+1, false); //top row to false. possible bug fix: upperBound+1
	for (long i = 0; i < resistor_vals.size(); ++i) { 
		mat.at(i).at(0) = true;
	}
	for (long i = 1; i < resistor_vals.size(); ++i) {
		for (long j = 1; j < upperBound+1; ++j) {
			mat.at(i).at(j) = false;
		}
	}

	// Make vector for the inverse of the resistor values
	// Traverse through the matrix and fill with bool vals as to whether or not it can make a specific sum
	for (long i = 1; i < mat.size(); ++i) { // resistor values
		for (long j = 1; j < upperBound+1; ++j) { // inverse resistance values

			// int inv_of_curr_resistor = (int)((1/resistor_vals.at(i)) * 10000000000);
			long resistor_big = (long)((1/resistor_vals.at(i))*1000000); // 26315
			// j = 26315
			if (j < resistor_big) { // If the resistance value at the current spot is less than the computed "
				mat.at(i).at(j) = mat.at(i-1).at(j); // set it to the value above itself
				continue;
			}

			float x = 1/(map_inverse_resistances.at(j)/1000000.0);
			float y = resistor_vals.at(i);

			if (fmodf(x,y) == 0) { // Evenly divides
				mat.at(i).at(j) = true;
				continue;
			}

			if (mat.at(i-1).at(j)) { // One above is true

				mat.at(i).at(j) = true;
				continue;
			} else { // One above is false
				mat.at(i).at(j) = mat.at(i).at(j-resistor_big);
				continue;
			}
		}
	}
	vector<sols> potential_sols;
	vector<float> useless;
    sols invalid(-1, -1, -1);
    invalid.resistors = useless;
    potential_sols.push_back(invalid);

	long inv_target = (long)((1/target)*1000000);

	for (long i = inv_target; i < upperBound+1; ++i) { // start at the target_inv
		if (mat.at(mat.size()-1).at(i)) { // if it's true then back track and see if valid by size

			vector <sols> purgatory = backtracking(mat, resistor_vals, i, upperBound, lowerBound, num_r, target, tolerance);

			if (purgatory.empty()) { // over num_r
				continue;
			} else { // gud then quit
				potential_sols.push_back(purgatory.at(0));


				break;
			}
		}
	}


	for (long i = inv_target-1; i > 0; --i) {
		if (mat.at(mat.size()-1).at(i)) {
			vector <sols> purgatory1 = backtracking(mat, resistor_vals, i, upperBound, lowerBound, num_r, target, tolerance);

			if (purgatory1.empty()) {
				continue;
			} else {
				potential_sols.push_back(purgatory1.at(0));
				// cout << purgatory1.at(0) << endl;
				break;
			}
		}
	}
	
	// Sort by the perror
	sort(potential_sols.begin(), potential_sols.end());

	return (potential_sols.size() > 1 ? potential_sols.at(1) : potential_sols.at(0)); //invalid is always at the zero index
}


int main(int argc, char * const argv[]){ 
    if(argc != 5){ //Check if there are five args
        cerr << "./resistancematcher <target> <tolerance %> <num resistors> <input file>";
        return 1;
    }
    //Check the validity of each input one at a time.
    float target;
    float tolerance;
    int num_r;
    //process target value
    try { 
        target = stof(argv[1]);
        if(target <= 0){ 
            cerr << "Error: Invalid target value '" << argv[1] << "'." << endl;
            return 1;
        }
    } catch (invalid_argument){ 
        cerr << "Error: Invalid target value '" << argv[1] << "'." << endl;
        return 1;
    }
    //process the tolerance percantage value.
    try{ 
        tolerance = stof(argv[2]);
        if(tolerance < 0){ 
            cerr << "Error: Invalid tolerance value '" << argv[2] << "'." << endl;
            return 1;
        }
    } catch (invalid_argument){ 
        cerr << "Error: Invalid tolerance value '" << argv[2] << "'." << endl;
        return 1;
    }
    //process the number of resistors value
    try{ 
        num_r = stoi(argv[3]);
        if(num_r <= 0 || num_r > 10){ 
            cerr << "Error: Invalid number of resistors '" << argv[3] << "'." << endl;
            return 1;
        }
    } catch (invalid_argument){ 
        cerr << "Error: Invalid number of resistors '" << argv[3] << "'." << endl;
        return 1;
    }
    //now we need to check the file and then parse for validity.
    pair<double, double> error_bounds = calculate_bounds(tolerance, target);
    vector<float> resistor_vals = process_file(argv[4], error_bounds.second);
    if(resistor_vals.empty()){ 
        output(num_r, tolerance, resistor_vals, target, 0, argv[1]);
    }
    sort(resistor_vals.begin(), resistor_vals.end()); //for binary search

    // Find out how much r begin used
    sols possible_ans = populate(resistor_vals, tolerance, num_r, target);

    output(num_r, tolerance, possible_ans.resistors, target, possible_ans.best_fit, argv[1]);
    return 0;
}