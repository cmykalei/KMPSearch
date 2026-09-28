
## KMP Search

##### Changes

```
I aimed to address some major design flaws that resulted (very subtly) incorrect output. I'm unsure if the algorithm itself is written and implemented correctly in regards to complexity and formalities, but I can be sure that with the tests that I've done, it prints correct output now.


Changes:

1. Boundaries and edge cases
    I've added checks for whole word matches. This isn't a part of the KMP
    search algorithm, but I couldn't come up with a way to do this with
    the table only:

       Words with spaces in between:
            `$ java KMPsearch "with this" FiftySentences.txt`
        Output:
            27 the coyote hit the coyote with this mean coyote

        Whole word with spaces:
            `$ java KMPsearch "quick " FiftySentences.txt`
        Output:
            27 a marmot should chase the quick wolf
            5 the quick wolf hated this badger
            27 the badger is chasing the quick badger
            5 the quick mean coyote can chase the badger
            5 the quick badger hit the marmot
            31 the hedgehog is answering the quick coyote with the hedgehog

        No partial matches:
            `$ java KMPsearch "answer" FiftySentences.txt`
        Output: (don't worry, it's only for debugging)
            Not bound: a nutcracker is answering the badger with the friendly coyote (next='i')
            Not bound: the hedgehog is answering the hedgehog (next='i')
            Not bound: that wolf was answering the badger (next='i')
            Not bound: the badger is answering the coyote (next='i')
            Not bound: the marmot is answering a wolf (next='i')
            Not bound: the hedgehog is answering the quick coyote with the hedgehog (next='i')
            17 the coyote will answer this wolf

        This should also work for single characters. The method is called
        `printIfMatch(int i, int j, String line)` and it's a static method
        found under the main in KMPsearch.java


2. Skip values in KMP skip array:
    My original solution used the row indices (the amount of unique symbols)
    for each column, to get the LPS for each possible substring given by
    the row indices.

    This was in conjunction with a 2D array (e.g., table[row][cow]) that
    contained the skip table values for each [row][col]. This might have
    been okay during the search, but I felt that it was overcomplicating things.

    My final solution avoids this completely by directly accessing the skip
    value given the char (int) at the column. So table[row][col] is actually
    table[char][col].


3. Wildcard '*'
    I've changed the wildcard to '\0' to allow for '*' to be searched for.
    I just changed the char back to '*' when printing only.
    It does mean that output is a bit ambigious:
        $ java KMPsearch "*-+"
        *,*,-,+
        *,0,1,2
        +,1,2,0
        -,1,0,3
        *,1,2,3
    I didn't spend too long on how to handle this row since it will be
    the same each time for this assignment. I hope this wasn't too bold of an
    assumption to make!


Thanks!
- Kalei

```