/*******************************************************************************
 * Name          : PathSumThreeWays.java
 * Author        : Brian S. Borowski
 * Version       : 1.0
 * Date          : April 6, 2015
 * Last modified : April 10, 2017
 * Description   : Solution to Project Euler #82
 ******************************************************************************/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathSumThreeWays {
    private int[][] values, cost;
    private int minSum;

    public PathSumThreeWays(String filename) throws FileNotFoundException,
            IOException,
            NumberFormatException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> valueList = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            valueList.add(line);
        }
        reader.close();
        values = new int[valueList.size()][];
        for (int row = 0; row < values.length; row++) {
            line = valueList.get(row);
            String[] parts = line.split(",");
            values[row] = new int[parts.length];
            for (int col = 0; col < parts.length; col++) {
                try {
                    values[row][col] = Integer.parseInt(parts[col]);
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Bad value " + parts[col]
                            + " on line " + (row + 1) + ".");
                }
            }
        }
        displayTable(values);
        System.out.println();
    }

    /**
     * Dynamic programming solution that uses a two-dimensional array as the
     * cost table.
     * @return the minimum sum from the leftmost column to the rightmost column
     * in the two-dimensional values member variable.
     */
    public int getMinSum() {
        int m = values[0].length, n = values.length;
        cost = new int[n][m];

        for (int row = 0; row < n; row++) {
            cost[row][0] = values[row][0];
        }

        for (int col = 1; col < m; col++) {
            cost[0][col] = cost[0][col - 1] + values[0][col];
            // Traverse down first.
            for (int row = 1; row < n; row++) {
                cost[row][col] = Math.min(cost[row - 1][col],
                                       cost[row][col - 1]) + values[row][col];    
            }
            // Traverse up, possibly finding a lower cost.
            for (int row = n - 2; row >= 0; row--) {
                cost[row][col] = Math.min(cost[row][col],
                                       cost[row + 1][col] + values[row][col]);
            }
        }
        displayTable(cost);
        System.out.println();
        // Find the minimum value in the rightmost column.
        minSum = cost[0][m - 1];
        for (int row = 1; row < n; row++) {
            if (cost[row][m - 1] < minSum) {
                minSum = cost[row][m - 1];
            }
        }
        return minSum;
    }

    /**
     * Alternate solution that uses a one-dimensional array as the cost table.
     * @return the minimum sum from the leftmost column to the rightmost column
     * in the two-dimensional values member variable.
     */
    public int getMinSum2() {
        int n = values.length;
        int[] sol = new int[n];
         
        for (int i = 0; i < n; i++) {
            sol[i] = values[i][n - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            // Traverse down first.
            sol[0] += values[0][i];
            for (int j = 1; j < n; j++) {
                sol[j] = Math.min(sol[j - 1] + values[j][i],
                                  sol[j] + values[j][i]);
            }
            // Traverse up, possibly finding a lower cost.
            for (int j = n - 2; j >= 0; j--) {
                sol[j] = Math.min(sol[j], sol[j + 1] + values[j][i]);
            }
        }
        int min = sol[0];
        for (int i = 1; i < n; i++) {
            if (sol[i] < min) {
                min = sol[i];
            }
        }
        return min;
    }

    /**
     * Displays a two-dimensional array of integers on the screen, nicely
     * formatted to the width of the widest cell.
     * @param table a two-dimensional array of integers
     */
    public void displayTable(int[][] table) {
        int m = table[0].length,
            n = table.length,
            maxCellWidth = numDigits(Math.max(Math.max(m, n), getMax(table))),
            maxRowWidth = numDigits(m);
        for (int i = 0, len = numDigits(n); i < len; i++) {
            System.out.print(" ");
        }
        for (int col = 0; col < m; col++) {
            System.out.print(" ");
            int cellLength = numDigits(col);
            for (int i = maxCellWidth - cellLength; i > 0; i--) {
                System.out.print(" ");
            }
            System.out.print(col);
        }
        System.out.println();
        for (int row = 0; row < n; row++) {
            int cellLength = numDigits(row);
            for (int i = maxRowWidth - cellLength; i > 0; i--) {
                System.out.print(" ");
            }
            System.out.print(row);
            for (int col = 0; col < m; col++) {
                cellLength = numDigits(table[row][col]);
                for (int i = maxCellWidth - cellLength; i > 0; i--) {
                    System.out.print(" ");
                }
                System.out.print(" " + table[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * Backtracks over the cost table to determine the integers that comprise
     * the minimum sum, starting in the right column and ending in the left
     * column.
     * @return an array of integers that comprise the minimum sum.
     */
    public int[] getSolution() {
        ArrayList<Integer> solutionList = new ArrayList<Integer>();
        int n = cost.length - 1,
            m = cost[0].length - 1,
            min = cost[0][m],
            row = 0,
            col = m,
            minRow = 0, minCol = 0;
        for (row = 0; row < n; row++) {
            if (cost[row][col] < min) {
                min = cost[row][col];
                minRow = row;
            }
        }
        row = minRow;
        // If there's only one column, col will already equal 0. In that case,
        // do not add the value to the list because it will be added after the
        // while loop terminates.
        if (col != 0) {
            solutionList.add(values[row][col]);
        }
        col--;

        while (col > 0) {
            solutionList.add(values[row][col]);
            minRow = row;
            minCol = col;

            // Determine if the minimum value is above, below, or to the left
            // of the current cell.
            min = cost[row][col];
            // Above
            if (row > 0 && cost[row - 1][col] < min) {
                min = cost[row - 1][col];
                minRow = row - 1;
            }
            // Below
            if (row < n && cost[row + 1][col] < min) {
            	min = cost[row + 1][col];
                minRow = row + 1;
            }
            // Left
            if (cost[row][col - 1] < min) {
                min = cost[row][col - 1];
                minRow = row;
                minCol = col - 1;
            }
            row = minRow;
            col = minCol;
        }
        solutionList.add(values[row][0]);
        int[] solution = new int[solutionList.size()];
        for (int i = solutionList.size() - 1, j = 0; i >= 0; i--, j++) {
            solution[j] = solutionList.get(i);
        }
        // Sanity check to verify the sum of the numbers found during back-
        // tracking is equal to the minimum sum found in the cost table.
        assert getSum(solution) == minSum;
        return solution;
    }

    public static int numDigits(int num) {
        int count = 1;
        while (num >= 10) {
            num /= 10;
            ++count;
        }
        return count;
    }

    public static int getSum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private int getMax(int[][] table) {
        int m = table[0].length, n = table.length, max = Integer.MIN_VALUE;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (table[row][col] > max) {
                    max = table[row][col];
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        String filename = "matrix.txt";
        PathSumThreeWays pathSum = null;
        try {
            pathSum = new PathSumThreeWays(filename);
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error: File '" + filename + "' not found.");
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("Error: Cannot read '" + filename + "'.");
            System.exit(1);
        } catch (NumberFormatException nfe) {
            System.err.println("Error: " + nfe.getMessage());
            System.exit(1);
        }
        System.out.println("Min sum: " + pathSum.getMinSum());
        System.out.println(
                "Values:  " + Arrays.toString(pathSum.getSolution()));
        System.exit(0);
    }
}
