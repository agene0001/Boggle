/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;

public class BoggleSolver {
    // first 3 letters to word
    private HashMap<String, TrieSetTemp> tree = new HashMap<>();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String i : dictionary) {
            if (i.length() > 2) {
                String st = i.substring(0, 3);
                if (tree.get(st) == null) {
                    tree.put(st, new TrieSetTemp());
                }
                tree.get(st).add(i);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int rows = board.rows();
        int cols = board.cols();
        ArrayList<String> lis = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char start = board.getLetter(i, j);
                String word = start + "";
                if (word.equals("Q")) word += 'U';
                boolean[][] revealed = new boolean[rows][];
                for (int ip = 0; ip < rows; ip++) revealed[ip] = new boolean[cols];
                revealed[i][j] = true;
                if (i + 1 < rows) {
                    if (j + 1 < cols) {
                        String letter = board.getLetter(i + 1, j + 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs1(lis, board, word + letter, i + 1, j + 1,
                             copyArr(revealed), rows, cols);
                    }
                    if (j - 1 >= 0) {
                        String letter = board.getLetter(i + 1, j - 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs1(lis, board, word + letter, i + 1, j - 1,
                             copyArr(revealed), rows, cols);

                    }
                    String letter = board.getLetter(i + 1, j) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs1(lis, board, word + letter, i + 1, j,
                         copyArr(revealed), rows, cols);

                }
                if (i - 1 >= 0) {
                    if (j + 1 < cols) {
                        String letter = board.getLetter(i - 1, j + 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs1(lis, board, word + letter, i - 1, j + 1,
                             copyArr(revealed), rows, cols);
                    }
                    if (j - 1 >= 0) {
                        String letter = board.getLetter(i - 1, j - 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs1(lis, board, word + letter, i - 1, j - 1,
                             copyArr(revealed), rows, cols);

                    }
                    String letter = board.getLetter(i - 1, j) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs1(lis, board, word + letter, i - 1, j,
                         copyArr(revealed), rows, cols);

                }
                if (j + 1 < cols) {
                    String letter = board.getLetter(i, j + 1) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs1(lis, board, word + letter, i, j + 1,
                         copyArr(revealed), rows, cols);

                }
                if (j - 1 >= 0) {
                    String letter = board.getLetter(i, j - 1) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs1(lis, board, word + letter, i, j - 1,
                         copyArr(revealed), rows, cols);

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
                      boolean[][] revealed, int rows, int cols, TrieSetTemp trie) {
        if (trie.keysWithPrefix(word).iterator().hasNext()) {
            if (trie.contains(word)) {
                if (word.length() > 2 && !lis.contains(word)) lis.add(word);
            }
            revealed[i][j] = true;
            if (i + 1 < rows) {
                if (j + 1 < cols) {
                    if (!revealed[i + 1][j + 1]) {
                        String letter = board.getLetter(i + 1, j + 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs2(lis, board, word + letter, i + 1, j + 1, copyArr(revealed), rows, cols,
                             trie);
                    }
                }
                if (j - 1 >= 0) {
                    if (!revealed[i + 1][j - 1]) {
                        String letter = board.getLetter(i + 1, j - 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs2(lis, board, word + letter, i + 1, j - 1,
                             copyArr(revealed), rows, cols, trie);
                    }
                }
                if (!revealed[i + 1][j]) {
                    String letter = board.getLetter(i + 1, j) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs2(lis, board, word + letter, i + 1, j, copyArr(revealed), rows, cols, trie);
                }
            }
            if (i - 1 >= 0) {
                if (j + 1 < cols) {
                    if (!revealed[i - 1][j + 1]) {

                        String letter = board.getLetter(i - 1, j + 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs2(lis, board, word + letter, i - 1, j + 1,
                             copyArr(revealed), rows, cols, trie);
                    }
                }
                if (j - 1 >= 0) {
                    if (!revealed[i - 1][j - 1]) {

                        String letter = board.getLetter(i - 1, j - 1) + "";
                        if (letter.equals("Q")) letter += 'U';
                        dfs2(lis, board, word + letter, i - 1, j - 1,
                             copyArr(revealed), rows, cols, trie);
                    }
                }
                if (!revealed[i - 1][j]) {

                    String letter = board.getLetter(i - 1, j) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs2(lis, board, word + letter, i - 1, j, copyArr(revealed), rows, cols, trie);
                }
            }
            if (j + 1 < cols) {
                if (!revealed[i][j + 1]) {

                    String letter = board.getLetter(i, j + 1) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs2(lis, board, word + letter, i, j + 1, copyArr(revealed), rows, cols, trie);
                }
            }
            if (j - 1 >= 0) {
                if (!revealed[i][j - 1]) {

                    String letter = board.getLetter(i, j - 1) + "";
                    if (letter.equals("Q")) letter += 'U';
                    dfs2(lis, board, word + letter, i, j - 1, copyArr(revealed), rows, cols, trie);
                }
            }

        }
    }

    private void dfs1(ArrayList<String> lis, BoggleBoard board, String word, int i, int j,
                      boolean[][] revealed, int rows, int cols) {
        revealed[i][j] = true;
        if (word.length() < 3) {
            if (i + 1 < rows) {
                if (j + 1 < cols) {
                    if (!revealed[i + 1][j + 1]) {
                        String letter = board.getLetter(i + 1, j + 1) + "";
                        TrieSetTemp temp = tree.get(word + letter);
                        if (letter.equals("Q")) letter += 'U';
                        if (temp != null) {
                            dfs2(lis, board, word + letter, i + 1, j + 1, copyArr(revealed), rows,
                                 cols, temp);
                        }
                    }
                }
                if (j - 1 >= 0) {
                    if (!revealed[i + 1][j - 1]) {
                        String letter = board.getLetter(i + 1, j - 1) + "";
                        TrieSetTemp temp = tree.get(word + letter);
                        if (letter.equals("Q")) letter += 'U';
                        if (temp != null) {
                            dfs2(lis, board, word + letter, i + 1, j - 1,
                                 copyArr(revealed), rows, cols, temp);
                        }
                    }
                }
                if (!revealed[i + 1][j]) {

                    String letter = board.getLetter(i + 1, j) + "";
                    TrieSetTemp temp = tree.get(word + letter);
                    if (letter.equals("Q")) letter += 'U';
                    if (temp != null) {
                        dfs2(lis, board, word + letter, i + 1, j, copyArr(revealed), rows, cols,
                             temp);
                    }
                }
            }
            if (i - 1 >= 0) {
                if (j + 1 < cols) {
                    if (!revealed[i - 1][j + 1]) {
                        String letter = board.getLetter(i - 1, j + 1) + "";
                        TrieSetTemp temp = tree.get(word + letter);
                        if (letter.equals("Q")) letter += 'U';
                        if (temp != null) {
                            dfs2(lis, board, word + letter, i - 1, j + 1,
                                 copyArr(revealed), rows, cols, temp);
                        }
                    }
                }
                if (j - 1 >= 0) {
                    if (!revealed[i - 1][j - 1]) {

                        String letter = board.getLetter(i - 1, j - 1) + "";
                        TrieSetTemp temp = tree.get(word + letter);
                        if (letter.equals("Q")) letter += 'U';
                        if (temp != null) {

                            dfs2(lis, board, word + letter, i - 1, j - 1,
                                 copyArr(revealed), rows, cols, temp);
                        }
                    }
                }
                if (!revealed[i - 1][j]) {

                    String letter = board.getLetter(i - 1, j) + "";
                    TrieSetTemp temp = tree.get(word + letter);
                    if (letter.equals("Q")) letter += 'U';
                    if (temp != null) {
                        dfs2(lis, board, word + letter, i - 1, j, copyArr(revealed), rows, cols,
                             temp);
                    }
                }
            }
            if (j + 1 < cols) {
                if (!revealed[i][j + 1]) {

                    String letter = board.getLetter(i, j + 1) + "";
                    TrieSetTemp temp = tree.get(word + letter);
                    if (letter.equals("Q")) letter += 'U';
                    if (temp != null) {
                        dfs2(lis, board, word + letter, i, j + 1, copyArr(revealed), rows, cols,
                             temp);
                    }
                }
            }
            if (j - 1 >= 0) {
                if (!revealed[i][j - 1]) {

                    String letter = board.getLetter(i, j - 1) + "";
                    TrieSetTemp temp = tree.get(word + letter);
                    if (letter.equals("Q")) letter += 'U';
                    if (temp != null) {
                        dfs2(lis, board, word + letter, i, j - 1, copyArr(revealed), rows, cols,
                             temp);
                    }
                }
            }
        }
        else {
            TrieSetTemp temp = tree.get(word.substring(0, 3));
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
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);

            // tp.write(word + System.getProperty("line.separator"));
        }
        System.out.println(solver.scoreOf("SNOW"));
        StdOut.println("Score = " + score);
        // tp.close();
    }
    // }
}
