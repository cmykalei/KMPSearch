import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * KMPsearch class.
 * Has a KMPskipTable object which comprises a 2D array for table values.
 *
 * @see     KMPskipTable
 */
public class KMPsearch {

    /**
     * Entry point main.
     * Starts search or computation for KMP algorithm based on length of args.
     */
    public static void main(String[] args) {
        try {
            /* If one argument is provided then just compute the skip */
            if (args.length == 1) {
                KMPskipTable skip = new KMPskipTable(args[0]);
                skip.print();
            } else if (args.length == 2) {
                /* Otherwise, open the file and search for the word */
                try (
                    BufferedReader reader =
                        new BufferedReader(new FileReader(args[1]));
                ) {
                    /* Compute the skip table for the word */
                    String target = args[0];
                    KMPskipTable skip = new KMPskipTable(target);

                    /* Read through the file line by line */
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int i = 0; /* The current index in text */
                        int j = 0; /* The current index of pattern */

                        while (i < line.length()) {
                            char t = line.charAt(i);

                            /* If match is found for this character */
                            if (t == skip.pattern[j]) {
                                i++; /* Move forward in the line */
                                j++; /* Move forward in the pattern */

                                /* If at the end of the table */
                                if (j == skip.m) {
                                    //KMPsearch.printIfMatch(i, j, line);
                                    KMPsearch.printPartialMatch(target, line, (i + 1 - j));
                                    j = 0; /* Go back to start of pattern */
                                }
                            } else {
                                /* If there is an LPS then get the skip value */
                                if (j > 0) {
                                    i += skip.table[t][j];
                                    j = 0; /* Go back to start of pattern */
                                } else {
                                    i++; /* Else move forward in the text */
                                }
                            }
                        }
                    }
                    reader.close();
                }
            } else {
                throw new IllegalArgumentException("Incorrect length of args provided.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            System.out.println("Usage: java KMPsearch marmot FiftySentences.txt");
            System.out.println("Usage: java KMPsearch marmot");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            System.exit(1);
        }
    }


    /**
     * Prints the match with bash highlighting.
     * 
     * @param   target      The word to highlight in bold.
     * @param   line        The line which the word was found in.
     * @param   index       The index where the occurence was first found.
     */
    private static void printPartialMatch(String target, String line, int index) {
        System.out.println(index + " " + (line.replace(target, "\033[1m" + target + "\033[0m")));
    }


    /**
     * Prints a line if the word passes the boundary checks.
     *
     * @param       i       The current marker within the line.
     * @param       j       The current length of the assumed match.
     */
    private static void printIfMatch(int i, int j, String line) {
        /* If not at the start then check previous */
        boolean boundLeft = true;
        if ((i - j) > 0) {
            char prev = line.charAt(i - j - 1);
            char curr = line.charAt(i - j);
            if (prev != ' ' && curr != ' ') {
                boundLeft = false;
            }
        }

        /* If not at the end then check next */
        boolean boundRight = true;
        if (i < line.length()) {
            char next = line.charAt(i);
            char curr = line.charAt(i - 1);
            if (next != ' ' && curr != ' ') {
                boundRight = false;
            }
        }

        /* If whole word is matched then print result */
        if (boundLeft && boundRight) {
            System.out.println((i + 1 - j) + " " + line);
        }
    }

}
