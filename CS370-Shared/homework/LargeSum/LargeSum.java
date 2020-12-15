/**
 *	Eric Altenburg, Hamzah Nizami, and Constance Xu
 *	I pledge my honor that I have abided by the Stevens Honor System.
 */

import java.util.*;
import java.io.*;

public class LargeSum {
    static List<Integer>  method1() throws IOException {
        List<Integer> result = new ArrayList<Integer>();

        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader (file));

        int [][] mat = new int[200][50];
        String x;
        int temp1;

        // Populate matrix with 0's
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 50; j++) {
                mat[i][j] = 0;
            }
        }

        int j;

        // Populate matrix with the numbers from the file
        for (int i = 0; i < 200 && ((x = br.readLine()) != null); i++) {

            j = 49;
            for (int k = x.length()-1; k >= 0; k--) {
                mat[i][j] = Character.getNumericValue(x.charAt(k));
                j--;
            }
        }

        int temp2 = 0;

        // Perform addition
        for (int i = 49; i >= 0; i--) {
            for ( j = 199; j >= 0; j--) {
                temp2 += mat[j][i];
            }

            if (temp2 > 10) { // overflow
                int temp3 = (temp2 % 10);
                temp2 /= 10;
                result.add(0, temp3);
            } else { // no overflow

                result.add(0, temp2);
                temp2 = 0;
            }
        }

        // Overflow at the very end
        while (temp2 != 0) {
            if (temp2 > 10) { // overflow
                int temp3 = (temp2 % 10);
                temp2 /= 10;
                result.add(0, temp3);
            } else { // no overflow

                result.add(0, temp2);
                temp2 = 0;
            }
        }

        while (result.size() != 0 && result.get(0) == 0) {
        	if (result.size() == 1) { // Only a zero in the result
        		break;
        	}
            result.remove(0);
        }

        return result;
    }

    static StringBuilder method2(List<Integer> result) {
    	StringBuilder s = new StringBuilder();
    	for (int i = 0; i < result.size() && s.length()<10; i++){
    		s.append(result.get(i));
    	}
    	return s;
    }

    public static void print_arr(List<Integer> arr) {
        StringBuilder sb = new StringBuilder();

        for (Integer i : arr) {
            sb.append(i);
        }
        System.out.println(sb.toString());
    }

    public static void main (String []args) throws IOException {
        List<Integer> result = method1();

        System.out.print("Full sum: ");

        print_arr(result);

        System.out.print("First 10 digits: ");

        System.out.println(method2(result));
    }
}
