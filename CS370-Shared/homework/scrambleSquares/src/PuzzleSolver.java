//package Assignment4;
/**
 *  Name:           PuzzleSolver.java
 *  Authors:        Eric Altenburg, Hamzah Nizami, and Constance Xu
 *  Description:    Scramble Puzzle Solver
 *  Pledge:         I pledge my honor that I have abided by the Stevens Honor System.
 *  Date:           25 February 2020
 **/
import java.util.*;
import java.io.*;
public class PuzzleSolver {
    public static class Pair<L,R> {
        private L l;
        private R r;
        public Pair(L l, R r){
            this.l = l;
            this.r = r;
        }
        public L getL(){ return l; }
        public R getR(){ return r; }
        public void setL(L l){ this.l = l; }
        public void setR(R r){ this.r = r; }
    }
    private final static class Tile implements Cloneable{
        final int numTile;
        final ArrayList<Integer> tileSides;
        public Tile (ArrayList<Integer> arr, int num) {
            this.numTile = num;
            this.tileSides = arr;
        }
        /**
         * Performs a rotation on a given tile 90 degrees to the right
         */
//        void doRotations() {
////            this.tileSides.add(0, this.tileSides.get(3));
////            this.tileSides.remove(4);
////        }
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(this.tileSides);
            return s.toString();
        }
//        public Tile clone() throws CloneNotSupportedException{
//            Tile obj = (Tile)super.clone();
//            obj.tileSides = new ArrayList<>(this.tileSides);
//            return obj;
//        }
    }
    public static ArrayList<String> printed_sols = new ArrayList<>();
    public static HashMap<Character, Integer> charToNum = new HashMap<>();
    public static ArrayList<Pair<String, HashMap<Integer, Tile>>> actual_solutions = new ArrayList<>();
    public static String nextBoardPerm(String board){
        int j = 0, k = 3, l = 6;
        String boardDigits = board;
        StringBuilder sb = new StringBuilder();
        String newRotation = "";
        for(int i = 0; i < 4; i++) {
            while (l < 9) {
                newRotation = sb.append(boardDigits.charAt(l)).append(boardDigits.charAt(k)).append(boardDigits.charAt(j)).toString();
                j++;
                k++;
                l++;
            }
        }
        return newRotation;
    }
    public static String lowestBoardPerm(String board){
        String result = board;
        for(int i = 0; i < 3; i++) {
            String nextPerm = nextBoardPerm(board);
            if(Integer.parseInt(nextPerm) < Integer.parseInt(result)) {
                result = nextPerm;
            }
            board = nextPerm;
        }
        return result;
    }
    public static boolean validMove(ArrayList<Tile> puzzle, int numTile) {
        if (numTile == 0) {
            return true;
        }
        if (numTile < 3) {
            return puzzle.get(numTile).tileSides.get(3) == puzzle.get(numTile - 1).tileSides.get(1) * -1;
        } else if (numTile % 3 == 0) {
            return puzzle.get(numTile).tileSides.get(0) == puzzle.get(numTile - 3).tileSides.get(2) * -1;
        } else {
            return puzzle.get(numTile).tileSides.get(0) == puzzle.get(numTile - 3).tileSides.get(2) * -1 && puzzle.get(numTile).tileSides.get(3) == puzzle.get(numTile - 1).tileSides.get(1) * -1;
        }
    }
    /**
     * Mainly used to print a tile for testing
     * @param x - Single tile to be printed
     * @return String representing the tile
     */
    public static String printS(Tile x) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i ++) {
            s.append(x.tileSides.get(i));
            s.append(",");
        }
        return s.toString();
    }
    public static ArrayList<String> backToString(ArrayList<Integer> temp, HashMap<Integer, Character> inversedCharToNum){
        ArrayList<String> temp1 = new ArrayList<String>();
        for (int i = 0; i < temp.size();i++){
            StringBuilder y = new StringBuilder();
            int x = temp.get(i);
            y.append(inversedCharToNum.get(Math.abs(x)));
            if (x < 0) {
                y.append("0");
            } else {
                y.append("1");
            }
            temp1.add(y.toString());
        }
        return temp1;
    }
    /**
     * str will give me the input string e.g. "123459876"
     * the HashMap will have the first digit that we are looking for which will have the corresponding
     * tile to it. i.e. 1: tile([B0,R1,Y0,G1])
     */
    public static String prettyPrint(String str, HashMap<Integer, Tile> solutions, HashMap<Integer, Character> inversedCharToNum){
        StringBuilder x = new StringBuilder();
        x.append("+--------+--------+--------+\n");
        //first tile row, top
        ArrayList temp1 = backToString(solutions.get(Integer.valueOf(str.substring(0,1))).tileSides, inversedCharToNum);
        ArrayList temp2 = backToString(solutions.get(Integer.valueOf(str.substring(1,2))).tileSides, inversedCharToNum);
        ArrayList temp3 = backToString(solutions.get(Integer.valueOf(str.substring(2,3))).tileSides, inversedCharToNum);
        x.append("|" + str.substring(0,1) + "  "+ temp1.get(0) +"   |"+ str.substring(1,2) +"  "+ temp2.get(0) +"   |"+ str.substring(2,3) + "  "+ temp3.get(0)+"   |\n");
        //first tile row, middle
        x.append("|"+temp1.get(3) +"    "+ temp1.get(1)+"|"+ temp2.get(3)+"    "+ temp2.get(1)+"|"+ temp3.get(3)+"    "+ temp3.get(1)+"|\n");
        //first tile row, bottom
        x.append("|   "+temp1.get(2)+"   |   "+ temp2.get(2)+"   |   "+temp3.get(2)+"   |\n");
        x.append("+--------+--------+--------+\n");
        //After this code runs, we should have the following:
        /**
         * +--------+--------+--------+
         * |1  G0   |2  B0   |4  G0   |
         * |Y1    R0|R1    Y0|Y1    B0|
         * |   B1   |   B1   |   G1   |
         * +--------+--------+--------+
         *
         */
        ArrayList temp4 = backToString(solutions.get(Integer.valueOf(str.substring(3,4))).tileSides, inversedCharToNum);
        ArrayList temp5 = backToString(solutions.get(Integer.valueOf(str.substring(4,5))).tileSides, inversedCharToNum);
        ArrayList temp6 = backToString(solutions.get(Integer.valueOf(str.substring(5,6))).tileSides, inversedCharToNum);
        //second tile row, top
        x.append("|" + str.substring(3,4) + "  "+ temp4.get(0) +"   |"+ str.substring(4,5) +"  "+ temp5.get(0) +"   |"+ str.substring(5,6) + "  "+ temp6.get(0)+"   |\n");
        //second tile row, middle
        x.append("|"+temp4.get(3) +"    "+ temp4.get(1)+"|"+ temp5.get(3)+"    "+ temp5.get(1)+"|"+ temp6.get(3)+"    "+ temp6.get(1)+"|\n");
        //second tile row, bottom
        x.append("|   "+temp4.get(2)+"   |   "+ temp5.get(2)+"   |   "+temp6.get(2)+"   |\n");
        x.append("+--------+--------+--------+\n");
        //After this code runs, we should have the following:
        /**
         * +--------+--------+--------+
         * |1  G0   |2  B0   |4  G0   |
         * |Y1    R0|R1    Y0|Y1    B0|
         * |   B1   |   B1   |   G1   |
         * +--------+--------+--------+
         * |9  B0   |6  B0   |7  G0   |
         * |Y1    R0|R1    Y0|Y1    R0|
         * |   G1   |   G1   |   B1   |
         * +--------+--------+--------+
         */
        ArrayList temp7 = backToString(solutions.get(Integer.valueOf(str.substring(6,7))).tileSides, inversedCharToNum);
        ArrayList temp8 = backToString(solutions.get(Integer.valueOf(str.substring(7,8))).tileSides, inversedCharToNum);
        ArrayList temp9 = backToString(solutions.get(Integer.valueOf(str.substring(8,9))).tileSides, inversedCharToNum);
        //third tile row, top
        x.append("|" + str.substring(6,7) + "  "+ temp7.get(0) +"   |"+ str.substring(7,8) +"  "+ temp8.get(0) +"   |"+ str.substring(8,9) + "  "+ temp9.get(0)+"   |\n");
        //third tile row, middle
        x.append("|"+temp7.get(3) +"    "+ temp7.get(1)+"|"+ temp8.get(3)+"    "+ temp8.get(1)+"|"+ temp9.get(3)+"    "+ temp9.get(1)+"|\n");
        //third tile row, bottom
        x.append("|   "+temp7.get(2)+"   |   "+ temp8.get(2)+"   |   "+temp9.get(2)+"   |\n");
        x.append("+--------+--------+--------+\n");
        //After this code runs, we should have the following:
        /**
         * +--------+--------+--------+
         * |1  G0   |2  B0   |4  G0   |
         * |Y1    R0|R1    Y0|Y1    B0|
         * |   B1   |   B1   |   G1   |
         * +--------+--------+--------+
         * |9  B0   |6  B0   |7  G0   |
         * |Y1    R0|R1    Y0|Y1    R0|
         * |   G1   |   G1   |   B1   |
         * +--------+--------+--------+
         * |3  G0   |8  G0   |5  B0   |
         * |R1    B0|B1    Y0|Y1    G0|
         * |   Y1   |   R1   |   R1   |
         * +--------+--------+--------+
         */
        return x.toString();
    }
    /**
     * First prints out the tiles that were input with a simple for loop, then prints out solutions that are not duplicates
     * with a pretty print function
     * @param  tiles - Initial tiles unchanged
     * @param solutions - solutions in pairs with the order that they are placed, then the other is the orientation of
     */
    public static void outputTiles (ArrayList<Tile> tiles, ArrayList<Pair<String, HashMap<Integer, Tile>>> solutions, HashMap<Integer, Character> inversedCharToNum) {
        // TODO
        StringBuilder s = new StringBuilder();
        s.append("Input tiles:\n");
        for (int i = 1; i < 10; i++) {
            s.append(i);
            s.append(". <");
            for (int j = 0; j < 4; j++) {
                int temp = tiles.get(i-1).tileSides.get(j); // 2
                StringBuilder side = new StringBuilder();
                side.append(inversedCharToNum.get(Math.abs(temp))); // B
                if (temp < 0) {
                    side.append("0");
                } else {
                    side.append("1");
                }
                s.append(side.toString());
                if (j == 3) {
                    continue;
                } else {
                    s.append(", ");
                }
            }
            s.append(">\n");
        }
        System.out.println(s.toString());
        // Loop through solutions use pairs
        if (solutions.size() == 0) {
            System.out.println("No solution found.");
            return;
        }
        System.out.print(solutions.size() + " unique solution" + (solutions.size() != 1 ? "s" : "") + " found:\n");
//        for (int i = 0; i < solutions.size(); i++) {
//            System.out.println(prettyPrint(solutions.get(i).l, solutions.get(i).r, inversedCharToNum));
//        }
    }
    /**
     * Reads the file and line by line converts to a tile and adds to an array list with the number associated
     * and the total amt of rotations
     * @param file
     * @return - Array list of tiles
     * @throws IOException
     */
    public static ArrayList<Tile> readFile (File file, HashMap<Integer, Character> inversedCharToNum) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader (file));
        ArrayList<Tile> tempArr = new ArrayList<>();
        String x; // for reading file
        int i = 1; // for assigning
        // Read each line individually
        while ((x = br.readLine()) != null) {
            int lastMappedNum = 1;
            ArrayList<Integer> tempNums = new ArrayList<>();
            // Separate through commas
            for (int j = 1, k = 0; j < 5; j ++) {
                String temp = x.substring(k, k+2); //B0 or B1 etc.
                if(!charToNum.containsKey(temp.charAt(0))) {
                    charToNum.put(temp.charAt(0), lastMappedNum++);
                }
                if(temp.charAt(1) == '0') {
                    tempNums.add(charToNum.get(temp.charAt(0)) *-1);
                } else {
                    tempNums.add(charToNum.get(temp.charAt(0)));
                }
                k+=3;
            }
            Tile temp = new Tile(tempNums, i);
            tempArr.add(temp);
            i++;
        }
        Character[] keys = charToNum.keySet().toArray(new Character[0]);
        for (int l = 0; l < charToNum.size(); l++){
            inversedCharToNum.put(charToNum.get(keys[l]),keys[l]);
        }
        return tempArr;
    }
    /**
     * Main algorithm with backtracking and pruning
     * @param inputTiles
     * @return
     */
    public static ArrayList<Pair<String, HashMap<Integer, Tile>>> algo (ArrayList<Tile> inputTiles, HashMap<Integer, Character> inversedCharToNum) throws CloneNotSupportedException {
        ArrayList<Pair<String, HashMap<Integer, Tile>>> solutions = new ArrayList<>();
        boolean[] used = new boolean[10];
        Arrays.fill(used, Boolean.FALSE);
        ArrayList<Tile> usedPieces = new ArrayList<>();
        ArrayList<Integer> empty = new ArrayList<Integer>(Collections.nCopies(4, 0));
        for (int i = 0; i < 10; i++) {
            usedPieces.add(new Tile (empty, 0));
        }
        StringBuilder order = new StringBuilder();
        algo_helper (inputTiles, 0, order, solutions, usedPieces, used, empty, inversedCharToNum);
        return solutions;
    }
    public static Tile cloneT (Tile t) {
        Tile temp = new Tile(t.tileSides, t.numTile);
        return temp;
    }
    /**
     * Where meat of the algo is done
     * @param inputTiles - Input tiles that will eventually be modified based on their orientation
     * @param num - Pieces placed thus far
     * @param order - Order that the tiles are placed
     * @param solutions - Arraylist of pairs that holds the order and the tiles
     * @param usedPieces - Pieces that have been used already of size 10 for easier indexing when it comes to pieces
     * @param used - Bool representation of what's been used
     */
    public static void algo_helper (ArrayList<Tile> inputTiles, int num, StringBuilder order, ArrayList<Pair<String, HashMap<Integer, Tile>>> solutions, ArrayList<Tile> usedPieces, boolean [] used, ArrayList<Integer> empty, HashMap<Integer, Character> inversedCharToNum) throws CloneNotSupportedException {
        if (num == 9) { // solution is found
            HashMap<Integer, Tile> tempH = new HashMap<>();
            for (int i = 1; i < 10; i++) {
                Tile tempT = cloneT(inputTiles.get(i-1));
                tempH.put(tempT.numTile, tempT);
            }
            printed_sols.add(prettyPrint(order.toString(), tempH, inversedCharToNum));
            actual_solutions.add(new Pair(order.toString(), tempH));
            solutions.add(new Pair(order.toString(), tempH));
            return;
        }
        for (int i = 1; i < 10; i++) { // Iterate through the board
            if (!used[i]) { // If the piece has not been used
                used[i] = true;
                for (int j = 0; j < 4; j++) {
                    usedPieces.set(num, inputTiles.get(i-1)); // Add it to the used pieces
                    if (validMove(usedPieces, num)) {
                        order.append(i);
                        if (num+1 == 9) {
//                            usedPieces.set(9, inputTiles.get(i));
                            for (int k = 0; k < 4; k++) {
                                if (validMoveFinal(usedPieces)) {
                                    algo_helper(inputTiles, num+1, order, solutions, usedPieces, used, empty, inversedCharToNum);
                                } else {
                                    inputTiles.set(i-1, doRotations(inputTiles.get(i-1)));
                                }
                            }
                            if (validMoveFinal(usedPieces)) {
                                algo_helper(inputTiles, num+1, order, solutions, usedPieces, used, empty, inversedCharToNum);
                            }
//                            usedPieces.set(9, new Tile(empty, 0));
                        }
                        else{
                            algo_helper(inputTiles, num+1, order, solutions, usedPieces, used, empty, inversedCharToNum);
                        }
                        order.deleteCharAt(order.length()-1);
                    }
                    usedPieces.set(num, new Tile(empty, 0));
                    inputTiles.set(i-1, doRotations(inputTiles.get(i-1)));
                }
                used[i] = false;
            }
        }
        return;
    }
    public static boolean validMoveFinal (ArrayList<Tile> inputTile){
        boolean a = inputTile.get(5).tileSides.get(2) + inputTile.get(8).tileSides.get(0) == 0;
        boolean b = inputTile.get(7).tileSides.get(1) + inputTile.get(8).tileSides.get(3) == 0;
        return a &&b;
    }
    public static Tile doRotations(Tile t) {
        t.tileSides.add(0, t.tileSides.get(3));
        t.tileSides.remove(4);
        return t;
    }
    public static ArrayList<Pair<String, HashMap<Integer,Tile>>> stripSol(ArrayList<Pair<String, HashMap<Integer, Tile>>> solutions){
        ArrayList<Pair<String, HashMap<Integer, Tile>>> result = new ArrayList<>();
        ArrayList<String> tempS = new ArrayList<>();
        HashMap<Integer, Tile> or = new HashMap<Integer, Tile>();
        for (int i = 0; i < solutions.size(); i++){ //creates an arraylist of all the possible solutions
            String temp = lowestBoardPerm(solutions.get(i).l);
            String beforeLowest = solutions.get(i).l;
            if (temp.equals(beforeLowest)) {
                HashMap<Integer, Tile> tempMap = solutions.get(i).r;
                for (int j = 0; j < temp.length();j++){
                    int key = Character.getNumericValue(temp.charAt(j));
                    or.put(key, tempMap.get(key));
                }
                result.add(new Pair(temp, or));
                tempS.add(printed_sols.get(i));
            }
        }
        printed_sols = tempS;
        return result;
    }
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        File file = new File("testcases/input04.txt");
        HashMap<Integer, Character> inversedCharToNum = new HashMap<>();
        ArrayList<Tile> inputTiles = readFile(file, inversedCharToNum);
        ArrayList<Tile> tilesForPrinting = inputTiles;
        ArrayList<Pair<String, HashMap<Integer, Tile>>> solutions = algo(inputTiles, inversedCharToNum);
        outputTiles(tilesForPrinting, stripSol(actual_solutions), inversedCharToNum);
        if (actual_solutions.size() != 0) {
            for (int i = 0; i < printed_sols.size(); i++) {
                System.out.println(printed_sols.get(i));
            }
        }
    }
}