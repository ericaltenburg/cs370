/*******************************************************************************
 * Name        : XORDecryption.java
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : March 23, 2015
 * Description : Solution to Project Euler #59
 ******************************************************************************/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XORDecryption {
    private static char[] original, copy;
    private static final double THRESHOLD = 0.90;
    private static int requiredChars;

    public static boolean tryKey(char[] key) {
        int i = 0, j = 0, count = 0;
        for (char c : original) {
            copy[i] = c = (char)(c ^ key[j]);
            i++;
            j++;
            // We've gone through all characters in the key, so start over.
            if (j == 3) {
                j = 0;
            }
            if (c >= 'a' && c <= 'z' ||
                c >= 'A' && c <= 'Z' || c == ' ' || c == '.') {
                count++;
            }
        }

        if (count >= requiredChars) {
            System.out.print("key = " + String.valueOf(key) + ", sum = "
                    + getAsciiSum() + ": ");
            displayText();
            return true;
        }
        return false;
    }

    public static int getAsciiSum() {
        int sum = 0;
        for (char c : copy) {
            sum += c;
        }
        return sum;
    }

    public static void displayText() {
        for (char c : copy) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> asciiValues = new ArrayList<Integer>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("cipher.txt"))) {
            int c, t = 0;
            // Using features from the fast integer parser.
            while ((c = reader.read()) != -1) {
                if (c == ',' || c == '\n') {
                    asciiValues.add(t);
                    t = 0;
                } else {
                    t = t * 10 + (c - '0');
                }
            }
        } catch (IOException ioe) {
            System.err.println("Error: Cannot read file 'cipher.txt'.");
            System.exit(1);
        }
        original = new char[asciiValues.size()];
        copy = new char[asciiValues.size()];
        int i = 0;
        for (Integer asciiValue : asciiValues) {
            original[i++] = (char)asciiValue.intValue();
        }

        // Number of characters required for the key to be deemed correct.
        requiredChars = (int)(THRESHOLD * original.length);
        char[] key = new char[3];
        outer:
        for (char x = 'a'; x <= 'z'; x++) {
            key[0] = x;
            for (char y = 'a'; y <= 'z'; y++) {
                key[1] = y;
                for (char z = 'a'; z <= 'z'; z++) {
                    key[2] = z;
                    if (tryKey(key)) {
                        // This will break out of the labeled outer loop.
                        break outer;
                    }
                }
            }
        }
        System.exit(0);
    }
}
