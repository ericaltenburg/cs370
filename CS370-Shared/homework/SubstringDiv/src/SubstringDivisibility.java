/**
 *	Eric Altenburg, Hamzah Nizami, and Constance Xu
 *	I pledge my honor that I have abided by the Stevens Honor System.
 */

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class SubstringDivisibility {

    /**
     * Generates HashMap with a value containing multiples of prime number n that have the same last two
     * digits. The key is said last two digits as a String.
     * @param n Prime number supplied to create multiples of
     * @return HashMap<String, ArrayList<String>>
     */
    public static HashMap<String, ArrayList<String>> generateHashMap(int n, String y){
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = n; i < 1000; i+=n){
            String x = Integer.toString(i);
            if (x.length() == 2){ //append a 0 in front if the number is only two digits
                x = "0" + x;
            }
            else if (x.length() == 1){ //no duplicates allowed
                continue;
            }
            if (x.charAt(1) != x.charAt(2) && x.charAt(0) != x.charAt(1) && x.charAt(0) != x.charAt(2) && y.contains(x.substring(1,2)) && y.contains(x.substring(2,3)) && y.contains(x.substring(0,1))){
                arr.add(x);
            }
        }
        for (int i = 0; i < arr.size(); i++){
            //if the key exists then, append to that arraylist in the value
            //else, generate new arraylist for the value and then create key
            //value pair
            String key = arr.get(i).substring(1,3);
            String value = arr.get(i);
            if (map.containsKey(key)){ //check if the key ex
                //map.replace(key, map.get(key).add(value));
                ArrayList<String> z = map.get(key);
                z.add(value);
                map.replace(key, z);
            }
            else {
                ArrayList<String> curr = new ArrayList<String>();
                curr.add(value);
                map.put(key, curr);
            }
        }
        return map;
    }


    /**
     * Pad zeroes to the front of string where it might only have two digits
     * @param num String num to be padded
     * @param offset offset to be padded by
     * @return padded string of num
     */
    public static String padZero(String num, int offset){
        for(int i = 0; i < offset; i++) {
            num = "0" + num;
        }
        return num;
    }

    /**
     *
     * @param str - Input string
     * @param lastDivNum - Largest prime to be used
     * @return
     */
    public static ArrayList<String> generateNthMultiples(String str, int lastDivNum){

        // Convert the input string to an array of chars then sort and store in string
        char[] tempArray = str.toCharArray();
        Arrays.sort(tempArray);
        String sortedInput = new String(tempArray);

        // Grab the left-most number from sorted input string and pad zeros to it
        int hundrethPlace = Character.getNumericValue(sortedInput.charAt(0));
        int lowestNum = Integer.parseInt(hundrethPlace + "00");

        // Arraylist to hold all multiples
        ArrayList<String> allMultiples = new ArrayList<>();

        // Generates all multiples of the largest prime up to 1000 and stores them in the arraylist
        for(int i = lowestNum; i < 1000; i++) {
            int offset = 3 - Integer.toString(i).length();

            String convertedNum = (offset == 0 ? Integer.toString(i) : padZero(Integer.toString(i), offset));

            // Filters out stuff based on the input string
            if (i % lastDivNum == 0 && str.indexOf(convertedNum.charAt(0)) >= 0 && str.indexOf(convertedNum.charAt(1)) >= 0 && str.indexOf(convertedNum.charAt(2)) >= 0) {
                Set<Character> checkDuplicates = new HashSet<>();
                for(int j = 0; j < convertedNum.length(); j++) {
                    checkDuplicates.add(convertedNum.charAt(j));
                }
                if(checkDuplicates.size() != convertedNum.length()) {
                    continue;
                } else {
                    String temp = Integer.toString(i);
                    if (temp.length() != 3) {
                        temp = padZero(temp, 3-temp.length());
                    }
                    allMultiples.add(temp);
                }
            }
        }


        return allMultiples;
    }

    /**
     * Checks for dups
     * @param temp
     * @return
     */
    public static boolean noDupChecker (String temp){
        char[] toChar = temp.toCharArray();
        Set<Character> split = new HashSet<>();

        for (char c : toChar) {
            split.add(c);
        }
        return (toChar.length == split.size() ? true : false);
    }

    public static char findMissing (String inputString, String temp) {
        char[] input = inputString.toCharArray();
        char[] built = temp.toCharArray();
        Arrays.sort(input);
        Arrays.sort(built);
        char answer = ' ';
        for(int i = 0; i < input.length; i++) {
            if (i >= built.length) {
                answer = input[i];
                break;
            }
            if(input[i] != built[i]){
                answer = input[i];
                break;
            }
        }
       return answer;
    }

    /**
     * Recursive helper for dynamically making for loops
     * @param inputString
     * @param temp
     * @param result
     * @param allMaps
     * @param level
     */
    public static void buildPermutationsHelper(String inputString, String temp, ArrayList<String> result, ArrayList<HashMap<String, ArrayList<String>>> allMaps, int level) {
        if (temp.length() == inputString.length()-1) {
            // If statement to check the stuff to make sure it's valid
            if (noDupChecker(temp)) {


                char answer = findMissing(inputString, temp);

                temp = answer + temp;
                result.add(temp);
            }

            return;
        }

        HashMap<String, ArrayList<String>> curr = allMaps.get(level);
        String query = temp.substring(0,2);

        ArrayList<String> val = curr.get(query);

        if (val == null) {
            return;
        }

        for (int i = 0; i < val.size(); i++) {
            String valNum = val.get(i).charAt(0) + temp;

            buildPermutationsHelper(inputString, valNum, result, allMaps, level-1);
        }
    }

    /**
     * Generates correct strings
     * @param inputString
     * @param allMaps
     * @param primes
     * @param index
     * @return
     */
    public static ArrayList<String> buildPermutations(String inputString, ArrayList<HashMap<String, ArrayList<String>>> allMaps, int [] primes, int index) {

        // Sub in the primes array through parameters along with the calculation to help the run time
        ArrayList<String> allMultiples = generateNthMultiples(inputString, primes[index]);

        // Will hold all partial strings one digit off
        ArrayList<String> result = new ArrayList<>();

        String firstThree;

        // Iterate through the multiples of the largest prime
        for (int i = 0; i < allMultiples.size(); i++) {

            firstThree = allMultiples.get(i);

            //now we query the hashmap for an arraylist of values
            String query = allMultiples.get(i).substring(0, 2);
            String tail = allMultiples.get(i).substring(2);

            buildPermutationsHelper(inputString, firstThree, result, allMaps, index-1);
        }

        return result;

    }

    public static void main(String[] args) {
        // Read user input from the command line
        String str = args[0];

        /* for timing */
        long start = System.nanoTime();
        /* for timing */        

        int len = str.length();
        int prime_index = len-4;
        int [] primes = {2, 3, 5, 7, 11, 13, 17};

        // List to hold all hashmaps generated
        ArrayList<HashMap<String, ArrayList<String>>> maps = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        // Generate hashmaps and load them into the list
        if (len != 4) {
            for (int i = 0; i <= prime_index-1; i++) {
                maps.add(generateHashMap(primes[i], str));
            }
        } else {
            ArrayList<String> lenFour = generateNthMultiples(str, 2);
            int sum = 0;
            char answer = ' ';
            int i = 0;
            for (String s : lenFour) {
                answer = findMissing(str, lenFour.get(i));
                i++;
                s = answer + s;
                result.add(s);
            }
        }

        // List to be sorted holding the permutations
        result = buildPermutations(str, maps, primes, prime_index);

        //build_permutation(result, maps, primes, prime_index, str);

        Collections.sort(result);
        long sum = 0;
        for (String num : result) {
            sum += Long.parseLong(num);
            System.out.println(num);
        }
        System.out.println("Sum: " + sum);

        /* for timing */
        System.out.printf("Elapsed time: %.6f ms\n", (System.nanoTime() - start) / 1e6);
        /* for timing */
    }
}