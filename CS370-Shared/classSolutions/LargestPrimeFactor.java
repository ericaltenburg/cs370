/*******************************************************************************
 * Name        : LargestPrimeFactor.java
 * Author      : Brian S. Borowski
 * Version     : 1.0
 * Date        : February 2, 2016
 * Description : Solution to Project Euler #3
 ******************************************************************************/
public class LargestPrimeFactor {

    public static void main(String[] args) {
        long start = System.nanoTime(),
             n = 600851475143L;
        // Factors repeat after the floor of the sqrt(n), so stop there.
        int i = 2, maxFactor = (int)Math.sqrt(n);

        while (n % i == 0) {
            // We found a prime factor that is 2.
            n /= i;
            System.out.println(i);
        }
        i++;
        maxFactor = (int)Math.sqrt(n);
        while (i <= maxFactor) {
            if (n % i == 0) {
                // We found a prime factor.
                n /= i;
                System.out.println(i);
                // Recompute the new upper bound on the maximum possible
                // factor.
                maxFactor = (int)Math.sqrt(n);
            } else {
                i += 2;
            }
        }
        // This one is the largest prime factor.
        System.out.println(n);
        double elapsed = (System.nanoTime() - start) / 1e6;
        System.out.println("Elapsed time: " + elapsed + " ms");
    }
}
