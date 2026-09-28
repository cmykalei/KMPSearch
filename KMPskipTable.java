import java.util.*;

/**
 * KMPskipTable class.
 * Generates a KMP skip table in the format of a 2D array.
 * Has public member variables to access when the table is used for searching.
 *
 * @see     KMPsearch
 */
public class KMPskipTable {

    final int BYTES = 128;      /* Size of array for ASCII values */

    public char[] pattern;      /* The word pattern to generate a table */
    public char[] symbols;      /* The unique symbols within the word */
    public int[] lps;           /* Array for longest prefix suffix of word */
    public int[][] table;       /* The two-dimensional KMP skip table */

    public int m;               /* Length of the pattern from the word */
    public int k;               /* Dimension as the count of symbols */

    /**
     * KMPskipTable constsructor.
     * Initialises a KMP skip table for the specified pattern.
     *
     * @see     computeLPS(String pattern)
     * @param   word   The String pattern to generate a KMP skip table for.
     */
    public KMPskipTable(String word) {
        pattern = word.toCharArray();

        /* Generate set unique symbols from the pattern */
        symbols = KMPskipTable.setSymbols(pattern);
        k = symbols.length;

        /* Generate LPS array for the pattern */
        lps = KMPskipTable.computeLPS(pattern);
        m = pattern.length;

        /* Create two-dimensional table for direct to the char skip values */
        table = new int[BYTES][m];

        /* Loop through all the symbols in the pattern */
        for (int col = 0; col < m; col++) {
            table['*'][col] = col + 1; /* TODO: find better wildcard */

            /* Loop through the unique symbols in the pattern */
            for(int row = 0; row < k; row++) {
                char s = symbols[row];

                /* Set the skip values in the table */
                if (s == pattern[col]) {
                     /* Skip 0 for this column if occurence found */
                    table[s][col] = 0;
                } else {
                     /* Else try to find the longest prefix-suffix */
                    int j = 0;

                    /* Set LPS if past the first symbol */
                    if (col > 0) {
                        j = lps[col - 1];
                    }

                    /* Find the longest proper prefix */
                    while (j > 0 && pattern[j] != s) {
                        j = lps[j - 1];
                    }

                    /* Increase count when matched */
                    if (pattern[j] == s) {
                        j++;
                    }

                    /* Set the skip value into the table */
                    table[s][col] = (col + 1) - j;
                }
            }
        }
    }


    /**
     * Computes the longest prefix-suffix in the word with the specified chars.
     *
     * @param       w   The Array of chars in the word.
     * @return The integer LPS, longest prefix-suffix given chars.
     */
    public static int[] computeLPS(char[] w) {
        int[] lps = new int[w.length];

        int j = 0;      /* Length of prefix-suffix found */
        int i = 1;      /* Current index in the pattern */

        while (i < w.length) {
            /* If the next character matches the current */
            if (w[i] == w[j]) {
                /* Insert LPS value and move to the next symbol */
                lps[i] = j + 1;
                j++;
                i++;
            } else {
                /* Set LPS if any previous match was found */
                if (j != 0) {
                    j = lps[j];
                } else {
                    /* Else set to 0 and move to next character */
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    /**
     * Creates a set of possible symbols in a word, sorts alphabetically.
     *
     * @param   word    The pattern to find unique symbols in.
     * @return  The char Array of sorted unique symbols in a word.
     */
    public static char[] setSymbols(char[] word) {
        /* Create a Hash Set to insert only unique symbols */
        Set<Character> set = new HashSet<>();
        for (char character : word) {
            set.add(character);
        }

        /* Create an Array List to sort alphabetically */
        List<Character> list = new ArrayList<>(set);
        Collections.sort(list);
        list.add('\0');

        /* Fill the char Array with the symbols */
        char[] arr = new char[list.size()];
        int k = 0;
        while (k < list.size()) {
            arr[k] = list.get(k);
            k++;
        }

        return arr;
    }


    /**
     * Prints the KMP skip table format.
     */
    public void print() {
        /* The header row word */
        System.out.print("*");
        for (char p : pattern) {
            System.out.print("," + p);
        }
        System.out.println();

        /* The symbol rows with skip values in each column */
        for (int row = 0; row < k; row++) {
            char c = symbols[row];
            if (c == '\0') {
                c = '*';
            }
            System.out.print(c);

            /* Then get each skip value from its table cell */
            for (int col = 0; col < m; col++) {
                System.out.print("," + table[symbols[row]][col]); /* Actual char */
            }
            System.out.println();
        }
    }
}
