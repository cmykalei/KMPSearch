# KMP Search
Knuth-Morris-Pratt string search algorithm implementation in Java.

## Project 🌳
```
.
├── filename.txt
├── KMPsearch.java
└── KMPskipTable.java
```

### Usage
Run from the command line with a pattern and a text file:
```bash
java KMPsearch "pattern" filename.txt
```

**Changes:**

1. **Boundaries and edge cases** — Added checks for whole word matches. This isn't a part of the KMP search algorithm, but was added to prevent partial matches. Supported patterns:
    - `java KMPsearch "with this" FiftySentences.txt` — words with spaces
    - `java KMPsearch "quick " FiftySentences.txt` — whole word with trailing space
    - `java KMPsearch "answer" FiftySentences.txt` — no partial matches

    The method is `printIfMatch(int i, int j, String line)` in `KMPsearch.java`.

2. **Skip values in KMP skip array** — Changed from a 2D array indexed by row indices to direct character access (`table[char][col]`).

3. **Wildcard `*`** — Internally mapped to `\0` to allow `*` to be searched for literally. Output converts it back when printing:
    ```
    $ java KMPsearch "*-+"
    *,*,-,+
    *,0,1,2
    +,1,2,0
    -,1,0,3
    *,1,2,3
    ```

## Commands
1. To run the search use `java KMPsearch "pattern" filename.txt`.
