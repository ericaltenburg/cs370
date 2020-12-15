/*******************************************************************************
 * Name          : InputTest.java
 * Author        : Brian S. Borowski
 * Version       : 1.1
 * Date          : February 4, 2016
 * Last modified : January 22, 2018
 * Description   : Solution to SPOJ INTEST - Enormous Input Test
 *                 http://www.spoj.com/problems/INTEST/
 ******************************************************************************/
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

class InputReader {
    private InputStream stream;
    private byte[] buf = new byte[8192];
    private int currentChar, numChars;

    public InputReader(InputStream stream) {
        this.stream = stream;
    }

    public int nextInt() throws IOException {
        int c = '0', value = 0;
        while (true) {
            if (currentChar >= numChars) {
                currentChar = 0;
                numChars = stream.read(buf);
            }
            if ((c = buf[currentChar++]) < 33) {
                break;
            }
            value = (value << 3) + (value << 1) + (c & 15);
        }
        return value;
    }
}

class InputTest {

    public static int method1() throws IOException {
        int count = 0;
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String input = br.readLine();
        String[] parts = input.split(" ");
        int n = Integer.parseInt(parts[0]), k = Integer.parseInt(parts[1]);
        for (int i = 0; i < n; i++) {
            if (Integer.parseInt(br.readLine()) % k == 0) {
                count++;
            }
        }
        return count;
    }

    public static int method2() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(System.in);
        int n = readInt(bis), k = readInt(bis), count = 0;
        for (int i = 0; i < n; i++) {
            if (readInt(bis) % k == 0) {
                count++;
            }
        }
        return count;
    }

    public static int method3() throws IOException {
        InputReader ir = new InputReader(System.in);
        int n = ir.nextInt(), k = ir.nextInt(), count = 0;
        for (int i = 0; i < n; i++) {
            if (ir.nextInt() % k == 0) {
                count++;
            }
        }
        return count;
    }

    private static int readInt(InputStream in) throws IOException {
        int value = 0;
        boolean found = false;

        for (int c = 0; (c = in.read()) != -1;) {
            if (c >= '0' && c <= '9') {
                found = true;
                value = value * 10 + (c - '0');
            } else if (found)
                break;
        }

        return value;
    }

    public static void main(String[] args) throws IOException {
        // Method 3 is the fastest.
        System.out.println(method3());
    }
}
