/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BoggleSolver {
    // first 3 letters to word
    private HashMap<String, TrieSETemp> tree = new HashMap<>();
    private HashMap<String, Iterator<String>> visited = new HashMap<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String i : dictionary) {
            if (i.length() > 2) {
                String st = i.substring(0, 3);
                if (tree.get(st) == null) {
                    tree.put(st, new TrieSETemp());
                }
                tree.get(st).add(i);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        // for (TrieSETemp trie : tree.values()) {
        //     for (Iterator<String> it = trie.iterator(); it.hasNext(); ) {
        //         String str = it.next();
        //         System.out.println(str);
        //     }
        // }
        ArrayList<String> lis = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char start = board.getLetter(i, j);
                String word = start + "";
                // if (word.equals("Q")) word += 'U';
                boolean[][] revealed = new boolean[rows][];
                for (int ip = 0; ip < rows; ip++) revealed[ip] = new boolean[cols];
                revealed[i][j] = true;
                for (int t = i - 1; t < i + 2; t++) {
                    for (int r = j - 1; r < j + 2; r++) {
                        if (t != i || r != j) {
                            if (t < rows && t >= 0 && r < cols && r >= 0) {
                                String letter = board.getLetter(t, r) + "";
                                // if (letter.equals("Q")) letter += 'U';

                                dfs1(lis, board, word + letter, t, r,
                                     copyArr(revealed), rows, cols);
                            }
                        }
                    }
                }
            }


        }
        return lis;
    }

    // i=rows j=cols
    private String reveseString(String str) {
        String nstr = "";
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i); // extracts each character
            nstr = ch + nstr; // adds each character in front of the existing string
        }
        return nstr;
    }

    // This method copies a 2D array of any type T
    private static boolean[][] copyArr(boolean[][] original) {
        int rows = original.length;
        int cols = original[0].length;
        boolean[][] copy = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            // Using System.arraycopy for each row
            System.arraycopy(original[i], 0, copy[i], 0, cols);
        }

        return copy;
    }

    private void dfs2(ArrayList<String> lis, BoggleBoard board, String word, int i, int j,
                      boolean[][] revealed, int rows, int cols, TrieSETemp trie) {

        revealed[i][j] = true;
        String wordStr = word.replace("Q", "QU");
        if (visited.get(wordStr) == null)
            visited.put(wordStr, trie.keysWithPrefix(wordStr).iterator());
        if (visited.get(wordStr).hasNext()) {
            if (trie.contains(wordStr) && !lis.contains(wordStr))
                lis.add(wordStr);

            for (int k = i - 1; k < i + 2; k++) {
                for (int kj = j - 1; kj < j + 2; kj++) {
                    if (k != i || kj != j) {
                        if (k < rows && k >= 0 && kj < cols && kj >= 0) {
                            if (!revealed[k][kj]) {

                                String letter = board.getLetter(k, kj) + "";
                                // if (letter.equals("Q")) letter += 'U';
                                dfs2(lis, board, word + letter, k, kj, copyArr(revealed), rows,
                                     cols,
                                     trie);

                            }


                        }

                    }
                }
            }

        }
    }

    private void dfs1(ArrayList<String> lis, BoggleBoard board, String word, int i, int j,
                      boolean[][] revealed, int rows, int cols) {
        revealed[i][j] = true;
        String tempr = word.replace("Q", "QU");
        if (tempr.length() > 2) {
            String tempr1 = tempr.substring(0, 3);
            if (!lis.contains(tempr)
                    && tree.get(tempr1) != null && tree.get(tempr1).contains(tempr))
                lis.add(tempr);
        }
        if (word.length() < 3) {
            for (int ki = i - 1; ki < i + 2; ki++) {
                for (int kj = j - 1; kj < j + 2; kj++) {
                    if (ki != i || kj != j) {
                        if (ki < rows && ki >= 0 && kj < cols && kj >= 0) {
                            if (!revealed[ki][kj]) {
                                String letter = board.getLetter(ki, kj) + "";
                                String query = word + letter;
                                TrieSETemp temp = tree.get(
                                        query.replace("Q", "QU").substring(0, 3));
                                // if (letter.equals("Q")) letter += 'U';
                                if (temp != null) {
                                    dfs2(lis, board, query, ki, kj, copyArr(revealed), rows,
                                         cols, temp);
                                }
                            }
                        }
                    }
                }
            }

        }
        else {
            TrieSETemp temp = tree.get(tempr);
            if (temp != null) {
                dfs2(lis, board, word, i, j, copyArr(revealed), rows, cols, temp);
            }

        }
    }

    /*
    type send
       UNIT
    UNITE
    UNITED
    */
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word.length() > 2 && tree.get(word.substring(0, 3)) != null && tree.get(
                word.substring(0, 3)).contains(word)) {
            switch (word.length()) {
                case 3:
                    return 1;
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                default:
                    return 11;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        // for (int i = 0; i < 1000; i++) {
        BoggleBoard board = new BoggleBoard(args[1]);
        // FileWriter tp = new FileWriter("temp1.txt");
        int score = 0;
        int ctr = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
            ctr++;

            // tp.write(word + System.getProperty("line.separator"));
        }
        System.out.println(ctr);
        // System.out.println(solver.scoreOf("SNOW"));
        StdOut.println("Score = " + score);
        // tp.close();
    }
    // }
}
