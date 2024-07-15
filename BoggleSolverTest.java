import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoggleSolverTest {
    ArrayList<String> setup(String dict, String boardStr) {
        In in = new In(dict);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        // for (int i = 0; i < 1000; i++) {
        BoggleBoard board = new BoggleBoard(boardStr);
        // FileWriter tp = new FileWriter("temp1.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);

            // tp.write(word + System.getProperty("line.separator"));
        }
        System.out.println(score);
        return (ArrayList<String>) solver.getAllValidWords(board);
        // tp.close();

    }

    @Test
    void getAllValidWords1() {

        ArrayList<String> dictionary = setup("dictionary-yawl.txt", "board-qwerty.txt");
        assertEquals(new HashSet<>(
                             Arrays.asList("RET", "NAT", "TAN", "ART", "TAR", "RAT", "REW", "REZ", "TAZZE",
                                           "WET", "TEW", "RAZZ", "TZAR", "TREZ", "AZERTY", "TREW", "RAZER",
                                           "ZANZE", "WERT", "NAZE", "RAZE", "RAN")),
                     new HashSet<>(dictionary));
    }

    @Test
    void getAllValidWords2() {

        ArrayList<String> dictionary = setup("dictionary-algs4.txt", "board-q.txt");
        HashSet<String> hash1 = new HashSet<>(
                Arrays.asList("TIES", "LETS", "SIN", "SINE", "SIT", "SITE", "SITS", "NET",
                              "REQUEST",
                              "TRIES", "TEN", "TENS", "ONE", "ITS", "EQUATION", "EQUATIONS",
                              "LET", "QUITE", "QUERIES", "QUESTION", "QUESTIONS", "TIN", "TIE",
                              "TAT", "REQUIRE", "RES", "REST", "SER", "STATE"));
        HashSet<String> hash1Cpy = new HashSet<>(hash1);
        HashSet<String> hash2 = new HashSet<>(dictionary);
        hash1.removeAll(hash2);
        System.out.println(hash1);
        assertEquals(hash1Cpy, hash2);
    }

}