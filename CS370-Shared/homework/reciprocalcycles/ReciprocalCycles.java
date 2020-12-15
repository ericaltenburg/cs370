/**
 *	Authors:		Eric Altenburg, Hamzah Nizami, Constance Xu
 *	File: 			ReciprocalCycles.java
 *	Description:	Modified Euler 26
 * 	Date:			10 March 2020
 *	Pledge: 		I pledge my honor that I have abided by the Stevens Honor System.
 **/

import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.*;

public class ReciprocalCycles {

    /**
     * Formats the output
     * @param num
     * @param cycle_length
     * @param divisor
     */
    public static void output (String num, int cycle_length, int divisor) {
        System.out.print("1/" + divisor + " = " + num);
        if (cycle_length > 0) {
            System.out.print(", cycle length " + cycle_length);
        }
    }

    /**
     * Finds the cycle length and makes the string through long division. If a remainder is found again, then there is
     * a cycle. P cool stuff.
     * @param input
     */
    public static void cycle_length_finder (int input) {
        StringBuilder s = new StringBuilder();
        int cycle_length = 1;
        List<Integer> remainders = new ArrayList<>();
        int remainder = 1;
        int parenthesis_index = 1;

        s.append(1/input);

        if (input > 1) { // Check easy edge case
            s.append("."); // We know there is going to be a decimal now
            int numerator = 10;
            remainders.add(numerator);

            for(;;) { // Cool looking infinite for loop
                if (numerator == 0) { // the number divides evenly so reset the numbers
                    cycle_length = 0;
                    break;
                }

                int amount_divides = numerator / input;

                s.append(amount_divides);
                numerator = (numerator - (amount_divides * input)) * 10;

                if (remainders.contains(numerator)) { // You have found the repeat break out
                    for (int i = 0; i < remainders.size(); i++) {
                        if (remainders.get(i) == numerator) {
                            parenthesis_index = i;
                            break;
                        }
                    }

                    // Add in the parenthesis
                    StringBuilder temp = new StringBuilder();
                    temp.append(s.substring(0, parenthesis_index+2));
                    temp.append("(");
                    temp.append(s.substring(parenthesis_index+2));
                    temp.append(")");
                    s.setLength(0);
                    s = temp;

                    break;
                }

                remainders.add(numerator); // Long division stuff
                cycle_length++;
            }
        }

        output(s.toString(), cycle_length-parenthesis_index, input);
    }

    public static void main(String[] args) {
        int divisor = 2;

        // ERROR CHECKING
       if (args.length != 1) { // Usage error
           System.err.print("Usage: java ReciprocalCycles <denominator>");
           System.exit(1);
       }
       try { // If it is a String
           divisor = Integer.parseInt(args[0]);
       } catch (NumberFormatException e) {
           System.err.print("Error: Denominator must be an integer in [1, 2000]. Received '"+ args[0] +"'.");
           System.exit(1);
       }
       if (divisor < 1 || divisor > 2000) { // Invalid range
           System.err.print("Error: Denominator must be an integer in [1, 2000]. Received '"+ divisor +"'.");
           System.exit(1);
       }

        cycle_length_finder(divisor);
    }
}