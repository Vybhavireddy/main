public class BoyerMoore {
    // Function to create the bad character heuristic table
    static int[] badCharHeuristic(String pattern) {
        int[] badChar = new int[256]; // ASCII character size
        int m = pattern.length();

        // Initialize all occurrences as -1
        for (int i = 0; i < 256; i++)
            badChar[i] = -1;

        // Fill the actual value of the last occurrence of each character
        for (int i = 0; i < m; i++)
            badChar[pattern.charAt(i)] = i;

        return badChar;
    }

    // Function to perform Boyer-Moore pattern search
    static void search(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        int[] badChar = badCharHeuristic(pattern);

        int s = 0; // shift of the pattern with respect to text
        while (s <= (n - m)) {
            int j = m - 1;

            // Keep reducing index j while characters match
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
                j--;

            // If pattern is found
            if (j < 0) {
                System.out.println("Pattern found at index: " + s);

                // Shift the pattern so that the next character aligns
                s += (s + m < n) ? m - badChar[text.charAt(s + m)] : 1;
            } else {
                // Shift the pattern so that the bad character aligns
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
            }
        }
    }

    public static void main(String[] args) {
        String text = "ABAAABCD";
        String pattern = "ABC";

        search(text, pattern);
    }
}
